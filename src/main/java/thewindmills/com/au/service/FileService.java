package thewindmills.com.au.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import thewindmills.com.au.model.Book;
import thewindmills.com.au.model.LocalItem;
import thewindmills.com.au.repository.LocalItemRepository;

@Singleton
public class FileService {

    private static final Logger LOG = Logger.getLogger(FileService.class);

    @ConfigProperty(name="FILE_PATH")
    private String filepath;


    @Inject
    private BookService bookService;

    @Inject
    private LocalItemRepository localItemRepository;


    @Transactional
    public void scan() {

        File mainFolder = new File(filepath);
        if (!mainFolder.isDirectory()) {
            LOG.error("Main Directory is a file not directory!");
            return;
        }

        PanacheQuery<LocalItem> query = localItemRepository.findAll();

        List<LocalItem> items = new ArrayList<>();

        items.addAll(query.list());


        List<File> mainFiles = Arrays.asList(mainFolder.listFiles());

        int removed = 0;

        for (LocalItem item : items) {
            File f = new File(item.getFolderName());
            if (!mainFiles.contains(f)) {
                localItemRepository.delete(item);
                removed++;
            }

        }

        LOG.info("Removed " + removed + " items!");

        int scanned = 0;
        for (File folder : mainFolder.listFiles()) {

            if (!folder.isDirectory()) {
                continue;
            }

            List<File> files = Arrays.asList(folder.listFiles());

            

            LocalItem localItem;
            try {
                localItem = identifyBook(folder, files);
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
                continue;
            }

            if (localItem == null) {
                continue;
            }
            scanned++;


        }

        LOG.info("Scanned " + scanned + " books!");

    }





    private LocalItem identifyBook(File folder, List<File> files) throws URISyntaxException, IOException, InterruptedException {

        if (files.size() == 0) {
            return null;
        }

        File file;

        if (files.size() > 1) {
            boolean valid = false;
            for (File f : files) {
                if (f.isFile() && f.getName().equals("main.pdf")) {
                    valid = true;
                    file = f;
                }
            }
            if (!valid) {
                return null;
            }
        }

        if (files.size() == 1) {
            file = files.get(0);
        } else {
            return null;
        }

        if (file.isDirectory() || !file.getName().endsWith(".pdf")) {
            return null;
        }

        Book book = null;
        boolean queried = false;

        if (folder.getName().contains("{") && folder.getName().contains("}")) {

            Pattern pattern = Pattern.compile("(?<=\\{).*(?=\\})");
            Matcher matcher = pattern.matcher(folder.getName());

            MatchResult match = matcher.results().findFirst().get();

            String idString = folder.getName().substring(match.start(), match.end());

            if (idString.contains("-")) {

                String[] split = idString.split("-");

                String loc = split[0];
                String id = split[1];

                if (loc.equals("google")) {
                    book = bookService.findByGoogleId(id);
                } else {
                    book = bookService.findByIsbn(id);
                }
                queried = true;
                

            }
        }

        if (!queried) {
            book = bookService.findFirstTitleLike(folder.getName());
        }

        if (book == null) {
            return null;
        }

        

        LocalItem out = new LocalItem();
        out.setBook(book);
        out.setFileName(file.getAbsolutePath());
        out.setFolderName(folder.getAbsolutePath());
        out.setSize(file.length());

        return localItemRepository.save(out);
    }



}

package thewindmills.com.au.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import thewindmills.com.au.model.Book;
import thewindmills.com.au.model.LocalItem;

@ApplicationScoped
public class LocalItemRepository implements PanacheRepository<LocalItem> {

    @Inject
    BookRepository bookRepository;


    public LocalItem findByFolderName(String folderName) {
        return find("folderName",folderName).firstResult();
    }

    public LocalItem save(LocalItem localItem) {

        if (localItem.id != null) {
            if (findById(localItem.id) != null) {
                localItem = findById(localItem.id);
            }
        }

        if (findByFolderName(localItem.getFolderName()) != null) {
            String fileName = localItem.getFileName();
            Book book = localItem.getBook();
            localItem = findByFolderName(localItem.getFolderName());
            localItem.setFileName(fileName);
            localItem.setBook(book);


        }

        

        localItem.setBook(bookRepository.save(localItem.getBook()));

        localItem.persist();

        return findById(localItem.id);

    }

}

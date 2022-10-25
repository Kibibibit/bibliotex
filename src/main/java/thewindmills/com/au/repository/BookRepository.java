package thewindmills.com.au.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import thewindmills.com.au.model.Book;
import thewindmills.com.au.model.IndustryIdentifier;
import thewindmills.com.au.utils.StringBuilder;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    private static final Logger LOG = Logger.getLogger(BookRepository.class);

    public Book findByIsbn(String isbn) {

        String type;

        if (isbn.length() == 10) {
            type = "ISBN_10";
        } else if (isbn.length() == 13) {
            type = "ISBN_13";
        } else {
            return null;
        }

        StringBuilder builder = new StringBuilder("select b from ")
        .concat("Book b, ")
        .concat("VolumeInfo v, ")
        .concat("IndustryIdentifier i ")
        .concat("where b.volumeInfo.id = i.volumeInfo.id")
        .concat(" and i.type = ?1 and ")
        .concat("i.identifier = ?2");

        return find(builder.build(),type,isbn).firstResult();

    }

    public Book findByTitle(String title) {
        return find("select b from Book b, VolumeInfo v where b.volumeInfo.id = v.id and v.title = ?1", title).firstResult();
    }

    public Book findFirstTitleLike(String title) {
        
        return find("select b from Book b, VolumeInfo v where b.volumeInfo.id = v.id and v.title like ?1",String.format("%%%s%%",title)).firstResult();
    }

    public Book findById(String id) {
        return find("id", id).firstResult();
    }

    @Transactional
    public Book save(Book book) {

        if (findById(book.getId()) != null) {
            book = findById(book.getId());
        }

        if (book.getVolumeInfo() != null) {
            
            if (book.getVolumeInfo().getIndustryIdentifiers().isEmpty()) {
                LOG.warn("Book has no identifiers!");
                return null;
            }

            if (book.getVolumeInfo().getImageLinks() != null) {
                book.getVolumeInfo().getImageLinks().persist();
            }
            
            book.getVolumeInfo().persist();
    
            for (IndustryIdentifier identifier : book.getVolumeInfo().getIndustryIdentifiers()) {
                identifier.setVolumeInfo(book.getVolumeInfo());
                identifier.persist();
            }

            book.getVolumeInfo().persist();
            

            
        } else {
            LOG.warn("No volume info for this book!");
            return null;
        }

        book.persist();

        return this.findById(book.getId());

    }

}

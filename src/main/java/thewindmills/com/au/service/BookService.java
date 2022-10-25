package thewindmills.com.au.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import thewindmills.com.au.model.Book;
import thewindmills.com.au.model.BookMessage;
import thewindmills.com.au.repository.BookRepository;

@Singleton
public class BookService {

    @Inject
    private BookRepository bookRepository;

    @Inject
    private HttpService httpService;

    private ObjectMapper mapper = new ObjectMapper();

    @Transactional
    private Book queury(String field, String value) {

        Book book;
        HttpResponse<String> response;
        try {
            response = httpService.search(String.format("%s:%s", field, value));
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        if (response.statusCode() == 200) {
            try {
                BookMessage message = mapper.readValue(response.body(), BookMessage.class);
                if (message.getTotalItems() > 0) {
                    book = message.getItems().get(0);
                    book = bookRepository.save(book);
                    return book;
                } else {
                    return null;
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Transactional
    public Book findFirstTitleLike(String title) {
        Book book = bookRepository.findFirstTitleLike(title);

        if (book == null) {
            book = queury("intitle", title);
        }

        return book;
    }

    @Transactional
    public Book findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);

        if (book == null) {
            book = queury("isbn", isbn);
        }

        return book;
    }

    @Transactional
    public Book findByGoogleId(String id) {

        Book book = bookRepository.findById(id);

        if (book == null) {

            HttpResponse<String> response;
            try {
                response = httpService.findByGoogleId(id);
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }

            if (response.statusCode() == 200) {
                try {
                    book = mapper.readValue(response.body(), Book.class);
                    book = bookRepository.save(book);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        }

        return book;

    }

}

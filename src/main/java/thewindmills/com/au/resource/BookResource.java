package thewindmills.com.au.resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;

import thewindmills.com.au.model.Book;
import thewindmills.com.au.model.BookMessage;
import thewindmills.com.au.repository.BookRepository;
import thewindmills.com.au.service.HttpService;

@Path("/api/v1/books")
public class BookResource {

    @Inject
    HttpService httpService;

    @Inject
    BookRepository bookRepository;

    @Transactional
    @GET
    @Path("/{query}")
    @Produces("application/json")
    public List<Book> queryApi(@PathParam("query") String query) throws IOException, InterruptedException, URISyntaxException {
        
        String json = httpService.search(query).body();

        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();

        BookMessage books = mapper.readValue(json,BookMessage.class);
        List<Book> out = new ArrayList<Book>();

        for (Book book : books.getItems()) {
            Book b = bookRepository.save(book);
            if (b != null) {
                out.add(b);
            }
        }
        return out;
    }

    @GET
    @Path("/id/{id}")
    @Produces("application/json")
    public Book getByGoogleId(@PathParam("id") String id) throws URISyntaxException, IOException, InterruptedException {
        
        Book b = bookRepository.findById(id);
        if (b == null) {
            HttpResponse<String> response = httpService.findByGoogleId(id);

            if (response.statusCode() == 200) {

                ObjectMapper mapper = new ObjectMapper();

                b = mapper.readValue(response.body(),Book.class);
                b = bookRepository.save(b);
                
            } else {
                System.err.println("No book with " + id);
                throw new NotFoundException("Could not find book");

            }
        }

        return b;

    }
}

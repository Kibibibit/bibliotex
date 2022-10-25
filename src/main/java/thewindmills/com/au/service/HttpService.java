package thewindmills.com.au.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.inject.Singleton;

import org.apache.http.client.utils.URIBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
public class HttpService {
    
    @ConfigProperty(name="BOOKS_KEY")
    private String key;

    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public HttpResponse<String> search(String query) throws IOException, InterruptedException, URISyntaxException {

        URIBuilder builder = new URIBuilder();
        URI uri = builder
        .setScheme("https")
        .setHost("www.googleapis.com")
        .setPath("/books/v1/volumes")
        .addParameter("q",query)
        .addParameter("key", key)
        .build();

        System.out.println(uri);

        HttpRequest request = HttpRequest
            .newBuilder()
            .GET()
            .uri(uri)
            .header("Accept","application/json")
            .build();
        
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
    }

    public HttpResponse<String> findByGoogleId(String id) throws URISyntaxException, IOException, InterruptedException {

        URIBuilder builder = new URIBuilder();
        URI uri = builder.setScheme("https").setHost("www.googleapis.com").setPath("/books/v1/volumes"+id).addParameter("key", key).build();

        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).header("Accept", "application/json").build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response;

    }

}

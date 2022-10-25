package thewindmills.com.au.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import thewindmills.com.au.service.FileService;

@Path("api/v1/scan")
public class ScanResource {
    
    @Inject
    FileService fileService;

    @GET
    public Response scan() {

        fileService.scan();

        return Response.ok().build();

    }

}

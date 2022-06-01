package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * test service
 */
@Path("test")
public class TestService {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }
    /**
     * restores the json-files
     * @return Response
     */
    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore() {
        try {
            java.nio.file.Path path = Paths.get(Config.getProperty("groupJSON"));
            String filename = path.getFileName().toString();
            String folder = path.getParent().toString();

            byte[] groupJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("groupJSON"));
            fileOutputStream.write(groupJSON);

            path = Paths.get(Config.getProperty("studentJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] studentJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("studentJSON"));
            fileOutputStream.write(studentJSON);

            path = Paths.get(Config.getProperty("teacherJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] teacherJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("teacherJSON"));
            fileOutputStream.write(teacherJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHandler.initLists();
        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}

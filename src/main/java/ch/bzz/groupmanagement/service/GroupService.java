package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Group;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("group")
public class GroupService {
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listStudents () {
        List<Group> groupList = DataHandler.getInstance().readAllGroups();
        Response response = Response
                .status(200)
                .entity(groupList)
                .build();
        return response;
    }

    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readStudent (@QueryParam("id") int id) {
        Group group = DataHandler.getInstance().readGroupByID(id);
        Response response = Response
                .status(200)
                .entity(group)
                .build();
        return response;
    }
}

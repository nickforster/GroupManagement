package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Group;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Path("group")
public class GroupService {
    /**
     * lists all groups
     * @return the value of the groupList
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listStudents () {
        List<Group> groupList = DataHandler.readAllGroups();
        Response response = Response
                .status(200)
                .entity(groupList)
                .build();
        return response;
    }

    /**
     * reads a group by its id
     * @param id
     * @return group by its id
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readStudent (@QueryParam("id") int id) {
        Group group = DataHandler.readGroupByID(id);
        int httpStatus = 200;
        if (group == null) {
            httpStatus = 404;
        }
        Response response = Response
                .status(httpStatus)
                .entity(group)
                .build();
        return response;
    }

    /**
     * deletes a group by its id
     * @param id
     * @return empty String
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteGroup(
            @QueryParam("id") int id
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteGroup(id)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * creates a group with the parameters given
     * @param title
     * @param description
     * @param graduationYear
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createGroup(
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("graduationYear") int graduationYear,
            @FormParam("teacherID") int teacherID


    ) {
        Group group = new Group();

        group.setId(DataHandler.getGroupId());
        group.setTitle(title);
        group.setDescription(description);
        group.setGraduationYear(graduationYear);
        group.setTeacherID(teacherID);

        DataHandler.insertGroup(group);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a group by its id with the parameters given
     * @param id
     * @param title
     * @param description
     * @param graduationYear
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @FormParam("id") int id,
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("graduationYear") int graduationYear

    ) {
        int httpStatus = 200;
        Group group = DataHandler.readGroupByID(id);

        if (group != null) {
            group.setTitle(title);
            group.setDescription(description);
            group.setGraduationYear(graduationYear);
            DataHandler.updateGroup();
        } else {
            httpStatus = 410;
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}

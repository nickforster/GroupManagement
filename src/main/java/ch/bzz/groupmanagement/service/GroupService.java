package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Group;
import ch.bzz.groupmanagement.model.Teacher;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("group")
public class GroupService {
    /**
     * lists all groups
     * @return the value of the groupList
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGroups () {
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
    public Response readGroup (@QueryParam("id") int id) {
        Group group = DataHandler.readGroupByID(id);
        int httpStatus = 200;
        if (group == null) {
            httpStatus = 410;
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
     * @param group with all parameters
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createGroup(
            @Valid @BeanParam Group group
    ) {
        int httpStatus = 200;
        Teacher teacher = DataHandler.readTeacherByID(group.getTeacherID());
        if (teacher != null) {
            group.setId(DataHandler.getGroupId());
            DataHandler.insertGroup(group);
        } else {
            httpStatus = 400;
        }


        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a group by its id with the parameters given
     * @param group the group with all parameters
     * @param id
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateGroup(
            @Valid @BeanParam Group group,
            @FormParam("id") int id
    ) {
        int httpStatus = 200;
        Group oldGroup = DataHandler.readGroupByID(id);
        Teacher teacher = DataHandler.readTeacherByID(group.getTeacherID());

        if (teacher == null) {
            httpStatus = 400;
        } else if (oldGroup != null) {
            oldGroup.setTitle(group.getTitle());
            oldGroup.setDescription(group.getDescription());
            oldGroup.setGraduationYear(group.getGraduationYear());
            oldGroup.setTeacherID(group.getTeacherID());
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

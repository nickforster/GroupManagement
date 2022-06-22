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
     * @param userRole
     * @return the value of the groupList
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGroups (
            @CookieParam("userRole") String userRole
    ) {
        List<Group> groupList = null;
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            groupList = DataHandler.readAllGroups();
        }
        return Response
                .status(httpStatus)
                .entity(groupList)
                .build();
    }

    /**
     * reads a group by its id
     * @param id
     * @param userRole
     * @return group by its id
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readGroup (
            @QueryParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        Group group = null;
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            group = DataHandler.readGroupByID(id);
            if (group == null) {
                httpStatus = 410;
            }
        }

        return Response
                .status(httpStatus)
                .entity(group)
                .build();
    }

    /**
     * deletes a group by its id
     * @param id
     * @param userRole
     * @return empty String
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteGroup(
            @QueryParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else if (!DataHandler.deleteGroup(id)){
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
     * @param userRole
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createGroup(
            @Valid @BeanParam Group group,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else {
            Teacher teacher = DataHandler.readTeacherByID(group.getTeacherID());
            if (teacher != null) {
                group.setId(DataHandler.getGroupId());
                DataHandler.insertGroup(group);
            } else {
                httpStatus = 400;
            }
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
     * @param userRole
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateGroup(
            @Valid @BeanParam Group group,
            @FormParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else {
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
        }


        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}

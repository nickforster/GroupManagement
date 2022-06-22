package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Group;
import ch.bzz.groupmanagement.model.Teacher;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("teacher")
public class TeacherService {
    /**
     * lists all teachers
     * @param userRole
     * @return the value of the teacherList
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTeachers (
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        List<Teacher> teacherList = null;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            teacherList = DataHandler.readAllTeachers();
        }

        return Response
                .status(httpStatus)
                .entity(teacherList)
                .build();
    }

    /**
     * reads a teacher by its id
     * @param id
     * @param userRole
     * @return teacher by its id
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readTeacher (
            @QueryParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        Teacher teacher = null;
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            teacher = DataHandler.readTeacherByID(id);
            if (teacher == null) {
                httpStatus = 410;
            }
        }


        return Response
                .status(httpStatus)
                .entity(teacher)
                .build();
    }

    /**
     * deletes a teacher by its id
     * @param id
     * @param userRole
     * @return empty String
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTeacher(
            @QueryParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else if (!DataHandler.deleteTeacher(id)) {
            httpStatus = 410;
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * creates a teacher with the parameters given
     * @param teacher with all parameters
     * @param userRole
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createTeacher(
            @Valid @BeanParam Teacher teacher,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else {
            teacher.setId(DataHandler.getTeacherId());
            DataHandler.insertTeacher(teacher);
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a group by its id with the parameters given
     * @param teacher with all parameters
     * @param id
     * @param userRole
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateTeacher(
            @Valid @BeanParam Teacher teacher,
            @FormParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else {
            Teacher oldTeacher = DataHandler.readTeacherByID(id);

            if (oldTeacher != null) {
                oldTeacher.setFirstName(teacher.getFirstName());
                oldTeacher.setLastName(teacher.getLastName());
                DataHandler.updateTeacher();
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

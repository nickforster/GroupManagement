package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Teacher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("teacher")
public class TeacherService {
    /**
     * lists all teachers
     * @return the value of the teacherList
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTeachers () {
        List<Teacher> teacherList = DataHandler.readAllTeachers();
        Response response = Response
                .status(200)
                .entity(teacherList)
                .build();
        return response;
    }

    /**
     * reads a teacher by its id
     * @param id
     * @return teacher by its id
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readTeacher (@QueryParam("id") int id) {
        Teacher teacher = DataHandler.readTeacherByID(id);
        int httpStatus = 200;
        if (teacher == null) {
            httpStatus = 404;
        }
        Response response = Response
                .status(httpStatus)
                .entity(teacher)
                .build();
        return response;
    }

    /**
     * deletes a teacher by its id
     * @param id
     * @return empty String
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTeacher(
            @QueryParam("id") int id
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteTeacher(id)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * creates a teacher with the parameters given
     * @param firstName
     * @param lastName
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createTeacher(
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName
    ) {
        Teacher teacher = new Teacher();

        teacher.setId(DataHandler.getTeacherId());
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);

        DataHandler.insertTeacher(teacher);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a group by its id with the parameters given
     * @param id
     * @param firstName
     * @param lastName
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateTeacher(
            @FormParam("id") int id,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName

    ) {
        int httpStatus = 200;
        Teacher teacher = DataHandler.readTeacherByID(id);

        if (teacher != null) {
            teacher.setFirstName(firstName);
            teacher.setLastName(lastName);
            DataHandler.updateTeacher();
        } else {
            httpStatus = 410;
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}

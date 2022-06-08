package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
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
            httpStatus = 410;
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
     * @param teacher with all parameters
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createTeacher(
            @Valid @BeanParam Teacher teacher
    ) {
        teacher.setId(DataHandler.getTeacherId());
        DataHandler.insertTeacher(teacher);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a group by its id with the parameters given
     * @param teacher with all parameters
     * @param id
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateTeacher(
            @Valid @BeanParam Teacher teacher,
            @FormParam("id") int id
    ) {
        int httpStatus = 200;
        Teacher oldTeacher = DataHandler.readTeacherByID(id);

        if (oldTeacher != null) {
            oldTeacher.setFirstName(teacher.getFirstName());
            oldTeacher.setLastName(teacher.getLastName());
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

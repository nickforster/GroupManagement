package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Teacher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        List<Teacher> teacherList = DataHandler.getInstance().readAllTeachers();
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
        Teacher teacher = DataHandler.getInstance().readTeacherByID(id);
        Response response = Response
                .status(200)
                .entity(teacher)
                .build();
        return response;
    }
}

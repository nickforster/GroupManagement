package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("student")
public class StudentService {
    /**
     * lists all students
     * @return the value of the studentList
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listStudents () {
        List<Student> studentList = DataHandler.getInstance().readAllStudents();
        Response response = Response
                .status(200)
                .entity(studentList)
                .build();
        return response;
    }

    /**
     * reads a student by its id
     * @param id
     * @return student by its id
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readStudent (@QueryParam("id") int id) {
        Student student = DataHandler.getInstance().readStudentByID(id);
        Response response = Response
                .status(200)
                .entity(student)
                .build();
        return response;
    }
}
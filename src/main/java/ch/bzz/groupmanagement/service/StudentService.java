package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Group;
import ch.bzz.groupmanagement.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
        List<Student> studentList = DataHandler.readAllStudents();
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
        Student student = DataHandler.readStudentByID(id);
        int httpStatus = 200;
        if (student == null) {
            httpStatus = 404;
        }
        Response response = Response
                .status(httpStatus)
                .entity(student)
                .build();
        return response;
    }

    /**
     * deletes a student by its id
     * @param id
     * @return empty String
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteStudent(
            @QueryParam("id") int id
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteStudent(id)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * creates a student with the parameters given
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param phoneNumber
     * @param groupID
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createStudent(
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("birthDate") String birthDate,
            @FormParam("phoneNumber") String phoneNumber,
            @FormParam("groupID") int groupID
    ) {
        Student student = new Student();

        student.setId(DataHandler.getStudentId());
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setBirthDate(LocalDate.parse(birthDate));
        student.setPhoneNumber(phoneNumber);
        student.setGroupID(groupID);

        DataHandler.insertStudent(student);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a student by its id with the parameters given
     * @param id
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param phoneNumber
     * @param groupID
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @FormParam("id") int id,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("birthDate") String birthDate,
            @FormParam("phoneNumber") String phoneNumber,
            @FormParam("groupID") int groupID

    ) {
        int httpStatus = 200;
        Student student = DataHandler.readStudentByID(id);

        if (student != null) {
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setBirthDate(LocalDate.parse(birthDate));
            student.setPhoneNumber(phoneNumber);
            student.setGroupID(groupID);
            DataHandler.updateStudent();
        } else {
            httpStatus = 410;
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
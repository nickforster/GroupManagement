package ch.bzz.groupmanagement.service;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.model.Group;
import ch.bzz.groupmanagement.model.Student;
import ch.bzz.groupmanagement.util.BirthDate;

import javax.validation.Valid;
import javax.ws.rs.*;
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
            httpStatus = 410;
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
     * @param student with all parameters
     * @return empty String
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createStudent(
            @Valid @BeanParam Student student,
            @FormParam("birthDate") @BirthDate(value=1900) String birthDate
    ) {
        int httpStatus = 200;
        Group group = DataHandler.readGroupByID(student.getGroupID());

        if (group == null || !student.setBirthDate(birthDate)) {
            httpStatus = 400;
        } else {
            student.setId(DataHandler.getStudentId());
            student.setGroupID(student.getGroupID());
            student.setBirthDate(birthDate);
            DataHandler.insertStudent(student);
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a student by its id with the parameters given
     * @param student with all parameters
     * @param id the id of the student to edit
     * @return empty String
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateStudent(
            @Valid @BeanParam Student student,
            @FormParam("id") int id,
            @FormParam("birthDate") @BirthDate(value=1900) String birthDate
    ) {
        int httpStatus = 200;
        Student oldStudent = DataHandler.readStudentByID(id);
        Group group = DataHandler.readGroupByID(student.getGroupID());

        if (oldStudent == null) {
            httpStatus = 410;
        } else if (group == null || !oldStudent.setBirthDate(birthDate)){
            httpStatus = 400;
        } else {
            oldStudent.setFirstName(student.getFirstName());
            oldStudent.setLastName(student.getLastName());
            oldStudent.setBirthDate(student.getBirthDate());
            oldStudent.setPhoneNumber(student.getPhoneNumber());
            oldStudent.setGroupID(student.getGroupID());
            DataHandler.updateStudent();
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
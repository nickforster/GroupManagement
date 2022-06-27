package ch.bzz.groupmanagement.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * A teacher of a group
 */
public class Teacher {
    private int id;

    @FormParam("firstName")
    @Size(min=2, max=40)
    @NotEmpty
    private String firstName;

    @FormParam("lastName")
    @Size(min=2, max=40)
    @NotEmpty
    private String lastName;

    /**
     * gets the id from the teacher-object
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the teacher-object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the firstName from the teacher-object
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the firstName of the teacher-object
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the lastName from the teacher-object
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the lastName of the teacher-object
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

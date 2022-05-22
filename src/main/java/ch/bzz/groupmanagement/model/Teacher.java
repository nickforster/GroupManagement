package ch.bzz.groupmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * A teacher of a group
 */
public class Teacher {
    @JsonIgnore
    private List<Group> groups;
    private int id;
    private String firstName;
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

    /**
     * gets the list of Groups from the teacher-object
     * @return
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * sets the list of groups of the teacher-object
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}

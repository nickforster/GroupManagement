package ch.bzz.groupmanagement.model;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.util.LocalDateDeserializer;
import ch.bzz.groupmanagement.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

/**
 * A student in a group
 */
public class Student {
    @JsonIgnore
    private Group group;
    private int id;
    private String firstName;
    private String lastName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
    private String phoneNumber;

    /**
     * gets the id from the student-object
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the student-object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the firstName from the student-object
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the firstName of the student-object
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the lastName from the student-object
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the lastName of the student-object
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the birthDate from the student-object
     * @return
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * sets the birthDate of the student-object
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * gets the phoneNumber from the student-object
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets the phoneNumber of the student-object
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * gets the group from the student-object
     * @return
     */
    public Group getGroup() {
        return group;
    }

    /**
     * sets the group of the student-object
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * creates a Group-Object without the studentList
     * @param id the key
     */
    public void setGroupID(int id) {
        setGroup(new Group());
        Group group = DataHandler.readGroupByID(id);
        getGroup().setId(id);
        getGroup().setTitle(group.getTitle());
        getGroup().setDescription(group.getDescription());
        getGroup().setGraduationYear(group.getGraduationYear());
        getGroup().setTeacherID(group.getId());
    }
}

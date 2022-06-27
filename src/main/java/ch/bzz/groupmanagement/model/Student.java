package ch.bzz.groupmanagement.model;

import ch.bzz.groupmanagement.data.DataHandler;
import ch.bzz.groupmanagement.util.LocalDateDeserializer;
import ch.bzz.groupmanagement.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.time.LocalDate;

/**
 * A student in a group
 */
public class Student {
    private int id;

    @JsonIgnore
    private Group group;

    @FormParam("groupID")
    @Min(0)
    private int groupID;

    @FormParam("firstName")
    @NotEmpty
    @Size(min=2, max=40)
    private String firstName;

    @FormParam("lastName")
    @NotEmpty
    @Size(min=2, max=40)
    private String lastName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @FormParam("phoneNumber")
    @NotEmpty
    @Pattern(regexp = "(\\b(0041|0)|\\B\\+41)(\\s?\\(0\\))?(\\s)?[1-9]{2}(\\s)?[0-9]{3}(\\s)?[0-9]{2}(\\s)?[0-9]{2}\\b")
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
     * sets the birthDate of the student-object by String
     * @return true/false if is was successful or not
     */
    @JsonIgnore
    public boolean setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
            return true;
        } catch (Exception e) {
            return false;
        }
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
        groupID = id;
    }

    public int getGroupID() {
        return groupID;
    }
}

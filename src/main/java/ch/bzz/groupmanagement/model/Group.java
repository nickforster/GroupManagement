package ch.bzz.groupmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * A group in the group Management
 */
public class Group {
    @JsonIgnore
    private List<Student> students;
    @JsonIgnore
    private Teacher teacher;
    private int id;
    private String title;
    private String description;
    private int graduationYear;

    /**
     * gets the id from the group-object
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the group-object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the title from the group-object
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title of the group-object
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets the description from the group-object
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description of the group-object
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets the graduationYear from the group-object
     * @return
     */
    public int getGraduationYear() {
        return graduationYear;
    }

    /**
     * sets the graduationYear of the group-object
     */
    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    /**
     * gets the list of students from the group-object
     * @return
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * sets the student of the group-object
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * gets the teacher from the group-object
     * @return
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * sets the teacher of the group-object
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}

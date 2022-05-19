package ch.bzz.groupmanagement.data;

import ch.bzz.groupmanagement.model.Group;
import ch.bzz.groupmanagement.model.Student;
import ch.bzz.groupmanagement.model.Teacher;
import ch.bzz.groupmanagement.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Group> groupList;
    private List<Student> studentList;
    private List<Teacher> teacherList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setGroupList(new ArrayList<>());
        readGroupJSON();
        setStudentList(new ArrayList<>());
        readStudentJSON();
        setTeacherList(new ArrayList<>());
        readTeacherJSON();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }

    public List<Group> readAllGroups() {
        return getGroupList();
    }

    public Group readGroupByID(int id) {
        Group group = null;
        for (Group entry : getGroupList()) {
            if (entry.getId() == id) {
                group = entry;
            }
        }
        return group;
    }

    public List<Student> readAllStudents() {
        return getStudentList();
    }

    public Student readStudentByID(int id) {
        Student student = null;
        for (Student entry : getStudentList()) {
            if (entry.getId() == id) {
                student = entry;
            }
        }
        return student;
    }

    public List<Teacher> readAllTeachers() {
        return getTeacherList();
    }

    public Teacher readTeacherByID(int id) {
        Teacher teacher = null;
        for (Teacher entry : getTeacherList()) {
            if (entry.getId() == id) {
                teacher = entry;
            }
        }
        return teacher;
    }

    private void readGroupJSON() {
        try {
            String path = Config.getProperty("groupJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Group[] groups = objectMapper.readValue(jsonData, Group[].class);
            for (Group group : groups) {
                getGroupList().add(group);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readStudentJSON() {
        try {
            String path = Config.getProperty("studentJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Student[] students = objectMapper.readValue(jsonData, Student[].class);
            for (Student student : students) {
                getStudentList().add(student);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readTeacherJSON() {
        try {
            String path = Config.getProperty("teacherJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Teacher[] teachers = objectMapper.readValue(jsonData, Teacher[].class);
            for (Teacher teacher : teachers) {
                getTeacherList().add(teacher);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }
}
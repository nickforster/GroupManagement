package ch.bzz.groupmanagement.data;

import ch.bzz.groupmanagement.model.Group;
import ch.bzz.groupmanagement.model.Student;
import ch.bzz.groupmanagement.model.Teacher;
import ch.bzz.groupmanagement.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static List<Group> groupList;
    private static List<Student> studentList;
    private static List<Teacher> teacherList;
    private static int groupId = 0;
    private static int teacherId = 0;

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
     * reads all groups
     * @return list of groups
     */
    public static List<Group> readAllGroups() {
        return getGroupList();
    }

    /**
     * reads a group by its id
     * @param id
     * @return the group (null=not found)
     */
    public static Group readGroupByID(int id) {
        Group group = null;
        for (Group entry : getGroupList()) {
            if (entry.getId() == id) {
                group = entry;
            }
        }
        return group;
    }

    /**
     * reads all students
     * @return list of students
     */
    public static List<Student> readAllStudents() {
        return getStudentList();
    }

    /**
     * reads a student by its id
     * @param id
     * @return the student (null=not found)
     */
    public static Student readStudentByID(int id) {
        Student student = null;
        for (Student entry : getStudentList()) {
            if (entry.getId() == id) {
                student = entry;
            }
        }
        return student;
    }

    /**
     * reads all teachers
     * @return list of teachers
     */
    public static List<Teacher> readAllTeachers() {
        return getTeacherList();
    }

    /**
     * reads a teacher by its id
     * @param id
     * @return the teacher (null=not found)
     */
    public static Teacher readTeacherByID(int id) {
        Teacher teacher = null;
        for (Teacher entry : getTeacherList()) {
            if (entry.getId() == id) {
                teacher = entry;
            }
        }
        return teacher;
    }

    /**
     * reads the groups from the JSON-file
     */
    private static void readGroupJSON() {
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

    /**
     * reads the students from the JSON-file
     */
    private static void readStudentJSON() {
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

    /**
     * reads the teachers from the JSON-file
     */
    private static void readTeacherJSON() {
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

    /**
     * gets groupList
     * @return value of groupList
     */
    public static List<Group> getGroupList() {
        if (groupList == null) {
            setGroupList(new ArrayList<>());
            readGroupJSON();
        }

        return groupList;
    }

    /**
     * sets groupList
     * @param groupList the value to set
     */
    public static void setGroupList(List<Group> groupList) {
        DataHandler.groupList = groupList;
    }

    /**
     * gets studentList
     * @return value of studentList
     */
    public static List<Student> getStudentList() {
        if (studentList == null) {
            setStudentList(new ArrayList<>());
            readStudentJSON();
        }

        return studentList;
    }

    /**
     * sets studentList
     * @param studentList the value to set
     */
    public static void setStudentList(List<Student> studentList) {
        DataHandler.studentList = studentList;
    }

    /**
     * gets teacherList
     * @return value of teacherList
     */
    public static List<Teacher> getTeacherList() {
        if (teacherList == null) {
            setTeacherList(new ArrayList<>());
            readTeacherJSON();
        }

        return teacherList;
    }

    /**
     * sets teacherList
     * @param teacherList the value to set
     */
    public static void setTeacherList(List<Teacher> teacherList) {
        DataHandler.teacherList = teacherList;
    }

    /**
     * deletes a group by its id
     * @param id the key
     * @return success=true/false
     */
    public static boolean deleteGroup(int id) {
        Group group = readGroupByID(id);
        if (group != null) {
            getGroupList().remove(group);
            writeGroupJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * inserts a new group into the groupList
     * @param group the group to be saved
     */
    public static void insertGroup(Group group) {
        getGroupList().add(group);
        writeGroupJSON();
    }

    /**
     * updates a groupList
     */
    public static void updateGroup() {
        writeGroupJSON();
    }

    /**
     * writes the groupList to the JSON-file
     */
    private static void writeGroupJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("groupJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getGroupList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * return an id that isn't used already
     * @return
     */
    public static int getGroupId() {
       while (readGroupByID(groupId) != null) {
           groupId++;
       }
       return groupId;
    }

    /**
     * deletes a teacher by its id
     * @param id the key
     * @return success=true/false
     */
    public static boolean deleteTeacher(int id) {
        Teacher teacher = readTeacherByID(id);
        if (teacher != null) {
            getTeacherList().remove(teacher);
            writeTeacherJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * inserts a new teacher into the teacherList
     * @param teacher the teacher to be saved
     */
    public static void insertTeacher(Teacher teacher) {
        getTeacherList().add(teacher);
        writeTeacherJSON();
    }

    /**
     * updates a teacherList
     */
    public static void updateTeacher() {
        writeTeacherJSON();
    }

    private static void writeTeacherJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("teacherJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getTeacherList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * return an id that isn't used already
     * @return
     */
    public static int getTeacherId() {
        while (readTeacherByID(teacherId) != null) {
            teacherId++;
        }
        return teacherId;
    }
}
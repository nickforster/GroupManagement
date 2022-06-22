package ch.bzz.groupmanagement.model;

/**
 * An application user
 */
public class User {
    private int Id;
    private String username;
    private String password;
    private String role;

    /**
     * default constructor
     */
    public User() {
        setRole("guest");
    }

    /**
     * Gets the id of the user
     * @return id
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the id of the user
     * @param id
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Gets the username of the user
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role of the user
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }
}


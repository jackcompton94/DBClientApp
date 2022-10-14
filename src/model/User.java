package model;

import databaseAccess.accessUsers;
import javafx.scene.control.PasswordField;

import java.sql.Timestamp;
import java.util.Date;

public class User {

    private int userId;
    private String userName;
    private String password;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    public static String currentUser;

    public User(int userId, String userName, String password, Date createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    @Override
    public String toString() {   // overrides default Object Class behavior when added to a ComboBox
        return ("ID [" + getUserId() + "] " + getUserName());
    }

    public static boolean authUser(String user, String pass) {
        for (User u : accessUsers.getAllUsers()) {
            currentUser = user;
            if (user.equals(u.getUserName())) {
                if (pass.equals(u.getPassword())) {
                    System.out.println("User Authenticated: " + currentUser);
                    return true;
                }
            }
        }
        System.out.println("User Failed Authentication: " + currentUser);
        return false;
    }
}

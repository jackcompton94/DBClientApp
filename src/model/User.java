package model;

import databaseAccess.accessUsers;
import javafx.scene.control.PasswordField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

public class User {

    private final int userId;
    private final String userName;
    private final String password;
    private final Date createDate;
    private final String createdBy;
    private final Timestamp lastUpdated;
    private final String lastUpdatedBy;
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

    public static boolean authUser(String user, String pass) throws IOException {
        for (User u : accessUsers.getAllUsers()) {
            currentUser = user;
            if (user.equals(u.getUserName())) {
                if (pass.equals(u.getPassword())) {

                    // writes to login_activity if successful
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    PrintWriter writer = new PrintWriter(new FileWriter("login_activity.txt", true));
                    writer.write(LocalDateTime.now(UTC).format(dtf) + " | " + currentUser + " | success\n");
                    writer.close();
                    return true;
                }
            }
        }
        // writes to login_activity if invalid
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
        writer.write(LocalDateTime.now(UTC).format(dtf) + " | " + currentUser + " | invalid\n");
        writer.close();
        return false;
    }
}

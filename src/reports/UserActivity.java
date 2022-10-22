package reports;

import databaseAccess.accessUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.time.ZoneOffset.UTC;

/**
 * @Author Jack Compton
 */

public class UserActivity {

    public static ObservableList<UserActivity> userActivityRecords = FXCollections.observableArrayList();
    private String date;
    private String time;
    private String username;
    private String auth;

    public UserActivity(String date, String time, String username, String auth) {
        this.date = date;
        this.time = time;
        this.username = username;
        this.auth = auth;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public String getAuth() {
        return auth;
    }

    public static ObservableList<UserActivity> getUserActivityRecords() {
        return userActivityRecords;
    }

    /**
     *
     * once the username and password are checked against the User table, we create a "login_activity.txt" file to track
     * the timestamp and success/invalid authentication of the login
     *
     * after the line is created, we use the "login_activity.txt" file to input a UserActivity object within the application
     * that is later used in the "ViewUserActivity" report
     *
     * @param user
     * entered username, checked against User table in MySQL Server
     *
     * @param pass
     * entered user password, checked against User table in MySQL Server
     *
     * @return
     * boolean
     *
     * @throws IOException
     * Signals that an I/O exception of some sort has occurred.
     */
    public static boolean authUser(String user, String pass) throws IOException {
        for (User u : accessUsers.getAllUsers()) {
            User.currentUser = user;
            if (user.equals(u.getUserName())) {
                if (pass.equals(u.getPassword())) {

                    // writes to login_activity if successful
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    PrintWriter writer = new PrintWriter(new FileWriter("login_activity.txt", true));
                    writer.write(LocalDateTime.now(UTC).format(dtf) + " | " + User.currentUser + " | success\n");
                    writer.close();

                    // reads from login_activity then adds into user activity report


                    Scanner read = new Scanner(new File("login_activity.txt"));
                    while (read.hasNextLine()) {
                        try {
                            String date = read.next();
                            String time = read.next();
                            String x = read.next();
                            String username = read.next();
                            String y = read.next();
                            String  auth = read.next();
                            UserActivity record = new UserActivity(date, time, username, auth);
                            userActivityRecords.add(record);
                        } catch (NoSuchElementException e) { }
                    }
                    read.close();
                    return true;
                }
            }
        }
        // writes to login_activity if invalid
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
        writer.write(LocalDateTime.now(UTC).format(dtf) + " | " + User.currentUser + " | invalid\n");
        writer.close();

        // reads from login_activity then adds into user activity report

        Scanner read = new Scanner(new File("login_activity.txt"));
        while (read.hasNextLine()) {
            try {
                String date = read.next();
                String time = read.next();
                String x = read.next();
                String username = read.next();
                String y = read.next();
                String auth = read.next();
                UserActivity record = new UserActivity(date, time, username, auth);
                userActivityRecords.add(record);
            } catch (NoSuchElementException e) { }
        }
        read.close();
        return false;
    }
}

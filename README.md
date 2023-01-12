# Purpose:
This application is used to manage a customer and employee contact list by scheduling appointments with customers for the contacts at the company. 
Additionally, this application features 3 reports to organize information related to: 
- monthly appointments
- individual contact schedules
- user login attempt logs


# IDE/Java Module: 
IntelliJ IDEA Community 2021.1.3 x64
JDK version: JavaFX-SDK-17.0.1
JavaFX version: 11.0.2
 
 
$ MySQL Connector: 
mysql-connector-java-8.0.25


# Running the program:
At launch, the user is presented with a login screen. The username/password is matched against
a MySQL Server database for authentication. After login, three options are presented to the user including:
View Customers, View Appointments, and View Reports.


# Additional Report:
User Activity Report - this report accepts the 'login_activity.txt' file as input,
and creates an object for each login to track who is accessing the Scheduler application.

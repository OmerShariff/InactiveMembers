import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberInformationFromDatabase {

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://your_database_url";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        // Retrieve member information from the database
        List<String> memberInformation = retrieveMemberInformation();

        if (memberInformation != null && !memberInformation.isEmpty()) {
            // Print or use the retrieved member information
            System.out.println("Member Information:");
            for (String info : memberInformation) {
                System.out.println(info);
            }
        } else {
            System.out.println("Member visited within the last 30 days or no member found.");
        }
    }

    private static List<String> retrieveMemberInformation() {
        List<String> memberInformation = new ArrayList<>();

        // Database query to retrieve member information
        String sqlQuery = "SELECT first_name, last_name, phone_number, email, address, city, state, " +
                "membership_ID, membership_type, last_visit FROM members WHERE last_visit <= ?";

        try (
            // Establish a database connection
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            // Create a PreparedStatement with the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)
        ) {
            // Set the parameter for the query (current date - 30 days)
            Date currentDate = new Date();
            Date thirtyDaysAgo = new Date(currentDate.getTime() - (30L * 24 * 60 * 60 * 1000));
            preparedStatement.setDate(1, new java.sql.Date(thirtyDaysAgo.getTime()));

            // Execute the query and retrieve the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve member details from the result set
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String phoneNumber = resultSet.getString("phone_number");
                    String email = resultSet.getString("email");
                    String address = resultSet.getString("address");
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");
                    int membershipID = resultSet.getInt("membership_ID");
                    String membershipType = resultSet.getString("membership_type");
                    Date lastVisit = resultSet.getDate("last_visit");

                    // Add member information to the list
                    memberInformation.add("Name: " + firstName + " " + lastName);
                    memberInformation.add("Phone Number: " + phoneNumber);
                    memberInformation.add("Email: " + email);
                    memberInformation.add("Address: " + address + ", " + city + ", " + state);
                    memberInformation.add("Membership ID: " + membershipID);
                    memberInformation.add("Membership Type: " + membershipType);
                    memberInformation.add("Last Visit: " + formatDate(lastVisit));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberInformation;
    }

    // Helper method to format a date
    private static String formatDate(Date date) {
        if (date != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } else {
            return "N/A";
        }
    }
}

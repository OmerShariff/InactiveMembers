import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberInformation {

    public static void main(String[] args) {
        // Sample data
        String first_name = "John";
        String last_name = "Doe";
        String phone_number = "123-456-7890";
        String email = "john.doe@example.com";
        String address = "123 Main St";
        String city = "Anytown";
        String state = "CA";
        int membership_ID = 12345;
        String membership_type = "Gold";
        String last_visit_str = "2023-01-01"; // Sample last_visit date (YYYY-MM-DD)

        // Parse the last_visit date
        Date last_visit = parseDate(last_visit_str);

        // Check if last_visit is more than 30 days before the current date
        if (isMoreThan30DaysAgo(last_visit)) {
            // Format member information into viewable lists
            System.out.println("Member Information:");
            System.out.println("Name: " + first_name + " " + last_name);
            System.out.println("Phone Number: " + phone_number);
            System.out.println("Email: " + email);
            System.out.println("Address: " + address + ", " + city + ", " + state);
            System.out.println("Membership ID: " + membership_ID);
            System.out.println("Membership Type: " + membership_type);
            System.out.println("Last Visit: " + last_visit_str);
        } else {
            System.out.println("Member visited within the last 30 days.");
        }
    }

    // Helper method to parse a date string
    private static Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to check if a date is more than 30 days ago
    private static boolean isMoreThan30DaysAgo(Date date) {
        if (date == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long lastVisitMillis = date.getTime();
        long thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000; // 30 days in milliseconds
        return currentTimeMillis - lastVisitMillis > thirtyDaysInMillis;
    }
}

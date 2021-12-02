package helper;

import java.time.LocalDateTime;

public class StringConverter {

    /**
     * Extracts time from LocalDateTime instance and converts it into a String.
     *
     * @param dateTime The LocalDateTime to convert into a String.
     * @return A string representing the time in the format HH:MM:SS.
     */
    public static String timeToString(LocalDateTime dateTime) {
        return String.format("%02d:%02d:%02d", dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
    }

    /**
     * Extracts date from LocalDateTime instance and converts it into a String.
     *
     * @param dateTime The LocalDateTime to convert into a String.
     * @return A string representing the date in the format DD/MM/YYYY.
     */
    public static String dateToString(LocalDateTime dateTime) {
        return String.format("%02d/%02d/%04d", dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear());
    }
}

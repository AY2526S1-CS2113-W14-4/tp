package internity.core;

/**
 * Represents a simple calendar date consisting of a day, month and year.
 *
 * <p>
 * The {@code Date} class encapsulates a specific day in the calendar and provides
 * methods to access and modify the day, month, and year. It also implements
 * {@link Comparable} to allow chronological comparison between {@code Date} objects.
 * </p>
 *
 */
public class Date implements Comparable<Date> {
    private int day;
    private int month;
    private int year;

    /**
     * Constructs a {@code Date} object with the specified day, month, and year.
     *
     * <p>
     * The constructor initialises the date fields using the respective setter methods.
     * </p>
     *
     * @param day   the day of the month
     * @param month the month of the year
     * @param year  the year
     */
    public Date(int day, int month, int year) {
        setDay(day);
        setMonth(month);
        setYear(year);
    }

    /**
     * Returns the month of this date.
     *
     * @return the month (1-12)
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month of this date.
     *
     * @param month the month (1-12) to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Returns the day of this date.
     *
     * @return the day of the month
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the day of this date.
     *
     * @param day the day of the month to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Returns the year of this date.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of this date.
     *
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        }
        if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    /**
     * Returns a string representation of this date in the format "DD-MM-YYYY".
     *
     * <p>
     * Day and month are zero-padded to two digits, and year is padded to four digits.
     * For example, a date with day = 3, month = 4, year = 2025 will be
     * formatted as "03-04-2025".
     * </p>
     *
     * @return the formatted string representation of this date
     */
    @Override
    public String toString() {
        return String.format("%02d-%02d-%04d", day, month, year);
    }
}

package org.emarsys;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class DueDateHandler {

    private static final Logger logger = LogManager.getLogger();

    static final int DAY_START_HOUR = 9;
    static final int DAY_END_HOUR = 17;
    private static final Map<Integer, Integer> DAYS_PER_MONTH;

    static {
        DAYS_PER_MONTH = new HashMap<Integer, Integer>();
        DAYS_PER_MONTH.put(1, 31);
        DAYS_PER_MONTH.put(2, 28);
        DAYS_PER_MONTH.put(3, 31);
        DAYS_PER_MONTH.put(4, 30);
        DAYS_PER_MONTH.put(5, 31);
        DAYS_PER_MONTH.put(6, 30);
        DAYS_PER_MONTH.put(7, 31);
        DAYS_PER_MONTH.put(8, 31);
        DAYS_PER_MONTH.put(9, 30);
        DAYS_PER_MONTH.put(10, 31);
        DAYS_PER_MONTH.put(11, 30);
        DAYS_PER_MONTH.put(12, 31);
    }

    int submitYear;
    int submitMonth;
    int submitDay;
    int submitHour;
    int turnAroundHours;

    public DueDateHandler(int submitYear, int submitMonth, int submitDay, int submitHour, int turnAroundHours) {
        if (submitYear > 0) {
            this.submitYear = submitYear;
        } else {
            throw new IllegalArgumentException("SubmitYear parameter must not be less than 1!");
        }

        if (submitMonth < 13 && submitMonth > 0) {
            this.submitMonth = submitMonth;
        } else {
            throw new IllegalArgumentException("SubmitMonth parameter must be from 1-12");
        }

        if (submitDay > 1 && submitDay < 32) {
            this.submitDay = submitDay;
        } else {
            throw new IllegalArgumentException("SubmitDay parameter must be from 1-31");
        }

        if (submitHour < DAY_END_HOUR && submitHour > DAY_START_HOUR) {
            this.submitHour = submitHour;
        } else {
            throw new IllegalArgumentException("SubmitHour parameter must be from 9-17");
        }

        if (turnAroundHours > 0) {
            this.turnAroundHours = turnAroundHours;
        } else {
            throw new IllegalArgumentException("TurnAroundHours parameter must not be less than 1!");
        }
    }

    private String countEndDate(int startMonth, int startDay, int startYear, int daysToAdd) {
        int numberOfDaysTemp = DAYS_PER_MONTH.get(startMonth);

        // For finding leap year.
        if (startMonth == 2 && startYear % 4 == 0 && startYear % 100 != 0 && startYear % 400 != 0) {
            numberOfDaysTemp = DAYS_PER_MONTH.get(startMonth) + 1;
        }

        // Calculate the updated day.
        int updatedDay = startDay + daysToAdd;

        // If the updated day is greater than the number of days in the start month,
        // carry over the extra days to the next month.
        while (updatedDay > numberOfDaysTemp) {
            updatedDay -= numberOfDaysTemp;
            startMonth++;

            // If the start month is greater than 12, carry over the extra months to the next year.
            if (startMonth > 12) {
                startMonth = 1;
                startYear++;
            }
        }
        return (startMonth + "/" + updatedDay + "/" + startYear);
    }

    // Implementation of the Zeller's congruence algorithm to calculate the day of the week for any Gregorian calendar date.
    private int findSubmitDayOfWeek(int day, int month, int year) {
        if (month == 1) {
            month = 13;
            year--;
        }
        if (month == 2) {
            month = 14;
            year--;
        }
        int k = year % 100;
        int j = year / 100;
        int h = day + 13 * (month + 1) / 5 + k + k / 4 + j / 4 + 5 * j;

        logger.info("SubmitDay index " + h % 7 + " counted by the Zeller's method");

        return h % 7;
    }

    public String calculateDueDate() {
        int extraDays = 0;
        int extraHours;

        if (turnAroundHours > DAY_END_HOUR - submitHour) {
            extraDays++;
            extraDays += (turnAroundHours - (DAY_END_HOUR - submitHour)) / 8;
            extraHours = (turnAroundHours - (DAY_END_HOUR - submitHour)) % 8;
        } else {
            return ("Task completion DATE: " + submitMonth + "/" + submitDay + "/" + submitYear)
                    + " HOUR: " + (submitHour + turnAroundHours);
        }

        int dayOfWeek = findSubmitDayOfWeek(submitDay, submitMonth, submitYear);
        int daysToAdd = countExtraWeekDays(extraDays, dayOfWeek);

        return ("Task completion DATE: " + countEndDate(submitMonth, submitDay, submitYear, daysToAdd)
                + " HOUR: " + (DAY_START_HOUR + extraHours));
    }

    private List<String> generateWeekdaysList(int startIndex, int n) {
        List<String> weekdays = Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

        List<String> generatedWeekdays = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int j = 0;
            while (j < weekdays.size()) {
                generatedWeekdays.add(weekdays.get((startIndex + j) % weekdays.size()));
                j++;
            }
        }
        return generatedWeekdays;
    }

    private int countExtraWeekDays(int extraWorkingDays, int startDay) {
        int countWeekDays = 0;
        int extraWeekDays = 0;
        List<String> generatedWeekdays = generateWeekdaysList(startDay, extraWorkingDays / 7 + 2);

        for (int i = 1; i < generatedWeekdays.size() - 1; i++) {
            String actualDay = generatedWeekdays.get(i);
            if (countWeekDays == extraWorkingDays) {
                break;
            }
            if (!actualDay.equals("Saturday") && !actualDay.equals("Sunday")) {
                countWeekDays++;
            }
            extraWeekDays = i;
        }
        logger.info(extraWorkingDays +" plus workingdays occurs in the upcoming " + extraWeekDays + " calendar days");

        return extraWeekDays;
    }
}
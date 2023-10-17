package org.emarsys;
// Java Program to Generate Desired Calendar
// Without calendar.get() function or
// Inputting the Year and the Month

// Importing required classes
import java.util.*;

// Main class
public class DueDateHandler {

    int submitYear;
    int submitMonth;
    int submitDay;
    int submitHour;
    int turnAroundHours;
    static int dayStartHour = 9;
    static int dayEndHour = 17;
    private static final Map<Integer, Integer> daysPerMonth;

    static {
        daysPerMonth = new HashMap<Integer,Integer>();
        daysPerMonth.put(1,31);
        daysPerMonth.put(2,28);
        daysPerMonth.put(3,31);
        daysPerMonth.put(4,30);
        daysPerMonth.put(5,31);
        daysPerMonth.put(6,30);
        daysPerMonth.put(7,31);
        daysPerMonth.put(8,31);
        daysPerMonth.put(9,30);
        daysPerMonth.put(10,31);
        daysPerMonth.put(11,30);
        daysPerMonth.put(12,31);
    }

    public DueDateHandler(int submitYear, int submitMonth, int submitDay, int submitHour, int turnAroundHours) {
        this.submitYear = submitYear;
        this.submitMonth = submitMonth;
        this.submitDay = submitDay;
        this.submitHour = submitHour;
        this.turnAroundHours = turnAroundHours;
    }

    public String countEndDate(int startMonth, int startDay, int startYear, int daysToAdd) {
        int numberOfDaysTemp = daysPerMonth.get(startMonth);

        // For finding leap year.
        if (startMonth == 2 && startYear % 4 == 0 && startYear % 100 != 0 && startYear % 400 != 0) {
            numberOfDaysTemp = daysPerMonth.get(startMonth) + 1;
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

        // Print the updated date.
        return (startMonth + "/" + updatedDay + "/" + startYear);
    }

    // Implementation fo the Zeller’s congruence is an algorithm to calculate the day of the week for any Gregorian calendar date.
    public int findSubmitDayOfWeek(int day, int month, int year)
    {
        if (month == 1)
        {
            month = 13;
            year--;
        }
        if (month == 2)
        {
            month = 14;
            year--;
        }
        int q = day;
        int m = month;
        int k = year % 100;
        int j = year / 100;
        int h = q + 13*(m + 1) / 5 + k + k / 4 + j / 4 + 5 * j;
        int dayOfTheWeek = h % 7;
        //System.out.println("Zellers result is: "+h );
        return dayOfTheWeek;
    }
    public String calculateDueDate(){
        int extraDays = 0;
        int extraHours = 0;

        if (turnAroundHours > dayEndHour-submitHour){
            extraDays ++;
            extraDays += (turnAroundHours-(dayEndHour-submitHour))/8;
            extraHours = (turnAroundHours-(dayEndHour-submitHour))%8;
        }
        else {
            extraHours = turnAroundHours;
        }

        int dayOfWeek = findSubmitDayOfWeek(submitDay,submitMonth, submitYear);
        //System.out.println("Extradays to add: " + extraDays);
        int daysToAdd = countExtraWeekDays(extraDays, dayOfWeek, extraHours);

        return ("Date: "+ countEndDate(submitMonth, submitDay, submitYear, daysToAdd) +" Hour: " + (dayStartHour + extraHours));
    }

    public List<String> generateWeekdays(int startIndex, int n) {
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

    public int countExtraWeekDays(int extraWorkingDays, int startDay, int extraHours){
        int countWeekDays = 0;
        int extraWeekDays = 0;
        List<String> generatedWeekdays = generateWeekdays(startDay,extraWorkingDays/7+2);

        for (int i = 1; i < generatedWeekdays.size()-1; i++) {
            String actualDay = generatedWeekdays.get(i);
            if (countWeekDays==extraWorkingDays){
                break;
            }
            if (!actualDay.equals("Saturday") && !actualDay.equals("Sunday")){
                countWeekDays++;
            }
            extraWeekDays = i;
        }
        return extraWeekDays;
    }
}
package org.emarsys;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        // Reading input by creating object of Scanner class
        Scanner sc = new Scanner(System.in);

        // Display message only
        System.out.print("Enter the submit Year in a format like '1986' : ");

        // Reading String input value

        int submitYear = sc.nextInt();
        //int submitYear = 2023;

        // Display message only
        System.out.print("Enter the submit Month in a format like '9': ");

        // Reading String input value
        int submitMonth = sc.nextInt();
        //int submitMonth = 10;

        // Display message only
        System.out.print("Enter the submit Day in a format like '4': ");

        // Reading String input value
        int submitDay = sc.nextInt();
        //int submitDay = 20;

        System.out.print("Enter the submit working hour in a format like '15' between 9-17: ");

        // Reading String input value
        int submitHour = sc.nextInt();
        //int submitHour = 10;

        System.out.print("Enter the turnaround time in hours in a format like '75': ");

        // Reading String input value
        int turnAroundHours = sc.nextInt();
        //int turnAroundHours = 3;

        System.out.println(new DueDateHandler(submitYear,submitMonth,submitDay,submitHour,turnAroundHours).calculateDueDate());
        //System.out.println(new DueDateHandler(submitYear,submitMonth,submitDay,submitHour,turnAroundHours).countExtraWeekDays(2,6,2));
    }
}
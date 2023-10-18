package org.emarsys;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try{
            // Reading input by creating object of Scanner class
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the SUBMIT YEAR in a simple number format: ");
            int submitYear = sc.nextInt();

            System.out.print("Enter the SUBMIT MONTH in a simple number format: ");
            int submitMonth = sc.nextInt();

            System.out.print("Enter the SUBMIT DAY in a simple number format: ");
            int submitDay = sc.nextInt();

            System.out.print("Enter the SUBMIT WORKING HOUR in a simple number format between 9-17: ");
            int submitHour = sc.nextInt();

            System.out.print("Enter the TURNAROUND TIME in hours in a simple number format: ");
            int turnAroundHours = sc.nextInt();

            System.out.println(new DueDateHandler(submitYear,submitMonth,submitDay,submitHour,turnAroundHours).calculateDueDate());

        } catch (InputMismatchException e){
            System.out.println("Must enter the value in number format, no other characters allowed!");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
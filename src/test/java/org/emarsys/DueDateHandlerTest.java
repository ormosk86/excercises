package org.emarsys;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DueDateHandlerTest {

    @Test
    void testCalculateDueDate() {
        String result = new DueDateHandler(2023,10,20,16,8).calculateDueDate();
        assertEquals("Task completion DATE: 10/23/2023 HOUR: 16", result);
    }
    @Test
    void testCalculateDueDateLeapYear() {
        String result = new DueDateHandler(2024,2,28,13,9).calculateDueDate();
        assertEquals("Task completion DATE: 2/29/2024 HOUR: 14", result);
    }
    @Test
    void testCalculateDueDateNotLeapYear() {
        String result2 = new DueDateHandler(2023,2,28,13,9).calculateDueDate();
        assertEquals("Task completion DATE: 3/1/2023 HOUR: 14", result2);
    }
    @Test
    void testCalculateDueDateTurnaroundSameDay() {
        String result = new DueDateHandler(2022,10,12,10,3).calculateDueDate();
        assertEquals("Task completion DATE: 10/12/2022 HOUR: 13", result);
    }
    @Test
    void testCalculateDueDateNewYear() {
        String result = new DueDateHandler(2023, 12, 29, 14, 15).calculateDueDate();
        assertEquals("Task completion DATE: 1/2/2024 HOUR: 13", result);
    }
    @Test
    void testCalculateDueDateEndOfTheYear() {
        String result = new DueDateHandler(2023, 12, 28, 14, 9).calculateDueDate();
        assertEquals("Task completion DATE: 12/29/2023 HOUR: 15", result);
    }
    @Test
    void testFindSubmitDayOfWeekFriday() {
        int result = new DueDateHandler(2033, 11, 25, 14, 9).findSubmitDayOfWeek(25,11,2033);
        assertEquals(6, result);
    }
    @Test
    void testFindSubmitDayOfWeekMonday() {
            int result = new DueDateHandler(2023, 10, 23, 10, 5).findSubmitDayOfWeek(23,10,2023);
        assertEquals(2, result);
    }
    @Test
    void testFindSubmitDayOfWeekThursdayLeapYear() {
        int result = new DueDateHandler(2024, 2, 28, 10, 5).findSubmitDayOfWeek(29,2,2024);
        assertEquals(5, result);
    }
    @Test
    void testFindSubmitDayOfWeekFridayLeapYear() {
        int result = new DueDateHandler(2024, 3, 1, 10, 5).findSubmitDayOfWeek(1,3,2024);
        assertEquals(6, result);
    }
    @Test
    void testCalculateDueDate31Month() {
        String result = new DueDateHandler(2023, 8, 30, 10, 8).calculateDueDate();
        assertEquals("Task completion DATE: 8/31/2023 HOUR: 10", result);
    }
    @Test
    void testCalculateDueDate30Month() {
        String result = new DueDateHandler(2023, 11, 30, 10, 9).calculateDueDate();
        assertEquals("Task completion DATE: 12/1/2023 HOUR: 11", result);
    }
    //Exceptions
    @Test
    void testNegativeSubmitYearMustThrowException() {
        assertThrows(IllegalArgumentException.class,() -> new DueDateHandler(-203, 11, 30, 10, 9));
    }
    @Test
    void testLowSubmitMonthMustThrowException() {
        assertThrows(IllegalArgumentException.class,() -> new DueDateHandler(2029, 0, 5, 10, 9));
    }
    @Test
    void testHighSubmitMonthMustThrowException() {
        assertThrows(IllegalArgumentException.class,() -> new DueDateHandler(2029, 13, 5, 10, 9));
    }
    @Test
    void testLowSubmitDayMustThrowException() {
        assertThrows(IllegalArgumentException.class,() -> new DueDateHandler(2020, 10, 0, 10, 9));
    }
    @Test
    void testHighSubmitDayMustThrowException() {
        assertThrows(IllegalArgumentException.class,() -> new DueDateHandler(2020, 10, 32, 10, 9));
    }
    @Test
    void testWrongSubmitHourMustThrowException() {
        assertThrows(IllegalArgumentException.class,() -> new DueDateHandler(2020, 8, 5, 21, 9));
    }
    @Test
    void testNegativeSubmitHourMustThrowException() {
        assertThrows(IllegalArgumentException.class,() -> new DueDateHandler(2026, 11, 30, 10, -9));
    }
}
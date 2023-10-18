package org.emarsys;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DueDateHandlerTest {

    @Test
    void testCalculateDueDate() {
        String result = new DueDateHandler(2023,10,20,16,8).calculateDueDate();
        assertEquals("Date: 10/23/2023 Hour: 16", result);
    }
    @Test
    void testCalculateDueDateLeapYear() {
        String result = new DueDateHandler(2024,2,28,13,9).calculateDueDate();
        assertEquals("Date: 2/29/2024 Hour: 14", result);
    }
    @Test
    void testCalculateDueDateNotLeapYear() {
        String result2 = new DueDateHandler(2023,2,28,13,9).calculateDueDate();
        assertEquals("Date: 3/1/2023 Hour: 14", result2);
    }
    @Test
    void testCalculateDueDateTurnaroundSameDay() {
        String result = new DueDateHandler(2022,10,12,10,3).calculateDueDate();
        assertEquals("Date: 10/12/2022 Hour: 13", result);
    }
    //Edge cases
    @Test
    void testCalculateDueDateNewYear() {
        String result = new DueDateHandler(2023, 12, 29, 14, 15).calculateDueDate();
        assertEquals("Date: 1/2/2024 Hour: 13", result);
    }

    @Test
    void testCalculateDueDateEndOfTheYear() {
        String result = new DueDateHandler(2023, 12, 28, 14, 9).calculateDueDate();
        assertEquals("Date: 12/29/2023 Hour: 15", result);
    }

    @Test
    void testCalculateDueDate31Month() {
        String result = new DueDateHandler(2023, 8, 30, 10, 8).calculateDueDate();
        assertEquals("Date: 8/31/2023 Hour: 10", result);
    }

    @Test
    void testCalculateDueDate30Month() {
        String result = new DueDateHandler(2023, 11, 30, 10, 9).calculateDueDate();
        assertEquals("Date: 12/1/2023 Hour: 11", result);
    }
    //Exceptions
    @Test
    void testNegativeSubmitYearMustThrowException() {
        assertThrows(IllegalArgumentException.class,() ->{
            var dueDateHandler = new DueDateHandler(-203, 11, 30, 10, 9);
        });
    }
    @Test
    void testWrongSubmitMonthMustThrowException() {
        assertThrows(IllegalArgumentException.class,() ->{
            var dueDateHandler = new DueDateHandler(2020, 16, 30, 10, 9);
        });
    }
}
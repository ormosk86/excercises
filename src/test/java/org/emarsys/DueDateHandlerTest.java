package org.emarsys;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DueDateHandlerTest extends TestCase {

    @Test
    public void testGetMyString() {
        String result = new DueDateHandler(2023,10,20,16,8).calculateDueDate();
        assertEquals("Date: 10/23/2023 Hour: 16", result);

    }
}
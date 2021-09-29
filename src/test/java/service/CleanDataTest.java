package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleanDataTest {

    @Test
    void cleanFromUnwantedSymbol() {
        StringBuilder notCleanedString = new StringBuilder("<div><p>!&hello -  &mdash;&laquo;</p></div>");

        final String expectedString = "hello";

        StringBuilder actualString = CleanData.cleanFromUnwantedSymbol(notCleanedString);

        assertEquals(expectedString, actualString.toString());
    }
}
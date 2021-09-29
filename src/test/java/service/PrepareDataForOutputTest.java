package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrepareDataForOutputTest {

    @Test
    void makeArrayListFromAllWords() {
        String notPreparedString = "Give me me to to the map";

        String expectedString = "INSERT INTO public.result VALUES\n" +
                                "(1, 'me', 2),\n" +
                                "(2, 'to', 2),\n" +
                                "(3, 'Give', 1),\n" +
                                "(4, 'map', 1),\n" +
                                "(5, 'the', 1);";

        String actualString = PrepareDataForOutput.makeMapFromAllWords(notPreparedString);

        assertEquals(expectedString, actualString);
    }
}
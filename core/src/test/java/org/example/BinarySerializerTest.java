package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySerializerTest {

    final String TEST_DIRECTORY = "../core/src/test/resources/test.bin";
    Beverage testSoda;
    List<Beverage> testList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        testSoda = new Soda("Test", 1.99, 100, 52);
        testList.add(testSoda);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get(TEST_DIRECTORY);
        Files.deleteIfExists(path);
    }

    @Test
    void serializeToAndFromBinary() throws IOException {
        BinarySerializer.serializeToBinary((Object) testSoda, TEST_DIRECTORY);
        Beverage deserializedBeverage = (Beverage) BinarySerializer.deserializeFromBinary(TEST_DIRECTORY);
        assertEquals(testSoda, deserializedBeverage);
    }

    @Test
    void serializeListToAndFromBinary() throws IOException {
        BinarySerializer.serializeListToBinary(testList, TEST_DIRECTORY);
        List<Beverage> deserializedList = (List<Beverage>) BinarySerializer.deserializeFromBinary(TEST_DIRECTORY);
        assertEquals(testList, deserializedList);
    }
}
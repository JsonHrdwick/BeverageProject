package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class SodaTest {

    private Set<Soda> testSet;
    private Soda testSingleton;

    @BeforeEach
    void setUp() {
        testSet = new TreeSet<>();
        testSet.add(new Soda("Coke", 2.99, 200, 32));
        testSet.add(new Soda("Fanta", 5.99, 400, 64));
        testSet.add(new Soda("Dr. Pepper", 3.99, 180, 32));
        testSet.add(new Soda("Sprite", 1.99, 100, 16));

        testSingleton = new Soda("Coke", 2.99, 200, 32);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void serializeAndDeserializeToCSV() throws IOException {

        Soda.serializeToCSV(testSingleton, "src/test/resources/SerializeTest.csv");
        Beverage deserializedSoda = Soda.deserializeFromCSV("src/test/resources/SerializeTest.csv");


        // Tests the overridden Soda.equals method
        assertTrue(testSingleton.equals(deserializedSoda));

    }

    @Test
    void setToAndFromCSV() throws IOException {
        Soda.setToCSV(testSet, "src/test/resources/SetSerializeTest.csv");
        Set<Soda> deserializeSet = new TreeSet<>();
        deserializeSet = Soda.setFromCSV("src/test/resources/SetSerializeTest.csv");

        assertEquals(testSet, deserializeSet);
    }
    @Test
    void toAndFromXML() throws IOException {
        Soda.serializeToXML(testSingleton, "src/test/resources/XMLSerializeTest.xml");
        Beverage newSoda = Soda.deserializeFromXML("src/test/resources/XMLSerializeTest.xml");

        assertTrue(testSingleton.equals(newSoda));
    }

    @Test
    void toAndFromBinary() throws IOException {
        Beverage testSoda = new Soda("Coke", 2.99, 200, 32);
        BinarySerializer.serializeToBinary(testSoda, "src/test/resources/BinarySerializeTest.binary");
        Beverage newSoda = (Soda) BinarySerializer.deserializeFromBinary("src/test/resources/BinarySerializeTest.binary");

        assertTrue(testSoda.equals(newSoda));
    }

}
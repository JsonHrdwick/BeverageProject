package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BinarySerializer {
    /**
     *
     * @param object
     * @param filename
     * @throws IOException
     */
    public static void serializeToBinary(Object object, String filename) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            byte[] data = baos.toByteArray();
            Path filePath = Paths.get(filename);
            Files.write(filePath, data);
        }
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static Object deserializeFromBinary(String filename) throws IOException {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            Files.deleteIfExists(path);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void serializeListToBinary(List<Beverage> list, String filename) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(list);
            byte[] data = baos.toByteArray();
            Path filePath = Paths.get(filename);
            Files.write(filePath, data);
        }
    }
    public static List<Beverage> deserializeListFromBinary(String filename) throws IOException, ClassNotFoundException {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            List<Beverage> list = (List<Beverage>) ois.readObject();
            Files.deleteIfExists(path);
            return list;
        }
    }
}

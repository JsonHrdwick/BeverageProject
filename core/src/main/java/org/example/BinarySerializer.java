package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}

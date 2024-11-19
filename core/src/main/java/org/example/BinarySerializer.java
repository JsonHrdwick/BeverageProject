package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BinarySerializer {
    private static final String FS = File.separator;
    private static final String USER_HOME_DIRECTORY = System.getProperty("user.home");
    private static final String BEVERAGE_SERIALIZER_DIRECTORY = FS + USER_HOME_DIRECTORY + FS + "BeverageFiles";
    /**
     *
     * @param object
     * @param filename
     * @throws IOException
     */
    public static void serializeToBinary(Object object, String filename) throws IOException {
        File dataFile = new File(BEVERAGE_SERIALIZER_DIRECTORY);
        if (!dataFile.exists()) {
            dataFile.mkdirs();
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            byte[] data = baos.toByteArray();
            File file = new File(BEVERAGE_SERIALIZER_DIRECTORY + FS + filename);
            Path filePath = Path.of(file.getAbsolutePath());
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
        File file = new File(BEVERAGE_SERIALIZER_DIRECTORY + FS + filename);
        Path filePath = Path.of(file.getAbsolutePath());
        byte[] data = Files.readAllBytes(filePath);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            Files.deleteIfExists(filePath);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void serializeListToBinary(List<Beverage> list, String filename) throws IOException {
        File dataFile = new File(BEVERAGE_SERIALIZER_DIRECTORY);
        if (!dataFile.exists()) {
            dataFile.mkdirs();
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(list);
            byte[] data = baos.toByteArray();
            File file = new File(BEVERAGE_SERIALIZER_DIRECTORY + FS + filename);
            Path filePath = Path.of(file.getAbsolutePath());
            if(!Files.exists(filePath)) {Files.createFile(filePath);}
            Files.write(filePath, data);
        }
    }
    public static List<Beverage> deserializeListFromBinary(String filename) throws IOException, ClassNotFoundException {
        File file = new File(BEVERAGE_SERIALIZER_DIRECTORY + FS + filename);
        Path filePath = Path.of(file.getAbsolutePath());
        byte[] data = Files.readAllBytes(filePath);

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            List<Beverage> list = (List<Beverage>) ois.readObject();
            Files.deleteIfExists(filePath);
            return list;
        }
    }
}

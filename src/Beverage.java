import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Beverage implements Serializable {
    String name;
    int price;
    int calories;
    int flOz;

    // Constructor
    public Beverage(String name, int price, int calories, int flOz) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.flOz = flOz;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public void setFlOz(int flOz) {
        this.flOz = flOz;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getCalories() {
        return calories;
    }
    public int getFlOz() {
        return flOz;
    }

    // Serialize object
    public static void serializeToCSV(Beverage beverage, String filename) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(beverage);
            byte[] data = baos.toByteArray();

            Path filePath = Paths.get("beverage.csv");
            Files.write(filePath, data);
        }
    }

    // Deserialize object
    public static Beverage deserializeFromCSV(String filename) throws IOException, ClassNotFoundException {
        Path filePath = Paths.get("beverage.csv");

        byte[] data = Files.readAllBytes(filePath);

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (Beverage) ois.readObject();
        }
    }

    public static boolean isEqual(Beverage beverage1, Beverage beverage2) {
        if (!beverage1.getName().equals(beverage2.getName())) {
            return false;
        } else if (beverage1.getPrice() != beverage2.getPrice()) {
            return false;
        } else if (beverage1.getCalories() != beverage2.getCalories()) {
            return false;
        } else if (beverage1.getFlOz() != beverage2.getFlOz()) {
            return false;
        }
        return true;
    }

}

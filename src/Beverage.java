import java.io.*;

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

    // Serializers
    public static void serializeToCSV(Beverage beverage, String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(beverage);
        System.out.println("Beverage serialized to " + filename);
        objectOutputStream.close();
        fileOutputStream.close();
    }
    public static Beverage deserializeFromCSV(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Beverage readBev = (Beverage) objectInputStream.readObject();
        System.out.println("Beverage deserialized from " + filename);
        objectInputStream.close();
        fileInputStream.close();

        return readBev;
    }
}

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Beverage implements Serializable {
    private String name;
    private int price;
    private int calories;
    private int flOz;

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
        Path path = Paths.get(filename);
        Files.writeString(path, prettyPrintCSV(beverage));

    }

    // Deserialize object
    public static Beverage deserializeFromCSV(String filename) throws IOException, OutOfMemoryError {
        Path path = Paths.get(filename);
        List<String> filedata = Files.readAllLines(path);
        String[] data = filedata.getFirst().split(",");

        // Default data values
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].isEmpty()){
                data[i] = "0";
            }
        }

        Beverage newBev = new Beverage(data[0].trim(), Integer.parseInt(data[1].trim()), Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()));
        return newBev;
    }

    public static String prettyPrintCSV(Beverage beverage) {
        return beverage.getName() + "," + beverage.getPrice() + "," + beverage.getCalories() + "," + beverage.getFlOz();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beverage beverage = (Beverage) o;
        return price == beverage.price && calories == beverage.calories && flOz == beverage.flOz && Objects.equals(name, beverage.name);
    }

}

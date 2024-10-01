import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

interface Beverage<T> {

    void setName(String name);

    void setPrice(double price);

    void setCalories(int calories);

    void setFlOz(int flOz);

    String getName();

    double getPrice();

    int getCalories();

    int getFlOz();

    void serializeToCSV(String s) throws IOException;

    T deserializeFromCSV(String s) throws IOException;

    boolean equals(Object o);
}

class Soda implements Beverage, Serializable {
    private String name;
    private double price;
    private int calories;
    private int flOz;

    // Constructor
    public Soda(String name, double price, int calories, int flOz) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.flOz = flOz;
    }

    public Soda() {
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
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

    public double getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public int getFlOz() {
        return flOz;
    }

    // Serialize Object
    // Methods changed from static to implement interface override
    public void serializeToCSV(String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.writeString(path, prettyPrintCSV(this));

    }

    // Deserialize object
    public Soda deserializeFromCSV(String filename) throws OutOfMemoryError, IOException {
        Path path = Paths.get(filename);
        List<String> filedata = Files.readAllLines(path);
        String[] data = filedata.getFirst().split(",");

        // Default data values
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].isEmpty()){
                data[i] = "0";
            }
        }

        return new Soda(data[0].trim(), Double.parseDouble(data[1].trim()), Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()));
    }

    public static String prettyPrintCSV(Soda soda) {
        return soda.getName() + "," + soda.getPrice() + "," + soda.getCalories() + "," + soda.getFlOz();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Soda beverage = (Soda) o;
        return price == beverage.price && calories == beverage.calories && flOz == beverage.flOz && Objects.equals(name, beverage.name);
    }

}

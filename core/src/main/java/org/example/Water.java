package org.example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

//@XStreamAlias("SodaClass")

public class Water implements Beverage, Serializable, Comparable<Water> {
    private String name;
    private double price;
    private int calories;
    private int flOz;
    private boolean sparkling;


    // Constructor
    public Water(String name, double price, int calories, int flOz, boolean sparkling) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.flOz = flOz;
        this.sparkling = sparkling;
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
    public void setSparkling(boolean sparkling) { this.sparkling = sparkling; }

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
    public boolean isSparkling() {return sparkling;}

    /**
     *
     * @param water Water object to be serialized
     * @param filename File directory to serialize to
     * @throws IOException
     */
    public static void serializeToCSV(Water water, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.createFile(path);
        Files.writeString(path, prettyPrintCSV(water), StandardOpenOption.APPEND);
    }

    /**
     *
     * @param filename File path of serialized file
     * @return Deserialized Water object
     * @throws IOException
     */
    public static Water deserializeFromCSV(String filename) throws IOException {
        Path path = Paths.get(filename);
        List<String> filedata = Files.readAllLines(path);
        String[] data = filedata.get(0).split(",");
        Files.deleteIfExists(path);
        return formatValues(data);
    }

    /**
     *
     * @param waters
     * @param filename
     * @throws IOException
     */
    public static void setToCSV(Set<Water> waters, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.createFile(path);
        for (Water water : waters) {
            Files.writeString(path, prettyPrintCSV(water), StandardOpenOption.APPEND);

        }
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static TreeSet<Water> setFromCSV(String filename) throws IOException {
        TreeSet<Water> coffeeSet = new TreeSet<>();
        Path path = Paths.get(filename);
        List<String> fileData = Files.readAllLines(path);

        for (String fileDatum : fileData) {
            String[] data = fileDatum.split(",");
            coffeeSet.add(formatValues(data));

        }
        Files.deleteIfExists(path);
        return coffeeSet;
    }

    /**
     *
     * @param water
     * @param filename
     * @throws IOException
     */
    public static void serializeToXML(Water water, String filename) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.processAnnotations(Water.class);
        String dataXML = xstream.toXML(water);
        Path path = Paths.get(filename);
        Files.writeString(path, dataXML);
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static Water deserializeFromXML(String filename) throws IOException {
        Path path = Paths.get(filename);
        XStream xstream = new XStream(new StaxDriver());
        Class[] types = new Class[]{Water.class};
        xstream.allowTypes(types);
        String dataXML = Files.readString(path);
        Files.deleteIfExists(path);
        return (Water) xstream.fromXML(dataXML);
    }

    /**
     *
     * @param data
     * @return
     */
    public static Water formatValues(String[] data){
        // Default data values
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].isEmpty()){
                data[i] = "0";
            }
            else {
                data[i] = data[i].trim();
            }
        }
        return new Water(data[0].trim(), Double.parseDouble(data[1].trim()), Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()), Boolean.parseBoolean(data[4].trim()));
    }

    /**
     *
     * @param water
     * @return
     */
    private static String prettyPrintCSV(Water water) {
        return water.getName() + "," + water.getPrice() + "," + water.getCalories() + "," + water.getFlOz() + "," + water.isSparkling() + "\n";
    }

    /**
     *
     * @param water
     *
     */
    public static void sodaPrint(Water water) {
        System.out.println(water.getName() + " , $" + water.getPrice() + " , " + water.getCalories() + " , " + water.getFlOz());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Water water = (Water) o;
        return price == water.price && calories == water.calories && flOz == water.flOz && Objects.equals(name, water.name);
    }

    @Override
    public int compareTo(Water o) {
        return -o.name.compareTo(name);
    }
}

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

public class Coffee implements Beverage, Serializable, Comparable<Coffee> {
    private String name;
    private double price;
    private int calories;
    private int flOz;
    private int caffeineLevel;
    private Milk milkType;


    // Constructor
    public Coffee(String name, double price, int calories, int flOz, int caffeineLevel, Milk milkType) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.flOz = flOz;
        this.caffeineLevel = caffeineLevel;
        this.milkType = milkType;
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
    public void setCaffeineLevel(int caffeineLevel) {this.caffeineLevel = caffeineLevel;}
    public void setMilkType(Milk milkType) {this.milkType = milkType;}


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
    public int getCaffeineLevel() { return caffeineLevel; }
    public Milk getMilkType() {return milkType;}

    /**
     *
     * @param coffee Coffee object to be serialized
     * @param filename File directory to serialize to
     * @throws IOException
     */
    public static void serializeToCSV(Coffee coffee, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.createFile(path);
        Files.writeString(path, prettyPrintCSV(coffee), StandardOpenOption.APPEND);
    }

    /**
     *
     * @param filename File path of serialized file
     * @return Deserialized Coffee object
     * @throws IOException
     */
    public static Coffee deserializeFromCSV(String filename) throws IOException {
        Path path = Paths.get(filename);
        List<String> filedata = Files.readAllLines(path);
        String[] data = filedata.get(0).split(",");
        Files.deleteIfExists(path);
        return formatValues(data);
    }

    /**
     *
     * @param coffees
     * @param filename
     * @throws IOException
     */
    public static void setToCSV(Set<Coffee> coffees, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.createFile(path);
        for (Coffee coffee : coffees) {
            Files.writeString(path, prettyPrintCSV(coffee), StandardOpenOption.APPEND);

        }
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static TreeSet<Coffee> setFromCSV(String filename) throws IOException {
        TreeSet<Coffee> coffeeSet = new TreeSet<>();
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
     * @param coffee
     * @param filename
     * @throws IOException
     */
    public static void serializeToXML(Coffee coffee, String filename) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.processAnnotations(Coffee.class);
        String dataXML = xstream.toXML(coffee);
        Path path = Paths.get(filename);
        Files.writeString(path, dataXML);
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static Coffee deserializeFromXML(String filename) throws IOException {
        Path path = Paths.get(filename);
        XStream xstream = new XStream(new StaxDriver());
        Class[] types = new Class[]{Coffee.class};
        xstream.allowTypes(types);
        String dataXML = Files.readString(path);
        Files.deleteIfExists(path);
        return (Coffee) xstream.fromXML(dataXML);
    }

    /**
     *
     * @param data
     * @return
     */
    public static Coffee formatValues(String[] data){
        // Default data values
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].isEmpty()){
                data[i] = "0";
            }
            else {
                data[i] = data[i].trim();
            }
        }
        return new Coffee(data[0].trim(), Double.parseDouble(data[1].trim()), Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()), Integer.parseInt(data[4].trim()), Milk.valueOf(data[5].trim()));
    }

    /**
     *
     * @param coffee
     * @return
     */
    private static String prettyPrintCSV(Coffee coffee) {
        return coffee.getName() + "," + coffee.getPrice() + "," + coffee.getCalories() + "," + coffee.getFlOz() + "," + coffee.getCaffeineLevel() + "," + coffee.getMilkType() + "\n";
    }

    /**
     *
     * @param coffee
     */
    public static void coffeePrint(Coffee coffee) {
        System.out.println(coffee.getName() + " , $" + coffee.getPrice() + " , " + coffee.getCalories() + " , " + coffee.getFlOz() + " , " + coffee.getCaffeineLevel() + " , " + coffee.getMilkType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coffee coffee = (Coffee) o;
        return price == coffee.price && calories == coffee.calories && flOz == coffee.flOz && Objects.equals(name, coffee.name);
    }

    @Override
    public int compareTo(Coffee o) {
        return -o.name.compareTo(name);
    }

}

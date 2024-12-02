package org.example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

//@XStreamAlias("SodaClass")

public class Soda implements Beverage, Serializable, Comparable<Soda> {
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

    /**
     *
     * @param soda Soda object to be serialized
     * @param filename File directory to serialize to
     * @throws IOException
     */
    public static void serializeToCSV(Beverage soda, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.createFile(path);
        Files.writeString(path, prettyPrintCSV(soda), StandardOpenOption.APPEND);
    }

    /**
     *
     * @param filename File path of serialized file
     * @return Deserialized Soda object
     * @throws IOException
     */
    public static Soda deserializeFromCSV(String filename) throws IOException {
        Path path = Paths.get(filename);
        List<String> filedata = Files.readAllLines(path);
        String[] data = filedata.get(0).split(",");
        Files.deleteIfExists(path);
        return formatValues(data);
    }

    /**
     *
     * @param sodas
     * @param filename
     * @throws IOException
     */
    public static void setToCSV(Set<Soda> sodas, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.createFile(path);
        for (Beverage soda : sodas) {
            Files.writeString(path, prettyPrintCSV(soda), StandardOpenOption.APPEND);

        }
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static TreeSet<Soda> setFromCSV(String filename) throws IOException {
        TreeSet<Soda> sodaSet = new TreeSet<>();
        Path path = Paths.get(filename);
        List<String> fileData = Files.readAllLines(path);

        for (String fileDatum : fileData) {
            String[] data = fileDatum.split(",");
            sodaSet.add(formatValues(data));

        }
        Files.deleteIfExists(path);
        return sodaSet;
    }

    /**
     *
     * @param soda
     * @param filename
     * @throws IOException
     */
    public static void serializeToXML(Beverage soda, String filename) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.processAnnotations(Soda.class);
        String dataXML = xstream.toXML(soda);
        Path path = Paths.get(filename);
        Files.writeString(path, dataXML);
    }

    /**
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static Soda deserializeFromXML(String filename) throws IOException {
        Path path = Paths.get(filename);
        XStream xstream = new XStream(new StaxDriver());
        Class[] types = new Class[]{Soda.class};
        xstream.allowTypes(types);
        String dataXML = Files.readString(path);
        Files.deleteIfExists(path);
        return (Soda) xstream.fromXML(dataXML);
    }

    /**
     *
     * @param data
     * @return
     */
    public static Soda formatValues(String[] data){
        // Default data values
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].isEmpty()){
                data[i] = "0";
            }
            else {
                data[i] = data[i].trim();
            }
        }
        return new Soda(data[0].trim(), Double.parseDouble(data[1].trim()), Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()));
    }

    /**
     *
     * @param soda
     * @return
     */
    private static String prettyPrintCSV(Beverage soda) {
        return soda.getName() + "," + soda.getPrice() + "," + soda.getCalories() + "," + soda.getFlOz() + "\n";
    }

    /**
     *
     * @param soda
     */
    public static void sodaPrint(Beverage soda) {
        System.out.println(soda.getName() + " , $" + soda.getPrice() + " , " + soda.getCalories() + " , " + soda.getFlOz());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Soda beverage = (Soda) o;
        return price == beverage.price && calories == beverage.calories && flOz == beverage.flOz && Objects.equals(name, beverage.name);
    }

    @Override
    public int compareTo(Soda o) {
        return -o.name.compareTo(name);
    }
}

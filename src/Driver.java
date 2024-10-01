import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Soda fanta = new Soda("Fanta", 1, 150, 12);
        System.out.println(fanta.getName());
        System.out.println(fanta.getPrice());

        fanta.serializeToCSV("beverage.csv");
        Soda bev2 = new Soda().deserializeFromCSV("beverage.csv");
        System.out.println(bev2.getName());
        System.out.println(bev2.getPrice());

        if (fanta.equals(bev2)) {
            bev2 = null;
            System.out.println("Beverage already exists. Duplicate removed");
        } else
            System.out.println("Beverage added");
    }

}


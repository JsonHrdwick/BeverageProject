import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Beverage bev1 = new Beverage("Soda", 1, 150, 12);
        System.out.println(bev1.getName());
        System.out.println(bev1.getPrice());

        Beverage.serializeToCSV(bev1, "beverage.csv");
        Beverage bev2 = Beverage.deserializeFromCSV("beverage.csv");
        System.out.println(bev2.getName());
        System.out.println(bev2.getPrice());

        if (bev1.equals(bev2)) {
            bev2 = null;
            System.out.println("Beverage already exists. Duplicate removed");
        } else
            System.out.println("Beverage added");
    }

}


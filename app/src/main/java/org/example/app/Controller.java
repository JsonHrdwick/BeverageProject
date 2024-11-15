package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    List<Beverage> beverages = new ArrayList<>();
    final String SERIAL_DIRECTORY = "app/src/main/resources/org/example/app/beverage.bin";
    @FXML
    private TableView<Beverage> beverageTable;

    @FXML
    private TableColumn<Beverage, String> nameColumn;

    @FXML
    private TableColumn<Beverage, Double> priceColumn;
    @FXML
    private Label totalSale;
    NumberFormat formatter = new DecimalFormat("#0.00");
    private Double total = 0.0;

    @FXML
    private void initialize() throws IOException {
        // Set up the columns to use the appropriate properties from the Beverage class
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        try { beverages = BinarySerializer.deserializeListFromBinary(SERIAL_DIRECTORY);}
        catch (IOException e) { System.out.println("No serialized file available");} catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Load initial data into the table
        onPageUpdate();
    }

    @FXML
    protected void onCokeButtonClick() throws IOException {
        beverages.add(new Soda("Coca Cola", 2.99, 260, 12));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onDietButtonClick() throws IOException {
        beverages.add(new Soda("Diet Coke", 2.99, 0, 12));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onCherryButtonClick() throws IOException {
        beverages.add(new Soda("Cherry Coke", 3.39, 280, 12));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onFantaButtonClick() throws IOException {
        beverages.add(new Soda("Fanta", 2.99, 200, 12));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onSpriteButtonClick() throws IOException {
        beverages.add(new Soda("Sprite", 2.49, 140, 12));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onTonicButtonClick() throws IOException {
        beverages.add(new Soda("Tonic", 1.99, 140, 12));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onEnergyButtonClick() throws IOException {
        beverages.add(new Soda("Red Bull", 4.99, 400, 10));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onCoffeeButtonClick() throws IOException {
        beverages.add(new Coffee("Coffee", 2.09, 80, 16, 300, Milk.SOY));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    protected void onWaterButtonClick() throws IOException {
        beverages.add(new Water("Water", 0.99, 0, 24, false));
        total += beverages.get(beverages.size()-1).getPrice();
        onPageUpdate();
    }
    @FXML
    private void onPageUpdate() throws IOException {
        ObservableList<Beverage> listItems = FXCollections.observableArrayList(beverages);
        beverageTable.setItems(listItems);
        total = 0.0;
        beverages.forEach(i -> total += i.getPrice());
        totalSale.setText("$"+formatter.format(total));
        serializeBeverages();
    }
    @FXML void onClearButtonClick() throws IOException {
        beverages.clear();
        total = 0.0;
        onPageUpdate();
    }
    public void serializeBeverages() throws IOException {
        BinarySerializer.serializeListToBinary(beverages, SERIAL_DIRECTORY);
    }

}
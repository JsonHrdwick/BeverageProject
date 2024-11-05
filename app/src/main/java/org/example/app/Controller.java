package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.Beverage;
import org.example.Soda;

import java.util.Set;
import java.util.TreeSet;

public class Controller {
    public ListView beverageList;
    Set<Beverage> beverages = new TreeSet<>();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onCokeButtonClick() {
        beverages.add(new Soda("Coca Cola", 2.99, 260, 12));
        onPageUpdate();
    }
    @FXML
    protected void onDietButtonClick() {
        beverages.add(new Soda("Diet Coke", 2.99, 0, 12));
        onPageUpdate();
    }
    @FXML
    protected void onCherryButtonClick() {
        beverages.add(new Soda("Cherry Coke", 3.39, 280, 12));
        onPageUpdate();
    }
    @FXML
    protected void onFantaButtonClick() {
        beverages.add(new Soda("Fanta", 2.99, 200, 12));
        onPageUpdate();
    }
    @FXML
    protected void onSpriteButtonClick() {
        beverages.add(new Soda("Sprite", 2.99, 140, 12));
        onPageUpdate();
    }
    @FXML
    protected void onTonicButtonClick() {
        beverages.add(new Soda("Sprite", 2.99, 140, 12));
        onPageUpdate();
    }
    @FXML
    protected void onEnergyButtonClick() {
        beverages.add(new Soda("Red Bull", 2.99, 400, 10));
        onPageUpdate();
    }
    @FXML
    protected void onCoffeeButtonClick() {
        beverages.add(new Soda("Coffee", 2.99, 80, 16));
        onPageUpdate();
    }
    @FXML
    protected void onWaterButtonClick() {
        beverages.add(new Soda("Water", 2.99, 0, 24));
        onPageUpdate();
    }
    @FXML
    private void onPageUpdate(){
        ObservableList<Beverage> listItems = FXCollections.observableArrayList(beverages);
        beverageList.setItems(listItems);

    }
}
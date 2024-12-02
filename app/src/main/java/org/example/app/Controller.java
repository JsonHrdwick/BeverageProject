package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Controller {
    Map<String, Beverage> vendingMenu = new HashMap<>();
    Map<String, Integer> vendingInventory = new HashMap<>();
    List<Beverage> purchasedBeverages = new ArrayList<>();

    final String ASSETS_DIRECTORY = "core/src/main/resources/assets/";
    final String MENU_FILE = ASSETS_DIRECTORY + "vendingMenu.csv";
    final String INVENTORY_FILE = ASSETS_DIRECTORY + "vendingInventory.csv";

    final Image OUT_OF_STOCK_IMAGE = new Image("/assets/oos.jpg");

    final String SERIAL_FILE_NAME = "beverage.bin";

    NumberFormat formatter = new DecimalFormat("#0.00");
    Double total = 0.0;

    @FXML
    AnchorPane rootPane;
    VBox menuBox = new VBox(10);
    HBox currentRow = new HBox(10);
    final Integer COLUMNS = 3;

    final Integer IMG_WIDTH = 70;
    final Integer IMG_HEIGHT = 110;

    TableView<Beverage> beverageTable = new TableView<>();
    TableColumn<Beverage, String> nameColumn = new TableColumn<>("Name");
    TableColumn<Beverage, Double> priceColumn = new TableColumn<>("Price");
    Label totalSale = new Label("$0.00");

    Map<ImageView, Image> oosList = new HashMap<>();


    @FXML
    private void initialize() throws IOException {
        retrieveInventory();
        generateMenu();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        beverageTable.getColumns().addAll(nameColumn, priceColumn);
        beverageTable.setPrefSize(IMG_WIDTH*3, IMG_HEIGHT*1.25);
        menuBox.getChildren().add(beverageTable);

        currentRow = new HBox(10);
        currentRow.setAlignment(Pos.CENTER_RIGHT);
        Label saleText = new Label("Total Sale:");
        currentRow.getChildren().add(saleText);
        currentRow.getChildren().add(totalSale);
        menuBox.getChildren().add(currentRow);

        Button clearBtn = new Button("Clear Sale");
        clearBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                onClearButtonClick();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button refillBtn = new Button("Refill");
        refillBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                resetInventory();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuBox.getChildren().add(clearBtn);
        menuBox.getChildren().add(refillBtn);

        try { purchasedBeverages = BinarySerializer.deserializeListFromBinary(SERIAL_FILE_NAME);}
        catch (IOException e) { System.out.println("No serialized file available");} catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Load initial data into the table
        onPageUpdate();
    }

    private void onClearButtonClick() throws IOException {
        purchasedBeverages.clear();
        total = 0.0;
        onPageUpdate();
    }
    @FXML
    private void buildBeverageSlot(Beverage beverage, String imgFile) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        Image image = new Image("assets/" + imgFile, true);
        ImageView imgView = new ImageView();
        if (vendingInventory.get(beverage.getName()) > 0) {
            imgView.setImage(image);
        } else {
            oosList.put(imgView, image);
            imgView.setImage(OUT_OF_STOCK_IMAGE);
        }
        imgView.setFitWidth(IMG_WIDTH);
        imgView.setFitHeight(IMG_HEIGHT);
        vbox.getChildren().add(imgView);

        Button btn = new Button();
        btn.setText("Purchase");
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                purchaseClickHandler(beverage, imgView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        vbox.getChildren().add(btn);

        if (currentRow.getChildren().size() == COLUMNS) {
            menuBox.getChildren().add(currentRow);
            currentRow = new HBox(10);
        }

        currentRow.getChildren().add(vbox);

    }

    private void purchaseClickHandler(Beverage beverage, ImageView imgView) throws IOException {
        Integer count = vendingInventory.get(beverage.getName());
        if (count > 0) {
            vendingInventory.put(beverage.getName(), --count);
            updateInventory();
            purchasedBeverages.add(beverage);
            onPageUpdate();
        } else {
            oosList.put(imgView, imgView.getImage());
            imgView.setImage(OUT_OF_STOCK_IMAGE);


        }

    }

    private void generateMenu() throws IOException {
        Path path = Paths.get(MENU_FILE);
        List<String> filedata = Files.readAllLines(path);
        for (String line : filedata) {
            String[] parts = line.split(",");
            if (parts[0].equals("Soda")){
                Beverage soda = Soda.formatValues(new String[]{parts[2], parts[3], parts[4], parts[5]});
                vendingMenu.put(parts[2], soda);
                buildBeverageSlot(soda, parts[1]);
            } else if (parts[0].equals("Coffee")){
                Beverage coffee = Coffee.formatValues(new String[]{parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]});
                vendingMenu.put(parts[2], coffee);
                buildBeverageSlot(coffee, parts[1]);
            } else if (parts[0].equals("Water")){
                Beverage water = Water.formatValues(new String[]{parts[2], parts[3], parts[4], parts[5], parts[8]});
                vendingMenu.put(parts[2], water);
                buildBeverageSlot(water, parts[1]);
            }
        }

        if (!currentRow.getChildren().isEmpty()) {
            menuBox.getChildren().add(currentRow);
        }
        rootPane.getChildren().add(menuBox);
    }

    private void retrieveInventory() throws IOException {
        Path path = Paths.get(INVENTORY_FILE);
        List<String> filedata = Files.readAllLines(path);
        for (String line : filedata) {
            String[] parts = line.split(",");
            vendingInventory.put(parts[0], Integer.parseInt(parts[1]));
        }
    }

    private void onPageUpdate() throws IOException {
        ObservableList<Beverage> listItems = FXCollections.observableArrayList(purchasedBeverages);
        beverageTable.setItems(listItems);
        total = 0.0;
        purchasedBeverages.forEach(i -> total += i.getPrice());
        totalSale.setText("$"+formatter.format(total));
        serializeBeverages();
    }
    public void serializeBeverages() throws IOException {
        BinarySerializer.serializeListToBinary(purchasedBeverages, SERIAL_FILE_NAME);
    }
    private void updateInventory() throws IOException {
        Path path = Paths.get(INVENTORY_FILE);
        StringBuilder inventoryCSV = new StringBuilder();
        vendingInventory.forEach((key, value) -> {
            inventoryCSV.append(key).append(",").append(value).append("\n");
        });

        Files.writeString(path, inventoryCSV.toString()) ;

    }
    private void resetInventory() throws IOException {
        vendingInventory.forEach((key, value) -> {
            vendingInventory.put(key, 20);
        });
        oosList.forEach(ImageView::setImage);
        updateInventory();
    }

}
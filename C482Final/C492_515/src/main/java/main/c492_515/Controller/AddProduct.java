package main.c492_515.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.c492_515.Main;
import main.c492_515.Model.Inventory;
import main.c492_515.Model.Part;
import main.c492_515.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.c492_515.Model.Inventory.getAllParts;
import static main.c492_515.Model.Inventory.lookupPart;

public class AddProduct implements Initializable {

    public TableView ProductPart1;
    public TableColumn ProductPart1Id;
    public TableColumn ProductPart1Name;
    public TableColumn ProductPart1Inventory;
    public TableColumn ProductPart1Price;
    public TableView ProductPart2;
    public TableColumn ProductPart2Id;
    public TableColumn ProductPart2Name;
    public TableColumn ProductPart2Inventory;
    public TableColumn ProductPart2Price;
    public TextField searchParts;
    public TextField name;
    public TextField stock;
    public TextField price;
    public TextField min;
    public TextField max;
    private static int incVar = 100;
    public Button save;
    public TextField id;
    public Button add;
    public Button remove;
    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    /** Initialize addProduct Controller method.
     *  Initializes and sets the tableViews and columns
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ProductPart1.setItems(Inventory.getAllParts());
        ProductPart1Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductPart1Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPart1Inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPart1Price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductPart2Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductPart2Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPart2Inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPart2Price.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Loads up Main.fxml and exits AddProduct
     * @param actionEvent - triggered by button click
     * @throws Exception
     */
    public void toMain(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 900, 390);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    /** search Parts method.
     * used to trigger lookupPart method from inventory and set the tableView to show only the parts that match the
     * search criteria
     * @param actionEvent
     */
    public void searchParts(ActionEvent actionEvent) {

        String q = searchParts.getText();
        ObservableList<Part> parts = lookupPart(q);
        ProductPart1.setItems(parts);
        if (parts.size() == 0) {

            try {
                int o = Integer.parseInt(searchParts.getText());
                Part p = Inventory.lookupPart(o);
                if (p != null) {
                    parts.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
            if (parts.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "No results found");
                alert.showAndWait();
                ProductPart1.setItems(getAllParts());
            }
        }
        searchParts.setText("");
    }


    /** save addProduct method.
     * Method verifies all information put into text fields meets required criteria, displays error messages if not.
     * Once all information is correct, it creates a new Product, adds associated parts to the associatedParts list
     * and adds it to the allProducts list and returns the user to the main screen where the new Product is
     * displayed in the table.
     * @param actionEvent - triggered by clicking "save" button
     * @throws IOException
     */
    public void saveProduct(ActionEvent actionEvent) throws Exception {
        //check if fields are empty

        if (name.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please enter part name");
            alert.showAndWait();
            return;
        }
        if (price.getText().isBlank() || !isDouble(price.getText()) || !isDouble(price.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please enter numeric value for price");
            alert.showAndWait();
            return;
        }
        if (stock.getText().isBlank() || !isInteger(stock.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please enter integer value for inventory amount");
            alert.showAndWait();
            return;
        }
        if (min.getText().isBlank() || !isInteger(min.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please enter integer value for minimum inventory amount");
            alert.showAndWait();
            return;
        }
        if (max.getText().isBlank() || !isInteger(max.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please enter integer value for max inventory");
            alert.showAndWait();
            return;
        }


        //check if fields meet integer criteria
        try {
            double priceD = Double.parseDouble(price.getText());
            int stockI = Integer.parseInt(stock.getText());
            int minI = Integer.parseInt(min.getText());
            int maxI = Integer.parseInt(max.getText());
            if (stockI < minI){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Current inventory amount must be greater than or equal to minimum inventory");
                alert.showAndWait();
                return;
            }
            if (minI > maxI) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        " Minimum inventory amount must be greater than or equal to maximum inventory amount");
                alert.showAndWait();
                return;
            }
            if (stockI > maxI){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Current inventory amount must be less than or equal to maximum inventory");
                alert.showAndWait();
                return;
            }
        }
        catch (NumberFormatException e){
            return;
        }
        if (tempAssociatedParts == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Product must have at least one item in associatedParts list");
            alert.showAndWait();
            return;
        }

        ++incVar;
        try {
            Product newProduct = new Product(incVar, name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()),
                    Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));
            for (Part tempAssociatedPart : tempAssociatedParts) {
                newProduct.addAssociatedPart(tempAssociatedPart);
            }
            Inventory.addProduct(newProduct);

        }
        catch (NumberFormatException e) {
            //ignore
        }

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 900, 390);
            stage.setTitle("Inventory System");
            stage.setScene(scene);
            stage.show();
        }


    /** remove temporary associated part method.
     * Method removes part from the tempAssociatedParts list.
     * @param actionEvent - triggered by button click "remove associated part"
     */
    public void removeTempAssociatedPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you'd like to remove this part?");
        alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        Part selectedPart = (Part) ProductPart2.getSelectionModel().getSelectedItem();
                        tempAssociatedParts.remove(selectedPart);
                    }
                });
    }

    /** is integer method.
     * method checks to see if input is integer or not. Returns true if integer, otherwise returns false
     * @param textField - evaluation input
     * @return return true for integer, false for all other values
     */
    public boolean isInteger(String textField) {
        try {
            Integer.parseInt(textField);
            return true;
        }
        catch(NumberFormatException e ) {
            return false;
        }
    }
    /** is double method.
     * method checks to see if input is double or not. Returns true if double, otherwise returns false
     * @param textField - evaluation input
     * @return return true for double, false for all other values
     */
    public boolean isDouble(String textField) {
        try {
            Double.parseDouble(textField);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /** add temporary associated part method.
     * uses selected part from tableView 1 and adds it to the tempAssociatedParts list, then populates that list on
     * tableView2
     * @param actionEvent
     */
    public void addTempAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) ProductPart1.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            tempAssociatedParts.add(selectedPart);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Please select a part");
            alert.showAndWait();
        }
        ProductPart2.setItems(tempAssociatedParts);


    }
    }


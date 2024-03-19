package main.c492_515.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.c492_515.Main;
import main.c492_515.Model.Inventory;
import main.c492_515.Model.Part;
import main.c492_515.Model.Product;

import static main.c492_515.Model.Inventory.*;


public class MainFormController implements Initializable {
    //Main Form Product table
    public TableView<Product> MainProducts;
    public TableColumn MainProductId;
    public TableColumn MainProductName;
    public TableColumn MainProductInventory;
    public TableColumn MainProductPrice;


    //Main form Parts table
    public TableView<Part> MainParts;
    public TableColumn MainPartId;
    public TableColumn MainPartName;
    public TableColumn MainPartInventory;
    public TableColumn MainPartPrice;
    public Button MainExit;
    public Button deleteProduct;
    public TextField partSearch;
    public TextField productSearch;
    public Button addProduct;
    public Button deletePart;
    public Button ModifyProductB;
    public Button ModifyPartB;

    /** Modify Part from Main Controller method
     * Method observes selected part, assigns it to variable part and initiates method setSavePart from ModifyPart to
     * allow ModifyPart screen to pull data over. Loads up ModifyPart.fxml
     * @param actionEvent - triggered by clicking Modify button
     * @throws IOException
     */
    public void modifyPartMain(ActionEvent actionEvent) throws IOException {
        if (MainParts.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please select a part to modify.");
            alert.showAndWait();
            return;
        }
        Part part = MainParts.getSelectionModel().getSelectedItem();
        ModifyPart.setSavePart(part);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 400, 420);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    /** Add Part method
     * Method opens new scene for AddPart.fxml
     * @param actionEvent - triggered by "add" button
     * @throws IOException
     */
    public void addPartMain(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 400, 420);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** exit program method
     * Asks for confirmation to shut down and then shuts down the program
     * @param event
     */
    public void exitProgram(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) MainExit.getScene().getWindow();
                stage.close();
            }
        });
    }

    /** Modify Product from Main Controller method
     * Method observes selected product, assigns it to variable product and initiates method setSaveProduct from
     * ModifyProduct to allow ModifyProduct screen to pull data over. Loads up ModifyProduct.fxml
     * @param actionEvent - triggered by clicking Modify button
     * @throws IOException
     */
    public void modifyProductMain(ActionEvent actionEvent) throws IOException {
        if (MainProducts.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please select a product to modify.");
            alert.showAndWait();
            return;
        }
        Product product = MainProducts.getSelectionModel().getSelectedItem();
        ModifyProduct.setSaveProduct(product);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Initialize MainFormController Controller method.
     *  Initializes and sets the tableViews and columns
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainProducts.setItems(Inventory.getAllProducts());
        MainProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainParts.setItems(Inventory.getAllParts());
        MainPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** delete Part method.
     * Deletes part that is selected in table after receiving confirmation
     * @param actionEvent - triggered by delete button click
     */
    public void deletePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this part?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Inventory.deletePart((Part) MainParts.getSelectionModel().getSelectedItem());
            }});
    }

    /** delete Product method.
     * Checks associatedParts list and Deletes product that is selected if list is empty in table after receiving
     * confirmation
     * @param actionEvent - triggered by delete button click
     */

    public void deleteProduct(ActionEvent actionEvent) {
        Product product = MainProducts.getSelectionModel().getSelectedItem();
        if (product.getAssociatedParts().size() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Unable to delete Product with associated parts.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you'd like to delete this product?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Inventory.deleteProduct(MainProducts.getSelectionModel().getSelectedItem());
            }
        });
    }

    /** part search method.
     * Performs linear search through Observable List allParts using lookupPart method based on input information.
     * First attempts to search by string Name and if no match is found, search is performed by int ID
     * @param actionEvent - type in search box and push "enter" on keyboard
     */
    public void partSearch(ActionEvent actionEvent) {

        String q = partSearch.getText();
        ObservableList<Part> parts = lookupPart(q);
        MainParts.setItems(parts);
        if (parts.size() == 0) {

            try {
                int o = Integer.parseInt(partSearch.getText());
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
                MainParts.setItems(getAllParts());
            }
        }
        partSearch.setText("");
    }

    /** product search method.
     * Performs linear search through Observable List allProducts using lookupProduct method based on input information.
     * First attempts to search by string Name and if no match is found, search is performed by int ID
     * @param actionEvent - type in search box and push "enter" on keyboard
     */

    public void productSearch(ActionEvent actionEvent) {
        String q = productSearch.getText();
        ObservableList<Product> products = lookupProduct(q);
        MainProducts.setItems(products);
        if (products.size() == 0) {

            try {
                int o = Integer.parseInt(productSearch.getText());
                Product p = Inventory.lookupProduct(o);
                if (p != null) {
                    products.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
            if (products.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "No results found");
                alert.showAndWait();
                MainProducts.setItems(getAllProducts());
            }
        }
        productSearch.setText("");
    }

    /** Add Product method
     * Method opens new scene for AddProduct.fxml
     * @param actionEvent - triggered by "add" button
     * @throws IOException
     */
    public void addProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

}



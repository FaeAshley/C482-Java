package main.c492_515.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.c492_515.Main;
import main.c492_515.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {

    public RadioButton inHouse;
    public RadioButton outsourced;
    public Text textSix;
    public TextField fieldSeven;
    public TextField id;
    public TextField name;
    public TextField stock;
    public TextField price;
    public TextField min;
    public TextField max;
    public ToggleGroup addPartToggle;
    public Button savePart;
    static Part modifiedPart;
    private static int incVar = 100;


    /** set Save Part method.
     * pulls static variable part over from the selected item of the Parts tableView on the previous screen
     * @param part - selected part from prior Main screen
     */
    public static void setSavePart(Part part) {
        modifiedPart = part;
        System.out.println(modifiedPart);
    }

    /** initialize method
     * initializes and pulls data from setSavePart modifiedPart part and populates fields on screen with the data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (modifiedPart instanceof InHouse inhouse) {
            fieldSeven.setText(String.valueOf(inhouse.getMachineId()));
            addPartToggle.selectToggle(inHouse);
        } else {
            Outsourced outsource = (Outsourced) modifiedPart;
            fieldSeven.setText(String.valueOf(outsource.getCompanyName()));
            addPartToggle.selectToggle(outsourced);
            textSix.setText("Company Name");
        }
        id.setText(String.valueOf(modifiedPart.getId()));
        name.setText(modifiedPart.getName());
        stock.setText(String.valueOf(modifiedPart.getStock()));
        min.setText(String.valueOf(modifiedPart.getMin()));
        max.setText(String.valueOf(modifiedPart.getMax()));
        price.setText(String.valueOf(modifiedPart.getPrice()));

    }

    /** to Main method
     * takes user back to Main.fxml
     * @param actionEvent - triggered by button
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

    /** onInHouse radio button selection method.
     * Switches the text textSix to Machine ID when InHouse radio button is selected
     * @param actionEvent - triggered by changing radio button
     */
    public void onInHouse(ActionEvent actionEvent) {
        textSix.setText("Machine ID");

    }

    /** onOutsourced radio button selection method.
     * Switches the text textSix and text field fieldSeven to Company Name when Outsourced radio button is selected
     * @param actionEvent - triggered by changing radio button
     */
    public void onOutsourced(ActionEvent actionEvent) {
        textSix.setText("Company Name");
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
        }
        catch(NumberFormatException e ) {
            return false;
        }
    }
    /** save modify Part method.
     * Method verifies all information put into text fields meets required criteria, displays error messages if not.
     * Once all information is correct, it updates the existing Part and updates the allParts list and returns the user
     * to the main screen where the new information is displayed in the table.
     * @param actionEvent - triggered by clicking "save" button
     * @throws IOException
     */
    public void save(ActionEvent actionEvent) throws IOException {
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
        if (inHouse.isSelected() && fieldSeven.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please enter integer value for field seven");
            alert.showAndWait();
            return;
        }

        try {
            double priceD = Double.parseDouble(price.getText());
            int stockI = Integer.parseInt(stock.getText());
            int minI = Integer.parseInt(min.getText());
            int maxI = Integer.parseInt(max.getText());
            if (stockI < minI) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Current inventory amount must be greater than or equal to minimum inventory");
                alert.showAndWait();
                return;
            }
            if (minI > maxI) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        " Minimum inventory amount must be greater than or equal to maximum inventory amount");
                alert.showAndWait();
                return;
            }
            if (stockI > maxI) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Current inventory amount must be less than or equal to maximum inventory");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            return;
        }



        if (inHouse.isSelected() && !isInteger(fieldSeven.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Machine ID must be integer value");
            alert.showAndWait();
            return;
        }

        else if ((modifiedPart instanceof InHouse && outsourced.isSelected())
                ||  (modifiedPart instanceof Outsourced && inHouse.isSelected())) {
            Inventory.deletePart(modifiedPart);
            if (inHouse.isSelected()) {


                ++incVar;
                InHouse newPart = new InHouse(incVar, name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()),
                        Integer.parseInt(fieldSeven.getText()));
                Inventory.addPart(newPart);
            }

            else {
                ++incVar;
                Outsourced newPart = new Outsourced(incVar, name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(stock.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), fieldSeven.getText());
                Inventory.addPart(newPart);
            }
        } else {
            modifiedPart.setName(name.getText());
            modifiedPart.setStock(Integer.parseInt(stock.getText()));
            modifiedPart.setPrice(Double.parseDouble(price.getText()));
            modifiedPart.setMin(Integer.parseInt(min.getText()));
            modifiedPart.setMax(Integer.parseInt(max.getText()));
            if (inHouse.isSelected()) {
                InHouse ih = (InHouse) modifiedPart;
                ih.setMachineId(Integer.parseInt(fieldSeven.getText()));
            } else {
                Outsourced outsourced = (Outsourced) modifiedPart;
                outsourced.setCompanyName(fieldSeven.getText());
            }

            Inventory.updatePart(Inventory.getAllParts().indexOf(modifiedPart),
                    modifiedPart);
        }


            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 900, 390);
            stage.setTitle("Inventory System");
            stage.setScene(scene);
            stage.show();

        }
    }


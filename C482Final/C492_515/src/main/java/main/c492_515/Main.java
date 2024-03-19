package main.c492_515;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.c492_515.Model.*;

/** @author Fae Ashley
 * Logical error: Data being pulled from Main was not populating in the ModifyPart.fxml as expected.
 * The ModifyPart controller was accidentally set to the AddPart controller. I made this determination by completing
 * the data transfer with my ModifyProduct form first, ensured all code within the classes was correct, and then
 * inspected the ModifyPart.fxml where I found the error.
 * <br>
 * Enhancement:
 * An enhancement that would be useful for this product would be a database solution for the backend that would store
 * data after the program has been turned off.
 * <br>
 * Javadocs directory: C:\Users\Fae\Documents\school\Javadoc
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 390);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();

        //test data
        Product A = new Product(1, "A", 0.05, 20, 1, 5);
        Product B = new Product(2, "B", 0.05, 20, 1, 5);
        Product C = new Product(3, "C", 0.05, 20, 1, 5);

        Inventory.addProduct(A);
        Inventory.addProduct(B);
        Inventory.getAllProducts().add(C);


        InHouse A1 = new InHouse(1, "A", 10.00, 2, 1, 60, 700);
        Outsourced B2 = new Outsourced(2, "B", 2.50, 33, 1, 75, "Chick Feet");
        InHouse C3 = new InHouse(3, "C", 7.75, 22, 1, 80, 32);


        Inventory.addPart(A1);
        Inventory.addPart(B2);
        Inventory.getAllParts().add(C3);



    }
}
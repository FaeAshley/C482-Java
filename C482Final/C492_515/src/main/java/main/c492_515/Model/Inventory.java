package main.c492_515.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/** Inventory super class.
  Super class inventory to subclasses Part and Product. Holds Observable lists, setters and getters.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** addPart Method.
      Add part method within inventory class allows you to add a new part to the allParts Observable List
      @param newPart - Part to be added to the observable list allParts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
        System.out.println("Inventory Part addPart Method");
    }

    /** addProduct Method.
     Add product method within inventory class allows you to add a new product to the allProducts Observable List
     @param newProduct - Product to be added to the observable list allProducts (Product)
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        System.out.println("Inventory Product addProduct Method triggered." + newProduct);
    }

    /** lookupPart method.
      Method used to do a linear search through allParts by ID Observable List.
      @param partId - int used to find the Part
      @return - return part if found, otherwise return null
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        System.out.println("Inventory Part lookupPart ID Method triggered.");
        for (Part p : allParts)
            if (p.getId() == partId) {
                return p;
            }
        return null;
    }
    /** lookupProduct method.
     Method used to do a linear search through allProducts by ID Observable List.
     @param productId - int used to find the Product
     @return - return product if found, otherwise return null
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        System.out.println("Inventory Product lookupProduct ID Method triggered.");
        for (Product p : allProducts)
            if (p.getId() == productId) {
                return p;
            }
        return null;
    }

    /** lookupPart method.
     Method used to do a linear search through allParts by string Name Observable List and adds each Part to the list
     namedParts.
     @param partName - string used to find the Part/perform search
     @return - return list namedParts
     */
    public static ObservableList<Part> lookupPart(String partName){
        System.out.println("Inventory Part lookupPart String Method triggered.");
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part p : allParts){
            if(p.getName().contains(partName)){
                namedParts.add(p);
            }

        }
        return namedParts;
    }

    /** lookupProduct method.
     Method used to do a linear search through allProducts by string Name Observable List and adds each Part to the list
     namedProducts.
     @param productName - string used to find the Product/perform search on
     @return - return list namedProducts
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        System.out.println("Inventory Product lookupProduct String Method triggered.");
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product p : allProducts){
            if(p.getName().contains(productName)){
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    /** update Part method.
      Method used to update allParts list with new information
      @param index - int used to select and set correct part
      @param selectedPart - Part used to select and set correct part
     */
    public static void updatePart(int index, Part selectedPart) {
        System.out.println("Inventory Part updatePart Method triggered.");
        allParts.set(index, selectedPart);
    }
    /** update Product method.
     Method used to update allProducts list with new information
     @param index - int used to select and set correct product
     @param selectedProduct - Part used to select and set correct part
     */
    public static void updateProduct(int index, Product selectedProduct) {
        System.out.println("Inventory Product updateProduct Method triggered.");
        allProducts.set(index, selectedProduct);
    }

    /** delete part method.
      Method used to remove a part from the allParts list.
      @param selectedPart - Part to be removed from list
      @return true
     */
    public static boolean deletePart(Part selectedPart) {
        System.out.println("Inventory Part deletePart Method triggered.");
        allParts.remove(selectedPart);
        return true;
    }

    /** delete product method.
     Method used to remove a product from the allProducts list.
     @param selectedProduct - Product to be removed from list
     @return true
     */
    public static boolean deleteProduct(Product selectedProduct) {
        System.out.println("Inventory Product deleteProduct Method triggered.");
        allProducts.remove(selectedProduct);
        return true;
    }

    /** get all Parts method.
      Get All parts method used to retrieve full list of parts allParts
      @return list of allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /** get all Products method.
     Get All products method used to retrieve full list of parts allProducts
     @return list of allProducts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}

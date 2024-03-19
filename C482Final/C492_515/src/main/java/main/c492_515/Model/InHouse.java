package main.c492_515.Model;

public class InHouse extends Part{
    private int machineId;

    /** InHouse class.
     Creation of subclass InHouse with super constructor and additional setters/getters for the machineId parameter.

     @param id - unique ID associated with each part (int)
     @param name - name of part (string)
     @param price - price/cost of part (double)
     @param stock - total current inventory amount of part (int)
     @param min - minimum allowable inventory amount of part (int)
     @param max - maximum allowable inventory amount of part (int)
      @param machineId - associated int machine ID (int)
     */
    public InHouse (int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** setMachineId method.
     Method sets the machineId for the InHouse Part
     @param machineId
     */
    public void setMachineId (int machineId){
        this.machineId = machineId;
        System.out.println("InHouse setMachineId method");
    }

    /** getMachineId method.
     Retrieves the Machine ID for an InHouse Part.
      @return machine ID, an integer.
     */
    public int getMachineId (){
        System.out.println("InHouse getMachineId method");
        return machineId;
    }

}

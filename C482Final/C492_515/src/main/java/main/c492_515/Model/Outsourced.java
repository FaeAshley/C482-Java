package main.c492_515.Model;

public class Outsourced extends Part{
    private String companyName;

    /** class Outsourced, subclass of Part class.
      Constructor and super-constructor for creation of the Outsourced subclass.
     @param id - unique ID associated with each part (int)
     @param name - name of part (string)
     @param price - price/cost of part (double)
     @param stock - total current inventory amount of part (int)
     @param min - minimum allowable inventory amount of part (int)
     @param max - maximum allowable inventory amount of part (int)
      @param companyName - Outsourced specific parameter for the associated Company Name (int)
     */
    public Outsourced (int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Method used to set Company Name.
       Method sets an outsourced part's company name.
      @param companyName - string that sets the company name
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
        System.out.println("Outsourced setCompanyName method.");
    }

    /** get Company Name method.
      Method used to retrieve the Company name.
      @return - returns companyName (string)
     */
    public String getCompanyName(){
        System.out.println("Outsourced getCompanyName method.");
        return companyName;
    }

}
package middleLayer.MerchandiseInventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import databaseDAO.*;
import databaseDAO.MerchandiseData.MerchandiseDAO;
import databaseDAO.MerchandiseData.MerchandiseRoot;

public class Inventory{
    private static Inventory singletonInstance = null;

    ArrayList<Merchandise> list = new ArrayList<Merchandise>(); //only valid medication

    private MerchandiseRoot _merDAO; // Dependency Injection Principle
    
    SortInventory sortInv = new SortInventory();
	
	private Inventory() {  //constructor of all singleton classes should be private
		
		try {
			_merDAO = new MerchandiseDAO();
			list = _merDAO.getListOfMerchandise();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Inventory(MerchandiseRoot dao) {
		this._merDAO = dao;
		this.list = this._merDAO.getListOfMerchandise();
		
	}

	// singleton classes must have this method
    public static Inventory getInstance(){
        if (singletonInstance == null)
            singletonInstance = new Inventory();
        return singletonInstance;
    }
    
    public static Inventory getInstance(MerchandiseRoot dao) {
        if (singletonInstance == null) {
        	
            singletonInstance = new Inventory(dao);
        }
        return singletonInstance;
    }

	public void set_merDAO(MerchandiseRoot _merDAO) {
		this._merDAO = _merDAO;
		list =_merDAO.getListOfMerchandise();
	}

	// can also be called "displayAllMedication" (can be another name for it) --> only VALID medication
    public ArrayList<Merchandise> getMerchandise(){
    	list =_merDAO.getListOfMerchandise();
    	return list;
    }
    
    public ArrayList<Merchandise> getValidAndInvalidMerchandise() { //NOTE: method used in special few cases where invalid med are needed to be seen
    	return _merDAO.getListOfValidAndInvalidMerchandise();
    }
    
    // finds from ALL VALID and INVALID list and returns a medication with medID == medicationID
    public Merchandise searchAllValidAndInvalidMerchandiseWithID(int medicationID){ //NOTE: method used in special few cases where invalid med are needed to be seen
    	Merchandise foundMWithID = null; // if no medication with this ID exists, returns NULL
    	
    	ArrayList<Merchandise> allValidAndInvalid = this.getValidAndInvalidMerchandise();
    	for (int i = 0; i < allValidAndInvalid.size(); i ++){
    		if (allValidAndInvalid.get(i).medicationID == medicationID){
    			foundMWithID = allValidAndInvalid.get(i);
    		}
    	}
    	
    	return foundMWithID;
    }
    
    // can also be called "displayOnlyOTCMedication" for guest/home/main screen where only OTC medication information should be displayed for easy access...
    public ArrayList<Merchandise> getOnlyOTCMerchandise(){ //... for users like patients who only have access to OTCs
    	list =_merDAO.getListOfMerchandise();
    	ArrayList<Merchandise> allOTCOnlyMedication = new ArrayList<Merchandise>();
    	
        for (int i = 0; i < list.size(); i ++){
        	if (list.get(i).isOTC == true) { // only new condition different from above method
        		allOTCOnlyMedication.add(list.get(i));
        	}
        }
        
        return allOTCOnlyMedication;
    }
    
    // CODE SMELL - Large Class - moved 3 methods below related to displaying/sorting inventory to new class called "SortInventory"
    // organizes and returns inventory sorted alphabetically
    public ArrayList<Merchandise> displayAlphabetically(ArrayList<Merchandise> listToSortAlphabetically){
    	
    	return sortInv.displayAlphabetically(listToSortAlphabetically);
    }
    
    // organizes and returns inventory sorted by quantity (lowest to highest)
    public ArrayList<Merchandise> displayByQuantity(ArrayList<Merchandise> listToSortByQuantity){
    	
    	return sortInv.displayByQuantity(listToSortByQuantity);
    }
    
    // organizes and returns inventory sorted by quantity (lowest to highest)
    public ArrayList<Merchandise> displayByPrice(ArrayList<Merchandise> listToSortByPrice){
    	
    	return sortInv.displayByPrice(listToSortByPrice);
    }

    // increase quantity of medication already existing in inventory (if exists)
    public boolean increaseQuantity(int medicationID, int increasedQuantity) throws Exception{
    	
    	if (increasedQuantity < 0) {
			throw new Exception("Quantity to increase by must be a non-negative number!");
		}
    	
    	boolean medicationIncreased = false;
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	if (specificMedication == null) {
    		return medicationIncreased;
    	}
    	
    	specificMedication.quantity += increasedQuantity;
    	medicationIncreased = true;
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
        return medicationIncreased;
    }

    // decrease quantity of medication already existing in inventory, if possible (if medication exists)
    public boolean[] decreaseQuantity(int medicationID, int decreasedQuantity) throws Exception{
    	
    	if (decreasedQuantity < 0) {
			throw new Exception("Quantity to decrease by must be a non-negative number!");
		}
    	
    	boolean medicationDecreased = false;
    	boolean enoughQuantityToDecrease = true;
    	boolean itemLowInStock = false;
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null || decreasedQuantity < 0) { // if medID does not exist in inventory, can't do anything
    		// no change because want initial boolean values as above
    		boolean[] booleanArray = {medicationDecreased, enoughQuantityToDecrease, itemLowInStock};
    		//System.out.println("medication not found");
    		return booleanArray;
    	}
    	
    	
    	else { // otherwise it exists, then can potentially decrease
    		//System.out.println("medication found: " + specificMedication);
	        int potentialNewQuantity = specificMedication.quantity - decreasedQuantity; // check to see new quantity if decreased
			if (potentialNewQuantity < 0) {
	    		enoughQuantityToDecrease = false; // decrease will not occur if new quantity results in being less than 0
	    	}
	    	else { // otherwise, decrease can occur
	    		specificMedication.quantity -= decreasedQuantity;
	    		medicationDecreased = true;
	    	}
	    	
	    	// variable that keeps track if item is low in stock
	        if(specificMedication.quantity < 3){
	            itemLowInStock = true;
	        }
    	}
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	boolean[] booleanArray = {medicationDecreased, enoughQuantityToDecrease, itemLowInStock}; // return values easy for front end
    	return booleanArray;
    }
    
    // delete a medication from inventory (if exists)
    public boolean delete(int medicationID){
    	
    	boolean medicationRemoved = false;
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if medID does not exist in inventory, can't do anything
    		return medicationRemoved; // return false
    	}
    	
//    	list.remove(specificMedication);
    	specificMedication.setIsValid(false);
    	medicationRemoved = true; // remove successful
    	
    	// modify database accordingly
    	_merDAO.deleteMedicationInDatabase(medicationID);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
        
        return medicationRemoved;
    }
    
    // add a new medication to inventory (if it already doesn't exist)
    public boolean addToInventory(Merchandise m) throws Exception{
    	
    	if (m.getPrice() < 0) {
			throw new Exception("Price must be a non-negative number!");
		}
    	if (m.getQuantity() < 0) {
			throw new Exception("Quantity must be a non-negative number!");
		}
    	boolean medicationAlreadyExists = false;
    	boolean medicationAdded = false;
    	boolean medCanBeAdded = false;
    	int oldMedId = -1;
        for (int i = 0; i < list.size(); i ++){  // check in the list of valid medication
            if (list.get(i).name.equals(m.name) && list.get(i).type == m.type && list.get(i).form == m.form && list.get(i).isOTC == m.isOTC){
            	medicationAlreadyExists = true; // if same medication already exists, can't add it again bc duplicates
            }
        }
        if (medicationAlreadyExists == false) {
        	oldMedId = isMedAddedBefore(m);
        	if (oldMedId != -1) {
        	medCanBeAdded = true;
        	_merDAO.updateMedicationInDatabase(oldMedId, m);
        	medicationAdded = true;
        	//once database is updated, also updated this class's list variable by reading from database
        	list = _merDAO.getListOfMerchandise();
        	}
        }
        
        
        
//        if (medicationAlreadyExists == false) { // otherwise, can add
//        if(medCanBeAdded == true ) { // MAYBE A SUB METHOD????
//        	_merDAO.updateMedicationInDatabase(oldMedId, m);
//        	medicationAdded = true;
//        	//once database is updated, also updated this class's list variable by reading from database
//        	list = _merDAO.getListOfMerchandise();
//        }
//        else if (medCanBeAdded == false && medicationAlreadyExists == false){
        	if (medicationAlreadyExists == false && medCanBeAdded == false) {
        	list.add(m);
        	medicationAdded = true;
        	
        	  // modify database accordingly
        	_merDAO.addMedicationToDatabase(m);
        	
        	//once database is updated, also updated this class's list variable by reading from database
        	list = _merDAO.getListOfMerchandise();
        }
      
        return medicationAdded;
    }

	public int isMedAddedBefore (Merchandise m) {             // set previously deleted medication to valid
//		boolean isAddedBefore = false;
		int medID = -1;
		ArrayList<Merchandise> invalidAndValidList = new ArrayList<Merchandise>();
    	invalidAndValidList = _merDAO.getListOfValidAndInvalidMerchandise();
    	for (int j = 0; j < invalidAndValidList.size(); j ++) {
    		 if (invalidAndValidList.get(j).name.equals(m.name) && invalidAndValidList.get(j).type == m.type 
    				 && invalidAndValidList.get(j).form == m.form && invalidAndValidList.get(j).isOTC == m.isOTC){
             //	medicationAlreadyExists = true; // if same medication already exists, can't add it again bc duplicates
    			 invalidAndValidList.get(j).setIsValid(true);
    			 medID = invalidAndValidList.get(j).getMedicationID();
    			 _merDAO.updateValidInDB(invalidAndValidList.get(j).getMedicationID(),invalidAndValidList.get(j));
 //   			 isAddedBefore = true;
    			 break;
             }
    	}
    	return medID;
	}
    
    // finds from VALID list and returns a medication with medID == medicationID
    public Merchandise searchMerchandiseWithID(int medicationID){
    	Merchandise foundMWithID = null; // if no medication with this ID exists, returns NULL
    	this.updateFromDatabase();
    	for (int i = 0; i < list.size(); i ++){
    		if (list.get(i).medicationID == medicationID){
    			foundMWithID = list.get(i);
    		}
    	}
    	
    	return foundMWithID;
    }
    
    
    // OCP NOT followed for below 3 modification methods because as a group, we have decided that there are no other modifications to the medication that can be added in the future
    // modifies the name of the medication but first makes sure the same medication doesn't already exist in the system
    public boolean modifyMedicationName(int medicationID, String newName) throws Exception {
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID); // find medication with medID = medicationID
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		return false;
    	}
    	
    	// otherwise, can potentially update name but first must check to see if an identical medication with name newNAME already exists --> if does, can't make modification
    	boolean medicationAlreadyExists = false;
        for (int i = 0; i < list.size(); i ++){ //notice using newNAME
            if (list.get(i).name.equals(newName) && list.get(i).type == specificMedication.type && list.get(i).form == specificMedication.form && list.get(i).isOTC == specificMedication.isOTC){
            	medicationAlreadyExists = true;
            }
        }
        
        if (medicationAlreadyExists == true) { // if medication already exists then throw exception to front end that can't update
        	throw new Exception();
        }
        
        // otherwise, medication does not already exist and so can update its name
    	specificMedication.setName(newName);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	return true;
    }
    
    // modifies the price of the medication
    public boolean modifyMedicationPrice(int medicationID, double newPrice) throws Exception {
    	
    	if (newPrice < 0) {
			throw new Exception("Price must be a non-negative number!");
		}
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		return false;
    	}
    	
    	// otherwise, update the medication's price
    	specificMedication.setPrice(newPrice);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	return true;
    }
    
    // modifies the description of the medication
    public boolean modifyMedicationDescription(int medicationID, String newDescription) {
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		return false;
    	}
    	
    	// otherwise, update the medication's description
    	specificMedication.setDescription(newDescription);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	return true;
    }
    
    // good design to put in a method; required for front end use
    public void updateFromDatabase() {
    	this.list = _merDAO.getListOfMerchandise();
    }

}
    

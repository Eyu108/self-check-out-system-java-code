

package com.thelocalmarketplace.software;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.*;

public class InternalDatabase {
	public static final Map<String, Product> internalDataBase_Description = new HashMap<>();
	public static final Map<Product, Double> internalDataBase_weight = new HashMap<>();
	/**
	 * adds all the elements in the barcode database to the internal databases
	 * which will be used by the attendant through searching the description
	 * @param barcodedProductDatabase the database in the hardware
	 * 
	 */
	public static void addData(Map<Barcode, BarcodedProduct> barcodedProductDatabase) {
		 Set<Barcode> keySet = barcodedProductDatabase.keySet();
		List<Barcode> keyList = new ArrayList<Barcode>();
		keyList.addAll(keySet);
		for (int i = 0;i < barcodedProductDatabase.size(); i++) {
			internalDataBase_Description.put(barcodedProductDatabase.get(keyList.get(i)).getDescription(), 
					barcodedProductDatabase.get(keyList.get(i)));
			internalDataBase_weight.put(barcodedProductDatabase.get(keyList.get(i)),
					barcodedProductDatabase.get(keyList.get(i)).getExpectedWeight());
			
		}
	}
	
		/**
		 * adds all the elements in the PLU database to the internal database
		 * which will be used by the attendant through searching the description
		 * @param PLUdataBase the database in the hardware
		 * 
		 */
	public static void addDataPLU(Map<PriceLookUpCode, PLUCodedProduct> PLUdataBase) {
		Set<PriceLookUpCode> keySet = PLUdataBase.keySet();
		List<PriceLookUpCode> keyList = new ArrayList<PriceLookUpCode>();
		keyList.addAll(keySet);
		for (int i = 0;i < PLUdataBase.size(); i++) {
			internalDataBase_Description.put(PLUdataBase.get(keyList.get(i)).getDescription(), 
					PLUdataBase.get(keyList.get(i)));
			}	
		
		
	}
	
}

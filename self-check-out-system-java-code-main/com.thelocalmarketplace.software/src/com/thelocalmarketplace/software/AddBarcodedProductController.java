package com.thelocalmarketplace.software;

import java.math.BigDecimal;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Represents the software controller for adding a barcoded product
 * @author Connell Reffo / connellr023 / 10186960
 * @author Jason Nhieu / jasonnhieu2509 / 30183710
 * @author Anthony Chan / anthonyych4n / 30174703
 * @author Maria Munoz / mariamunoz99 / 30175339
 * @author Phuong Le / annaisjoy / 30175125
 * @author Mauricio Murillo / MauriMurillo / 30180713
 */
public class AddBarcodedProductController implements BarcodeScannerListener {
	
    private SelfCheckoutStationLogic logic;
    
    /**
     * Tracks the total cost of all of the added products
     * To get total cost use calculateTotalCost() as this could be behind
     */
    public long totalCost;
    
    
    /**
     * AddBarcodedProductController Constructor
     * @param logic A reference to the logic instance
     * @throws NullPointerException If logic is null
     */
    public AddBarcodedProductController(SelfCheckoutStationLogic logic) throws NullPointerException {
    	if (logic == null) {
    		throw new NullPointerException("Logic is null");
    	}
    	
        this.logic = logic;
        this.logic.hardware.scanner.register(this);
    }
    
    /**
     * Used to register barcoded products to product database
     * @param item The item to register
     * @throws SimulationException If an already registered barcode is registered again
     */
    public static void registerBarcodedProduct(BarcodedProduct item) {
    	if (ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(item.getBarcode())) {
    		throw new InvalidStateSimulationException("Barcode already in database");
    	}
    	
    	ProductDatabases.BARCODED_PRODUCT_DATABASE.put(item.getBarcode(), item);
    }
    
    /**
     * Adds a new barcode
     * If a weight discrepancy is detected, then station is blocked
     * @param barcodedItem The item to be scanned and added
     * @throws SimulationException If no barcoded items registered
     * @throws SimulationException If session not started
     * @throws SimulationException If station is blocked
     * @throws SimulationException If bagging area scale is overloaded after adding item
     * @throws NullPointerException If barcode is null
     */
    public void addBarcode(Barcode barcode) throws SimulationException, NullPointerException {
    	if (barcode == null) {
            throw new NullPointerException("Barcode is null");
        }
    	else if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode)){
    		throw new InvalidStateSimulationException("No barcoded products registered");
    	}
    	else if (!this.logic.session.isSessionStarted()) {
    		throw new InvalidStateSimulationException("The session has not been started");
    	}
    	else if (this.logic.session.isStationBlocked()) {
    		throw new InvalidStateSimulationException("Station is blocked");
    	}
    	
    	this.totalCost = this.calculateTotalCost();
		
		this.logic.session.setBalance(new BigDecimal(totalCost));
		this.logic.session.addToCart(barcode);
		
		System.out.println("Item added to cart. Please place scanned item in bagging area");
    }
    
    /**
     * Calculates the total cost of the added products
     * @return The total cost as long
     */
    public long calculateTotalCost() {
    	long total = 0;
    	
    	for (Barcode b : ProductDatabases.BARCODED_PRODUCT_DATABASE.keySet()) {
    		BarcodedProduct p = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(b);
    		
    		total += p.getPrice();
    	}
    	
    	return total;
    }
    
    @Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) throws SimulationException, NullPointerException {
    	System.out.println("A barcoded item has been scanned");
    	
    	this.addBarcode(barcode);
    	
    	System.out.println("Place item in bagging area");
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
}


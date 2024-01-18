package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;


public class WeightDiscrepancyController implements ElectronicScaleListener {
	
	private SelfCheckoutStationLogic logic;
	
	/**
	 * True if the bagging area scale is not over weight capacity; False otherwise
	 */
	private boolean operational;
	
	private Mass expectedMass;
	private Mass actualMass;
	
	
	/**
	 * WeightDiscrepancyController Constructor
	 * @param logic A reference to the logic instance
	 * @throws NullPointerException If logic is null
	 */
	public WeightDiscrepancyController(SelfCheckoutStationLogic logic) throws NullPointerException {
		if (logic == null) {
			throw new NullPointerException("Logic is null");
		}
		
		this.logic = logic;
		this.logic.hardware.baggingArea.register(this);
		this.operational = true;
		
		this.expectedMass = Mass.ZERO;
		this.actualMass = Mass.ZERO;
	}
	
	/**
	 * Checks if there is a weight discrepancy
	 * @return True if there is a discrepancy; False otherwise
	 * @throws SimulationException If session not started
	 * @throws SimulationException If the scale is not operational
	 */
	public boolean checkWeightDiscrepancy() {
		if (!this.logic.session.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session not started");
		}
		else if (!this.checkOperational()) {
			throw new InvalidStateSimulationException("Scale not operational");
		}
		
		expectedMass = this.calculateExpectedMass();
		
		Mass sens = this.logic.hardware.baggingArea.getSensitivityLimit();

		if (actualMass.compareTo(expectedMass) == 0)  {
			return false;
		}
		
		// Check if expected mass is within sensitivity limit
		// (actual-sensitivity < expected < actual+sensitivity)
		if (actualMass.difference(sens).abs().compareTo(expectedMass) <= 0) {
			if (expectedMass.compareTo(actualMass.sum(sens)) <= 0) {
				return false;
			}
		}
		
		// Notify customer
		if (actualMass.compareTo(expectedMass) > 0) {
			this.notifyOverload();
		}
		else {
			this.notifyUnderload();
		}
		
		this.logic.session.setStationBlocked(true);
		return true;
	}
	
	/**
	 * Checks if the bagging area scale is operational
	 * @return True if there is a discrepancy; False otherwise
	 * @throws SimulationException If session not started
	 */
	public boolean checkOperational() {
		if (!this.logic.session.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session not started");
		}
		
		return this.operational;
	}
	
	/**
	 * Calculates the expected mass of the items added
	 * @return The expected mass
	 * @throws SimulationException If session not started
	 * @throws SimulationException If the scale is not operational
	 */
	public Mass calculateExpectedMass() throws SimulationException {
		if (!this.logic.session.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session not started");
		}
		else if (!this.checkOperational()) {
			throw new InvalidStateSimulationException("Scale not operational");
		}
		
		Mass total = Mass.ZERO;
    	
    	for (Barcode b : this.logic.session.getCart()) {
    		BarcodedProduct p = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(b);
    		
    		total = total.sum(new Mass(p.getExpectedWeight()));
    	}
    	
    	return total;
	}
	
	/**
	 * Triggered when mass on bagging area scale is changed
	 * If a weight discrepancy is detected, system is updated accordingly
	 */
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {

		// Update actual mass
		actualMass = mass;
		
		// Check for weight discrepancy
		this.checkWeightDiscrepancy();
	}
	
	/**
	 * Triggered when actual weight is over expected weight
	 */
	private void notifyOverload() {
		System.out.println("Weight discrepancy detected. Please remove item(s)");
	}
	
	/**
	 * Triggered when actual weight is under expected weight
	 */
	private void notifyUnderload() {
		System.out.println("Weight discrepancy detected. Please add item(s)");
	}
	
	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		this.operational = false;
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		this.operational = true;
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

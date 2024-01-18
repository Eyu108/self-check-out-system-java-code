package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;


public class SelfCheckoutStationLogic {

	/**
	 * Instance of the SelfCheckoutStation hardware to be controlled
	 */
	public SelfCheckoutStation hardware;
	
	/**
	 * Instance of the session, containing relevant data for the current session
	 */
	public CustomerSession session;
	
	/**
	 * Instance of the controller that handles payment with coin for CoinSlot Component
	 */
	public SlotController slotController;
	
	/**
	 * Instance of the controller that handles payment with coin for CoinValidator Component
	 */
	public ValidatorController validatorController;
	
	/**
	 * Instance of the controller that handles session starting and stopping
	 */
	public StartSessionController startSessionController;
	
	/**
	 * Instance of the controller that handles adding barcoded product
	 */
	public AddBarcodedProductController addBarcodedProductController;
	
	/**
	 * Instance of the controller that handles weight discrepancy detected
	 */
	public WeightDiscrepancyController weightDiscrepancyController;

	/**
	 * Constructor for a new SelfCheckoutStationController instance
	 * @param hardware A reference to an instance of SelfCheckoutStation hardware
	 * @throws NullPointerException If hardware passed is null
	 */
	public SelfCheckoutStationLogic(SelfCheckoutStation hardware) throws NullPointerException {
		if (hardware == null) {
			throw new NullPointerSimulationException("Hardware");
		}
		
		this.hardware = hardware;
		this.session = new CustomerSession();
		
		this.slotController = new SlotController(this);
		this.validatorController = new ValidatorController(this);
		this.startSessionController = new StartSessionController(this);
		this.addBarcodedProductController = new AddBarcodedProductController(this);
		this.weightDiscrepancyController = new WeightDiscrepancyController(this);
	}
}

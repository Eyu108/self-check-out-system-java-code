package com.thelocalmarketplace.software;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;


public class StartSessionController {

	private SelfCheckoutStationLogic logic;
	
	
	/**
	 * Constructor for StartSessionController
	 * @param logic Reference to logic instance
	 * @throws NullPointerException If logic is null
	 */
	public StartSessionController(SelfCheckoutStationLogic logic) throws NullPointerException {
		if (logic == null) {
    		throw new NullPointerException("Logic is null");
    	}
    	
        this.logic = logic;
	}
	
	/**
	 * Marks the current self checkout session as active
	 * @throws SimulationException If the session is already active
	 */
	public void startSession() throws SimulationException {
		if (this.logic.session.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session already started");
		}
		
		this.logic.session.setSessionStarted(true);
	}
}

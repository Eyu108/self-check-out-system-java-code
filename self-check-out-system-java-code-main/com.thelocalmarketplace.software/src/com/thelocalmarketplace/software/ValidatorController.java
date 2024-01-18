package com.thelocalmarketplace.software;

import java.math.BigDecimal;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.AbstractCoinValidator;
import com.tdc.coin.CoinValidatorObserver;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Controller class for Pay Use Case for CoinValidator Component
 * 
 * Contains a reference to the logic of self checkout station
 * 
 * This class implements CoinValidator Observer, and observes when a coin is deemed valid or invalid by the system
 *  

public class ValidatorController implements CoinValidatorObserver {
	
	private SelfCheckoutStationLogic logic;
	
	
	/**
	 * Constructor for ValidatorController
	 * 
	 * sets up the reference to the logic of the station
	 * attaches the observer to coinValidator hardware
	 * 
	 * @param logic reference to SelfCheckoutStationLogic the controller belongs to
	 * @throws NullPointerException If logic is null
	 */
	public ValidatorController(SelfCheckoutStationLogic logic) throws NullPointerException {
		if (logic == null) {
    		throw new NullPointerException("Logic is null");
    	}
		
		this.logic = logic;
		logic.hardware.coinValidator.attach(this);
	}
	
	/**
	 * Notifies the user via standard output that a coin was deemed valid
	 * and updates the balance in Session
	 * @param validator, reference to the coinValidator hardware component
	 * @param value, Value of the coin accepted
	 * @throws SimulationException If session not active
	 * @throws SimulationException If station is blocked
	 * @throws SimulationException If there is an unresolved weight discrepancy
	 */
	@Override
	public void validCoinDetected(AbstractCoinValidator validator, BigDecimal value) throws SimulationException {
		if (!this.logic.session.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session not started");
		}
		else if (this.logic.session.isStationBlocked()) {
			throw new InvalidStateSimulationException("Station is blocked");
		}
		
		this.logic.weightDiscrepancyController.checkWeightDiscrepancy();

		System.out.println("Valid Coin Detected");
		
		System.out.println("Update balance");
		
		//Subtract value
		BigDecimal newValue = logic.session.getBalance().subtract(value);
		
		//Prevent balance from being negative
		if (newValue.compareTo(BigDecimal.ZERO) <= 0) {
			newValue = BigDecimal.ZERO;
		}
		
		//set new balance
		logic.session.setBalance(newValue);
		
		//Check if balance has been paid in full
		if (logic.session.getBalance().compareTo(BigDecimal.ZERO) == 0) {
			
			System.out.println("Balance paid in full");
		}
		else {
			System.out.println("Balance owed: " + logic.session.getBalance());
		}
	}
	
	/**
	 * Notifies the user via standard output that a coin was deemed invalid
	 * @param validator, reference to the coinValidator hardware component
	 */
	@Override
	public void invalidCoinDetected(AbstractCoinValidator validator) {
		System.out.println("Invalid Coin Detected");
		
		System.out.println("Balance owed: " + logic.session.getBalance());
	}
	
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
	}
	
	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
	}
	
	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
	}
	
	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
	}
}

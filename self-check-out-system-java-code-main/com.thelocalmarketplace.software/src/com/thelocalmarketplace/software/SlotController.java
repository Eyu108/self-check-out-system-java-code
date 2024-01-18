package com.thelocalmarketplace.software;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.CoinSlot;
import com.tdc.coin.CoinSlotObserver;

/**
 * Controller class for Pay Use Case for CoinSlot Component
 * 
 * Contains a reference to the logic of self checkout station
 * 
 * This class implements CoinSlot Observer, and observes when a coin is entered by the user
 * 
 * 

public class SlotController implements CoinSlotObserver {
	
	private SelfCheckoutStationLogic logic;

	/**
	 * Constructor for SlotController
	 * 
	 * sets up the reference to the logic of the station
	 * attaches the observer to coinSlot hardware
	 * 
	 * @param logic reference to SelfCheckoutStationLogic the controller belongs to
	 * @throws NullPointerException If logic is null
	 */
	public SlotController(SelfCheckoutStationLogic logic) throws NullPointerException {
		if (logic == null) {
    		throw new NullPointerException("Logic is null");
    	}
		
		this.logic = logic;
		this.logic.hardware.coinSlot.attach(this);
	}
	
	/**
	 * Notifies the user via standard output that a coin was inserted
	 * 
	 * @param slot, reference to the coinSlot hardware component
	 */
	@Override
	public void coinInserted(CoinSlot slot) {
		System.out.println("A new coin was inserted to Coin Slot");	
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

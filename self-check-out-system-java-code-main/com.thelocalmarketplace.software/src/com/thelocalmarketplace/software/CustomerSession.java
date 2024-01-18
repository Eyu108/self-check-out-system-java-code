package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.jjjwelectronics.scanner.Barcode;

public class CustomerSession {
	
	/**
     * Tracks all of the barcodes that have been scanned during the session
     */
	private List<Barcode> cart;
	
	/**
	 * Tracks the balance owed
	 */
	private BigDecimal balance;
	
	/**
	 * Tracks if the session has been started
	 */
	private boolean sessionStarted;
	
	/**
	 * Tracks if this self checkout station is blocked
	 */
	private boolean stationBlocked;
	
	//private string language
	//private boolean readAloud
	//private Audio audioIO
	
	/**
	 * Constructor for a brand new session
	 */
	public CustomerSession() {
		cart = new ArrayList<Barcode>();
		balance = BigDecimal.ZERO;
		sessionStarted = false;
		stationBlocked = false;
	}
	
	/**
	 * Gets the customer's balance
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}
	
	/**
	 * Sets the customer's balance
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	/**
	 * Checks if the customer session is started
	 * @return the sessionStarted
	 */
	public boolean isSessionStarted() {
		return sessionStarted;
	}
	
	/**
	 * Sets the status of the session
	 * @param sessionStarted the sessionStarted to set
	 */
	public void setSessionStarted(boolean sessionStarted) {
		this.sessionStarted = sessionStarted;
	}
	
	/**
	 * Checks if the station is blocked
	 * @return the sessionBlocked
	 */
	public boolean isStationBlocked() {
		return stationBlocked;
	}
	
	/**
	 * Blocks or unblocks the self checkout station
	 * @param sessionBlocked the sessionBlocked to set
	 */
	public void setStationBlocked(boolean sessionBlocked) {
		this.stationBlocked = sessionBlocked;
		
		if (sessionBlocked) {
			System.out.println("Station blocked");
		}
		else {
			System.out.println("Station unblocked");
		}
	}
	
	/**
	 * Adds a barcode to customer's cart
	 * @param item new item to add to cart
	 */
	public void addToCart(Barcode item) {
		cart.add(item);
	}
	
	/**
	 * Gets the list of barcodes scanned into the customer's cart
	 * @return A list of barcodes
	 */
	public List<Barcode> getCart() {
		return this.cart;
	}
	
}

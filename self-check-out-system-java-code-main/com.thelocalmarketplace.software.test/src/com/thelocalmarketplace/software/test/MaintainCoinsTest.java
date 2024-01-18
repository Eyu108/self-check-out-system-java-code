

package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.MaintainCoins;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.PowerGrid;

public class MaintainCoinsTest {

	private static AbstractSelfCheckoutStation testStation;
	private static MaintainCoins testMaintainCoins;
	private static Coin testCoin;
	private static Coin testCoin2;
	
	@BeforeClass
	public static void initialSetUp() {
		
		Coin.DEFAULT_CURRENCY = Currency.getInstance("CAD");
		AbstractSelfCheckoutStation.configureCoinStorageUnitCapacity(5);
		
		testStation = new SelfCheckoutStationGold();
		testStation.plugIn(PowerGrid.instance());
		testStation.turnOn();
		testStation.getCoinStorage().disable();
		
		testCoin = new Coin(new BigDecimal(1.00));
		testCoin2 = new Coin(new BigDecimal(1.00));
		
		testMaintainCoins = new MaintainCoins(testStation);		
		
	}
	
	@After
	public void removeCoins() {
		testStation.getCoinStorage().disable();
		testStation.getCoinStorage().unload();
	}
	
	/**
	 * Test whether coins are added to the coin storage unit.
	 */
	@Test
	public void addCoinTest() {
		
		try {
			testMaintainCoins.addCoins(testCoin, testCoin2);
		} catch (CashOverloadException e) {
			fail();
		}
		
		assertTrue(testStation.getCoinStorage().getCoinCount() == 2);
		
	}
	
	/**
	 * Test whether coins are added when enabled.
	 */
	@Test
	public void addCoinEnabledTest() {
		
		testStation.getCoinStorage().enable();
		try {
			testMaintainCoins.addCoins(testCoin, testCoin2);
		} catch (CashOverloadException e) {
			fail();
		} catch (InvalidStateSimulationException e2) {
			assertTrue(true);
		}
		
	}
	
	/**
	 * Test whether coins are removed.
	 */
	@Test
	public void removeCoinTest() {
		
		try {
			testMaintainCoins.addCoins(testCoin, testCoin2);
			testMaintainCoins.removeCoins(1);
		} catch (CashOverloadException e) {
			fail();
		}
		
		assertTrue(testStation.getCoinStorage().getCoinCount() == 1);
		
	}
	
	/**
	 * Test whether coins are removed when the number of coins to be removed
	 * is greater than the number of coins in the storage.
	 */
	@Test
	public void removeCoinGreaterTest() {

		try {
			testMaintainCoins.addCoins(testCoin, testCoin2);
			testMaintainCoins.removeCoins(3);
		} catch (CashOverloadException e) {
			fail();
		} catch (InvalidStateSimulationException e2) {
			assertTrue(true);
		}
	}
	
	/**
	 * Test whether coin are removed when the storage unit is enabled.
	 */
	@Test
	public void removeCoinEnabledTest() {
		
		testStation.getCoinStorage().enable();
		try {
			testMaintainCoins.addCoins(testCoin, testCoin2);
			testMaintainCoins.removeCoins(1);
		} catch (CashOverloadException e) {
			fail();
		} catch (InvalidStateSimulationException e2) {
			assertTrue(true);
		}
	}
	
}

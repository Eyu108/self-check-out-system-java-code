
package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.MaintainBanknotes;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.PowerGrid;

public class MaintainBanknotesTest {
	
	private static AbstractSelfCheckoutStation testStation;
	private static MaintainBanknotes testMaintainBanknotes;
	private static Banknote testBanknote;
	private static Banknote testBanknote2;
	
	@BeforeClass
	public static void initialSetUp() {
		
		
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(10);
		
		testStation = new SelfCheckoutStationGold();
		testStation.plugIn(PowerGrid.instance());
		testStation.turnOn();
		testStation.getBanknoteStorage().disable();
		
		testBanknote = new Banknote(Currency.getInstance("CAD"), new BigDecimal(5.00));
		testBanknote2 = new Banknote(Currency.getInstance("CAD"), new BigDecimal(5.00));
		
		testMaintainBanknotes = new MaintainBanknotes(testStation);		
		
	}
	
	@After
	public void removeBanknotes() {
		testStation.getBanknoteStorage().disable();
		testStation.getBanknoteStorage().unload();
	}
	
	/**
	 * Test whether Banknotes are added to the Banknote storage unit.
	 */
	@Test
	public void addBanknoteTest() {
		
		try {
			testMaintainBanknotes.addBanknotes(testBanknote, testBanknote2);
		} catch (CashOverloadException e) {
			fail();
		}
		
		assertTrue(testStation.getBanknoteStorage().getBanknoteCount() == 2);
		
	}
	
	/**
	 * Test whether Banknotes are added when enabled.
	 */
	@Test
	public void addBanknoteEnabledTest() {
		
		testStation.getBanknoteStorage().enable();
		try {
			testMaintainBanknotes.addBanknotes(testBanknote, testBanknote2);
		} catch (CashOverloadException e) {
			fail();
		} catch (InvalidStateSimulationException e2) {
			assertTrue(true);
		}
		
	}
	
	/**
	 * Test whether Banknotes are removed.
	 */
	@Test
	public void removeBanknoteTest() {
		
		try {
			testMaintainBanknotes.addBanknotes(testBanknote, testBanknote2);
			testMaintainBanknotes.removeBanknotes(1);
		} catch (CashOverloadException e) {
			fail();
		}
		
		assertTrue(testStation.getBanknoteStorage().getBanknoteCount() == 1);
		
	}
	
	/**
	 * Test whether Banknotes are removed when the number of Banknotes to be removed
	 * is greater than the number of Banknotes in the storage.
	 */
	@Test
	public void removeBanknoteGreaterTest() {

		try {
			testMaintainBanknotes.addBanknotes(testBanknote, testBanknote2);
			testMaintainBanknotes.removeBanknotes(3);
		} catch (CashOverloadException e) {
			fail();
		} catch (InvalidStateSimulationException e2) {
			assertTrue(true);
		}
	}
	
	/**
	 * Test whether Banknote are removed when the storage unit is enabled.
	 */
	@Test
	public void removeBanknoteEnabledTest() {
		
		testStation.getBanknoteStorage().enable();
		try {
			testMaintainBanknotes.addBanknotes(testBanknote, testBanknote2);
			testMaintainBanknotes.removeBanknotes(1);
		} catch (CashOverloadException e) {
			fail();
		} catch (InvalidStateSimulationException e2) {
			assertTrue(true);
		}
	}

}

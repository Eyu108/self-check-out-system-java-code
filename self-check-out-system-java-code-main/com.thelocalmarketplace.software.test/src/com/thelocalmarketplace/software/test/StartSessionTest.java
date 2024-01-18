

package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.software.StartSession;

import powerutility.PowerGrid;



/**
 * Test class for startSession
 * 
 *
 */

public class StartSessionTest {
	
	private AbstractSelfCheckoutStation testStation;
	private StartSession testSession;
	
	@BeforeClass
	public static void initialSetUp() {
		
	}
	
	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown() {
		
	}
	
	/**
	 * Test whether the session is ended
	 */
	@Test
	public void endTest() {
		testStation = new SelfCheckoutStationGold();
		testStation.plugIn(PowerGrid.instance());
		testStation.turnOn();
		
		try {
			testSession = new StartSession(testStation);
		} catch (Exception e) {
			fail();
		}
		
		try {
			testSession.endSession();
		} catch (OverloadedDevice e) {
			fail();
		}
		assertTrue(!testSession.isActive());
	}
	
	/**
	 * Test whether a session is started when called using a gold station
	 */
	@Test
	public void goldTest() {
		testStation = new SelfCheckoutStationGold();
		testStation.plugIn(PowerGrid.instance());
		testStation.turnOn();
		
		try {
			testSession = new StartSession(testStation);
		} catch (Exception e) {
			fail();
		}
		assertTrue(testSession.isActive());
	}
	
	/**
	 * Test whether a session is started when called using a bronze station
	 */
	@Test
	public void bronzeTest() {
		testStation = new SelfCheckoutStationBronze();
		testStation.plugIn(PowerGrid.instance());
		testStation.turnOn();
		
		try {
			testSession = new StartSession(testStation);
		} catch (Exception e) {
			fail();
		}
		assertTrue(testSession.isActive());
	}
	
	/**
	 * Test whether a session is started when called using a silver station
	 */
	@Test
	public void silverTest() {
		testStation = new SelfCheckoutStationSilver();
		testStation.plugIn(PowerGrid.instance());
		testStation.turnOn();
		
		try {
			testSession = new StartSession(testStation);
		} catch (Exception e) {
			fail();
		}
		assertTrue(testSession.isActive());
	}

    
}

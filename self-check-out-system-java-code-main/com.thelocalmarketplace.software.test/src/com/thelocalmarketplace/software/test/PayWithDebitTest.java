

package com.thelocalmarketplace.software.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.BlockedCardException;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.PayWithDebit;
import com.thelocalmarketplace.software.StartSession;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import powerutility.PowerGrid;



public class PayWithDebitTest {

    private PayWithDebit paymentProcessor;
    private CardIssuer bank;
    private Card visaCard;
    private Card mastercard;
    private Card americanExpressCard;
    private InputStream originalSystemIn;
    
    private CardSwipeData cardSwipeData;
    
    private StartSession testSession;
    private AbstractSelfCheckoutStation testStation;
    
    private Calendar calendar2;

    @Before
    public void setUp() {
    	
    	testStation = new SelfCheckoutStationGold();
    	testStation.plugIn(PowerGrid.instance());
    	testStation.turnOn();
    	try {
			testSession = new StartSession(testStation);
		} catch (OverloadedDevice | EmptyDevice e) {
			fail();
		}
    	
        paymentProcessor = new PayWithDebit(testSession);
        bank = new CardIssuer("LocalBank", 5);
        visaCard = new Card("Visa", "1234567890123456", "Card Holder", "123", null, true, false);
        mastercard = new Card("Mastercard", "2345678901234567", "Card Holder", "456", null, true, false);
        
        //bank.block(mastercard.getNumber());
        americanExpressCard = new Card("American Express", "3456789012345678", "Card Owner", "789", null, true, false);
        
        Calendar calendar = new GregorianCalendar(2030, 1, 1);
        calendar2 = new GregorianCalendar(2020, 1, 1);
        bank.addCardData("1234567890123456", "Card Holder", calendar, "123", 1000);
        bank.addCardData("2345678901234567", "Card Holder", calendar, "456", 1000);
        
        
        //americanExpressCard.setExpirationYear(2023);
        originalSystemIn = System.in;
    }

    @Test
    public void testSuccessfulDebitTransaction() {
        //CardSwipeData cardSwipeData;
    	
		try {
			cardSwipeData = visaCard.swipe();
			testSession.setTotalPrice(10);
			System.setIn(new ByteArrayInputStream("Card Holder".getBytes()));
	        paymentProcessor.PayByDebitSwipe(visaCard, bank);
		} catch (IOException e) {
			fail();
		}
        assertTrue("Payment should be successful", paymentProcessor.getIdentity());
    }

    @Test
    public void testInvalidCardholderSignature() {
    	//CardSwipeData cardSwipeData;
    	
    	try {
    		cardSwipeData = visaCard.swipe();
            System.setIn(new ByteArrayInputStream("Invalid Signature".getBytes()));
            paymentProcessor.PayByDebitSwipe(visaCard, bank);
    	} catch (IOException e) {
    		fail();
    	}
        
        assertFalse("Payment should fail with an invalid signature", paymentProcessor.getIdentity());
    }

    @Test
    public void testBlockedCardTransaction() {
    	//CardSwipeData cardSwipeData;
    	
    	try {
    		bank.block("2345678901234567");
    		testSession.setTotalPrice(10);
    		
    		cardSwipeData = mastercard.swipe();
            System.setIn(new ByteArrayInputStream("Card Holder".getBytes()));
            paymentProcessor.PayByDebitSwipe(visaCard, bank);
    	} catch (IOException e) {
    		fail();
    	}
    	assertFalse("Payment should fail with an blocked card", paymentProcessor.getIdentity());
        
    }

    @Test
    public void testExpiredCardTransaction() {
    	//CardSwipeData cardSwipeData;
    	
    	
    	try {
    		bank.addCardData("3456789012345678", "Card Owner", calendar2, "789", 1000);
	        CardSwipeData cardSwipeData = americanExpressCard.swipe();
	        System.setIn(new ByteArrayInputStream("Card Owner".getBytes()));
	        paymentProcessor.PayByDebitSwipe(visaCard, bank);
    	} catch (IOException e) {
    		fail();
    	} catch (InvalidArgumentSimulationException e) {
    		assertTrue(true);
    	}
    	
    }
}

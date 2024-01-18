

package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.Scanner;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispensationSlot;
import com.tdc.banknote.BanknoteDispensationSlotObserver;
import com.tdc.banknote.BanknoteInsertionSlot;
import com.tdc.banknote.BanknoteInsertionSlotObserver;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteDispenserBronze;
import com.tdc.banknote.BanknoteDispenserGold;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.tdc.coin.CoinSlotObserver;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinDispenserBronze;
import com.tdc.coin.CoinDispenserGold;

import java.util.Arrays;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;


public class PayWithCash implements BanknoteInsertionSlotObserver,CoinSlotObserver,BanknoteDispensationSlotObserver{
	private Scanner scanner;
	private static StartSession session;
	
	public static void setSession(StartSession session) {
		PayWithCash.session = session;
	}
	
	// So i made the methods "void", but i don't know what the other parts of the system wait for when they call this class and these methods, so if there is a need to change something, just text me (Firdovsi)
	public Coin coinInserted;
	public Banknote banknoteInserted;
	
	public void setInsertedCoin(Coin newCoin) {
		coinInserted = newCoin;
	}
	public void setInsertedBanknote(Banknote newBanknote) {
		banknoteInserted = newBanknote;
	}
	

	public void Pay(AbstractSelfCheckoutStation selfCheckoutStation) throws DisabledException, CashOverloadException{
		double totalAmountDue = session.getTotalPrice() ;
		
		BigDecimal insertedDenomination;
		while(totalAmountDue > 0) {
			System.out.println("Enter your Banknote or Coin:");
			
			insertedDenomination = scanner.nextBigDecimal();
			
			if(Arrays.asList(session.getBanknoteDenominations()).contains(insertedDenomination) || Arrays.asList(session.getCoinDenominations()).contains(insertedDenomination)){
				//banknoteSlot.receive(); So the slot should receive the inserted banknote 
				
				if(insertedDenomination.doubleValue() < 4.0) { // Case if the inserted thing is a Coin
					Coin insertedCoin = new Coin(Currency.getInstance("CAD"), insertedDenomination);
					session.getCoinSlot().receive(insertedCoin);
					session.getStation().getCoinValidator().receive(insertedCoin);
					session.getStation().getCoinStorage().receive(insertedCoin);
					
					
				}
				else if(insertedDenomination.doubleValue() > 4.0){ // Case if the inserted thing is a Banknote
					Banknote insertedBanknote = new Banknote(Currency.getInstance("CAD"),insertedDenomination);
					session.getBanknoteSlot().receive(insertedBanknote);
					session.getStation().getBanknoteValidator().receive(insertedBanknote);
					session.getStation().getBanknoteStorage().receive(insertedBanknote);
					
					
				}
			
				totalAmountDue -= insertedDenomination.doubleValue();
			}
			else {
				continue;
			}
			
		}
		
		if (totalAmountDue < 0) {
		    double change = -1 * totalAmountDue;

		    System.out.println("Change to be returned as banknotes: " + change);

		    for (Banknote banknote : session.getBanknotes()) {
		        int numBanknotes = (int) (change / banknote.getDenomination().doubleValue());
		        if (numBanknotes > 0) {
		            System.out.println("Return " + numBanknotes + " x " + banknote.getDenomination() + " banknote(s) as a change");
		            for (int i = 0; i < numBanknotes; i++) {
		                session.getStation().getBanknoteOutput().receive(banknote); // Banknote dispenser receives the banknote we are currently iterating on
		            }
		            change -= numBanknotes * banknote.getDenomination().doubleValue();
		        }
		    }
		    totalAmountDue = change * -1;
		}
		
		// I am not sure where do we get the Change money. Do we take it from the storage or is there a separate number of banknotes and coins specifically for change
		
		/*
		if (totalAmountDue < 0) {
		    double change = -1 * totalAmountDue;

		    System.out.println("Change to be returned as coins: " + change);

		    for (Coin coin : session.getCoins()) { 
		        int numCoins = (int) (change / coin.getValue().doubleValue());
		        if (numCoins > 0) {
		            System.out.println("Return " + numCoins + " x " + coin.getValue() + " coin(s) as change");
		            for (int i = 0; i < numCoins; i++) {
		                session.getStation().getCoinTray().receive(coin); // Coin tray receives the coin we are currently iterating on
		            }
		            change -= numCoins * coin.getValue().doubleValue();
		        }
		    }
		}
		*/
		
	}
		
	
	
	
// I think these things below were for observers, so we can add some automation, but i didn't fully understand how to do it


	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void banknoteInserted(BanknoteInsertionSlot slot) {
		// TODO Auto-generated method stub
		try {
			Pay(session.getStation()); // I typed this part, and the "try catch" was added by the IDE
		} catch (DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CashOverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void banknoteEjected(BanknoteInsertionSlot slot) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void banknoteRemoved(BanknoteInsertionSlot slot) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void coinInserted(CoinSlot slot) {
		// TODO Auto-generated method stub
		try {
			Pay(session.getStation()); // I typed this part, and the "try catch" was added by the IDE
		} catch (DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CashOverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void banknoteDispensed(BanknoteDispensationSlot slot, List<Banknote> banknotes) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void banknotesRemoved(BanknoteDispensationSlot slot) {
		// TODO Auto-generated method stub
		
	}
	
}

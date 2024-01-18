
package com.thelocalmarketplace.software;

import java.util.List;

import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

public class MaintainCoins {
	
	private AbstractSelfCheckoutStation station;
	
	/**
	 * Constructor using a station to allow an attendant to simulated
	 * maintaining the amount of coins in the coin storage unit.
	 * @param usedStation
	 *     Any particular self checkout station
	 */
	public MaintainCoins(AbstractSelfCheckoutStation usedStation) {
		station = usedStation;
	}

	/**
	 * Simulates manually adding coins to the coin storage unit.
	 * @param coins
	 *     At least one coin
	 *     Note: ... means you can list more than one as long as they're separated by a comma
	 * @throws CashOverloadException
	 *     If more coins are added than the storage unit can contain
	 */
	public void addCoins(Coin... coins) throws CashOverloadException {
		
		if (station.getCoinStorage().isDisabled()) {
			station.getCoinStorage().load(coins);
		} else {
			throw new InvalidStateSimulationException("Station must be disabled.");
		}
	}
	
	/**
	 * Simulates manually removing coins from the coin storage unit.
	 * @param quantity
	 *     Number of coins to be removed
	 * @return
	 *     The list of removed coins
	 * @throws CashOverloadException
	 *     If too many coins are reloaded (somehow)
	 */
	public List<Coin> removeCoins(int quantity) throws CashOverloadException {
		List<Coin> removedCoins;
		
		if ((station.getCoinStorage().isDisabled()) && (quantity <= station.getCoinStorage().getCoinCount())) {
			
			removedCoins = station.getCoinStorage().unload();
			
			int returnQuantity = removedCoins.size() - quantity;
			
			for (int i = 0; i < returnQuantity; i++) {
				station.getCoinStorage().load(removedCoins.get(removedCoins.size() - 1));
				removedCoins.remove(removedCoins.size() - 1);
			}
		} else if (quantity > station.getCoinStorage().getCoinCount()) {
			throw new InvalidStateSimulationException("Cannot take more coins out than are in the storage unit.");
		} else {
			throw new InvalidStateSimulationException("Station must be disabled.");
		}
		return removedCoins;
	}
}

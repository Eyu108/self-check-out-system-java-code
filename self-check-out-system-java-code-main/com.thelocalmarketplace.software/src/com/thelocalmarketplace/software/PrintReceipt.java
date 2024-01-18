

package com.thelocalmarketplace.software;

import java.time.LocalDate;
import java.util.ArrayList;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;



public class PrintReceipt {

	private ArrayList<String> itemList;
	private ArrayList<Long> priceList;
	private long totalPrice;
	private StartSession session;
	
	/**
	 * Constructor using a session that can be used to simulate
	 * printing a receipt
	 * @param session
	 *     Any particular session at a station
	 */
	public PrintReceipt(StartSession session) {
		this.session = session;
		itemList = session.getPickedItems();
		priceList = session.getPriceList();
		totalPrice = session.getTotalPrice();
	}	
	
	/**
	 * Prints the contents of the receipt, cuts it and removes it
	 * from the printer.
	 * @return
	 *     The receipt, simulated as a string.
	 * @throws EmptyDevice
	 *     If the printer is either low on ink or paper.
	 * @throws OverloadedDevice
	 *     If a line exceeds the width of the paper.
	 */
	public String printReceipt() throws EmptyDevice, OverloadedDevice {
		String receipt = makeReceipt();
		
		try {
			for (char c : receipt.toCharArray()) {
				session.getStation().getPrinter().print(c);
			}
		} catch (EmptyDevice e) {
			return e.getMessage();
		} catch (OverloadedDevice e) {
			return e.getMessage();
		}
		
		session.getStation().getPrinter().cutPaper();
		return session.getStation().getPrinter().removeReceipt();
	}
	
	/**
	 * Creates the contents of the receipt
	 * @return
	 *     The contents of the receipt
	 */
	private String makeReceipt() {
		/* This is roughly how the receipt should look:
		 * RECEIPT DATE: [yyyy-MM-dd]
		 * 
		 * SALE:
		 * -[list of products] - [individual price]
		 * 
		 * TOTAL: [total amount paid]
		 */
		String receipt = "";
		
		LocalDate currentDate = LocalDate.now();
		receipt += "RECEIPT DATE: " + currentDate + "\n\nSALE:\n"; 
		
		int i = 0;
		for (String s : itemList) {
			receipt += "-" + s + " - " + priceList.get(i) + "\n";
			i++;
		}
		
		receipt += "\nTOTAL: " + totalPrice;
		
		return receipt;
	}
}


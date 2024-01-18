

package com.thelocalmarketplace.software;

import com.jjjwelectronics.*;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;


public class BarcodeListenerImplement implements BarcodeScannerListener {
	private StartSession session;
	
	public BarcodeListenerImplement(StartSession session) {
		this.session = session;
		
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method
		System.out.println("a device has been enabled.");
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("a device has been disabled");
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("a device has been turned on");
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("a device has been turned off");
		
	}

	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		// TODO Auto-generated method stub
		
		session.getItemControl().addItemScanning(barcode, barcodeScanner);
		
		
	}	

}

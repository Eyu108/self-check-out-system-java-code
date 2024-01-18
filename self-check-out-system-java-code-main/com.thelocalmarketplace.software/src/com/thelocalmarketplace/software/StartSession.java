

package com.thelocalmarketplace.software;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.io.BufferedReader;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.AbstractCardReader;
import com.jjjwelectronics.scale.*;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.*;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteInsertionSlot;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserGold;
import com.tdc.coin.CoinSlot;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinValidator;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.CoinTray;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.ProductDatabases;



public class StartSession {
	private AbstractSelfCheckoutStation station;
	private AbstractElectronicScale scale;
	private AbstractCardReader cardReader;
	private IBarcodeScanner handHeldScanner;
	private IBarcodeScanner mainScanner;
	private boolean isActive;
	private BanknoteInsertionSlot cashSlot;
	
	private PayWithCash cashListener; 
	private boolean paymentSuccessful = false;
	private ItemProcessingControl itemControl;
	private WeightDiscrepancy WD;
	private PayWithDebit pay_debit;
	private PayWithCredit pay_Credit;
	private PayWithCash pay_Cash;
	private PrintReceipt printReceipt;
	
	private EScaleListenerImplement scaleListener;
	private BarcodeListenerImplement barcodeListener;
	private CardlistenerImplement cardListener;
	private AbstractElectronicScale scanScale;
	private ArrayList<String> pickedItems; //this can be used for print receipt
	private ArrayList<Long> priceList; //this can be used for print receipt
	private ArrayList<Mass> weightList;
	private long totalPrice; //this can be used for print receipt
	private Mass expectedWeight = new Mass(0);
	private AttendantControl attendantControl;
	

	
	
	
	private Currency cad = Currency.getInstance("CAD");
	
	private BigDecimal[] banknoteDenominations = {new BigDecimal("5"),new BigDecimal("10"),new BigDecimal("20"),new BigDecimal("50"),new BigDecimal("100")};
	private Banknote banknote5 = new Banknote(cad, banknoteDenominations[0]); Banknote banknote10 = new Banknote(cad, banknoteDenominations[1]); Banknote banknote20 = new Banknote(cad, banknoteDenominations[2]); Banknote banknote50 = new Banknote(cad, banknoteDenominations[3]); Banknote banknote100 = new Banknote(cad, banknoteDenominations[4]);
	private Banknote[] banknotes = {banknote5, banknote10, banknote20 ,banknote50, banknote100};
	
	
	private BigDecimal[] coinDenominations = {new BigDecimal("0.01"), new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal("1.0"), new BigDecimal("2.0")};
	private Coin coin1 = new Coin(cad, coinDenominations[0]); Coin coin5 = new Coin(cad, coinDenominations[1]); Coin coin10 = new Coin(cad, coinDenominations[2]); Coin coin25 = new Coin(cad, coinDenominations[3]); Coin coin100 = new Coin(cad, coinDenominations[4]); Coin coin200 = new Coin(cad, coinDenominations[5]);
	private Coin[] coins = {coin5,coin10,coin25,coin100,coin200};
	
	private BanknoteInsertionSlot banknoteSlot = new BanknoteInsertionSlot();
	private BanknoteValidator banknoteValidator = new BanknoteValidator(cad,banknoteDenominations);
	//BanknoteStorageUnit banknoteStorage = new BanknoteStorageUnit();
	
	private CoinSlot coinSlot = new CoinSlot();
	private CoinValidator coinValidator = new CoinValidator(cad,Arrays.asList(coinDenominations));
	
	
	public StartSession(AbstractSelfCheckoutStation input_station) throws OverloadedDevice, EmptyDevice {
		setStation(input_station);
		setScale((AbstractElectronicScale)station.getBaggingArea());
		cardReader = (AbstractCardReader)station.getCardReader();
		setHandHeldScanner(station.getHandheldScanner());
		setMainScanner(station.getMainScanner());
		setScanScale((AbstractElectronicScale) station.getScanningArea());
		
		/* 
		 * Turn on should not be called in StartSession, the selfCheckoutStation being turned on
		 * should be necessary for StartSession to be called in the first place.
		 * Dongwen
		*/
		//station.turnOn();
		
		setWD(new WeightDiscrepancy(this));
		setItemControl(new ItemProcessingControl(this));
		pay_debit = new PayWithDebit(this);
		pay_Credit = new PayWithCredit(this);
		printReceipt = new PrintReceipt(this);
		
		setScaleListener(new EScaleListenerImplement(this));
		barcodeListener = new BarcodeListenerImplement(this);
		cardListener = new CardlistenerImplement();
		
		PayWithCash.setSession(this);
		cashSlot = input_station.getBanknoteInput();

		getScale().register(getScaleListener());
		cardReader.register(cardListener);
		getHandHeldScanner().register(barcodeListener);
		getMainScanner().register(barcodeListener);
		cashSlot.attach(new PayWithCash());
		isActive = true;
		getScanScale().register(getScaleListener());
		
		/*
		 * The welcome screen should already be displayed before startSession is called
		 * (The screen should be displayed when the station is turned on eg. when aComponentWasTurnedOn
		 * event is called, there was some event like that in the hardware)
		 * 
		 * Listen for input should also not be here, instead, StartSession should be called after there is input, 
		 * instead of listening for input after startSession is called
		 * 
		 * Dongwen
		 */
		//displaySplashScreen();
		//listenForInput();
		
		
		input_station.configureBanknoteDenominations(banknoteDenominations);
		input_station.configureCoinDenominations(coinDenominations);
		input_station.configureCurrency(cad);
		
		
		
		
	}
	
        
   
    public void displaySplashScreen() throws OverloadedDevice {
        System.out.println("Press anywhere to start");
        getHandHeldScanner().enable();
        getMainScanner().enable();
        cardReader.enable();
        getScale().enable();
        cashSlot.enable();
        station.getCoinSlot().enable();
    }
     public void listenForInput() throws OverloadedDevice {
        System.out.println("waiting......");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            br.readLine();
            System.out.println("Session started");
            while(isActive){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            System.err.println("Error reading from user");
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return isActive;
    }
    
  
 
    public void endSession() throws OverloadedDevice {
    	 getHandHeldScanner().disable();
         getMainScanner().disable();
         cardReader.disable();
         getScale().disable();
         cashSlot.disable();
         station.getCoinSlot().disable();   //changes need to be made
         station.getScanningArea().disable();
         
         /*
          * lol, someone forgot to change isActive to false when ending the session, I had to add it in
          * 
          * Dongwen
          */
         isActive = false;
        
    }
	
    public BigDecimal[] getBanknoteDenominations() {
    	return banknoteDenominations;
    }
    
    public BigDecimal[] getCoinDenominations() {
    	return coinDenominations;
    }
    
    public CoinSlot getCoinSlot() {
    	return coinSlot;
    }
    
    public BanknoteInsertionSlot getBanknoteSlot() {
    	return banknoteSlot;
    }
    


	public AbstractSelfCheckoutStation getStation() {
		return station;
	}
	
	public Banknote[] getBanknotes() {
		return banknotes;
	}
	
	public Coin[] getCoins() {
		return coins;
	}



	public void setStation(AbstractSelfCheckoutStation station) {
		this.station = station;
	}



	public ItemProcessingControl getItemControl() {
		return itemControl;
	}



	public void setItemControl(ItemProcessingControl itemControl) {
		this.itemControl = itemControl;
	}



	public AbstractElectronicScale getScanScale() { // for the plu items
		return scanScale;
	}



	public void setScanScale(AbstractElectronicScale scanScale) {
		this.scanScale = scanScale;
	}



	public AbstractElectronicScale getScale() {
		return scale;
	}



	public void setScale(AbstractElectronicScale scale) {
		this.scale = scale;
	}



	public IBarcodeScanner getHandHeldScanner() {
		return handHeldScanner;
	}



	public void setHandHeldScanner(IBarcodeScanner handHeldScanner) {
		this.handHeldScanner = handHeldScanner;
	}



	public IBarcodeScanner getMainScanner() {
		return mainScanner;
	}



	public void setMainScanner(IBarcodeScanner mainScanner) {
		this.mainScanner = mainScanner;
	}





	public ArrayList<String> getPickedItems() {
		return pickedItems;
	}



	public void setPickedItems(ArrayList<String> pickedItems) {
		this.pickedItems = pickedItems;
	}



	public long getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}



	public ArrayList<Long> getPriceList() {
		return priceList;
	}



	public void setPriceList(ArrayList<Long> priceList) {
		this.priceList = priceList;
	}



	public Mass getExpectedWeight() {
		return expectedWeight;
	}



	public void setExpectedWeight(Mass expectedWeight) {
		this.expectedWeight = expectedWeight;
	}



	public ArrayList<Mass> getWeightList() {
		return weightList;
	}



	public void setWeightList(ArrayList<Mass> weightList) {
		this.weightList = weightList;
	}



	public WeightDiscrepancy getWD() {
		return WD;
	}



	public void setWD(WeightDiscrepancy wD) {
		WD = wD;
	}



	public EScaleListenerImplement getScaleListener() {
		return scaleListener;
	}



	public void setScaleListener(EScaleListenerImplement scaleListener) {
		this.scaleListener = scaleListener;
	}



	public AttendantControl getAttendantControl() {
		return attendantControl;
	}



	public void setAttendantControl(AttendantControl attendantControl) {
		this.attendantControl = attendantControl;
	}

	
	
	
}

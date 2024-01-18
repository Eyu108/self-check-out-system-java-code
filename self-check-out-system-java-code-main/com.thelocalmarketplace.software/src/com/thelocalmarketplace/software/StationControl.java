

package com.thelocalmarketplace.software;
public class StationControl {
    private boolean isEnabled;

    public StationControl() {
        isEnabled = false; // Stations start as disabled
    }

    public void enableStation() {
        isEnabled = true;
        System.out.println("Station enabled."); 
    }

    public void disableStation() {
        isEnabled = false;
        System.out.println("Station disabled.");
    }

    public boolean isStationEnabled() {
        return isEnabled;
    }
}

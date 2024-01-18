package com.thelocalmarketplace.software;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.thelocalmarketplace.software.gui.AttendantStationGUI;

public class AttendantControl {
    private boolean WDAlarm = false;
    private boolean WDdecision;
    private String attendantSearchResult;
    private StartAttendantSession session;

    public AttendantControl(StartAttendantSession session) {
        this.session = session;
    }

    public void attendantSearchItem(String searchResults) { 
        attendantSearchResult = searchResults; 
        Set<String> keySet = InternalDatabase.internalDataBase_Description.keySet();
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);
        for(int i = 0; i < keySet.size(); i++) {
            if (keyList.get(i).contains(attendantSearchResult)){
                System.out.print(keyList.get(i));
            }
        }
    }

    public void sendWDMessage() {
        WDAlarm = true;
        session.runWDAlarm();
    }

    public void setWDDecision(String decision) {
        if ("yes".equals(decision))
            this.WDdecision = true;
        else
            this.WDdecision = false;
    }

    public boolean getWDDecision() {
        return this.WDdecision;
    }

    public void setAttendantStationGUI(AttendantStationGUI attendantStationGUI) {
        // TODO Auto-generated method stub
    }
}

package com.thelocalmarketplace.software;

import com.thelocalmarketplace.software.GUI.AttendantStationGUI;

public class StartAttendantSession {
	private AttendantStationGUI attendantGUI;
	private AttendantControl ATcontrol;
	private StartSession costumerSession1;
	private StartSession costumerSession2;  // three different sessions
	private StartSession costumerSession3;
	
	public StartAttendantSession(StartSession costumerSession1) {
		this.setCostumerSession1(costumerSession1);

		setAttendantGUI(new AttendantStationGUI(this));
		ATcontrol = new AttendantControl(this);
		
		
	}

	public AttendantControl getATControl() {
		return ATcontrol;
	}

	public void setATControl(AttendantControl control) {
		this.ATcontrol = control;
	}


	public StartSession getCostumerSession1() {
		return costumerSession1;
	}

	public void setCostumerSession1(StartSession costumerSession1) {
		this.costumerSession1 = costumerSession1;
	}

	public StartSession getCostumerSession2() {
		return costumerSession2;
	}

	public void setCostumerSession2(StartSession costumerSession2) {
		this.costumerSession2 = costumerSession2;
	}

	public StartSession getCostumerSession3() {
		return costumerSession3;
	}

	public void setCostumerSession3(StartSession costumerSession3) {
		this.costumerSession3 = costumerSession3;
	}

	public AttendantStationGUI getAttendantGUI() {
		return attendantGUI;
	}

	public void setAttendantGUI(AttendantStationGUI attendantGUI) {
		this.attendantGUI = attendantGUI;
	}
	
}

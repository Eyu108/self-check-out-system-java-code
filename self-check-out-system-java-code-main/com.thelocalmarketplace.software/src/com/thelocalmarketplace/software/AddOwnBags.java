

package com.thelocalmarketplace.software;

import com.jjjwelectronics.*;
import com.thelocalmarketplace.hardware.*;
import com.tdc.*;
import com.thelocalmarketplace.software.StartSession;
import com.jjjwelectronics.scale.*;
import com.jjjwelectronics.*;

public class AddOwnBags extends Item {

    private Mass maxBagMass;

    private long price;

    public AddOwnBags(Mass mass) {
        super(mass);
        price = 0;
        ;
    }

    public long getPrice() {
        return price;
    }

    public void setMaxBagMass(Mass max) {
        maxBagMass = max;
    }

    public void addOwnBag() {
        if (maxBagMass.compareTo(this.getMass()) >= 0) {
            StartSession.getShoppingCart().add(this);
            StartSession.updateExpectedMass(StartSession.getExpectedMass().sum(this.getMass()));
        }
    }
}


package com.thelocalmarketplace.software;

import java.util.HashMap;
import java.util.Map;

public class MembershipDatabase {
	/**
     * Instances of this class are not needed, so the constructor is private.
     */
    private MembershipDatabase() {}

    /**
     * Map containing registered memberships, where the key is the membership number.
     */
    public static final Map<String, Membership> REGISTERED_MEMBERS = new HashMap<>();

    /**
     * Retrieves a membership from the database based on the provided membership number.
     *
     * @param membershipNumber The membership number to look up.
     * @return The Membership object associated with the membership number, or null if not found.
     */
    public static Membership getMembership(String membershipNumber) {
        return REGISTERED_MEMBERS.get(membershipNumber);
    }

    /**
     * Adds a new membership to the database.
     *
     * @param membership The Membership object to add.
     */
    public static void addMembership(Membership membership) {
        REGISTERED_MEMBERS.put(membership.getMembershipNumber(), membership);
    }

    /**
     * Updates the points for a specific membership.
     *
     * @param membershipNumber The membership number of the member.
     * @param newPoints The new points value.
     */
    public static void updatePoints(String membershipNumber, int newPoints) {
        Membership membership = REGISTERED_MEMBERS.get(membershipNumber);
        if (membership != null) {
            membership.setPoints(newPoints);
        }
    }

}

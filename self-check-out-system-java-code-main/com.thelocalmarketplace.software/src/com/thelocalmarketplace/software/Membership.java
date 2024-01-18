
package com.thelocalmarketplace.software;

/**
 * Represents a membership in the self-checkout system.
 */

public class Membership {
	private String membershipNumber; // Unique identifier for the membership
    private int points; // Accumulated points for the member

    /**
     * Constructs a new Membership with the given membership number.
     *
     * @param membershipNumber The unique membership number.
     */
    public Membership(String membershipNumber) {
        this.membershipNumber = membershipNumber;
        this.points = 0; // Initialize points to zero
    }

    /**
     * Gets the membership number.
     *
     * @return The membership number.
     */
    public String getMembershipNumber() {
        return membershipNumber;
    }

    /**
     * Gets the accumulated points for the member.
     *
     * @return The accumulated points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the points for the member.
     *
     * @param points The new points value.
     * @throws IllegalArgumentException If points is negative.
     */
    public void setPoints(int points) {
        if (points >= 0) {
            this.points = points;
        } else {
            throw new IllegalArgumentException("Points cannot be negative.");
        }
    }

    @Override
    public String toString() {
        return "Membership{" +
                "membershipNumber='" + membershipNumber + '\'' +
                ", points=" + points +
                '}';
    }

}



package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.Membership;

public class MembershipTest {
	
	private Membership membership;

    @Before
    public void setUp() {
        // Create a Membership instance before each test
        membership = new Membership("123456789");
    }

    @Test
    public void testMembershipCreation() {
        assertNotNull("Membership instance should not be null", membership);
        assertEquals("Initial points should be zero", 0, membership.getPoints());

        System.out.println("Test: Membership Creation");
        System.out.println("Membership created successfully with initial points: " + membership.getPoints());
    }

    @Test
    public void testGetMembershipNumber() {
        assertEquals("Membership number should match", "123456789", membership.getMembershipNumber());

        System.out.println("Test: Get Membership Number");
        System.out.println("Membership number retrieved successfully: " + membership.getMembershipNumber());
    }

    @Test
    public void testSetPoints() {
        // Set and verify points
        membership.setPoints(100);
        assertEquals("Points should be updated", 100, membership.getPoints());
        System.out.println("Test: Set Points");
        System.out.println("Points set successfully. Updated points: " + membership.getPoints());

        // Try setting negative points (should throw an exception)
        try {
            membership.setPoints(-50);
        } catch (IllegalArgumentException e) {
            // Exception expected, points should remain unchanged
        }
        assertEquals("Points should remain unchanged after invalid update", 100, membership.getPoints());
        System.out.println("Attempted to set negative points. Points remained unchanged: " + membership.getPoints());
    }
}

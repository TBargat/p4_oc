package com.dummy.myerp.testbusiness.business;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Spring Context initialization class
 */
public class TestInitSpring extends BusinessTestCase {

	/**
	 * Constructor.
	 */
	public TestInitSpring() {
		super();
	}

	/**
	 * Test the initialization of the context
	 */
	@Test
	public void testInit() {
		SpringRegistry.init();
		assertNotNull(SpringRegistry.getBusinessProxy());
		assertNotNull(SpringRegistry.getTransactionManager());
	}
}

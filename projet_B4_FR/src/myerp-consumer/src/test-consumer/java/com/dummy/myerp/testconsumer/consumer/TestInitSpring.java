package com.dummy.myerp.testconsumer.consumer;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Spring Context initialization class
 */
public class TestInitSpring extends ConsumerTestCase {

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
		assertNotNull(SpringRegistry.getDaoProxy());
	}
}

package com.dummy.myerp.testconsumer.consumer;



import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Classe de test de l'initialisation du contexte Spring
 */
class TestInitSpring extends ConsumerTestCase {

    /**
     * Constructeur.
     */
    TestInitSpring() {
        super();
    }


    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    void testInit() {
        SpringRegistry.init();
        assertNotNull(SpringRegistry.getDaoProxy());
    }
}

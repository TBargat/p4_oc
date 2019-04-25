package com.dummy.myerp.consumer;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


/**
 * Help class for the classes of the Consumer layer
 */
public abstract class ConsumerHelper {

    /** The DaoProxy to use to access the method of the Dao */
    private static DaoProxy daoProxy;


    // ==================== Constructors ====================
    /**
     * Configuration method for the class
     *
     * @param pDaoProxy     -
     */
    public static void configure(DaoProxy pDaoProxy) {
        daoProxy = pDaoProxy;
    }


    // ==================== Getters/Setters ====================
    public static DaoProxy getDaoProxy() {
        return daoProxy;
    }
}

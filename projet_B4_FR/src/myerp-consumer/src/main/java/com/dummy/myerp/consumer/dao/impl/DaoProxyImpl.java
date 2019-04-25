package com.dummy.myerp.consumer.dao.impl;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


/**
 * <p>Implementation of the proxy to access the DAO</p>
 */
public final class DaoProxyImpl implements DaoProxy {

    // ==================== Attributes ====================
    /** {@link ComptabiliteDao} */
    private ComptabiliteDao comptabiliteDao;


    // ==================== Constructors ====================
    /** Singleton Design Pattern to get a single instance */
    private static final DaoProxyImpl INSTANCE = new DaoProxyImpl();

    /**
     * Return the instance of the Singleton
     *
     * @return {@link DaoProxyImpl}
     */
    protected static DaoProxyImpl getInstance() {
        return DaoProxyImpl.INSTANCE;
    }

    /**
     * Constructor.
     */
    private DaoProxyImpl() {
        super();
    }


    // ==================== Getters/Setters ====================
    public ComptabiliteDao getComptabiliteDao() {
        return this.comptabiliteDao;
    }
    public void setComptabiliteDao(ComptabiliteDao pComptabiliteDao) {
        this.comptabiliteDao = pComptabiliteDao;
    }
}

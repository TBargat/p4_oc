package com.dummy.myerp.business.impl;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <p>Transaction manager for the data layer</p>
 */
public class TransactionManager {

    // ==================== Static Attributes ====================
    /** PlatformTransactionManager for the DataSource MyERP */
    private static PlatformTransactionManager ptmMyERP;


    // ==================== Constructors ====================
    /** Singleton Design Pattern to have a single instance */
    private static final TransactionManager INSTANCE = new TransactionManager();
    /**
     * Return the proxy to access the Business Layer
     *
     * @return {@link TransactionManager}
     */
    public static TransactionManager getInstance() {
        return TransactionManager.INSTANCE;
    }
    /**
     * Return the proxy to access the Business Layer
     *
     * @param pPtmMyERP -
     * @return {@link TransactionManager}
     */
    public static TransactionManager getInstance(PlatformTransactionManager pPtmMyERP) {
        ptmMyERP = pPtmMyERP;
        return TransactionManager.INSTANCE;
    }
    /**
     * Constructor.
     */
    protected TransactionManager() {
        super();
    }


    // ==================== Methods ====================
    /**
     * Start a transaction on the DataSource MyERP
     *
     * @return TransactionStatus to give to the methods :
     *      <ul>
     *          <li>{@link #commitMyERP(TransactionStatus)}</li>
     *              <li>{@link #rollbackMyERP(TransactionStatus)}</li>
     *      </ul>
     */
    public TransactionStatus beginTransactionMyERP() {
        DefaultTransactionDefinition vTDef = new DefaultTransactionDefinition();
        vTDef.setName("Transaction_txManagerMyERP");
        vTDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return ptmMyERP.getTransaction(vTDef);
    }

    /**
     * Commit the transacton to the DataSource MyERP
     *
     * @param pTStatus returned by the method {@link #beginTransactionMyERP()}
     */
    public void commitMyERP(TransactionStatus pTStatus) {
        if (pTStatus != null) {
            ptmMyERP.commit(pTStatus);
        }
    }

    /**
     * Rollback the transaction on the DataSource MyERP
     *
     * @param pTStatus returned by the method {@link #beginTransactionMyERP()}
     */
    public void rollbackMyERP(TransactionStatus pTStatus) {
        if (pTStatus != null) {
            ptmMyERP.rollback(pTStatus);
        }
    }
}

package com.dummy.myerp.business.impl;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


/**
 * <p>Abstract class for the Managers</p>
 */
public abstract class AbstractBusinessManager {

    /** Proxy to access the Business layer */
    private static BusinessProxy businessProxy;
    /** Proxy to access the Consumer-DAO layer */
    private static DaoProxy daoProxy;
    /** Transaction manager */
    private static TransactionManager transactionManager;


    // ==================== Constructeurs ====================

    /**
     * Configuration method for the class
     *
     * @param pBusinessProxy      -
     * @param pDaoProxy           -
     * @param pTransactionManager -
     */
    public static void configure(BusinessProxy pBusinessProxy,
                                 DaoProxy pDaoProxy,
                                 TransactionManager pTransactionManager) {
        businessProxy = pBusinessProxy;
        daoProxy = pDaoProxy;
        transactionManager = pTransactionManager;
    }


    // ==================== Getters/Setters ====================

    /**
     * Return the proxy to access the Business Layer
     *
     * @return {@link BusinessProxy}
     */
    protected BusinessProxy getBusinessProxy() {
        return businessProxy;
    }


    /**
     * Return the proxy to access the Consumer-DAO Layer
     *
     * @return {@link DaoProxy}
     */
    protected DaoProxy getDaoProxy() {
        return daoProxy;
    }


    /**
     * Return the Transaction Manager
     *
     * @return TransactionManager
     */
    protected TransactionManager getTransactionManager() {
        return transactionManager;
    }


    /**
     * Return {@link Validator} for constraints
     *
     * @return Validator
     */
    protected Validator getConstraintValidator() {
        Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
        ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
        Validator vValidator = vFactory.getValidator();
        return vValidator;
    }
}

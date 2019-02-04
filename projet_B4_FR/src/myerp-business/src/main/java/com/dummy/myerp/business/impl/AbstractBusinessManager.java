package com.dummy.myerp.business.impl;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * <p>Classe mère des Managers</p>
 */
public abstract class AbstractBusinessManager {


    /** Le gestionnaire de Transaction */
    private static TransactionManager transactionManager;


    // ==================== Constructeurs ====================

    /**
     * Méthode de configuration de la classe

     * @param pTransactionManager -
     */
    public static void configure(TransactionManager pTransactionManager) {
        transactionManager = pTransactionManager;
    }


    // ==================== Getters/Setters ====================






    /**
     * Renvoie le gestionnaire de Transaction
     *
     * @return TransactionManager
     */
    protected TransactionManager getTransactionManager() {
        return transactionManager;
    }


    /**
     * Renvoie un {@link Validator} de contraintes
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

package com.dummy.myerp.consumer.dao.contrat;

/**
 * Interface of the Proxy to access the Dao
 */
public interface DaoProxy {

    /**
     * Return a {@link ComptabiliteDao}
     *
     * @return {@link ComptabiliteDao}
     */
	
    ComptabiliteDao getComptabiliteDao();

}

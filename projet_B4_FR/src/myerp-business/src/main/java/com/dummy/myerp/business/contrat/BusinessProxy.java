package com.dummy.myerp.business.contrat;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;


/**
 * <p>Proxy interface to access the Business layer</p>
 */
public interface BusinessProxy {

    // ==================== Managers ====================

    /**
     * Return the manager.
     *
     * @return ComptabiliteManager
     */
    ComptabiliteManager getComptabiliteManager();
    
    //DaoProxy getDaoProxy();
}

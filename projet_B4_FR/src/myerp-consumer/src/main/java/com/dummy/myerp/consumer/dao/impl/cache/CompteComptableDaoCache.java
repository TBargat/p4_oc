package com.dummy.myerp.consumer.dao.impl.cache;

import java.util.List;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;


/**
 * DAO Cache of {@link CompteComptable}
 */
public class CompteComptableDaoCache {

    // ==================== Attributes ====================
    /** The Compte Comptable List */
    private List<CompteComptable> listCompteComptable;


    // ==================== Constructors ====================
    /**
     * Instantiates a new Compte Comptable dao cache.
     */
    public CompteComptableDaoCache() {
    }


    // ==================== Methods ====================
    /**
     * Get the Compte Compatble by its Numero
     *
     * @param pNumero the Numero
     * @return {@link CompteComptable} or {@code null}
     */
    public CompteComptable getByNumero(Integer pNumero) {
        if (listCompteComptable == null) {
            listCompteComptable = ConsumerHelper.getDaoProxy().getComptabiliteDao().getListCompteComptable();
        }

        CompteComptable vRetour = CompteComptable.getByNumero(listCompteComptable, pNumero);
        return vRetour;
    }
}

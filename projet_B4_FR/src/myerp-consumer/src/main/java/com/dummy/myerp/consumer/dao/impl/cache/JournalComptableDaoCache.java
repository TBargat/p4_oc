package com.dummy.myerp.consumer.dao.impl.cache;

import java.util.List;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;


/**
 * DAO cache of {@link JournalComptable}
 */
public class JournalComptableDaoCache {

    // ==================== Attributes ====================
    /** The Compte Comptable List */
    private List<JournalComptable> listJournalComptable;


    // ==================== Constructors ====================
    /**
     * Instantiates a new Journal Comptable Dao cache.
     */
    public JournalComptableDaoCache() {
    }


    // ==================== Methods ====================
    /**
     * Get Journal Comptable by its Code
     *
     * @param pCode the code of {@link JournalComptable}
     * @return {@link JournalComptable} or {@code null}
     */
    public JournalComptable getByCode(String pCode) {
        if (listJournalComptable == null) {
            listJournalComptable = ConsumerHelper.getDaoProxy().getComptabiliteDao().getListJournalComptable();
        }

        JournalComptable vRetour = JournalComptable.getByCode(listJournalComptable, pCode);
        return vRetour;
    }
}

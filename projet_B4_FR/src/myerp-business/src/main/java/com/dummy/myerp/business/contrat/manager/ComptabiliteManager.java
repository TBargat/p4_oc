package com.dummy.myerp.business.contrat.manager;

import java.util.List;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;


/**
 * Interface of the Manager
 */
public interface ComptabiliteManager {

    /**
     * Return the list of Compte Comptable
     *
     * @return {@link List}
     */
    List<CompteComptable> getListCompteComptable();


    /**
     * Return the list of Journal Comptable
     *
     * @return {@link List}
     */
    List<JournalComptable> getListJournalComptable();


    /**
     * Return the list of Ecriture Comptable
     *
     * @return {@link List}
     */
    List<EcritureComptable> getListEcritureComptable();
    
    SequenceEcritureComptable getSequenceECByJournalCodeAndAnnee(String pJournalCode, Integer pAnnee) throws NotFoundException, TechnicalException, FunctionalException;

    /**
     * Add a reference to the Ecriture Comptable
     *
     * <strong>Rule to respect - RG_Compta_5 : </strong>
     * La référence d'une écriture comptable est composée du code du journal dans lequel figure l'écriture
     * suivi de l'année et d'un numéro de séquence (propre à chaque journal) sur 5 chiffres incrémenté automatiquement
     * à chaque écriture. Le formatage de la référence est : XX-AAAA/#####.
     * <br>
     * Ex : Journal de banque (BQ), Ecriture on the 31/12/2016
     * <pre>BQ-2016/00001</pre>
     *
     * <p><strong>Beware :</strong> the Ecriture is not saved on the DB</p>
     * @param pEcritureComptable The Ecriture Comptable used
     */
    void addReference(EcritureComptable pEcritureComptable) throws NotFoundException, TechnicalException, FunctionalException;

    /**
     * Check that the Ecriture Compatble meets the requirements set by the Rules
     *
     * @param pEcritureComptable -
     * @throws FunctionalException If the Ecriture Comptable does not meet the requirements
     */
    void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;

    /**
     * Insert a new Ecriture Comptable
     *
     * @param pEcritureComptable -
     * @throws FunctionalException If the Ecriture Comptable does not meet the requirements
     */
    void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;
    

    /**
     * Update an Ecriture Comptable
     *
     * @param pEcritureComptable -
     * @throws FunctionalException If the Ecriture Comptable does not meet the requirements
     */
    void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;

    /**
     * Delete the Ecriture Comptable with the id {@code pId}.
     *
     * @param pId The ID of the Ecriture Comptable
     */
    void deleteEcritureComptable(Integer pId);
    
    /**
     * Utility method to format the value of a reference
     */
    public String leftPad(int n, int padding);
}

package com.dummy.myerp.consumer.dao.contrat;

import java.util.List;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;

/**
 * DAO Interface 
 */
public interface ComptabiliteDao {

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

	// ==================== EcritureComptable ====================

	/**
	 * Return the list of Écriture Comptable
	 * 
	 * @return {@link List}
	 */
	List<EcritureComptable> getListEcritureComptable();

	/**
	 * Return the Écriture Comptable with the id {@code pId}.
	 *
	 * @param pId the id of the Ecriture Comptable
	 * @return {@link EcritureComptable}
	 * @throws NotFoundException : If the Ecriture Comptable is not found
	 */
	EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException;

	/**
	 * Return the Écriture Comptable with the reference {@code pRef}.
	 *
	 * @param pReference the reference of the Ecriture Comptable
	 * @return {@link EcritureComptable}
	 * @throws NotFoundException : If the Ecriture Comptable is not found
	 */
	EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException;

	/**
	 * Load the list of Ligne d'Ecriture of the Ecriture Comptable
	 * {@code pEcritureComptable}
	 *
	 * @param pEcritureComptable -
	 */
	void loadListLigneEcriture(EcritureComptable pEcritureComptable);

	/**
	 * Insert a new Ecriture Comptable
	 *
	 * @param pEcritureComptable -
	 */
	void insertEcritureComptable(EcritureComptable pEcritureComptable);

	/**
	 * Update the Ecriture Comptable.
	 *
	 * @param pEcritureComptable -
	 */
	void updateEcritureComptable(EcritureComptable pEcritureComptable);

	/** The Ecriture Comptable with the id {@code pId}.
	 *
	 * @param pId the id of the Ecriture Comptable
	 */
	void deleteEcritureComptable(Integer pId);

	SequenceEcritureComptable getSequenceECByJournalCodeAndAnnee(String pJournalCode, Integer pAnnee)
			throws NotFoundException, TechnicalException, FunctionalException;

	void insertOrUpdateSequenceEC(SequenceEcritureComptable pSequenceEC);
	
	
	
}

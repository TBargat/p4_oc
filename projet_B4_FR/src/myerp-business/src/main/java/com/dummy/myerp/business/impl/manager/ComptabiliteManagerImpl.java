package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.TransactionStatus;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;


/**
 * Comptabilite Manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Constructors ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     * @throws FunctionalException 
     * @throws TechnicalException 
     * @throws NotFoundException 
     */
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) throws NotFoundException, TechnicalException, FunctionalException {
    	// Year of the date of pEcritureComptable
    	Date dateEC = pEcritureComptable.getDate();
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
    	String yearEC = simpleDateFormat.format(dateEC).toUpperCase();
    	
    	//Code Journal of pEcritureComptable
    	String codeJournalEC = pEcritureComptable.getJournal().getCode();
    	
    	// We take the last value of the Sequence on the table sequence_ecriture_comptable 
    	// and add 1 to it unless there is no sequence yet for that year. Then the value is 1.
    	
    	int newValue = 1;
    	try {
    		SequenceEcritureComptable lastSECused = getDaoProxy().getComptabiliteDao().getSequenceECByJournalCodeAndAnnee(codeJournalEC, Integer.valueOf(yearEC));
    		if (lastSECused != null) {
        		int lastValue = lastSECused.getDerniereValeur();
        		newValue = lastValue + 1;
        	} 
    	}
    	// We catch the NotFound Exception in the second case, to avoid a mistake
    	catch (NotFoundException vException) { 
    		
    	}
    
    	// We update the reference to meet the requirements of "RG 5"
    	String reference = codeJournalEC + "-" + yearEC + "/" + leftPad(newValue, 5)  ;
    	
    	pEcritureComptable.setReference(reference);
    	this.updateEcritureComptable(pEcritureComptable);
    	
    	// We save via an update or insert the sequence in the table sequence_ecriture_comptable
    	SequenceEcritureComptable vSequenceEC = new SequenceEcritureComptable();
        vSequenceEC.setCodeJournal(pEcritureComptable.getJournal().getCode());
        vSequenceEC.setAnnee(Integer.valueOf(yearEC));
        vSequenceEC.setDerniereValeur(newValue);
    	getDaoProxy().getComptabiliteDao().insertOrUpdateSequenceEC(vSequenceEC); 
    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Check that the EC complies with the accounting rules
     *
     * @param pEcritureComptable -
     * @throws FunctionalException If the EC doesn't meet all the requirements
     */
    public void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== We check the unit constraints on the attributes of the EC
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                                          new ConstraintViolationException(
                                              "L'écriture comptable ne respecte pas les contraintes de validation",
                                              vViolations));
        }

        // ===== RG_Compta_2 : An EC is valid if, and only if, it is balanced
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

        // ===== RG_Compta_3 : An EC must have at least two Lignes d'Ecriture (1 for debit, 1 for credit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // We check the amount of lines
        if (pEcritureComptable.getListLigneEcriture().size() < 2
            || vNbrCredit < 1
            || vNbrDebit < 1) {
            throw new FunctionalException(
                "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }

        // We check that the content of the reference matches the attributes of the EC
        String vYearDate = new SimpleDateFormat("YYYY").format(pEcritureComptable.getDate());
        if (!pEcritureComptable.getReference().substring(3, 7).equals(vYearDate))
            throw new FunctionalException(
                    "L'année de la référence doit correspondre à celle de la date de l'écriture.");
        if (!pEcritureComptable.getReference().substring(0, 2).equals(pEcritureComptable.getJournal().getCode()))
            throw new FunctionalException(
                    "Le code journal de la référence doit correspondre au code du journal de l'écriture.");
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    public void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : The reference must be unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // We look for an EC with the same reference
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // If it is a new EC (id == null),
                // or if it doesn't match the found EC (id != idECRef),
                // it means that there is already an EC with that reference
                if (pEcritureComptable.getId() == null
                    || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // In that case, there is no other EC with this reference. So it is valid.
            }
        }
    }

    /**
     * Method to insert an EC in the DB
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     *  Method to update an EC in the DB
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * Method to delete an EC
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

   
    /**
     * Method to give the good format to the reference number
     */
	@Override
	public String leftPad(int n, int padding) {
		return String.format("%0" + padding + "d", n);
	}

	/**
     * Method to retrieve a Sequence Ecriture Comptable with Journal Code and Annee paramaters
     */
	@Override
	public SequenceEcritureComptable getSequenceECByJournalCodeAndAnnee(String pJournalCode, Integer pAnnee) throws NotFoundException, TechnicalException, FunctionalException {
		SequenceEcritureComptable sequenceToGet;
			try {
				sequenceToGet = getDaoProxy().getComptabiliteDao().getSequenceECByJournalCodeAndAnnee(pJournalCode, pAnnee);
			} catch (EmptyResultDataAccessException vEx) {
				throw new NotFoundException("SequenceEcritureComptable non trouvée : Code Journal = " + pJournalCode + " Annee = " + pAnnee);
			}
		return sequenceToGet;
	}


	
}

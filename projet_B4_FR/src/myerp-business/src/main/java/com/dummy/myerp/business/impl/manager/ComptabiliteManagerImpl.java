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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;


/**
 * Comptabilite manager implementation.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================
	@Autowired
	ComptabiliteDao comptabiliteDao;
	
	public void setComptabiliteDao(ComptabiliteDao vComptabiliteDao) {
		this.comptabiliteDao = vComptabiliteDao;
	}
	
	

    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return comptabiliteDao.getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return comptabiliteDao.getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return comptabiliteDao.getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     * @throws FunctionalException 
     * @throws TechnicalException 
     * @throws NotFoundException 
     */
    // TODO à tester
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) throws NotFoundException, TechnicalException, FunctionalException {
        // TODO à implémenter
        // Bien se réferer à la JavaDoc de cette méthode !
        /* Le principe :
                1.  Remonter depuis la persitance la dernière valeur de la séquence du journal pour l'année de l'écriture
                    (table sequence_ecriture_comptable)
                    
                    
                2.  * S'il n'y a aucun enregistrement pour le journal pour l'année concernée :
                        1. Utiliser le numéro 1.
                    * Sinon :
                        1. Utiliser la dernière valeur + 1
                3.  Mettre à jour la référence de l'écriture avec la référence calculée (RG_Compta_5)
                4.  Enregistrer (insert/update) la valeur de la séquence en persitance
                    (table sequence_ecriture_comptable)
         */
    	
    	// Annee de la Date de pEcritureComptable
    	Date dateEC = pEcritureComptable.getDate();
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
    	String yearEC = simpleDateFormat.format(dateEC).toUpperCase();
    	
    	//Code journal de pEcritureComptable
    	String codeJournalEC = pEcritureComptable.getJournal().getCode();
    	
    	// 1 - Remonte la derniere valeur
    	
    	int lastValue = comptabiliteDao.getSequenceECByJournalCode(codeJournalEC).getDerniereValeur();
    	int newValue;
    	// 2
    	if (!(lastValue == 0)) {
    		newValue = lastValue + 1;
    	} else {
    		newValue = 1;
    	} 
    	
    
    	// 3
    	StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
    	vStB.append(codeJournalEC).append("-").append(yearEC).append("/").append(leftPad(newValue, 5));
    	
    	String reference = vStB.toString();
    	
    	pEcritureComptable.setReference(reference);
    	
    	// 4
    	
    	comptabiliteDao.updateSequenceEC(codeJournalEC, newValue);
    	
    	
    }

    /**
     * {@inheritDoc}
     * @throws TechnicalException 
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException, TechnicalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    // TODO tests à compléter
    protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                                          new ConstraintViolationException(
                                              "L'écriture comptable ne respecte pas les contraintes de validation",
                                              vViolations));
        }

        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
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
        // On test le nombre de lignes car si l'écriture à une seule ligne
        //      avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
            || vNbrCredit < 1
            || vNbrDebit < 1) {
            throw new FunctionalException(
                "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }
        
        // TODO ===== RG_Compta_5 : Format et contenu de la référence
        // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
        // Annee de la Date de pEcritureComptable
    	Date dateEC = pEcritureComptable.getDate();
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
    	String yearEC = simpleDateFormat.format(dateEC).toUpperCase();
    	
    	//Code journal de pEcritureComptable
    	String codeJournalEC = pEcritureComptable.getJournal().getCode();
    	
    	// Annee de la ref
    	String yearRef = pEcritureComptable.getReference().substring(3, 7);
    	
    	// Code Journal de la ref
    	String codeJournalRef = pEcritureComptable.getReference().substring(0,2);
    	
    	if (!(yearEC.equalsIgnoreCase(yearRef) && codeJournalEC.equalsIgnoreCase(codeJournalRef))) {
    		throw new FunctionalException(
                    "L'écriture comptable n'a pas la bonne ref les informations de l'Ecriture Comptable et de la Reference sont differents" + yearEC + " / " + yearRef + " et " + codeJournalEC + " / " + codeJournalRef);
    		
    	}
        
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     * @throws TechnicalException 
     */
    protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException, TechnicalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = comptabiliteDao.getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                    || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }

    /**
     * {@inheritDoc}
     * @throws TechnicalException 
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException, TechnicalException {
        this.checkEcritureComptable(pEcritureComptable);
        comptabiliteDao.insertEcritureComptable(pEcritureComptable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
            comptabiliteDao.updateEcritureComptable(pEcritureComptable); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
            comptabiliteDao.deleteEcritureComptable(pId);
    }


	@Override
	public String leftPad(int n, int padding) {
		return String.format("%0" + padding + "d", n);
	}
}

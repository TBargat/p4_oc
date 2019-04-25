package com.dummy.myerp.testconsumer.consumer;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;

/**
 * ComptabiliteDaoImpl Integration Test
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bootstrapContext.xml" })
public class ComptabiliteDaoImplTest extends ConsumerTestCase {

	private ComptabiliteDaoImpl dao = (ComptabiliteDaoImpl) getDaoProxy().getComptabiliteDao();

	/** Test of the method getListCompteComptable() */

	@Test
	public void testGetListCompteComptable() {

		List<CompteComptable> listeCompteComptable = dao.getListCompteComptable();

		String firstLibelle = listeCompteComptable.get(0).getLibelle();
		String lastLibelle = listeCompteComptable.get(6).getLibelle();
		int sizeList = listeCompteComptable.size();

		assertEquals("Fournisseurs", firstLibelle);
		assertEquals("Prestations de services", lastLibelle);
		assertEquals(7, sizeList);

	}

	/** Test of the method getListCompteComptable() */

	@Test
	public void testGetListJournalComptable() {

		List<JournalComptable> listJournalComptable = dao.getListJournalComptable();

		String firstCode = listJournalComptable.get(0).getCode();
		String lastCode = listJournalComptable.get(3).getCode();
		int sizeList = listJournalComptable.size();

		assertEquals("AC", firstCode);
		assertEquals("OD", lastCode);
		assertEquals(4, sizeList);
	}

	/** Test of the whole CRUD cycle of the DAO */

	@Test(expected = NotFoundException.class)
	public void testEcritureComptableCRUDCycle() throws NotFoundException, TechnicalException, FunctionalException {

		/**
		 * Creation of instances of Ecriture Comptable and Ligne Ecriture Comptable to
		 * use the different methods
		 */

		JournalComptable journalComptable = dao.getListJournalComptable().get(2);

		CompteComptable firstCompteComptable = dao.getListCompteComptable().get(0);

		CompteComptable secondCompteComptable = dao.getListCompteComptable().get(1);

		EcritureComptable ecritureComptable = new EcritureComptable();

		ecritureComptable.setJournal(journalComptable);
		ecritureComptable.setReference("BQ-2018/00001");
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("Paiement facture X");
		ecritureComptable.setId(6);

		LigneEcritureComptable firstLigneEcritureComptable = new LigneEcritureComptable();

		firstLigneEcritureComptable.setEcritureId(ecritureComptable.getId());
		firstLigneEcritureComptable.setLigneId(1);
		firstLigneEcritureComptable.setCompteComptable(firstCompteComptable);
		firstLigneEcritureComptable.setLibelle("coucou");
		firstLigneEcritureComptable.setDebit(new BigDecimal("30.00"));

		LigneEcritureComptable lastLigneEcritureComptable = new LigneEcritureComptable();

		lastLigneEcritureComptable.setEcritureId(ecritureComptable.getId());
		lastLigneEcritureComptable.setLigneId(2);
		lastLigneEcritureComptable.setCompteComptable(secondCompteComptable);
		lastLigneEcritureComptable.setLibelle("coucou 2");
		lastLigneEcritureComptable.setCredit(new BigDecimal("30.00"));

		List<LigneEcritureComptable> listLigneEcritureComptable = new ArrayList<>();

		listLigneEcritureComptable.add(firstLigneEcritureComptable);
		listLigneEcritureComptable.add(lastLigneEcritureComptable);

		ecritureComptable.setListLigneEcriture(listLigneEcritureComptable);

		/**
		 * We do the insertion
		 */

		dao.insertEcritureComptable(ecritureComptable);

		/**
		 * We check the size of the list to be sure that the change occured
		 */
		List<EcritureComptable> listEcritureComptable = dao.getListEcritureComptable();
		int sizeListEcritureComptable = listEcritureComptable.size();

		assertEquals(6, sizeListEcritureComptable);

		/**
		 * We change the reference to check the Update method
		 */

		ecritureComptable.setReference("BQ-2018/00010");
		dao.updateEcritureComptable(ecritureComptable);

		assertEquals("Paiement facture X", dao.getEcritureComptableByRef("BQ-2018/00010").getLibelle());

		/**
		 * We finally test the Delete method
		 */

		dao.deleteEcritureComptable(ecritureComptable.getId());
		assertEquals(5, dao.getListEcritureComptable().size());
		dao.getEcritureComptableByRef("BQ-2018/00010");

	}

	/** Test of the exception of the method getEcritureComptable() */

	@Test(expected = NotFoundException.class)
	public void testNoEcritureId() throws Exception {
		dao.getEcritureComptable(10);
	}

	/** Test of the exception of the method .getEcritureComptableByRef() */

	@Test(expected = NotFoundException.class)
	public void testNoEcrituref() throws Exception {
		dao.getEcritureComptableByRef("FAKE");

	}
	
	/** Test of the exception of the method getSequenceECByJournalCodeAndAnnee() */
	
	@Test(expected = NotFoundException.class)
	public void testNoSequenceEcritureComptable() throws Exception {
		dao.getSequenceECByJournalCodeAndAnnee("FAKE", 1995);
	}
	/** Test of the methods of SequenceEcritureComptable */ 
	
	@Test
	public void testSequenceEcritureComptable() throws NotFoundException, TechnicalException, FunctionalException {
		SequenceEcritureComptable vSequenceEC = dao.getSequenceECByJournalCodeAndAnnee("OD", 2016);
		vSequenceEC.setDerniereValeur(1);

		dao.insertOrUpdateSequenceEC(vSequenceEC);
		assertEquals(1, dao.getSequenceECByJournalCodeAndAnnee("OD", 2016).getDerniereValeur());

		vSequenceEC.setDerniereValeur(0);
		dao.insertOrUpdateSequenceEC(vSequenceEC);
	}

}
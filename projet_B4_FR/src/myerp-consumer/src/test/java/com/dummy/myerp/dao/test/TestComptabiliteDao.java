package com.dummy.myerp.dao.test;

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
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class TestComptabiliteDao {

	private ComptabiliteDaoImpl dao = new ComptabiliteDaoImpl();

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

	@Test(expected = NotFoundException.class)
	public void testEcritureComptableCRUDCycle() throws NotFoundException, TechnicalException, FunctionalException {

		/**
		 * Creation d'une instance ecriture comptable et journal comptable pour tester
		 * les methodes
		 * 
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
		 * On fait les deux insertions
		 */

		dao.insertEcritureComptable(ecritureComptable);

		/**
		 * On s'assure qu'elle s'est bien ajoutee en regardans la taille de la liste et
		 * le dernier element
		 */
		List<EcritureComptable> listEcritureComptable = dao.getListEcritureComptable();
		int sizeListEcritureComptable = listEcritureComptable.size();

		/**
		 * on verifie la taille et le premier et dernier element de cette liste
		 */
		assertEquals(6, sizeListEcritureComptable);
		assertEquals(dao.getEcritureComptable(-1).getLibelle(),
				listEcritureComptable.get(sizeListEcritureComptable - 6).getLibelle());
		assertEquals(ecritureComptable.getLibelle(),
				listEcritureComptable.get(sizeListEcritureComptable - 1).getLibelle());

		/**
		 * On change la reference de notre objet pour tester un update
		 */

		ecritureComptable.setReference("BQ-2018/00010");

		dao.updateEcritureComptable(ecritureComptable);

		assertEquals("Paiement facture X", dao.getEcritureComptableByRef("BQ-2018/00010").getLibelle());

		/**
		 * on teste de le delete maintenant
		 */

		dao.deleteEcritureComptable(ecritureComptable.getId());

		assertEquals(5, dao.getListEcritureComptable().size());
		dao.getEcritureComptableByRef("BQ-2018/00010");

	}

	@Test(expected = NotFoundException.class)
	public void testNoEcritureId() throws Exception {
		dao.getEcritureComptable(10);
	}
	
	@Test(expected = NotFoundException.class)
	public void testNoEcrituref() throws Exception {
		dao.getEcritureComptableByRef("FAKE");
		
	}
	
	@Test(expected = NotFoundException.class)
	public void testNoSequenceEcritureComptable() throws Exception {
		dao.getSequenceECByJournalCode("FAKE");
	}
	
	
	@Test
	public void testSequenceEcritureComptable() throws NotFoundException, TechnicalException, FunctionalException {
		dao.updateSequenceEC("OD", 1);
		assertEquals(1, dao.getSequenceECByJournalCode("OD").getDerniereValeur());
		
		dao.updateSequenceEC("OD", 0);
	}
}

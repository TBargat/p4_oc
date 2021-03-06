package com.dummy.myerp.testbusiness.business;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

/**
 * ComptabiliteManagerImpl Integration Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bootstrapContext.xml" })
public class ComptabiliteManagerIntegrationTest extends BusinessTestCase {

	private ComptabiliteManager managerProxy = getBusinessProxy().getComptabiliteManager();

	/** Test of the method addReference() in the case of an update */

	@Test
	public void addReferenceUpdate() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setId(-1);
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2016/04/01"));
		vEcritureComptable.setLibelle("Test Update and Add Reference");

		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(401), "Divers 401", new BigDecimal(10), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(411), "Divers 411", new BigDecimal(10), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(4456), "Divers 4456", null, new BigDecimal(20)));

		managerProxy.addReference(vEcritureComptable);

		String libelleOfTheEcritureWithIdOne = managerProxy.getListEcritureComptable().get(4).getLibelle();
		assertEquals("Test Update and Add Reference", libelleOfTheEcritureWithIdOne);

		String referenceOfTheEcritureWithIdOne = managerProxy.getListEcritureComptable().get(4).getReference();
		assertEquals("AC-2016/00002", referenceOfTheEcritureWithIdOne);

		int derniereVaeurOfSequence = managerProxy.getSequenceECByJournalCodeAndAnnee("AC", 2016).getDerniereValeur();
		assertEquals(2, derniereVaeurOfSequence);

	}

	/**
	 * Test of the method addReference() in the case of an insert & Test of the
	 * Insert and Delete method for Ecriture Comptable
	 */

	@Test
	public void insertAndDelete() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
		vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"));
		vEcritureComptable.setReference("BQ-2019/00001");
		vEcritureComptable.setLibelle("Test Insert And Delete");

		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(401), "Divers 401", new BigDecimal(10), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(411), "Divers 411", new BigDecimal(10), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(4456), "Divers 4456", null, new BigDecimal(20)));

		managerProxy.insertEcritureComptable(vEcritureComptable);
		managerProxy.addReference(vEcritureComptable);

		String referenceOfTheLastInsertedEcriture = managerProxy.getListEcritureComptable().get(5).getReference();
		assertEquals("BQ-2019/00001", referenceOfTheLastInsertedEcriture);

		int derniereVaeurOfSequence = managerProxy.getSequenceECByJournalCodeAndAnnee("BQ", 2019).getDerniereValeur();
		assertEquals(1, derniereVaeurOfSequence);

		int sizeEcritureComptablListeAfterInsert = managerProxy.getListEcritureComptable().size();

		assertEquals(6, sizeEcritureComptablListeAfterInsert);

		int idOfLastEcritureComptableAdded = managerProxy.getListEcritureComptable().get(5).getId();

		managerProxy.deleteEcritureComptable(idOfLastEcritureComptableAdded);

		int sizeEcritureComptablListeAfterDelete = managerProxy.getListEcritureComptable().size();

		assertEquals(5, sizeEcritureComptablListeAfterDelete);

	}

	/** Test of the method checkEcritureComptableContext() exceptions */

	@Test
	public void checkEcritureComptableContext() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setReference("BQ-2019/00001");
		managerProxy.checkEcritureComptableContext(vEcritureComptable);
	}

	/** Test of the method checkEcritureComptable() */

	@Test
	public void checkEcritureComptable() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"));
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.setReference("AC-2019/00004");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		managerProxy.checkEcritureComptable(vEcritureComptable);
	}

	/** Test of the method checkEcritureComptableContext() exceptions */

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableContextRG6SameRef() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setId(0);
		vEcritureComptable.setReference("VE-2016/00002");
		managerProxy.checkEcritureComptableContext(vEcritureComptable);

	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableContextRG6NoId() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setReference("VE-2016/00002");
		managerProxy.checkEcritureComptableContext(vEcritureComptable);

	}

}

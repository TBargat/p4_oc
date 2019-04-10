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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bootstrapContext.xml" })
public class ComptabiliteManagerIntegrationTest extends BusinessTestCase {
	
	
	private ComptabiliteManager managerProxy = getBusinessProxy().getComptabiliteManager();

	@Test
	public void addReference() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setId(-1);
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2016/04/01"));
		vEcritureComptable.setLibelle("Test Update and Add Reference");

		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Divers 401", new BigDecimal(10),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                "Divers 411", new BigDecimal(10),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(4456),
                "Divers 4456", null,
                new BigDecimal(20)));
		
		
		managerProxy.addReference(vEcritureComptable);
		
		String libelleOfTheEcritureWithIdOne = managerProxy.getListEcritureComptable().get(4).getLibelle();
		assertEquals("Test Update and Add Reference", libelleOfTheEcritureWithIdOne);
		
		

	}
	
	@Test
	public void insertAndDelete() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();

		vEcritureComptable.setId(1);
		vEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
		vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2016/04/01"));
		vEcritureComptable.setReference("BQ-2016/01000");
		vEcritureComptable.setLibelle("Test Insert And Delete");

		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Divers 401", new BigDecimal(10),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                "Divers 411", new BigDecimal(10),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(4456),
                "Divers 4456", null,
                new BigDecimal(20)));
        
        managerProxy.insertEcritureComptable(vEcritureComptable);
		
		int sizeEcritureComptablListeAfterInsert = managerProxy.getListEcritureComptable().size();
		
		assertEquals(6, sizeEcritureComptablListeAfterInsert);
		
		int idOfLastEcritureComptableAdded = managerProxy.getListEcritureComptable().get(5).getId();
		
		managerProxy.deleteEcritureComptable(idOfLastEcritureComptableAdded);
		
		int sizeEcritureComptablListeAfterDelete = managerProxy.getListEcritureComptable().size();
		
		
		assertEquals(5, sizeEcritureComptablListeAfterDelete);
		
	}


}

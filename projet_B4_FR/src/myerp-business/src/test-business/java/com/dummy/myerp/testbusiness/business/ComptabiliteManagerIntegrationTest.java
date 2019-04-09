package com.dummy.myerp.testbusiness.business;

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

		vEcritureComptable.setId(6);
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"));
		vEcritureComptable.setLibelle("Achat Test");

		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		
		
		managerProxy.addReference(vEcritureComptable);
		
		// retrouver le assert

	}


}

package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

/**
 * ComptabiliteManagerImpl Integration Test
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/bootstrapContext.xml" })
public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    
    
    
    /** Test of the method checkEcritureComptableUnit() */
    
    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00004");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        		null, new BigDecimal(123),
        		null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), 
        		null, null, 
        		new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    /** Test of the method checkEcritureComptableUnit() exceptions */
    
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3SecondException() throws Exception {
    	
    	 EcritureComptable vEcritureComptable;
         vEcritureComptable = new EcritureComptable();
         vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
         vEcritureComptable.setDate(new Date());
         vEcritureComptable.setLibelle("Libelle");
         vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                  null, new BigDecimal(123),
                                                                                  null));
         
         manager.checkEcritureComptableUnit(vEcritureComptable);
    	
    }
    
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5() throws Exception {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("TE-2019/00004");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        		null, new BigDecimal(123),
        		null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), 
        		null, null, 
        		new BigDecimal(123)));
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5SecondException() throws Exception {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2018/00004");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        		null, new BigDecimal(123),
        		null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), 
        		null, null, 
        		new BigDecimal(123)));
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    
    
    
    
    
    

}

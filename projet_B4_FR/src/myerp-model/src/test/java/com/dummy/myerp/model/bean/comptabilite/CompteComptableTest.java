package com.dummy.myerp.model.bean.comptabilite;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 *  Compte Comptable Tests
 */

public class CompteComptableTest {
	
	/** Test of the method getBynumero() */
	
	@Test
	public void getBynumero() {
		
		CompteComptable vCompte;
	    List<CompteComptable> vList;
	    
	    vCompte = new CompteComptable();
        vCompte.setNumero(10);
        vCompte.setLibelle("Client");
        vList = new ArrayList<>(0);
        vList.add(vCompte);
        vList.add(new CompteComptable(11, "Provider"));
        
        assertEquals(CompteComptable.getByNumero(vList, 10), vCompte);
        
 
	}

}

package com.dummy.myerp.model.bean.comptabilite;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Journal Comptable Tests
 */

public class JournalComptableTest {

	/** Test of the method getByCode() */
	
	@Test
	public void getByCode() {

		JournalComptable vJournal;
		List<JournalComptable> vList;

		vJournal = new JournalComptable();
		vJournal.setCode("TC");
		vJournal.setLibelle("TestCode");
		vList = new ArrayList<>(0);
		vList.add(vJournal);
		vList.add(new JournalComptable("OC", "OtherCode"));

		assertEquals(JournalComptable.getByCode(vList, "TC"), vJournal);

	}

}

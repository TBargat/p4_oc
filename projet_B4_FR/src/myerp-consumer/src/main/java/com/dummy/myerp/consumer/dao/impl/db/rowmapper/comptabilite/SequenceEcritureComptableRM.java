package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;

/**
 * {@link RowMapper} of {@link SequenceEcritureComptable}
 */

public class SequenceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {

	@Override
	public SequenceEcritureComptable mapRow(ResultSet rs, int rowNum) throws SQLException {
		SequenceEcritureComptable vBean = new SequenceEcritureComptable();
		vBean.setCodeJournal(rs.getString("journal_code"));
		vBean.setAnnee(rs.getInt("annee"));
		vBean.setDerniereValeur(rs.getInt("derniere_valeur"));
		
		return vBean;
	}

}

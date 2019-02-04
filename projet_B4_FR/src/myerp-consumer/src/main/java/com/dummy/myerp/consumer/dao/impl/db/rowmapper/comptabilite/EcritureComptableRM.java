package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;


/**
 * {@link RowMapper} de {@link EcritureComptable}
 */
public class EcritureComptableRM implements RowMapper<EcritureComptable> {
	
	ComptabiliteDao comptabiliteDao;
	
	public EcritureComptableRM(ComptabiliteDao comptabiliteDao) {
		this.comptabiliteDao = comptabiliteDao;
	}


    @Override
    public EcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        EcritureComptable vBean = new EcritureComptable();
        vBean.setId(pRS.getInt("id"));
        vBean.setJournal(JournalComptable.getByCode(comptabiliteDao.getListJournalComptable(), pRS.getString("journal_code")));
        vBean.setReference(pRS.getString("reference"));
        vBean.setDate(pRS.getDate("date"));
        vBean.setLibelle(pRS.getString("libelle"));

        // Chargement des lignes d'écriture
        comptabiliteDao.loadListLigneEcriture(vBean);

        return vBean;
    }
}

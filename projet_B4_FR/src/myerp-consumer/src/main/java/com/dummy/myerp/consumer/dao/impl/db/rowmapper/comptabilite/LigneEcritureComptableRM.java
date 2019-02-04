package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;


/**
 * {@link RowMapper} de {@link LigneEcritureComptable}
 */
public class LigneEcritureComptableRM implements RowMapper<LigneEcritureComptable> {

    
    ComptabiliteDao comptabiliteDao;
    
    public LigneEcritureComptableRM(ComptabiliteDao comptabiliteDao) {
		this.comptabiliteDao = comptabiliteDao;
	}


    @Override
    public LigneEcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        LigneEcritureComptable vBean = new LigneEcritureComptable();
        vBean.setCompteComptable(CompteComptable.getByNumero(comptabiliteDao.getListCompteComptable(), pRS.getObject("compte_comptable_numero",
                Integer.class)));
        vBean.setCredit(pRS.getBigDecimal("credit"));
        vBean.setDebit(pRS.getBigDecimal("debit"));
        vBean.setLibelle(pRS.getString("libelle"));

        return vBean;
    }
}

package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.JournalComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.LigneEcritureComptableRM;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;

/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

	// ==================== Constructeurs ====================

	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");

	/**
	 * Constructeur.
	 */
	public ComptabiliteDaoImpl() {

	}

	// ==================== Méthodes ====================

	@Override
	public List<CompteComptable> getListCompteComptable() {
		CompteComptableRM vRM = new CompteComptableRM();
		String sql = "SELECT * FROM myerp.compte_comptable";
		List<CompteComptable> vList = jdbcTemplate.query(sql, vRM);
		return vList;
	}

	@Override
	public List<JournalComptable> getListJournalComptable() {
		JournalComptableRM vRM = new JournalComptableRM();
		String sql = "SELECT * FROM myerp.journal_comptable";
		List<JournalComptable> vList = jdbcTemplate.query(sql, vRM);
		return vList;
	}

	// ==================== EcritureComptable - GET ====================

	@Override
	public List<EcritureComptable> getListEcritureComptable() {
		EcritureComptableRM vRM = new EcritureComptableRM(this);
		String sql = "SELECT * FROM myerp.ecriture_comptable";
		List<EcritureComptable> vList = jdbcTemplate.query(sql, vRM);
		return vList;
	}

	@Override
	public EcritureComptable getEcritureComptable(Integer pId)
			throws NotFoundException, TechnicalException, FunctionalException {
		EcritureComptableRM vRM = new EcritureComptableRM(this);
		String sql = "SELECT * FROM myerp.ecriture_comptable WHERE id = ?";

		List<EcritureComptable> ecList;
		try {
			ecList = jdbcTemplate.query(sql, vRM, pId);
		} catch (Exception vEx) {
			throw new TechnicalException("EcritureComptable non trouvée : id=" + pId);
		}
		if (ecList.isEmpty()) {
			throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
		}
		if (ecList.size() > 1) {
			throw new FunctionalException("Plusieurs lignes trouvées pour le même : id= " + pId);
		}
		return ecList.get(0);
	}

	@Override
	public EcritureComptable getEcritureComptableByRef(String pReference)
			throws NotFoundException, TechnicalException, FunctionalException {

		EcritureComptableRM vRM = new EcritureComptableRM(this);
		String sql = "SELECT * FROM myerp.ecriture_comptable " + "WHERE reference = ?";

		List<EcritureComptable> ecList;
		try {
			ecList = jdbcTemplate.query(sql, vRM, pReference);
		} catch (Exception vEx) {
			throw new TechnicalException("EcritureComptable non trouvée : ref=" + pReference);
		}
		if (ecList.isEmpty()) {
			throw new NotFoundException("EcritureComptable non trouvée : ref=" + pReference);
		}
		if (ecList.size() > 1) {
			throw new FunctionalException("Plusieurs lignes trouvées pour le même : ref= " + pReference);
		}
		return ecList.get(0);

	}

	@Override
	public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {

		LigneEcritureComptableRM vRM = new LigneEcritureComptableRM(this);
		String sql = "SELECT * FROM myerp.ligne_ecriture_comptable\n" + "WHERE ecriture_id = ? " + "ORDER BY ligne_id";
		List<LigneEcritureComptable> vList = jdbcTemplate.query(sql, vRM, pEcritureComptable.getId());
		pEcritureComptable.getListLigneEcriture().clear();
		pEcritureComptable.getListLigneEcriture().addAll(vList);

	}

	// ==================== EcritureComptable - INSERT ====================

	@Override
	public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
		// ===== Ecriture Comptable
		String sql = "INSERT INTO myerp.ecriture_comptable \n" + "(id, journal_code, reference, date, libelle)\n"
				+ "VALUES (nextval('myerp.ecriture_comptable_id_seq'),\n" + "?, ?, ?, ?)";

		jdbcTemplate.update(sql, pEcritureComptable.getJournal().getCode(), pEcritureComptable.getReference(),
				pEcritureComptable.getDate(), pEcritureComptable.getLibelle());

		// ----- Récupération de l'id
		Integer vId = this.queryGetSequenceValuePostgreSQL(jdbcTemplate, "myerp.ecriture_comptable_id_seq",
				Integer.class);
		pEcritureComptable.setId(vId);

		// ===== Liste des lignes d'écriture
		this.insertListLigneEcritureComptable(pEcritureComptable);

	}

	/**
	 * Insert les lignes d'écriture de l'écriture comptable
	 * 
	 * @param pEcritureComptable l'écriture comptable
	 */
	protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {

		String sql = "INSERT INTO myerp.ligne_ecriture_comptable \n"
				+ "(ecriture_id, ligne_id, compte_comptable_numero, libelle, debit, credit) \n"
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		int vLigneId = 0;

		for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
			vLigneId++;
			jdbcTemplate.update(sql, pEcritureComptable.getId(), vLigneId, vLigne.getCompteComptable().getNumero(),
					vLigne.getLibelle(), vLigne.getDebit(), vLigne.getCredit());
		}
	}

	// ==================== EcritureComptable - UPDATE ====================

	@Override
	public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
		// ===== Ecriture Comptable

		String sql = "UPDATE myerp.ecriture_comptable SET\n" + "	journal_code = ?,\n" + "	reference = ?,\n"
				+ "	date = ?,\n" + "	libelle = ?\n" + "WHERE\n" + "	id = ?";

		jdbcTemplate.update(sql, pEcritureComptable.getJournal().getCode(), pEcritureComptable.getReference(),
				pEcritureComptable.getDate(), pEcritureComptable.getLibelle(), pEcritureComptable.getId());

		// ===== Liste des lignes d'écriture
		this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
		this.insertListLigneEcritureComptable(pEcritureComptable);
	}

	// ==================== EcritureComptable - DELETE ====================

	@Override
	public void deleteEcritureComptable(Integer pId) {
		// ===== Suppression des lignes d'écriture
		this.deleteListLigneEcritureComptable(pId);

		// ===== Suppression de l'écriture
		String sql = "DELETE FROM myerp.ecriture_comptable\n" + "WHERE id = ?";
		jdbcTemplate.update(sql, pId);
	}

	/**
	 * Supprime les lignes d'écriture de l'écriture comptable d'id
	 * {@code pEcritureId}
	 * 
	 * @param pEcritureId id de l'écriture comptable
	 */
	protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
		String sql = "DELETE FROM myerp.ligne_ecriture_comptable\n" + "WHERE ecriture_id = ?";
		jdbcTemplate.update(sql, pEcritureId);
	}

}

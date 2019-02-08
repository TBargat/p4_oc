package com.dummy.myerp.model.bean.comptabilite;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

	// ==================== Attributs ====================
	private String codeJournal;

	/** L'année */
	private int annee;
	/** La dernière valeur utilisée */
	private int derniereValeur;

	// ==================== Constructeurs ====================
	/**
	 * Constructeur
	 */
	public SequenceEcritureComptable() {
	}

	/**
	 * Constructeur
	 *
	 * @param pAnnee          -
	 * @param pDerniereValeur -
	 */
	public SequenceEcritureComptable(String pCodeJournal, int pAnnee, int pDerniereValeur) {
		codeJournal = pCodeJournal;
		annee = pAnnee;
		derniereValeur = pDerniereValeur;
	}

	// ==================== Getters/Setters ====================
	public String getCodeJournal() {
		return codeJournal;
	}

	public void setCodeJournal(String codeJournal) {
		this.codeJournal = codeJournal;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(Integer pAnnee) {
		annee = pAnnee;
	}

	public int getDerniereValeur() {
		return derniereValeur;
	}

	public void setDerniereValeur(int pDerniereValeur) {
		derniereValeur = pDerniereValeur;
	}

	// ==================== Méthodes ====================
	@Override
	public String toString() {
		final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
		final String vSEP = ", ";
		vStB.append("{").append("annee=").append(annee).append(vSEP).append("derniereValeur=").append(derniereValeur)
				.append("}");
		return vStB.toString();
	}
}

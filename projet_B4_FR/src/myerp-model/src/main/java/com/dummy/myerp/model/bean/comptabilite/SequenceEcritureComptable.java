package com.dummy.myerp.model.bean.comptabilite;


/**
 * Sequence Ecriture Comptable Bean
 */
public class SequenceEcritureComptable {

    // ==================== Attributes ====================
	/** The Code Journal */
	private String codeJournal;
	/** The Year */
    private Integer annee;
    /** The Last Value Used */
    private int derniereValeur;

    // ==================== Constructors ====================
    /**
     * Constructor
     */
    public SequenceEcritureComptable() {
    }

    /**
     * Constructor
     *
     * @param pAnnee -
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
    
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    public int getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }


    // ==================== Methods ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("annee=").append(annee)
            .append(vSEP).append("derniereValeur=").append(derniereValeur)
            .append("}");
        return vStB.toString();
    }
}

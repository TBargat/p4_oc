package com.dummy.myerp.model.bean.comptabilite;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;


/**
 * Écriture Comptable Bean
 */
public class EcritureComptable {

    // ==================== Attributes ====================
    /** The Id. */
    private Integer id;
    /** Journal comptable */
    @NotNull private JournalComptable journal;
    /** The Reference. */
    @Pattern(regexp = "\\w{1,5}-\\d{4}/\\d{5}")
    private String reference;
    /** The Date. */
    @NotNull private Date date;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 200)
    private String libelle;

    /** Ecriture Comptables' List */
    @Valid
    @Size(min = 2)
    private List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();


    // ==================== Getters/Setters ====================
    public Integer getId() {
        return id;
    }
    public void setId(Integer pId) {
        id = pId;
    }
    public JournalComptable getJournal() {
        return journal;
    }
    public void setJournal(JournalComptable pJournal) {
        journal = pJournal;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String pReference) {
        reference = pReference;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date pDate) {
        date = pDate;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    public List<LigneEcritureComptable> getListLigneEcriture() {
        return listLigneEcriture;
    }
    
    public void setListLigneEcriture(List<LigneEcritureComptable> pListLigneEcriture) {
    	listLigneEcriture = pListLigneEcriture;
    }

    /**
     * Compute and return the total of the Debit amounts on the Ligne Ecriture 
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} if there is no amount in the Debit
     */
    // TODO à tester
    public BigDecimal getTotalDebit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
            }
        }
        return vRetour;
    }

    /**
     * Compute and return the total of the Credit amounts on the Ligne Ecriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} if there is no amount in the Credit
     */
    public BigDecimal getTotalCredit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getCredit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getCredit());
            }
        }
        return vRetour;
    }

    /**
     * Return True is the Ecriture is balanced (TotalDebit = TotalCrédit)
     * @return boolean
     */
    public boolean isEquilibree() {
    	BigDecimal totalDebitAbsolute = this.getTotalDebit().setScale(2, RoundingMode.FLOOR); // To be able to compare the Credit and Debit
    	BigDecimal totalCreditAbsolute = this.getTotalCredit().setScale(2, RoundingMode.FLOOR);
        boolean vRetour = totalDebitAbsolute.equals(totalCreditAbsolute);
        return vRetour;
    }

    // ==================== Methods ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("id=").append(id)
            .append(vSEP).append("journal=").append(journal)
            .append(vSEP).append("reference='").append(reference).append('\'')
            .append(vSEP).append("date=").append(date)
            .append(vSEP).append("libelle='").append(libelle).append('\'')
            .append(vSEP).append("totalDebit=").append(this.getTotalDebit().toPlainString())
            .append(vSEP).append("totalCredit=").append(this.getTotalCredit().toPlainString())
            .append(vSEP).append("listLigneEcriture=[\n")
            .append(StringUtils.join(listLigneEcriture, "\n")).append("\n]")
            .append("}");
        return vStB.toString();
    }
	
}

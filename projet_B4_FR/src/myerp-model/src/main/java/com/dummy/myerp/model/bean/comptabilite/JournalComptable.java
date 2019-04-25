package com.dummy.myerp.model.bean.comptabilite;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Journal Comptable Bean
 */
public class JournalComptable {

    // ==================== Attributes ====================
    /** Code */
    @NotNull
    @Size(min = 1, max = 5)
    private String code;

    /** Libelle */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    // ==================== Constructors ====================
    /**
     * Instantiates a new Journal comptable.
     */
    public JournalComptable() {
    }

    /**
     * Instantiates a new Journal comptable.
     *
     * @param pCode the p code
     * @param pLibelle the p libelle
     */
    public JournalComptable(String pCode, String pLibelle) {
        code = pCode;
        libelle = pLibelle;
    }


    // ==================== Getters/Setters ====================
    public String getCode() {
        return code;
    }
    public void setCode(String pCode) {
        code = pCode;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }


    // ==================== Methods ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("code='").append(code).append('\'')
            .append(vSEP).append("libelle='").append(libelle).append('\'')
            .append("}");
        return vStB.toString();
    }


    // ==================== Méthodes STATIC ====================
    /**
     * Return the {@link JournalComptable} with the code {@code pCode} if it belongs to the list
     *
     * @param pList the list where to look for {@link JournalComptable}
     * @param pCode the code of the wanted {@link JournalComptable} 
     * @return {@link JournalComptable} or {@code null}
     */
    public static JournalComptable getByCode(List<? extends JournalComptable> pList, String pCode) {
        JournalComptable vRetour = null;
        for (JournalComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getCode(), pCode)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
}

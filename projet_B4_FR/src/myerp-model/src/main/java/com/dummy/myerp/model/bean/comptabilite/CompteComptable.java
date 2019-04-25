package com.dummy.myerp.model.bean.comptabilite;


import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  Compte Comptable Bean
 */
public class CompteComptable {
    // ==================== Attributes ====================
    /** The Numero. */
    @NotNull
    private Integer numero;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    // ==================== Constructors ====================
    /**
     * Instantiates a new Compte comptable.
     */
    public CompteComptable() {
    }

    /**
     * Instantiates a new Compte comptable.
     *
     * @param pNumero the numero
     */
    public CompteComptable(Integer pNumero) {
        numero = pNumero;
    }

    /**
     * Instantiates a new Compte comptable.
     *
     * @param pNumero the numero
     * @param pLibelle the libelle
     */
    public CompteComptable(Integer pNumero, String pLibelle) {
        numero = pNumero;
        libelle = pLibelle;
    }


    // ==================== Getters/Setters ====================
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer pNumero) {
        numero = pNumero;
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
            .append("numero=").append(numero)
            .append(vSEP).append("libelle='").append(libelle).append('\'')
            .append("}");
        return vStB.toString();
    }


    // ==================== Static Methods ====================
    /**
     * Return the {@link CompteComptable} with number {@code pNumero} if it belongs to the list
     *
     * @param pList the list where to look for {@link CompteComptable}
     * @param pNumero the number of the wanted {@link CompteComptable}
     * @return {@link CompteComptable} or {@code null}
     */
    public static CompteComptable getByNumero(List<? extends CompteComptable> pList, Integer pNumero) {
        CompteComptable vRetour = null;
        for (CompteComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getNumero(), pNumero)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
}

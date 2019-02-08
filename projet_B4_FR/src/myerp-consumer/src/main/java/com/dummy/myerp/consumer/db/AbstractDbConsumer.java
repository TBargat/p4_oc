package com.dummy.myerp.consumer.db;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


/**
 * <p>Classe mère des classes de Consumer DB</p>
 */
public abstract class AbstractDbConsumer {

// ==================== Attributs Static ====================





    // ==================== Constructeurs ====================

    /**
     * Constructeur.
     */
    protected AbstractDbConsumer() {
        super();
    }


    // ==================== Getters/Setters ====================



    // ==================== Méthodes ====================


    /**
     * Renvoie le dernière valeur utilisé d'une séquence
     *
     * <p><i><b>Attention : </b>Méthode spécifique au SGBD PostgreSQL</i></p>
     *
     * @param <T> : La classe de la valeur de la séquence.
     * @param pDataSourcesId : L'identifiant de la {@link DataSource} à utiliser
     * @param pSeqName : Le nom de la séquence dont on veut récupérer la valeur
     * @param pSeqValueClass : Classe de la valeur de la séquence
     * @return la dernière valeur de la séquence
     */
     
    protected <T> T queryGetSequenceValuePostgreSQL(JdbcTemplate jdbcTemplate,
                                                    String pSeqName, Class<T> pSeqValueClass) {
        String vSeqSQL = "SELECT last_value FROM " + pSeqName;
        T vSeqValue = jdbcTemplate.queryForObject(vSeqSQL, pSeqValueClass);

        return vSeqValue;
    }


   
  
}

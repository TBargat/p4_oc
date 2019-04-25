package com.dummy.myerp.consumer.db;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


/**
 * <p>Abstract Class for the DB Consumer</p>
 */
public abstract class AbstractDbConsumer {

// ==================== Static Attributes ====================
    /** Logger Log4j for the class */
    private static final Logger LOGGER = LogManager.getLogger(AbstractDbConsumer.class);


    /** Map of the DataSources */
    private static Map<DataSourcesEnum, DataSource> mapDataSource;


    // ==================== Constructors ====================

    /**
     * Constructor.
     */
    protected AbstractDbConsumer() {
        super();
    }


    // ==================== Getters/Setters ====================
    /**
     * Return a {@link DaoProxy}
     *
     * @return {@link DaoProxy}
     */
    protected static DaoProxy getDaoProxy() {
        return ConsumerHelper.getDaoProxy();
    }


    // ==================== Methods ====================
    /**
     * Return the wanted {@link DataSource}
     *
     * @param pDataSourceId -
     * @return SimpleJdbcTemplate
     */
    protected DataSource getDataSource(DataSourcesEnum pDataSourceId) {
        DataSource vRetour = this.mapDataSource.get(pDataSourceId);
        if (vRetour == null) {
            throw new UnsatisfiedLinkError("La DataSource suivante n'a pas été initialisée : " + pDataSourceId);
        }
        return vRetour;
    }


    /**
     * Return the last value of a sequence
     *
     * <p><i><b>Beware : </b>Method working specifically for PostgreSQL</i></p>
     *
     * @param <T> : The value's class of the Sequence.
     * @param pDataSourcesId : The id of {@link DataSource} to use
     * @param pSeqName :The name of the Sequence we want the value of
     * @param pSeqValueClass : Value's class of the Sequence
     * @return Last value of the sequence
     */
    protected <T> T queryGetSequenceValuePostgreSQL(DataSourcesEnum pDataSourcesId,
                                                    String pSeqName, Class<T> pSeqValueClass) {

        JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource(pDataSourcesId));
        String vSeqSQL = "SELECT last_value FROM " + pSeqName;
        T vSeqValue = vJdbcTemplate.queryForObject(vSeqSQL, pSeqValueClass);

        return vSeqValue;
    }


    // ==================== Static Methods ====================
    /**
     * Configuration method for the class
     *
     * @param pMapDataSource -
     */
    public static void configure(Map<DataSourcesEnum, DataSource> pMapDataSource) {
    	/** We lead the DataSource integration with an Enum */
        Map<DataSourcesEnum, DataSource> vMapDataSource = new HashMap<>(DataSourcesEnum.values().length);
        DataSourcesEnum[] vDataSourceIds = DataSourcesEnum.values();
        for (DataSourcesEnum vDataSourceId : vDataSourceIds) {
            DataSource vDataSource = pMapDataSource.get(vDataSourceId);            
            /** We check if the DataSource is configurated i.e. if it is in pMapDataSource and null*/
            if (vDataSource == null) {
                if (!pMapDataSource.containsKey(vDataSourceId)) {
                    LOGGER.error("La DataSource " + vDataSourceId + " n'a pas été initialisée !");
                }
            } else {
                vMapDataSource.put(vDataSourceId, vDataSource);
            }
        }
        mapDataSource = vMapDataSource;
    }
}

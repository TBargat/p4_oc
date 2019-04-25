package com.dummy.myerp.consumer.db.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * Utility classes for the ResultSet
 */
public abstract class ResultSetHelper {

	// ==================== Constructors ====================
	/**
	 * Constructor.
	 */
	protected ResultSetHelper() {
		super();
	}

	// ==================== Methods ====================
	/**
	 * Return the value of the column pColName as an <code>Integer</code>. If the
	 * column equals <code>null</code>, the method returns <code>null</code>
	 *
	 * @param pRS      : The ResultSet to call
	 * @param pColName : The name of the column in the return of the SQL request
	 * @return <code>Integer</code> or <code>null</code>
	 * @throws SQLException on an SQL error
	 */
	public static Integer getInteger(ResultSet pRS, String pColName) throws SQLException {
		Integer vRetour = null;
		int vInt = pRS.getInt(pColName);
		if (!pRS.wasNull()) {
			vRetour = new Integer(vInt);
		}
		return vRetour;
	}

	/**
	 * Return the value of the column pColName as an <code>Long</code>. If the
	 * column equals <code>null</code>, the method returns <code>null</code>
	 *
	 * @param pRS      : The ResultSet to call
	 * @param pColName : The name of the column in the return of the SQL request
	 * @return <code>Long</code> or <code>null</code>
	 * @throws SQLException on an SQL error
	 */
	public static Long getLong(ResultSet pRS, String pColName) throws SQLException {
		Long vRetour = null;
		Long vLong = pRS.getLong(pColName);
		if (!pRS.wasNull()) {
			vRetour = new Long(vLong);
		}
		return vRetour;
	}

	/**
	 * Return the value of the column pColName as a {@link Date} while truncating
	 * the hour If the column equals <code>null</code>, the method returns
	 * <code>null</code>.
	 *
	 * @param pRS      : The ResultSet to call
	 * @param pColName : The name of the column in the return of the SQL request
	 * @return {@link Date} or <code>null</code>
	 * @throws SQLException on an SQL error
	 */
	public static Date getDate(ResultSet pRS, String pColName) throws SQLException {
		Date vDate = pRS.getDate(pColName);
		if (vDate != null) {
			vDate = DateUtils.truncate(vDate, Calendar.DATE);
		}
		return vDate;
	}
}

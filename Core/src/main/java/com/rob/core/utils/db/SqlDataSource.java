package com.rob.core.utils.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import com.rob.core.utils.Properties;
import com.rob.core.utils.java.SessionObject;


public class SqlDataSource {
	
	private static final SqlDataSource instance = new SqlDataSource();
    private DataSource connectionPool;
    private static Properties properties;
    private SqlConnection testConnection;

    private SqlDataSource(){
    }

    public static SqlDataSource getInstance(){
        return instance;
    }
    
    public DataSource getPool(){
        return connectionPool;
    }
    
    /** Stringa per ottenere la data odierna */
    private String getDbDateQuery() {
    	return "SELECT NOW() AS DB_DATE";
    }
    
    /** Carica l'ora corrente dal database 
     * @param cnn 
     * @return 
     * @throws SQLException */
    public Calendar getDBdate(SqlConnection cnn) throws SQLException
    {
    	if (cnn==null) {
    		return Calendar.getInstance();
    	}
    	return getDBdate(cnn.getConnection());
    }
    
    /** Carica l'ora corrente dal database 
     * @param cnn 
     * @return 
     * @throws SQLException */
    public Calendar getDBdate(Connection cnn) throws SQLException
    {
    	Calendar res = Calendar.getInstance();
    	String sql = getDbDateQuery();    	
		if (StringUtils.isBlank(sql)) {
			return res;
		}
			
  		try 
  		(PreparedStatementBuilder bld = new PreparedStatementBuilder();)
  		{
  			bld.append(sql);
  			ResultSet rst = bld.executeQuery(cnn);
  			rst.next();
  			res.setTime(rst.getTimestamp("DB_DATE"));
  		}
  		
  		return res;
    }

    public static void init()
        throws NamingException, SQLException
    {
        instance._init();
    }
       
    /** Restituisce il nome del database */
    private String getDatabaseName(Connection con) throws SQLException {
    	PreparedStatementBuilder bld = null;
    	String result = "???";
  		
  		try {
  			//Prepara la query di riceca nome database
  			String sql = "SELECT DB_NAME() DB_NAME"; 
        
  			//CREAZIONE QUERY (MANAGER QUERY)
  			bld = new PreparedStatementBuilder();
  			bld.append(sql);
  			
  			//ESECUZIONE QUERY
  			ResultSet rst = bld.executeQuery(con);

  			//ESTRAZIONE RISULTATO
  			if (rst.next()) {
  				result = rst.getString("DB_NAME");
  			}
  			
  		} catch (Exception e) {
  			System.out.println(e.getMessage());
  			
  		} finally {
  			SessionObject.closeStatementBuilder(bld);
  		}
  		
			return result;
    }
    
    /** Restituisce la versione del database */
    private String getDatabaseVersion(Connection con, int dbms) throws SQLException {
    	PreparedStatementBuilder bld = null;
    	String result = "???";
  		
  		try {
  			
  			//CREAZIONE QUERY (MANAGER QUERY)
  			bld = new PreparedStatementBuilder();
  			bld.append("SELECT VERSION FROM SYS_VERSION");
  			
  			//ESECUZIONE QUERY
  			ResultSet rst = bld.executeQuery(con);

  			//ESTRAZIONE RISULTATO
  			if (rst.next()) {
  				result = rst.getString("VERSION") == null ? "???" : rst.getString("VERSION");
   			}
  			
  		} catch (Exception e) {
  			System.out.println(e.getMessage());
  			
  		} finally {
  			SessionObject.closeStatementBuilder(bld);
  		}
  		
			return result;
    }
    
    /**Istanzia datasource  
     * @return */
    public static DataSource getDataSource() {
    	DataSource result = null;
    	
    	/**Connessione tramite parametri core.properties*/
    	String jdbcUrl = properties.getConnectionUri();
    	if (StringUtils.isNotBlank(jdbcUrl)) {
    		com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig();
    		config.setJdbcUrl(String.format(jdbcUrl));
    		
    		boolean useSSL = Boolean.parseBoolean(properties.getSsl());
    		config.addDataSourceProperty("useSSL", useSSL);
    		
    		String username = properties.getConnectionUsername();
    		config.setUsername(username);
    		
    		String password = properties.getConnectionPassword();
    		config.setPassword(password);
    	
    		config.setMaximumPoolSize(10);
    		
    		result = new com.zaxxer.hikari.HikariDataSource(config);
    	}
        if (result!=null) {
        	return result;
        }
        /**************************************************************/
    	
        /**Connesione tramite JNDI*/
        try
        {
        	result = (DataSource) (new InitialContext());
        }
        catch(javax.naming.NamingException ex) {
        	result = null;
        }
        if (result!=null) {
        	return result;
        }

    	try
        {
    		result = (DataSource) (new InitialContext());
        }
        catch(javax.naming.NamingException ex1) {
        	result = null;
        }
        if (result!=null) {
        	return result;
        }
    	/**************************************************************/
    	
        return result;
    }
    
    
    /**Inizializzazione datasource */
    private void _init()
        throws NamingException, SQLException
    {
    	Connection cnn = null;
        
        //Inizializzazione datasource
        connectionPool = getDataSource();

        try 
        {     	 
          //Recupera la connessione
          cnn = connectionPool.getConnection();


				} finally {
					//RILASCIO CONNESSIONE DATABASE
					if (cnn!=null) {
						cnn.close();
					}
 				}
        
    }
    
		/**
		 * Initialize per unit test
		 * 
		 * @param testConnection
		*/
		public void initTest(Connection testConnection)  {
			try {
				this.testConnection = new SqlConnection(testConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void initTest(DataSource ds, int dbms)  {
			this.connectionPool = ds;
		}
		
		public SqlConnection getTestConnection() {
			return this.testConnection;
		}
    
}

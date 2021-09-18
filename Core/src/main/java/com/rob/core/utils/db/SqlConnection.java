package com.rob.core.utils.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.rob.core.exceptions.SessionObjectException;
import com.rob.core.utils.java.Commons;
import com.rob.core.utils.java.SessionObject;


/**
 * Il wrapper della classe java.sql.Connection.
 */
public class SqlConnection {
	
	private TransactionStatus transactionStatus;
	  private Object parentConnectionHolder;
	  
	  protected Connection cnn = null;

	  private Vector<Statement> statementPool = new Vector<Statement>();

	  private Vector<PreparedStatement> preparedStmtPool = new Vector<PreparedStatement>();

	  /**
	   * Crea una connessione al database che per essere utilizzata deve essere inizializzata.
	   */
	  public SqlConnection() {

	  }
	  
	  /**Mappa statica per la gestione delle connessioni */
	  private static Set<Integer> connMap = ConcurrentHashMap.newKeySet();
	  
	  /**Ogni connessione viene inizializzata una sola volta eseguendo alcuni comandi predefiniti (CryptContext, NLS, etc..)  */
	  private void init(Connection connection) {
		try {
			if (connMap.contains(connection.hashCode())) {
//				System.out.println("REUSE CONNECTION HASHCODE:" + connection.hashCode());
				return;
			}
			
//			System.out.println("INIT CONNECTION HASHCODE:" + connection.hashCode());
			
	        //Imposta il contesto per l'eventuale cifratura dei dati
	        setCryptContext(connection);
	        
	        //Set NLS
	        setDateFormat();
	        
	        //Memorizza inizializzazione
	        connMap.add(connection.hashCode());
	        
	        //La mappa cresce di continuo: limito il suo tasso di crescita per evitare memory leak
	        if (connMap.size()>10000) {
	        	connMap = ConcurrentHashMap.newKeySet();
	        }
		} catch (Exception e) {
			connMap = ConcurrentHashMap.newKeySet();
		}
	  }
	  
	  /**
	   * Crea e inizializza una connessione al database a partire da quella specificata già pronta per essere utilizzata.
	   * 
	   * @param connection
	   *          La connessione al database
	   * @throws SQLException
	   */
	  public SqlConnection(Connection connection) throws SQLException {
	    cnn = connection;
	    
	    //Inizializza connessione (CryptContext, NLS, etc..)
	    init(connection);
	    
	  }
	  
	  /** Restituisce l'oggetto Connection incapsulato dalla classe 
	 * @return */
	  public Connection getConnection() {
	  	return cnn;
	  }
	  
	  /**
	   * Indica se la connessione ha una transazione pendente.
	   * 
	   * @return un booleano che indica se c'è una transazione attiva per la connessione.
	   */
	  public boolean isTransactionPending() {
	    return transactionStatus != null;
	  }

	  /**
	   * Il metodo disattiva il commit automatico delle transazioni. Serve per iniziare una transazione che si concluderà
	   * con una commit o una rollback. Inoltre imposta l'indicatore di transazione pendente.
	   * 
	   * @throws SQLException
	   *           si verifica un problema sulla connessione viene sollevata una SQLException.
	   */
	  public void beginTrans() throws SQLException {
		  
		  if (transactionStatus != null) {
			  throw new SessionObjectException("E' gia' presente una transazione, impossibile proseguire.");
		  }
		  
		  parentConnectionHolder = TransactionSynchronizationManager.unbindResourceIfPossible(getTransactionManager().getDataSource());
		  
		  TransactionSynchronizationManager.bindResource(getTransactionManager().getDataSource(), new ConnectionHolder(cnn));
		  
		  transactionStatus = getTransactionManager().getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
	  }
	  
	  /**
	   * Il metodo esegue la commit di una transazione. Inoltre azzera l'indicatore di transazione pendente.
	   * 
	   * @throws SQLException
	   *           si verifica un problema sulla connessione solleva una SQLException.
	   */
	  public void commitTrans() throws SQLException {
		  
		  if (transactionStatus == null) {
			  throw new SessionObjectException("Non esiste nessuna transazione aperta, impossibile proseguire.");
		  }
		  
		  getTransactionManager().commit(transactionStatus);
		  
		  TransactionSynchronizationManager.unbindResource(getTransactionManager().getDataSource());
		  
		  if (parentConnectionHolder != null) {
			  TransactionSynchronizationManager.bindResource(getTransactionManager().getDataSource(), parentConnectionHolder);
			  parentConnectionHolder = null;
		  }
		  
		  transactionStatus = null;
	  }

	  /**
	   * Il metodo provvede ad eseguire il rollback di una transazione. Inoltre decrementa il contatore che fornisce il
	   * numero di transazioni pendenti.
	   * 
	   * @throws SQLException
	   *           si verifica un problema sulla connessione solleva una SQLException.
	   */
	  public void rollBackTrans() throws SQLException {
		  
		  if (transactionStatus == null) {
			  return;
		  }
		  
		  getTransactionManager().rollback(transactionStatus);
		  
		  TransactionSynchronizationManager.unbindResource(getTransactionManager().getDataSource());
		  
		  if (parentConnectionHolder != null) {
			  TransactionSynchronizationManager.bindResource(getTransactionManager().getDataSource(), parentConnectionHolder);
			  parentConnectionHolder = null;
		  }
		  
		  transactionStatus = null;
	  }
	  
	  /**
	   * Il metodo provvede ad impostare il formato della data sul server RDBMS per evitare l'inversione dei campi giorno e
	   * mese. Il formato dataora è impostato a "dd/MM/yyyy hh:mm:ss"
	   * 
	   * @throws SQLException
	   *           si verifica un problema sulla connessione solleva una SQLException.
	   */
	  private void setDateFormat() throws SQLException {
	      execute("SET DATEFORMAT DMY");
	      execute("SET DATEFIRST 1");
	  }
	  
	  /**
	   * Restituisce la data e l'ora corrente del RDBMS nel formato "dd/MM/yyyy HH:mm:ss".
	   * 
	   * @return Torna una stringa contentente la dataora di sistema del DBMS.
	   * @throws SQLException
	   *           si verifica un problema sulla connessione solleva una SQLException.
	   */
	  public String getDate() throws SQLException {

	    String query = "";
	    String result = "";

	    query = "SELECT GETDATE()";

	    ResultSet rst = null;
	    Statement stmt = null;
	    try {
	    	stmt = cnn.createStatement();
	    	rst = stmt.executeQuery(query);

	    	if (rst.next()) {
	    		result = Commons.formatDateTime(rst.getTimestamp(1));
	    	}
	    	
			} finally {
				this.closeResultSets(rst);
				this.closeStatements(stmt);
			}   

	    return result;
	  }
	  
	  /**
	   * Esegue una query di aggiornamento o modifica nel DB.
	   * 
	   * @param query
	   *          query da eseguire
	   * @return int numero di record aggiornati
	   * @throws SQLException
	   */
	  public int execute(String query) throws SQLException {
	    return execute(query, true);
	  }

	  /**
	   * Esegue una query di aggiornamento o modifica nel DB.
	   * 
	   * @param query
	   *          Query da eseguire
	   * @param upperCase
	   *          Trasforma in UpperCase la query
	   * @return int Numero di record aggiornati
	   * @throws SQLException
	   */
	  public int execute(String query, boolean upperCase) throws SQLException {

	    Statement statement = null;
	    int recordsAffected;

	    try {
	    	statement = cnn.createStatement();
	      if (upperCase) {
	        recordsAffected = statement.executeUpdate(query.toUpperCase());
	      } else {
	        recordsAffected = statement.executeUpdate(query);
	      }
	    } finally {
	    	this.closeStatements(statement);     
	    }

	    return recordsAffected;
	  }
	  
	  /**
	   * Esegue una query di aggiornamento o modifica nel DB.
	   * 
	   * @param bld
	   * 					PreparedStatementBuilder da eseguire
	   * @return int Numero di record aggiornati
	   * @throws SQLException
	   */
		public int executeUpdate(PreparedStatementBuilder bld) throws SQLException {
	  	try {
	  		if (bld==null) {
	  			return 0;
	  		}
	  		
	  		//Esegue comando su PreparedStatementBuilder
				return bld.executeUpdate(this);
				
			} finally {
				//Chiude le risorse utilizzate
				if (bld!=null) {
					bld.close();
				}
			}
	  }
	  
	  /**
	   * Rilascia la connessione al pool.
	   * 
	   * @throws SQLException
	   */
	  public void close() throws SQLException {

	    // Se esiste una transazione effettua una rollback per assicurarsi che tutto
	    // sia chiuso prima di rilasciare la connessione e chiudere gli statement.
	  	try {
	      if (isTransactionPending()) {
	        rollBackTrans();
	      }
			} catch (Exception e) {
				//Blocca eventuale eccezione
			}

	  	// chiude tutti gli statement eventualmente aperti
	  	try {
			  while (statementPool.size() > 0) {
			  	this.closeStatements(statementPool.elementAt(0));
			    statementPool.removeElementAt(0);
			  }
			} catch (Exception e) {
				//Blocca eventuale eccezione
			}

	    // chiude tutti i prepareStatement eventualmente aperti
	  	try {
		    while (preparedStmtPool.size() > 0) {
		    	this.closeStatements(preparedStmtPool.elementAt(0));
		      preparedStmtPool.removeElementAt(0);
		    }
			} catch (Exception e) {
				//Blocca eventuale eccezione
			}
	  	
	    // rilascia la connessione al pool
	    cnn.close();
	  }
	  
	  /**
	   * Metodo per il prepareStatement
	   * 
	   * @param strSql
	   *          Stringa SQL
	   * @return PreparedStatement
	   * @throws SQLException
	   */
	  public PreparedStatement prepareStatement(String strSql) throws SQLException {

	    PreparedStatement prepStmt = cnn.prepareStatement(strSql);
	    preparedStmtPool.addElement(prepStmt);

	    return prepStmt;
	  }

	  /**
	   * Prepara uno statement che può essere utilizzato per chiamare una stored procedure.
	   * 
	   * @param sql
	   *          Il sorgente della chiamata da efettuare.
	   * @return un CallableStatement che contiene la chiamata SQL precompilata.
	   * @throws SQLException
	   *           in caso di errore di accesso al database.
	   */
	  public CallableStatement prepareCall(String sql) throws SQLException {

	    CallableStatement stmt = cnn.prepareCall(sql);
	    statementPool.addElement(stmt);

	    return stmt;
	  }
	  
	  
		/**
		 * Chiude tutti i resultset passati in inputed ed eventualmente i relativi statement associati <br><BR>
		 * 
		 * <hr>
		 * 	<blockquote>
		 * 		<b> NOTA BENE </B><BR>
		 * 		Utilizzare sempre all'interno di un blocco finally
		 * 		</p>
		 * 	</blockquote>
		 * <hr>
		 * <BR><BR>
		 * 
		 * @param resultSets uno o più resultset
		 */
		protected final void closeResultSets(ResultSet...resultSets){
			try 
			{			
				//Niente da chiudere
				if (resultSets==null) {
					return;
				}
				
				for (ResultSet rs : resultSets) {
					try {
						if (rs!=null) {
							rs.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				//do nothing
			}
		}	


		
		
		/**
		 * Chiude tutti gli statement passati in input ed eventualmente i relativi resultset associati <br><BR>
		 * 
		 * <hr>
		 * 	<blockquote>
		 * 		<b> NOTA BENE </B><BR>
		 * 		Utilizzare sempre all'interno di un blocco finally
		 * 		</p>
		 * 	</blockquote>
		 * <hr>
		 * <BR><BR>
		 * 
		 * @param statements	uno o più statement
		 */
		protected final void closeStatements(Statement...statements){
			try 
			{
				if (statements==null) {
					return;
				}
				
				for (Statement stmt : statements) {
					try {
						if (stmt!=null) {
							stmt.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			} catch (Exception e) {
				//do nothing
			}
		}
		
		private DataSourceTransactionManager getTransactionManager() {
			return SpringContextBridge.getBean(DataSourceTransactionManager.class);
		}
		
		 /**Applica contesto di criptatura a connessione fornita*/
		private void setCryptContext(Connection connection) throws SQLException {
			if (DataEncryption.getInstance().isEnabled()) {
				CallableStatement cs = null;
				try {
					cs = DataEncryption.getInstance().setCryptContext(connection);
					cs.execute();
				} finally {
					SessionObject.closeStatements(cs);
					cs = null;
				}
			}
		} 

}

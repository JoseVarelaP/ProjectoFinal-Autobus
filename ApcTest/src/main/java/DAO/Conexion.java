package DAO;
import java.sql.*;
/**
 *
 * @author joseluis
 */
public class Conexion{
	final private String user = "postgres";
	private String pswd = "";
	private String bd = "";
	private String server = "";
	final private String driver = "org.postgresql.Driver";
	private Connection con; 
	
	public Conexion( String DatabaseName ){
		if( DatabaseName.isEmpty() )
		{
			System.out.println("No hay base de datos ingresado con los datos. Abortando.");
			return;
		}

		this.bd = DatabaseName;
		this.server = "jdbc:postgresql://localhost:5432/"+this.bd;

		try{
			// Solamente necesario en java < 1.6.
			Class.forName(this.driver);
			this.con = DriverManager.getConnection(this.server, this.user, this.pswd);
			
			if(this.con != null){
				System.out.println("La conexión a la BD: "+ this.server +" " + "se realizo al 100%");
			}
		}
		catch(SQLException ex){
			System.out.println("Error al intentar conectarse a la BD"+  this.server);
			this.con = null;
		}
		catch(ClassNotFoundException ex){
			System.out.println( "No se encontró la clase para conectarse. [" + ex + "]" );
			this.con = null;
		}
	}
	
	public void setQuery (String query){
		// Statement state = null;
		try{
			// state = con.createStatement();
			// state.executeQuery(query);
			con.createStatement().executeQuery(query);
		}
		catch (SQLException e){
			// Netbeans marca que esto deberia ser removido.
			// e.printStackTrace();
		}
	}

	public Connection getConnection()
	{
		return this.con;
	}
}

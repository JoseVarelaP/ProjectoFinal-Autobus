import java.sql.*;
/**
 *
 * @author joseluis
 */
class MiBDDaw{
	private static String user = "postgres";
	private static String pswd = "";
	private static String bd = "";
	private static String server = "jdbc:postgresql://localhost:5432/"+bd;
	private static String driver = "org.postgresql.Driver";
	private static Connection con; 
	
	public MiBDDaw( String DatabaseName ){
		if( DatabaseName.isEmpty() )
		{
			System.out.println("No hay base de datos ingresado con los datos. Abortando.");
			return;
		}
		try{
			// Solamente necesario en java < 1.6.
			Class.forName(driver);
			con = DriverManager.getConnection(server, user, pswd);
			
			if(con != null){
				System.out.println("La conexión a la BD: "+ server +" " + "se realizo al 100%");
			}
		}
		catch(SQLException ex){
			System.out.println("Error al intentar conectarse a la BD"+  server);
		}
		catch(ClassNotFoundException ex){
			System.out.println( "No se encontró la clase para conectarse. [" + ex + "]" );
		}
	}	
	
	public ResultSet getQuery(String query){
		Statement state = null;
		ResultSet result = null;

		if( con == null )
		{
			System.out.println("La conexión con el servidor no se ha establecido.");
			return result;
		}
		
		try{
			state=con.createStatement();
			result=state.executeQuery(query);
		}
		catch (SQLException e){
                        // Netbeans marca que esto deberia ser removido.
			e.printStackTrace();
		}
		return result;
	}
	
	public void setQuery (String query){
		Statement state = null;
		try{
			state = con.createStatement();
			state.executeQuery(query);
		}
		catch (SQLException e){
                        // Netbeans marca que esto deberia ser removido.
			e.printStackTrace();
		}
	}
}

import java.sql.*;
/**
 *
 * @author joseluis
 */
class MiBDDaw{
	final private static String user = "postgres";
	final private static String pswd = "";
	final private static String bd = "joseluis";
	final private static String server = "jdbc:postgresql://localhost:5432/"+bd;
	final private static String driver = "org.postgresql.Driver";
	private static Connection con; 
	
	public MiBDDaw(){
		try{
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
			System.out.println(ex);
		}
	}	
	
	public ResultSet getQuery(String query){
		Statement state = null;
		ResultSet result = null;
		
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

import java.sql.*;

class DAO{
	private Connection con;

	public DAO( Connection con )
	{
		this.con = con;
	}
	ResultSet Consulta(String query){
		Statement state = null;
		ResultSet result = null;

		if( this.con == null )
		{
			System.out.println("La conexión con el servidor no se ha establecido.");
			return result;
		}
		
		try{
			state = this.con.createStatement();
			result = state.executeQuery(query);
		}

		catch (SQLException e){
			e.printStackTrace();
		}
		return result;
	}
}
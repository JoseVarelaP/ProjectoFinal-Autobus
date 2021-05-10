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

	Boolean Agregar(String tabla, String[][] valores)
	{
		// Tenemos que procesar que tipo de informacion acabamos de recibir para de ahi ingresarlo.
		String conv = "";
		conv += String.format("INSERT INTO %s VALUES ", tabla);
		for( int i = 0; i < valores.length; i++ )
		{
			String linea = "(";
			for (String a : valores[i] )
				linea += String.format("%s,", a);
			
			linea += ")" + (i < valores.length ? "," : ";");
			conv += linea;
		}

		// Ya que tenemos la informacion procesada para utilizar, vamos a enviarlo.
		ResultSet res = this.Consulta(conv);

		// Si almenos tenemos un resultado despues de realizar esta operacion, entonces
		// el proceso fue existoso.
		if( !res.next() )
			return false;

		return true;
	}

	Boolean Eliminar( String tabla, String condicion )
	{
		// Verifica datos que recibimos, y que no estén vacios. No queremos eliminar una tabla completa, no?
		if( tabla.isEmpty() || condicion.isEmpty() )
		{
			System.out.println("[Eliminar] No hay suficientes datos disponibles.");
			return false;
		}
		// Primero busca la tabla, y verifica que existe.
		ResultSet res = this.Consulta( String.format("DELETE FROM %s WHERE %s", tabla, condicion) );

		return true;
	}
}
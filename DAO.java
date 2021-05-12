import java.sql.*;
import java.util.StringTokenizer;

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

	Boolean ProcesarConsulta( String query, String elemento_buscar )
	{
		if( elemento_buscar.isEmpty() )
		{
			System.out.println("[ProcesarConsulta] No hay suficientes datos disponibles.");
			return false;
		}

		ResultSet result = this.Consulta(query);

		if( result == null )
			return false;

		// Tenemos exito, hora de mostrar el menú.
		System.out.println("Resultado:");
		try{
			while(result.next()){
				String nombre = result.getString(elemento_buscar);
				// Comienza a dividir lo que contenga el nombre, y manda el resultado.
				StringTokenizer st = new StringTokenizer( nombre ,"(,)");
				while (st.hasMoreTokens()) {
					System.out.print(st.nextToken() + " ");
				}
				System.out.print("\n");
			}
		} catch (SQLException e)
		{
			return false;
		}

		return true;
	}

	Boolean Agregar(String tabla, String[][] valores)
	{
		// Tenemos que procesar que tipo de informacion acabamos de recibir para de ahi ingresarlo.
		String conv = "";
		conv += String.format("INSERT INTO %s VALUES (", tabla);

		Boolean necesitacorte = false;
		for( int i = 0; i < valores.length; i++ )
		{
			String linea = "";
			
			// Regla especial, ya que tenemos un ROW aqui, vamos a usar una bandera
			// para detectarel uso de esa misma.

			if( valores[i][0].matches("__ROW") && valores[i][1].matches("true") )
			{
				linea += "row(";
			}
			else if( valores[i][0].matches("__ROW") && valores[i][1].matches("false") )
			{
				//System.out.println( conv.substring(0, conv.length() - 1) );
				conv = conv.substring(0, conv.length() - 1);
				linea += "),";
			}
			else
			{
				//System.out.println( "\""+valores[i][0].substring( 4, valores[i][0].length() )+"\"" );
				linea += String.format( (i < valores.length-1) ? "%s," : "%s",
				valores[i][0].indexOf("STR_") != -1 ?
					( valores[i][1].equals("null") ? "null" : "\'"+valores[i][1]+"\'")
				: valores[i][1]
				);
			}

			// busca si el valor siguiente contiene la bandera falsa.
			/*
			if( i <= valores.length-2 && valores[i+1] != null )
			{
				if( valores[i+1][0].matches("__ROW") && valores[i+1][1].matches("false") )
				{
					linea.substring(0, linea.length() - 1);
				}
			}
			*/
			
			// for (String a : valores[i] )
			//for( int a = 0; a < valores[i].length; a++ )
			
			//linea += (i <= valores.length ? "" : ");");
			conv += linea;
		}

		conv += ");";

		// Ya que tenemos la informacion procesada para utilizar, vamos a enviarlo.
		try{
			System.out.println( conv );
			ResultSet res = this.Consulta(conv);
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			return false;
		}

		// El proceso fue exitoso.
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
		try{
			ResultSet res = this.Consulta( String.format("DELETE FROM %s WHERE %s", tabla, condicion) );
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			return false;
		}

		// El proceso fue exitoso.
		return true;
	}

	Boolean Modificar( String tabla, String nuevos_datos, String condicion )
	{
		// Verifica datos que recibimos, y que no estén vacios. No queremos eliminar una tabla completa, no?
		if( tabla.isEmpty() || condicion.isEmpty() || nuevos_datos.isEmpty() )
		{
			System.out.println("[Modificar] No hay suficientes datos disponibles.");
			return false;
		}
		// Primero busca la tabla, y verifica que existe.
		try{
			ResultSet res = this.Consulta( String.format("UPDATE %s SET %s WHERE %s", tabla, nuevos_datos, condicion) );
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			return false;
		}

		// El proceso fue exitoso.
		return true;
	}
}
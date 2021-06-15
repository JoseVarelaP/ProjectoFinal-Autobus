package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DAO{
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

		System.out.println("QUERY: " + query);
		
		try{
			state = this.con.createStatement();
			result = state.executeQuery(query);
		}

		catch (SQLException e){
			// e.printStackTrace();
		}
		return result;
	}

	/*
	public ArrayList<ArrayList<String>> ProcesarConsulta( String query, String elemento_buscar )
	{
		String[] val = { elemento_buscar };
		return ProcesarConsulta( query, val );
	}
	*/

	/**
	 * Regresa el conteo de objetos que se requieran de query.
	 * @param query	Las tablas a buscar la cantidad de elementos. 
	 * @return	Texto que representa el numero de objetos en la consulta.
	 */
	public int ConteoConsulta( String query )
	{
		ResultSet result = this.Consulta( String.format( "SELECT COUNT(*) AS total FROM %s", query ) );
		try{
			if( result.next() )
			{
				return result.getInt("total");
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}

	public ArrayList<ArrayList<String>> ProcesarConsulta( String query, String[] elemento_buscar )
	{

		ArrayList<ArrayList<String>> resultados = new ArrayList();
		ResultSet result = this.Consulta(query);

		if( result == null )
			return resultados;

		// Tenemos exito, hora de mostrar el menú.
		System.out.println("Resultado:");
		try{
			while(result.next()){
				String nombre = "";
				ArrayList<String> nom = new ArrayList();

				if( elemento_buscar != null && elemento_buscar.length != 0 )
					for( String l : elemento_buscar )
					{
						String val = result.getString(l).isEmpty() ? "null" : result.getString(l);
						nombre += val + ",";
					}
				else {
					for( int i = 1; i < 6; i++ )
						nombre += result.getString(i) + ",";
				}

				// Comienza a dividir lo que contenga el nombre, y manda el resultado.
				StringTokenizer st = new StringTokenizer( nombre ,"(,)");
				while (st.hasMoreTokens()) {
					nom.add(st.nextToken());
				}
				resultados.add(nom);
				System.out.print("\n");
			}
		} catch (SQLException e)
		{
			return resultados;
		}

		return resultados;
	}

	public String ConvierteLista( ArrayList<String> s )
	{
		String conv = "[";
		for( String a : s )
			conv += String.format("'%s',", a);
		conv += "]";
		return conv;
	}

	String ConvertirDatos( String[][] valores, Boolean convertir_llave )
	{
		String conv = "";
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
				Boolean NecesitaString = (valores[i][0].indexOf("STR_") != -1);
				Boolean EsNulo = valores[i][1].equals("null");
				String llaveconv = NecesitaString ? valores[i][0].substring( 4, valores[i][0].length() ) : valores[i][0];
				String valorconv = (NecesitaString ? ( EsNulo ? "null" : "\'"+valores[i][1]+"\'") : valores[i][1]);
				
				linea += String.format( (i < valores.length-1) ? "%s," : "%s",
				( /*(convertir_llave) ? llaveconv + " = " : "") + */ valorconv ) );
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

		return conv;
	}

	Boolean Agregar(String tabla, String[][] valores)
	{
		// Tenemos que procesar que tipo de informacion acabamos de recibir para de ahi ingresarlo.
		String conv = String.format("INSERT INTO %s VALUES (" + this.ConvertirDatos(valores, false), tabla);
		// conv += String.format("INSERT INTO %s VALUES (", tabla);

		// Ya que tenemos la informacion procesada para utilizar, vamos a enviarlo.
		try{
			System.out.println( conv );
			ResultSet res = this.Consulta(conv);
		} catch (Exception e)
		{
			// System.err.println(e.getMessage());
			return false;
		}

		// El proceso fue exitoso.
		return true;
	}

	Boolean Eliminar( String tabla, String condicion ) throws Exception
	{
		// Verifica datos que recibimos, y que no estén vacios. No queremos eliminar una tabla completa, no?
		if( tabla.isEmpty() || condicion.isEmpty() )
		{
			throw new Exception("[Eliminar] No hay suficientes datos disponibles.");
		}
		// Primero busca la tabla, y verifica que existe.
		try{
			String str = String.format("DELETE FROM %s WHERE %s", tabla, condicion);
			System.out.println( str );
			ResultSet res = this.Consulta( str );
		} catch (Exception e)
		{
			throw new Exception(e);
		}

		// El proceso fue exitoso.
		return true;
	}

	Boolean Modificar( String tabla, String nuevo_dato, String condicion ) throws Exception
	{
		// Verifica datos que recibimos, y que no estén vacios. No queremos eliminar una tabla completa, no?
		if( tabla.isEmpty() || condicion.isEmpty() || nuevo_dato.isEmpty() )
		{
			throw new Exception("[Modificar] No hay suficientes datos disponibles.");
		}
		// Primero busca la tabla, y verifica que existe.
		try{
			String str = String.format("UPDATE %s SET %s WHERE %s;", tabla, nuevo_dato, condicion);
			System.out.println( str );
			this.Consulta( str );
		} catch (Exception e)
		{
			throw new Exception(e);
		}

		// El proceso fue exitoso.
		return true;
	}
}
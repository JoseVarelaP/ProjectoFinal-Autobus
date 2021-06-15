package DAO;
import java.sql.*;

public class PuntoParada extends DAO {
	int Ind_Parada;
	String NombreParada;

	public PuntoParada(Connection c)
	{
		super(c);
	}

	public int Ind_Parada() { return this.Ind_Parada; }
	public String NombreParada() { return this.NombreParada; }

	public void Ind_Parada( int d ) { this.Ind_Parada = d; }
	public void NombreParada( String d ) { this.NombreParada = d; }

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public boolean ObtenerInfo( int ID )
	{
		ResultSet result = this.Consulta(
			String.format("SELECT ind_parada as IND, nombre_parada as Nombre FROM punto_parada WHERE ind_Parada = %s;", ID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.Ind_Parada +" tuvo un resultado nulo.");
			return false;
		}
		
		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				this.Ind_Parada = Integer.parseInt(result.getString("IND"));
				this.NombreParada = result.getString("Nombre");
			}
		} catch (SQLException e)
		{
			System.out.println(e);
		}
		return true;
	}

	/**
	 * Actualiza información existente de una entrada en la base de datos.
	 * Asume que el elemento ya existe.
	 * @return Resultado si la inserción fue exitosa.
	 */
	public boolean ActualizarInformacion()
	{
		String condicion = "ind_parada = " + this.Ind_Parada;

		try{
			this.Modificar( "punto_parada" , "nombre_parada = '" + this.NombreParada +"'", condicion );
		} catch (Exception e)
		{
			System.out.println(e);
			return false;
		}
		
		return true;
	}

	/**
	 * Registra la información de esta clase como una nueva entrada en la base de datos.
	 * @return Resultado si la inserción fue exitosa.
	 */
	public boolean RegistrarInformacion()
	{
		// Esta tabla son elementos para agregar una entrada.
		String Som[][] = {
			{"ind_parada", "DEFAULT"},
			{"STR_nombre_parada", this.NombreParada}
		};

		super.Agregar( "punto_parada", Som );
		return true;
	}
}
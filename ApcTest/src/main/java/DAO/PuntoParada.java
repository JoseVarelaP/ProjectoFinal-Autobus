package DAO;
import java.sql.*;

public class PuntoParada extends DAO {
	int Ind_Parada;
	String NombreParada;

	public PuntoParada(Connection c)
	{
		super(c);
	}

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public void ObtenerInfo( int ID )
	{
		ResultSet result = this.Consulta(
			String.format("SELECT ind_parada as IND, nombre_parada as Nombre FROM punto_parada WHERE Ind_Parada = %s;", ID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.Ind_Parada +" tuvo un resultado nulo.");
			return;
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
	}

	/**
	 * Realiza la señal devuelta al servidor de que queremos registrar nuevos datos
	 * provenientes de donde estabamos.
	 */
	public void ActualizarInformacion()
	{

	}
}
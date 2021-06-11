package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.transform.Result;

public class PuntoParada extends DAO {
	int Ind_Parada;
	String NombreParada;

	public PuntoParada(Connection c, int ID)
	{
		super(c);
		this.Ind_Parada = ID;
		this.ObtenerInfo();
	}

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public void ObtenerInfo()
	{
		ResultSet result = this.Consulta(
			String.format("SELECT fabricante as fab, nombreparada as npar FROM punto_parada WHERE Ind_Parada = %s;", this.NumSerie)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.Ind_Parada +" tuvo un resultado nulo.");
			return;
		}
		
		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				this.Ind_Parada = Integer.parseInt(result.getString("fab"));
				this.NombreParada = result.getDate("npar");
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
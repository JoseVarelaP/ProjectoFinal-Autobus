package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.transform.Result;

public class Autobus extends DAO {
	int NumSerie;
	String Fabricante;
	Date Fabricado;
	int Capacidad;

	public Autobus(Connection c, int ID)
	{
		super(c);
		this.NumSerie = ID;
		this.ObtenerInfo();
	}

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public void ObtenerInfo()
	{
		ResultSet result = this.Consulta(
			String.format("SELECT fabricante as fabricante, fabricado as fabricado, capacidad as capacidad FROM autobus WHERE num_serie = %s;", this.NumSerie)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.NumSerie +" tuvo un resultado nulo.");
			return;
		}
		
		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				// this.NumSerie = Integer.parseInt(result.getString("ID"));

				this.Fabricante = result.getDate("fabricante");
				this.Fabricado = result.getDate("fabricado");
				this.Capacidad = result.getDate("capacidad");
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
package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.transform.Result;

class Nombre {
	String PrimerNombre;
	String SegundoNombre;
	String Ap_Paterno;
	String Ap_Materno;
}

public class Conductor extends DAO {
	int ID;
	Nombre Nombre;
	int Edad;
	Date Fecha_Contrato;
	String Dir;

	public Conductor(Connection c, int ID)
	{
		super(c);
		this.ID = ID;
		this.Nombre = new Nombre();
		this.ObtenerInfo();
	}

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public void ObtenerInfo()
	{
		ResultSet result = this.Consulta(
			String.format("SELECT num_conductor as ID, (nombre).prim_nombre AS prim_nombre, (nombre).segu_nombre AS segu_nombre, (nombre).ap_paterno AS ap_paterno, (nombre).ap_materno AS ap_materno, fecha_contrat as Fecha, direccion as Dir FROM conductor WHERE num_conductor = %s;", this.ID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.ID +" tuvo un resultado nulo.");
			return;
		}

		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				this.ID = Integer.parseInt(result.getString("ID"));
	
				this.Nombre.PrimerNombre = result.getString("prim_nombre");
				this.Nombre.SegundoNombre = result.getString("segu_nombre");
				this.Nombre.Ap_Materno = result.getString("ap_materno");
				this.Nombre.Ap_Paterno = result.getString("ap_paterno");
				this.Fecha_Contrato = result.getDate("Fecha");
				this.Dir = result.getString("Dir");
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
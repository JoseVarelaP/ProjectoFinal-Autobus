package DAO;
import java.sql.*;

public class Rutas extends DAO {
	int num_ruta;
	PuntoParada dest_inicio;
	PuntoParada dest_final;
	String Desc;

	public Rutas(Connection c)
	{
		super(c);
		// this.num_ruta = ID;
		this.dest_inicio = new PuntoParada(c);
		this.dest_final = new PuntoParada(c);
	}

	public int NumRuta() { return this.num_ruta; }
	public PuntoParada DestInicio() { return this.dest_inicio; }
	public PuntoParada DestFinal() { return this.dest_final; }
	public String Descripcion() { return this.Desc; }

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public boolean ObtenerInfo(int numID)
	{
		ResultSet result = this.Consulta(
			String.format("SELECT num_ruta as ID, dest_inicio as dinicio, dest_final as dfinal, descripcion as desc FROM rutas WHERE num_ruta = %s;", numID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.num_ruta +" tuvo un resultado nulo.");
			return false;
		}

		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				this.num_ruta = Integer.parseInt(result.getString("ID"));
				this.dest_inicio.ObtenerInfo( Integer.parseInt(result.getString("dinicio")) );
				this.dest_final.ObtenerInfo( Integer.parseInt(result.getString("dinicio")) );
				this.Desc = result.getString("desc");
			}
		} catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}

		return true;
	}
}

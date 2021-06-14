package DAO;
import java.sql.*;

public class Viajes extends DAO {
	int num_viaje;
	Date horaPartida;
	Date horaLlegada;
	Rutas rutaUsarFK;
	Conductor conductorFK;

	public Viajes(Connection c, int ID)
	{
		super(c);
		this.num_viaje = ID;
		this.rutaUsarFK = new Rutas(c);
		this.conductorFK = new Conductor(c);
	}

	public int NumViaje() { return this.num_viaje; }
	public Date HoraPartida() { return this.horaPartida; }
	public Date HoraLlegada() { return this.horaLlegada; }
	public Rutas RutaAUsar() { return this.rutaUsarFK; }
	public Conductor ConductorAsignado() { return this.conductorFK; }

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public boolean ObtenerInfo(int ID)
	{
		ResultSet result = this.Consulta(
			String.format("SELECT num_viaje as numv, hor_partida as hpa, hor_llegada as hpl, ruta_a_usar_fk as raufk, conductor as cfk FROM viajes WHERE num_viaje = %s;", ID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ ID +" tuvo un resultado nulo.");
			return false;
		}

		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				this.num_viaje = Integer.parseInt(result.getString("numv"));
				this.horaPartida = result.getDate("hpa");
				this.horaLlegada = result.getDate("hpl");
				this.rutaUsarFK.ObtenerInfo( Integer.parseInt(result.getString("raufk")) );
				this.conductorFK.ObtenerInfo( Integer.parseInt(result.getString("cfk")) );
			}
		} catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}

		return true;
	}
}

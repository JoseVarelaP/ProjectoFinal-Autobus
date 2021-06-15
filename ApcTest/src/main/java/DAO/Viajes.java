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

	public void NumViaje( int d ) { this.num_viaje = d; }
	public void HoraPartida( Date d ) { this.horaPartida = d; }
	public void HoraLlegada( Date d ) { this.horaLlegada = d; }
	public void RutaAUsar( Rutas d ) { this.rutaUsarFK = d; }
	public void ConductorAsignado( Conductor d ) { this.conductorFK = d; }

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

	/**
	 * Actualiza información existente de una entrada en la base de datos.
	 * Asume que el elemento ya existe.
	 * @return Resultado si la inserción fue exitosa.
	 */
	public boolean ActualizarInformacion()
	{
		String condicion = "num_viaje = " + this.num_viaje;

		try{
			this.Modificar( "viajes" , "hor_partida = '" + this.horaPartida.toString() + "'", condicion );
			this.Modificar( "viajes" , "hor_llegada = '" + this.horaLlegada.toString() + "'", condicion );
			this.Modificar( "viajes" , "ruta_a_usar_fk = " + this.rutaUsarFK.num_ruta , condicion );
			this.Modificar( "viajes" , "conductor_fk = " + this.conductorFK.ID , condicion );
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
			{"num_viaje", "DEFAULT"},
			{"STR_hor_partida", this.horaPartida.toString()},
			{"STR_hor_llegada", this.horaLlegada.toString()},
			{"ruta_a_usar_fk", Integer.toString(this.rutaUsarFK.num_ruta)},
			{"conductor_fk", Integer.toString(this.conductorFK.ID)}
		};

		super.Agregar( "viajes", Som );
		return true;
	}
}

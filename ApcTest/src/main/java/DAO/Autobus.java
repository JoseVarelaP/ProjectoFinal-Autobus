package DAO;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Autobus extends DAO {
	int NumSerie;
	String Fabricante;
	LocalDate Fabricado;
	int Capacidad;

	public Autobus(Connection c)
	{
		super(c);
		this.ObtenerInfo();
	}

	public int ObtenerSerie() { return this.NumSerie; }
	public String ObtenerFabr() { return this.Fabricante; }
	public LocalDate ObtenerFecha() { return this.Fabricado; }
	public int ObtenerCapacidad() { return this.Capacidad; }

	public void CambiarSerie( int d ) { this.NumSerie = d; }
	public void CambiarFabr( String d ) { this.Fabricante = d; }
	public void CambiarFecha( LocalDate d ) { this.Fabricado = d; }
	public void CambiarCapacidad( int d ) { this.Capacidad = d; }

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
				 this.NumSerie = Integer.parseInt(result.getString("ID"));

				this.Fabricante = result.getString("fabricante");
				this.Fabricado = Instant.ofEpochMilli(result.getDate("fabricado").getTime())
					.atZone(ZoneId.systemDefault())
					.toLocalDate();

				this.Capacidad = result.getInt("capacidad");
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
	public boolean ActualizarInformacion()
	{
		String condicion = "num_serie = " + this.NumSerie;

		try{
			this.Modificar( "autobus" , "fabricante = '" + this.Fabricante +"'", condicion );
			this.Modificar( "autobus" , "fabricado = '" + this.Fabricado.toString() +"'", condicion );
			this.Modificar( "autobus" , "capacidad = " + this.Capacidad, condicion );
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
		// Hay que convertir la fecha ya que SQL date es diferente.
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateObj = LocalDate.parse( this.Fabricado.toString(), DTF );
		
		// Esta tabla son elementos para agregar una entrada.
		String Som[][] = {
			{"num_serie", "DEFAULT"},
			{"STR_fabricante", this.Fabricante},
			{"STR_fabricado", this.Fabricado.toString()},
			{"capacidad", Integer.toString(this.Capacidad)}
		};

		super.Agregar( "autobus", Som );
		return true;
	}
}
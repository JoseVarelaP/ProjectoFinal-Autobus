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
	Rutas Ruta_fk;

	public Autobus(Connection c)
	{
		super(c);
		Ruta_fk = new Rutas(c);
	}

	public int Serie() { return this.NumSerie; }
	public String Fabr() { return this.Fabricante; }
	public LocalDate Fecha() { return this.Fabricado; }
	public int Capacidad() { return this.Capacidad; }
	public Rutas RutaDedicada() { return this.Ruta_fk; }

	public void Serie( int d ) { this.NumSerie = d; }
	public void Fabr( String d ) { this.Fabricante = d; }
	public void Fecha( LocalDate d ) { this.Fabricado = d; }
	public void Capacidad( int d ) { this.Capacidad = d; }
	public void RutaDedicada( Rutas d ) { this.Ruta_fk = d; }

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public boolean ObtenerInfo(int ID)
	{
		ResultSet result = this.Consulta(
			String.format("SELECT num_serie as num_serie, fabricante as fabricante, fabricado as fabricado, capacidad as capacidad, ruta_fk as ruta_fk FROM autobus WHERE num_serie = %s;", ID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.NumSerie +" tuvo un resultado nulo.");
			return false;
		}
		
		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				this.NumSerie = Integer.parseInt(result.getString("num_serie"));

				this.Fabricante = result.getString("fabricante");
				this.Fabricado = Instant.ofEpochMilli(result.getDate("fabricado").getTime())
					.atZone(ZoneId.systemDefault())
					.toLocalDate();

				this.Capacidad = result.getInt("capacidad");

				this.Ruta_fk.ObtenerInfo( Integer.parseInt(result.getString("ruta_fk")) );
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
		String condicion = "num_serie = " + this.NumSerie;

		try{
			this.Modificar( "autobus" , "fabricante = '" + this.Fabricante +"'", condicion );
			this.Modificar( "autobus" , "fabricado = '" + this.Fabricado.toString() +"'", condicion );
			this.Modificar( "autobus" , "capacidad = " + this.Capacidad, condicion );
			this.Modificar( "autobus" , "num_serie = " + this.NumSerie, condicion );
			this.Modificar( "autobus" , "ruta_fk = " + this.Ruta_fk.num_ruta, condicion );
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
			{"num_serie", Integer.toString(this.NumSerie)},
			{"STR_fabricante", this.Fabricante},
			{"STR_fabricado", this.Fabricado.toString()},
			{"capacidad", Integer.toString(this.Capacidad)},
			{"ruta_fk", Integer.toString(this.Ruta_fk.num_ruta)},
		};

		super.Agregar( "autobus", Som );
		return true;
	}
}
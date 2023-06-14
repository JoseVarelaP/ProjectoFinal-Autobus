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

	public void CambiarNumRuta( int d ) { this.num_ruta = d; }
	public void CambiarDestInicio( PuntoParada d ) { this.dest_inicio = d; }
	public void CambiarDestFinal( PuntoParada d ) { this.dest_final = d; }
	public void CambiarDescripcion( String d ) { this.Desc = d; }

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
				this.dest_final.ObtenerInfo( Integer.parseInt(result.getString("dfinal")) );
				this.Desc = result.getString("desc");
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
		String condicion = "num_ruta = " + this.num_ruta;

		try{
			this.Modificar( "rutas" , "dest_inicio = " + this.dest_inicio.Ind_Parada, condicion );
			this.Modificar( "rutas" , "dest_final = " + this.dest_final.Ind_Parada, condicion );
			this.Modificar( "rutas" , "descripcion = '" + this.Desc + "'", condicion );
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
			{"num_ruta", "DEFAULT"},
			{"dest_inicio", Integer.toString(this.dest_inicio.Ind_Parada)},
			{"dest_final", Integer.toString(this.dest_final.Ind_Parada)},
			{"STR_descripcion", this.Desc}
		};

		super.Agregar( "rutas", Som );
		return true;
	}
}

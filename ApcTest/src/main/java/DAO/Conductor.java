package DAO;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

public class Conductor extends DAO {
	int ID;
	Nombre Nombre;
	int Edad;
	LocalDate Fecha_Contrato;
	String Dir;

	public Conductor(Connection c)
	{
		super(c);
		this.Nombre = new Nombre();
	}

	public int ObtenerID() { return this.ID; }
	public String PrimerNombre() { return Optional.ofNullable(this.Nombre.PrimerNombre).orElse(""); }
	public int Edad() { return this.Edad; }
	public String SegundoNombre() { return Optional.ofNullable(this.Nombre.SegundoNombre).orElse(""); }
	public String ApellidoPaterno() { return Optional.ofNullable(this.Nombre.Ap_Paterno).orElse(""); }
	public String ApellidoMaterno() { return Optional.ofNullable(this.Nombre.Ap_Materno).orElse(""); }
	public LocalDate FechaContrato() { return this.Fecha_Contrato; }
	public String Direccion() { return Optional.ofNullable(this.Dir).orElse(""); }

	public String ObtenerNombreCompleto()
	{
		return String.format( "%s %s %s %s",
					this.PrimerNombre(),
					this.SegundoNombre(),
					this.ApellidoPaterno(),
					this.ApellidoMaterno()
				);
	}

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public boolean ObtenerInfo( int numID )
	{
		ResultSet result = this.Consulta(
			String.format("SELECT num_conductor as ID, edad as Ed, (nombre).prim_nombre AS prim_nombre, (nombre).segu_nombre AS segu_nombre, (nombre).ap_paterno AS ap_paterno, (nombre).ap_materno AS ap_materno, fecha_contrat as Fecha, direccion as Dir FROM conductor WHERE num_conductor = %s;", numID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.ID +" tuvo un resultado nulo.");
			return false;
		}

		try{
			while( result.next() ){
				// Tenemos datos! hora de registrarlos.
				this.ID = Integer.parseInt(result.getString("ID"));

				this.Edad = Integer.parseInt(result.getString("Ed"));
	
				this.Nombre.PrimerNombre = result.getString("prim_nombre");
				this.Nombre.SegundoNombre = result.getString("segu_nombre");
				this.Nombre.Ap_Materno = result.getString("ap_materno");
				this.Nombre.Ap_Paterno = result.getString("ap_paterno");
				this.Fecha_Contrato = Instant.ofEpochMilli(result.getDate("Fecha").getTime())
					.atZone(ZoneId.systemDefault())
					.toLocalDate();
				this.Dir = result.getString("Dir");
			}
		} catch (SQLException e)
		{
			System.out.println("uh oh");
			System.out.println(e);
			return false;
		}

		return true;
	}

	/**
	 * Realiza la señal devuelta al servidor de que queremos registrar nuevos datos
	 * provenientes de donde estabamos.
	 */
	public boolean ActualizarInformacion()
	{
		// TODO: Actualizar esta dirección directo en postgres.
		String condicion = "num_conductor = " + this.ID;

		try{
			this.Modificar( "conductor" , "edad = " + this.Edad, condicion );
			this.Modificar( "conductor" , "nombre.prim_nombre = '" + this.Nombre.PrimerNombre +"'", condicion );
			this.Modificar( "conductor" , "nombre.segu_nombre = '" + this.Nombre.SegundoNombre +"'", condicion );
			this.Modificar( "conductor" , "nombre.ap_materno = '" + this.Nombre.Ap_Materno +"'", condicion );
			this.Modificar( "conductor" , "nombre.ap_paterno = '" + this.Nombre.Ap_Paterno +"'", condicion );
			this.Modificar( "conductor" , "direccion = '" + this.Dir +"'", condicion );
			this.Modificar( "conductor" , "fecha_contrat = '" + this.Fecha_Contrato.toString() +"'", condicion );
		} catch (Exception e)
		{
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	// some
	public void CambiarID(int n)
	{
		this.ID = n;
	}

	public void CambiarNombre( Nombre nl )
	{
		this.Nombre.PrimerNombre = nl.PrimerNombre;
		this.Nombre.SegundoNombre = nl.SegundoNombre;
		this.Nombre.Ap_Materno = nl.Ap_Materno;
		this.Nombre.Ap_Paterno = nl.Ap_Paterno;
	}
	
	public void CambiarFecha( LocalDate lvm )
	{
		this.Fecha_Contrato = lvm;
	}

	public void CambiarDireccion( String Dir )
	{
		this.Dir = Dir;
	}
	
	public void CambiarEdad( int nuevaEdad )
	{
		this.Edad = nuevaEdad;
	}

	public void EliminarEntrada()
	{
		System.out.println("Eliminando...");
	}
}

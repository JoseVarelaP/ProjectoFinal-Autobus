package DAO;
import java.sql.*;

public class Rutas extends DAO {
	int num_ruta;
	PuntoParada dest_inicio;
	PuntoParada dest_final;
	String Desc;

	public Rutas(Connection c, int ID)
	{
		super(c);
		this.num_ruta = ID;
		this.dest_inicio = new PuntoParada();
		this.dest_final = new PuntoParada();
	}

	public String NumRuta() { return this.num_ruta; }
	public String DestInicio() { return this.dest_inicio; }
	public String DestFinal() { return this.dest_final; }
	public String Descripcion() { return this.Desc; }

	/**
	 * Realiza la busqueda de la informacion del conductor para registrarlos en la clase.
	 */
	public boolean ObtenerInfo()
	{
		ResultSet result = this.Consulta(
			String.format("SELECT num_ruta as ID, dest_inicio as dinicio, dest_final as dfinal, descripcion as desc FROM rutas WHERE num_ruta = %s;", this.ID)
		);
		
		if( result == null )
		{
			System.out.println("Whoops, "+ this.ID +" tuvo un resultado nulo.");
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
	
	// some
	public void CambiarID(int n)
	{
		this.ID = n;
	}

	public void CambiarDireccion( String Dir )
	{
		this.Dir = Dir;
	}
}

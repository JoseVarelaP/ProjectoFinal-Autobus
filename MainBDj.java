/**
 *
 * @author joseluis
 */
import java.sql.*;
import java.util.Scanner;

public class MainBDj{
	public static void main (String []args){
		Scanner sc = new Scanner(System.in);

		System.out.println("Ingresa el nombre de la base de datos a buscar: ");
		Conexion conexion = new Conexion( sc.nextLine() );

		// Cierra el escaneador para evitar un leak.
		sc.close();

		ResultSet result;
		String appaterno,nombre,apmaterno;
		
		System.out.println("buscando");
		DAO administrador = new DAO( conexion.getConnection() );

		System.out.println("Mostrando lista.");
		administrador.ProcesarConsulta( "SELECT * FROM conductor", "nombre" );

		System.out.println("Eliminando una entrada de lista.");
		administrador.Eliminar( "conductor", "num_conductor = 5" );

		System.out.println("Agregando una entrada de lista.");
		// (5,31,row('Gonzalo','Torres','Romero',null), '2010-04-12', 'Dir Cinco'),

		String Som[][] = {
			{"num_conductor", "5"},
			{"edad", "31"},
			// Esta tabla representa un valor multi proveniente de un type.
			
			{"__ROW","true"},
			{"STR_prim_nombre","Gonzalo"},
			{"STR_segu_nombre","Torres"},
			{"STR_ap_paterno","Romero"},
			{"STR_ap_materno","null"},
			{"__ROW","false"},

			{"STR_fecha_contrat", "2010-04-12"},
			{"STR_direccion", "Dir Cinco"}
		};

		administrador.Agregar( "conductor", Som );
	}
}

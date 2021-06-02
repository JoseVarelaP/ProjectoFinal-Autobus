package DAO;
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

		// Si la conexión nunca se realizó, entoncess no hay necesidad de continuar.
		if( conexion.getConnection() == null )
			return;

		ResultSet result;
		String appaterno,nombre,apmaterno;
		
		System.out.println("\n-- buscando --\n");
		DAO administrador = new DAO( conexion.getConnection() );

		System.out.println("\n-- Mostrando lista. --\n");
		String[] cons = { "nombre", "edad" };
		administrador.ProcesarConsulta( "SELECT * FROM conductor", cons );

		System.out.println("\n-- Eliminando una entrada de lista. --\n");
		administrador.Eliminar( "conductor", "num_conductor = 5" );

		System.out.println("\n-- Agregando una entrada de lista. --\n");
		// (5,31,row('Gonzalo','Torres','Romero',null), '2010-04-12', 'Dir Cinco'),

		// Esta tabla son elementos para agregar una entrada.
		String Som[][] = {
			{"num_conductor", "5"},
			{"edad", "31"},
			
			{"__ROW","true"}, // BANDERA, Vea DAO.ConvertirDatos para detalles.
			// Esta tabla representa un valor multi proveniente de un type.
			{"STR_prim_nombre","Gonzalo"},	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_segu_nombre","Torres"},	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_ap_paterno","Romero"},	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_ap_materno","null"},		// BANDERA, Vea DAO.ConvertirDatos para detalles.
			
			{"__ROW","false"}, // BANDERA, Vea DAO.ConvertirDatos para detalles.

			{"STR_fecha_contrat", "2010-04-12"}, 	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_direccion", "Dir Cinco"}			// BANDERA, Vea DAO.ConvertirDatos para detalles.
		};

		administrador.Agregar( "conductor", Som );

		System.out.println("\n-- Modificando una entrada de lista. --\n");
		administrador.Modificar( "conductor" , "edad = 10", "num_conductor = 5");

		System.out.println("\n-- Mostrando nueva lista. --\n");
		administrador.ProcesarConsulta( "SELECT * FROM conductor", cons );
	}
}

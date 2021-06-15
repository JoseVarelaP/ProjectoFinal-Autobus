package PaquetesWeb;

import java.io.IOException;
import java.io.PrintWriter;

import DAO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// @WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class AgregarEmpleado extends HttpServlet{
	//Peticion GET
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rp) throws IOException{
		System.out.println("aun tengo que mandar cosas..");
		PrintWriter out = rp.getWriter();

		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Conductor con = new Conductor( conexion.getConnection() );

		/*
		<label for='PNombre'>Primer Nombre:</label>
		<input type="text" name='PNombre' id='PNombre'></input><br>
		<label for='SNombre'>Segundo Nombre:</label>
		<input type="text" name='SNombre' id='SNombre'></input><br>
		<label for='AplP'>Apellido Parterno:</label>
		<input type="text" name='AplP' id='AplP'></input><br>
		<label for='AplM'>Apellido Materno:</label>
		<input type="text" name='AplM' id='AplM'></input><br>
		<label for='Edad'>Edad:</label>
		<input type="text" name='Edad' id='Edad'></input><br>
		<label for='Fecha'>Fecha de Contratación:</label>
		<input type="text" name='Fecha' id='Fecha'></input><br>
		<label for='Dir'>Dirección:</label>
		<input type="text" name='Dir' id='Dir'></input><br>
		*/

		// Hay que convertir la fecha ya que SQL date es diferente.
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateObj = LocalDate.parse( rq.getParameter("Fecha"), DTF );
		
		// Esta tabla son elementos para agregar una entrada.
		String Som[][] = {
			{"num_conductor", "DEFAULT"},
			{"edad", rq.getParameter("Edad")},
			
			{"__ROW","true"}, // BANDERA, Vea DAO.ConvertirDatos para detalles.
			// Esta tabla representa un valor multi proveniente de un type.
			{"STR_prim_nombre", rq.getParameter("PNombre") },	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_segu_nombre", rq.getParameter("SNombre") },	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_ap_paterno", rq.getParameter("AplP") },	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_ap_materno",rq.getParameter("AplM") },		// BANDERA, Vea DAO.ConvertirDatos para detalles.
			
			{"__ROW","false"}, // BANDERA, Vea DAO.ConvertirDatos para detalles.

			{"STR_fecha_contrat", dateObj.toString()}, 	// BANDERA, Vea DAO.ConvertirDatos para detalles.
			{"STR_direccion", rq.getParameter("Dir")}			// BANDERA, Vea DAO.ConvertirDatos para detalles.
		};

		administrador.Agregar( "conductor", Som );
		rp.sendRedirect("index.jsp");
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
   }
}
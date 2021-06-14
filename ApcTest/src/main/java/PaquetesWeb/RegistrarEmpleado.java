package PaquetesWeb;

import java.io.IOException;
import java.io.PrintWriter;

import DAO.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// @WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class RegistrarEmpleado extends HttpServlet{
	//Peticion GET
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rp) throws IOException{
		System.out.println("aun tengo que mandar cosas..");
		PrintWriter out = rp.getWriter();

		String idABuscar = rq.getParameter("CID");

		if( idABuscar.isBlank() )
			return; // Cancela, no hagas nada.

		int val = Integer.parseInt(idABuscar);

		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		int res = administrador.ConteoConsulta( "conductor" );
		Conductor con = new Conductor( conexion.getConnection() );
		con.ObtenerInfo(val);

		// Ok, ya tenemos la información, hora de mostrarla.

		out.print( "<form action='EditarEmpleado' method='POST'>" );
		out.print( "<label for='PNombre'>Primer Nombre:</label>" );
		out.print( "<input type='text' name='PNombre' id='PNombre' value=" + con.PrimerNombre() + "></input><br>" );
		out.print( "<label for='SNombre'>Segundo Nombre:</label>" );
		out.print( "<input type='text' name='SNombre' id='SNombre' value=" + con.SegundoNombre() + "></input><br>" );
		out.print( "<label for='AplP'>Apellido Parterno:</label>" );
		out.print( "<input type='text' name='AplP' id='AplP' value=" + con.ApellidoPaterno() + "></input><br>" );
		out.print( "<label for='AplM'>Apellido Materno:</label>" );
		out.print( "<input type='text' name='AplM' id='AplM' value=" + con.ApellidoMaterno() + "></input><br>" );
		out.print( "<label for='Edad'>Edad:</label>" );
		out.print( "<input type='text' name='Edad' id='Edad' value=" + con.Edad() + "></input><br>" );
		out.print( "<label for='Dir'>Dirección:</label>" );
		out.print( "<input type='text' name='Dir' id='Dir' value=" + con.Direccion() + "></input><br>" );
		out.print( "</form>" );
		out.print( "<input type='submit' value='Submit'>" );
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		rp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = rp.getWriter();
		out.print("Datos que obtuve. <br>");
		
		out.print( String.format(
			"%s<br>%s<br>%s<br>%s<br>%s<br>",
			rq.getParameter("PNombre"),
			rq.getParameter("SNombre"),
			rq.getParameter("AplP"),
			rq.getParameter("AplM"),
			rq.getParameter("Dir")
		) );
	
		out.print("Terminé. <br>");
   }
}
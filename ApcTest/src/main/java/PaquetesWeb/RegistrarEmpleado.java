package PaquetesWeb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import DAO.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

		if( val <= 0 )
		{
			out.print( "<h2>La ID proporcionada no es válida.</h2>" );
			out.print( "<a href='index.jsp'>Regresar</a>" );
			return;
		}

		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Conductor con = new Conductor( conexion.getConnection() );
		con.ObtenerInfo(val);

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.ObtenerID() != 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );
			out.print( "<form action='RegistrarEmpleado' method='POST'>" );
			out.print( "<label for='ConID'>ID de Conductor:</label>" );
			out.print( "<input type='text' name='ConID' id='ConID' value=" + con.ObtenerID() + " readonly='readonly'></input><br>" );
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
			out.print( "<label for='Fecha'>Fecha de Contratación:</label>" );
			out.print( "<input type='date' name='Fecha' id='Fecha' value=" + con.FechaContrato() + "></input><br>" );
			out.print( "<label for='Dir'>Dirección:</label>" );
			out.print( "<input type='text' name='Dir' id='Dir' value=" + con.Direccion() + "></input><br>" );
			out.print( "<input type='submit' value='Submit'>" );
			out.print( "</form>" );
		} else {
			out.print( "<h2>No hay un conductor registrado con este ID.</h2>" );
			out.print( "<a href='index.jsp'>Regresar</a>" );
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("Datos que obtuve. <br>");

		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		int res = administrador.ConteoConsulta( "conductor" );
		Conductor con = new Conductor( conexion.getConnection() );
		con.ObtenerInfo( Integer.parseInt(request.getParameter("ConID")) );

		Nombre n = new Nombre();

		n.PrimerNombre = request.getParameter("PNombre");
		n.SegundoNombre = request.getParameter("SNombre");
		n.Ap_Materno = request.getParameter("AplM");
		n.Ap_Paterno = request.getParameter("AplP");

		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		LocalDate dateObj = LocalDate.parse( request.getParameter("Fecha"), DTF );
		con.CambiarFecha( dateObj );
		
		con.CambiarDireccion( request.getParameter("Dir") );
		con.CambiarNombre( n );
		
		out.print( String.format(
			"%s<br>%s<br>%s<br>",
			con.ObtenerID(),
			con.ObtenerNombreCompleto(),
			con.Direccion()
		) );

		con.ActualizarInformacion();
	
		out.print("Terminé. <br>");
   }
}
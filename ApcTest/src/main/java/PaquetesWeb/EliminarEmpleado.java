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
public class EliminarEmpleado extends HttpServlet{
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

			out.print( "<h2>Desea eliminar la entrada "+ val +"("+ con.ObtenerNombreCompleto() +")?</h2>" );
			out.print( "<form action='EliminarEmpleado' method='POST'>" );
			//out.print( "<input type='text' name='ConID' id='ConID' value=" + con.ObtenerID() + " readonly='readonly'></input><br>" );
			out.print( "<input type='text' name='ConID' id='ConID' value="+ val +" readonly='readonly'>" );
			out.print( "<input type='submit' value='Eliminar Entrada'>" );
			out.print( "</form>" );
		} else {
			out.print( "<h2>No hay un conductor registrado con este ID.</h2>" );
			out.print( "<a href='index.jsp'>Regresar</a>" );
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Conductor con = new Conductor( conexion.getConnection() );

		String idABuscar = request.getParameter("ConID");

		if( idABuscar.isBlank() )
			return; // Cancela, no hagas nada.
		
		int val = Integer.parseInt(request.getParameter("ConID"));
		try{
			administrador.Eliminar( "conductor", "num_conductor = " + val );
			response.sendRedirect("index.jsp");
		} catch(Exception e)
		{
			System.out.println( "No se logró eliminar la entrada " + val + "." );
			System.out.println( e );
			return;
		}
   }
}
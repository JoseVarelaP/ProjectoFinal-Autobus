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
public class AdminPuntoParada extends HttpServlet{

	void Agregar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		PrintWriter out = rp.getWriter();

		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		PuntoParada ab = new PuntoParada( conexion.getConnection() );

		ab.NombreParada( rq.getParameter("nombreparada") );
		ab.RegistrarInformacion();

		conexion.close();
		rp.sendRedirect("index.jsp");
	}

	void Eliminar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Autobus con = new Autobus( conexion.getConnection() );

		int val = Integer.parseInt(rq.getParameter("ConID"));

		if( val == 0 )
			return;
		try{
			administrador.Eliminar( "punto_parada", "ind_parada = " + val );
			conexion.close();
			rp.sendRedirect("index.jsp");
		} catch(Exception e)
		{
			System.out.println( "No se logró eliminar la entrada " + val + "." );
			System.out.println( e );
			return;
		}
	}

	void Editar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException {
		rp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = rp.getWriter();

		Conexion conexion = new Conexion( "joseluis" );
		PuntoParada con = new PuntoParada( conexion.getConnection() );

		con.NombreParada( rq.getParameter("nombreparada") );
		con.RegistrarInformacion();
	
		conexion.close();
		rp.sendRedirect("index.jsp");
	}

	void ConfirmarEdicion(HttpServletRequest rq, HttpServletResponse rp) throws IOException{
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
		PuntoParada con = new PuntoParada( conexion.getConnection() );
		con.ObtenerInfo(val);
		conexion.close();

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.Ind_Parada() != 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );
			out.print( "<form action='AdminPuntoParada' method='POST'>" );
			out.print( "<input type='hidden' name='MD' id='MD' value=2 readonly='readonly'></input><br>" );
			out.print( "<label for='ConID'>ID:</label>" );
			out.print( "<input type='text' name='ConID' id='ConID' value=" + con.Ind_Parada() + " readonly='readonly'></input><br>" );
			out.print( "<label for='nombreparada'>Nombre de Parada:</label>" );
			out.print( "<input type='text' name='nombreparada' id='nombreparada' value=" + con.NombreParada() + "></input><br>" );
			out.print( "<input type='submit' value='Submit'>" );
			out.print( "</form>" );
		} else {
			out.print( "<h2>No hay una parada registrado con este ID.</h2>" );
			out.print( "<a href='index.jsp'>Regresar</a>" );
		}
	}

	void ConfirmarEliminacion(HttpServletRequest rq, HttpServletResponse rp) throws IOException {
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
		PuntoParada con = new PuntoParada( conexion.getConnection() );
		con.ObtenerInfo(val);
		conexion.close();

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.Ind_Parada() > 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );

			out.print( "<h2>Desea eliminar la entrada "+ val +" ("+ con.NombreParada() +")?</h2>" );
			out.print( "<form action='AdminPuntoParada' method='POST'>" );
				out.print( "<input type='hidden' name='MD' id='MD' value=1 readonly='readonly'></input><br>" );
				out.print( "<input type='text' name='ConID' id='ConID' value="+ val +" readonly='readonly'>" );
				out.print( "<input type='submit' value='Eliminar Entrada'>" );
			out.print( "</form>" );
		} else {
			out.print( "<h2>No hay una parada registrada con este ID.</h2>" );
			out.print( "<a href='index.jsp'>Regresar</a>" );
		}
	}

	//Peticion GET
	// Se recibe esto cuando se carga la pagina sin datos.
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rp) throws IOException {
		switch( TipoManipulacion.values()[ Integer.parseInt(rq.getParameter("MD")) ] ){
			// La adición de datos no tiene una confirmación, asi que podemos dejarlo
			// como está.
			case EMP_ELIMINAR:
				ConfirmarEliminacion(rq, rp);
				break;
			case EMP_EDITAR:
				ConfirmarEdicion(rq, rp);
				break;

			default:
				break;
		}
	}
	
	// Se recibe esto cuando se manda la información.
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		switch( TipoManipulacion.values()[ Integer.parseInt(request.getParameter("MD")) ] ){
			case EMP_AGREGAR:
				Agregar(request, response);
				break;
			case EMP_ELIMINAR:
				Eliminar(request, response);
				break;
			case EMP_EDITAR:
				Editar(request, response);
				break;

			default:
				break;
		}
   }
}
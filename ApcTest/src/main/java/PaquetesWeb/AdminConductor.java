package PaquetesWeb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import DAO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

enum TipoManipulacion{
	EMP_AGREGAR,
	EMP_ELIMINAR,
	EMP_EDITAR
}

// @WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class AdminConductor extends HttpServlet{

	void Agregar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		PrintWriter out = rp.getWriter();

		Conexion conexion = new Conexion( "joseluis" );
		Conductor con = new Conductor( conexion.getConnection() );
		
		// Hay que convertir la fecha ya que SQL date es diferente.
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateObj = LocalDate.parse( rq.getParameter("Fecha"), DTF );
		con.CambiarFecha(dateObj);

		Nombre n = new Nombre();

		n.PrimerNombre = rq.getParameter("PNombre");
		n.SegundoNombre = rq.getParameter("SNombre");
		n.Ap_Paterno = rq.getParameter("AplP");
		n.Ap_Materno = rq.getParameter("AplM");

		con.CambiarNombre( n );
		con.CambiarEdad( Integer.parseInt(rq.getParameter("Edad")) );
		con.CambiarDireccion( rq.getParameter("Dir") );

		con.RegistrarInformacion();

		conexion.close();
		rp.sendRedirect("index.jsp");
	}

	void Eliminar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Conductor con = new Conductor( conexion.getConnection() );

		String idABuscar = rq.getParameter("ConID");

		if( idABuscar.isBlank() )
			return; // Cancela, no hagas nada.
		
		int val = Integer.parseInt(rq.getParameter("ConID"));
		try{
			administrador.Eliminar( "conductor", "num_conductor = " + val );
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
		Conductor con = new Conductor( conexion.getConnection() );
		con.ObtenerInfo( Integer.parseInt(rq.getParameter("ConID")) );

		Nombre n = new Nombre();

		n.PrimerNombre = rq.getParameter("PNombre");
		n.SegundoNombre = rq.getParameter("SNombre");
		n.Ap_Materno = rq.getParameter("AplM");
		n.Ap_Paterno = rq.getParameter("AplP");
		
		con.CambiarEdad( Integer.parseInt(rq.getParameter("Edad")) );

		// Hay que convertir la fecha ya que SQL date es diferente.
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		LocalDate dateObj = LocalDate.parse( rq.getParameter("Fecha"), DTF );
		con.CambiarFecha( dateObj );
		
		con.CambiarDireccion( rq.getParameter("Dir") );
		con.CambiarNombre( n );
		con.ActualizarInformacion();
	
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
		Conductor con = new Conductor( conexion.getConnection() );
		con.ObtenerInfo(val);
		conexion.close();

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.ObtenerID() != 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );
			out.print( "<form action='AdminConductor' method='POST'>" );
			out.print( "<input type='hidden' name='MD' id='MD' value=2 readonly='readonly'></input><br>" );
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
		Conductor con = new Conductor( conexion.getConnection() );
		con.ObtenerInfo(val);
		conexion.close();

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.ObtenerID() != 0 )
		{
			// Antes de esto, checa si el conductor fue asignada a un viaje. Sería
			// un problema permitir eliminarlo.
			ResultSet result = administrador.ConsultaPublica(
				String.format("SELECT num_conductor, conductor_fk FROM conductor as A INNER JOIN viajes as B ON A.num_conductor = B.conductor_fk where num_conductor = %d;", val)
			);
			
			int resultados = 0;

			try {
				while( result.next() ){
					resultados++;
				}
			} catch (Exception e) {
				// No hagas nada aquí, no hay problema.
			}
			
			
			if( resultados > 0 ){
				out.print( "<h2>Existe un viaje con este conductor.</h2>" );
				out.print( "<p>Por favor, elimine el viaje antes de continuar.</p>" );
				out.print( "<a href='index.jsp'>Regresar</a>" );
				return;
			}

			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );

			out.print( "<h2>Desea eliminar la entrada "+ val +"("+ con.ObtenerNombreCompleto() +")?</h2>" );
			out.print( "<form action='AdminConductor' method='POST'>" );
			out.print( "<input type='hidden' name='MD' id='MD' value=1 readonly='readonly'></input><br>" );
			//out.print( "<input type='text' name='ConID' id='ConID' value=" + con.ObtenerID() + " readonly='readonly'></input><br>" );
			out.print( "<input type='hidden' name='ConID' id='ConID' value="+ val +" readonly='readonly'>" );
			out.print( "<input type='submit' value='Eliminar Entrada'>" );
			out.print( "</form>" );
		} else {
			out.print( "<h2>No hay un conductor registrado con este ID.</h2>" );
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
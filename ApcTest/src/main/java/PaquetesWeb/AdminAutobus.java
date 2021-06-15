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

enum TipoManipulacion{
	EMP_AGREGAR,
	EMP_ELIMINAR,
	EMP_EDITAR
}

// @WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class AdminEmpleado extends HttpServlet{

	void Agregar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		PrintWriter out = rp.getWriter();

		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Autobus ab = new Autobus( conexion.getConnection() );

		public void CambiarSerie( int d ) { this.NumSerie = d; }
		public void CambiarFabr( String d ) { this.Fabricante = d; }
		public void CambiarFecha( LocalDate d ) { this.Fabricado = d; }
		public void CambiarCapacidad( int d ) { this.Capacidad = d; }

		ab.CambiarFabr( rq.getParameter("fabr") );
		
		// Hay que convertir la fecha ya que SQL date es diferente.
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateObj = LocalDate.parse( rq.getParameter("Fecha"), DTF );
		ab.CambiarFecha( dateObj );

		ab.CambiarCapacidad( Integer.parseInt( rq.getParameter("cap") ) );

		ab.RegistrarInformacion();

		conexion.close();
		rp.sendRedirect("index.jsp");
	}

	void Eliminar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Autobus con = new Autobus( conexion.getConnection() );

		String idABuscar = rq.getParameter("serID");

		if( idABuscar.isBlank() )
			return; // Cancela, no hagas nada.
		
		int val = Integer.parseInt(rq.getParameter("serID"));
		try{
			administrador.Eliminar( "autobus", "num_serie = " + val );
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
		Autobus con = new Autobus( conexion.getConnection() );

		public void CambiarSerie( int d ) { this.NumSerie = d; }
		public void CambiarFabr( String d ) { this.Fabricante = d; }
		public void CambiarFecha( LocalDate d ) { this.Fabricado = d; }
		public void CambiarCapacidad( int d ) { this.Capacidad = d; }

		con.CambiarFabr( rq.getParameter("fabr") );
		
		// Hay que convertir la fecha ya que SQL date es diferente.
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateObj = LocalDate.parse( rq.getParameter("Fecha"), DTF );
		con.CambiarFecha( dateObj );

		con.CambiarCapacidad( Integer.parseInt( rq.getParameter("cap") ) );

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
		Conductor con = new Conductor( conexion.getConnection() );
		con.ObtenerInfo(val);
		conexion.close();

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.ObtenerID() != 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );
			out.print( "<form action='AdminEmpleado' method='POST'>" );
			out.print( "<input type='hidden' name='MD' id='MD' value=2 readonly='readonly'></input><br>" );
			out.print( "<label for='ConID'>Numero de Serie:</label>" );
			out.print( "<input type='text' name='ConID' id='ConID' value=" + con.ObtenerID() + " readonly='readonly'></input><br>" );
			out.print( "<label for='fabricante'>Fabricante:</label>" );
			out.print( "<input type='text' name='fabricante' id='fabricante' value=" + con.PrimerNombre() + "></input><br>" );
			out.print( "<label for='fabricado'>Fabricado:</label>" );
			out.print( "<input type='date' name='fabricado' id='fabricado' value=" + con.SegundoNombre() + "></input><br>" );
			out.print( "<label for='capacidad'>Capacidad:</label>" );
			out.print( "<input type='text' name='capacidad' id='capacidad' value=" + con.SegundoNombre() + "></input><br>" );
			out.print( "<input type='submit' value='Submit'>" );
			out.print( "</form>" );
		} else {
			out.print( "<h2>No hay un Autobus registrado con este ID.</h2>" );
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
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );

			out.print( "<h2>Desea eliminar la entrada "+ val +"("+ con.ObtenerNombreCompleto() +")?</h2>" );
			out.print( "<form action='AdminEmpleado' method='POST'>" );
			//out.print( "<input type='text' name='ConID' id='ConID' value=" + con.ObtenerID() + " readonly='readonly'></input><br>" );
			out.print( "<input type='text' name='ConID' id='ConID' value="+ val +" readonly='readonly'>" );
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
			case EMP_EDITAR:
				ConfirmarEdicion(rq, rp);
				break;
			case EMP_ELIMINAR:
				ConfirmarEliminacion(rq, rp);
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
			case EMP_EDITAR:
				Editar(request, response);
				break;
			case EMP_ELIMINAR:
				Eliminar(request, response);
				break;

			default:
				break;
		}
   }
}
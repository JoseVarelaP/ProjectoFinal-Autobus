package PaquetesWeb;

import java.io.IOException;
import java.io.PrintWriter;

import DAO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// @WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class AdminViajes extends HttpServlet{

	Viajes ConvertirInformacion( HttpServletRequest rq )
	{
		Conexion conexion = new Conexion( "joseluis" );
		Viajes ab = new Viajes( conexion.getConnection() );

		// Hay que convertir la fecha ya que SQL date es diferente.
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		// Ya que no hay soporte de tiempo y hora al mismo tiempo como una manera de entrada valida,
		// hay que combinarlos en un metodo de tiempo que DateTimeFormatter puede utilizar.
		String Partida1Combinada = rq.getParameter("HoraPartidaDia") + " " + rq.getParameter("HoraPartidaHora");
		String Llegada1Combinada = rq.getParameter("HoraLlegadaDia") + " " + rq.getParameter("HoraLlegadaHora");

		// Modifica el numero de viaje si el ID es proveido.
		if( rq.getParameter("ConID") != null )
			ab.NumViaje( Integer.parseInt(rq.getParameter("ConID")) );
		
		LocalDateTime datePartida = LocalDateTime.parse( Partida1Combinada, DTF );
		LocalDateTime dateLlegada = LocalDateTime.parse( Llegada1Combinada, DTF );

		ab.HoraPartida( datePartida );
		ab.HoraLlegada( dateLlegada );

		Conductor c = new Conductor( conexion.getConnection() );
		c.ObtenerInfo( Integer.parseInt( rq.getParameter("conductorFK") ) );

		ab.ConductorAsignado( c );

		Rutas ruta = new Rutas( conexion.getConnection() );
		ruta.ObtenerInfo( Integer.parseInt( rq.getParameter("rutaUsarFK") ) );
		ab.RutaAUsar( ruta );

		return ab;
	}

	void Agregar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		Viajes ab = this.ConvertirInformacion( rq );
		ab.RegistrarInformacion();
		try{
		    ab.CerrarConexion();
		} catch( SQLException e )
		{
		    System.out.println( e );
		}

		rp.sendRedirect("index.jsp");
	}

	void Eliminar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
                System.out.println("Deleting");
		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Autobus con = new Autobus( conexion.getConnection() );

		int val = Integer.parseInt(rq.getParameter("ConID"));

		if( val == 0 )
			return;
		try{
			administrador.Eliminar( "viajes", "num_viaje = " + val );
			con.CerrarConexion();
			rp.sendRedirect("index.jsp");
		} catch(Exception e)
		{
			System.out.println( "No se logró eliminar la entrada " + val + "." );
			System.out.println( e );
		}
	}

	void Editar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException {
		rp.setContentType("text/html; charset=UTF-8");

		Viajes ab = this.ConvertirInformacion( rq );
		ab.ActualizarInformacion();
		try{
		    ab.CerrarConexion();
		} catch( SQLException e )
		{
		    System.out.println( e );
		}

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
		Viajes con = new Viajes( conexion.getConnection() );
		con.ObtenerInfo(val);

		ArrayList<Rutas> Rut = new ArrayList<>();
		ArrayList<Conductor> Con = new ArrayList<>();

		administrador.Identificadores( "num_conductor", "conductor" ).forEach(ids -> {
			Conductor c = new Conductor( conexion.getConnection() );
			if (c.ObtenerInfo( ids )) {
				Con.add( c );
			}
	    });

		administrador.Identificadores( "num_ruta", "rutas" ).forEach(ids -> {
			Rutas c = new Rutas( conexion.getConnection() );
			if (c.ObtenerInfo( ids )) {
				Rut.add( c );
			}
	    });

		conexion.close();

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.NumViaje() != 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );
			out.print( "<form action='AdminViajes' method='POST'>" );
			out.print( "<input type='hidden' name='MD' id='MD' value=2 readonly='readonly'></input><br>" );
			out.print( "<label for='ConID'>ID:</label>" );
			out.print( "<input type='text' name='ConID' id='ConID' value=" + con.NumViaje() + " readonly='readonly'></input><br>" );

			out.print( "<label for='HoraPartidaDia'>Fecha de Partida:</label>" );
			out.print( "<input type='date' name='HoraPartidaDia' id='HoraPartidaDia' value='" + con.HoraPartida().toLocalDate().toString() + "'></input>" );
			out.print( "<input type='time' name='HoraPartidaHora' id='HoraPartidaHora' value='" + con.HoraPartida().toLocalTime().toString() + "'></input><br>" );

			out.print( "<label for='HoraLlegadaDia'>Fecha de Llegada:</label>" );
			out.print( "<input type='date' name='HoraLlegadaDia' id='HoraLlegadaDia' value='" + con.HoraLlegada().toLocalDate().toString() + "'></input>" );
			out.print( "<input type='time' name='HoraLlegadaHora' id='HoraLlegadaHora' value='" + con.HoraLlegada().toLocalTime().toString() + "'></input><br>" );

			// Seccion para seleccion conductor
			out.print( "<label for='conductorFK'>Conductor:</label>" );
			out.print( "<select type='text' name='conductorFK' id='conductorFK' value=" + con.ConductorAsignado().ObtenerID() + ">" );
			Con.forEach(r -> {
			    out.print( String.format( "<option value='%s'>%s</option>", r.ObtenerID() , r.ObtenerNombreCompleto() ) );
			});
			out.print( "</select><br>" );
			
			// Seccion para seleccion ruta
			out.print( "<label for='rutaUsarFK'>Ruta a usar:</label>" );
			out.print( "<select type='text' name='rutaUsarFK' id='rutaUsarFK' value=" + con.RutaAUsar().NumRuta() + ">" );
			Rut.forEach(r -> {
			    out.print( String.format( "<option value='%s'>%s</option>", r.NumRuta() , r.Descripcion() ) );
			});
			out.print( "</select><br>" );
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
		Viajes con = new Viajes( conexion.getConnection() );
		con.ObtenerInfo(val);
		try{
		    con.CerrarConexion();
		} catch( SQLException e )
		{
		    System.out.println( e );
		}

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.NumViaje() > 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );

			out.print( "<h2>Desea eliminar la entrada "+ val +" ("+ con.RutaAUsar().Descripcion() +")?</h2>" );
			out.print( "<form action='AdminViajes' method='POST'>" );
				out.print( "<input type='hidden' name='MD' id='MD' value=1></input><br>" );
				out.print( "<input type='hidden' name='ConID' id='ConID' value="+ val +" readonly='readonly'>" );
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
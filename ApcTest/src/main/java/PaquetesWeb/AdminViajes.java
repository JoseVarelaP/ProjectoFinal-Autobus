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
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Ya que no hay soporte de tiempo y hora al mismo tiempo como una manera de entrada valida,
		// hay que combinarlos en un metodo de tiempo que DateTimeFormatter puede utilizar.
		String Partida1Combinada = rq.getParameter("HoraPartidaDia") + " " + rq.getParameter("HoraPartidaHora");
		String Llegada1Combinada = rq.getParameter("HoraLlegadaDia") + " " + rq.getParameter("HoraLlegadaHora");

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
		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Autobus con = new Autobus( conexion.getConnection() );

		int val = Integer.parseInt(rq.getParameter("ConID"));

		if( val == 0 )
			return;
		try{
			administrador.Eliminar( "punto_parada", "ind_parada = " + val );
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
		Rutas con = new Rutas( conexion.getConnection() );
		con.ObtenerInfo(val);

		ArrayList<PuntoParada> Rut = new ArrayList<>();

		administrador.Identificadores( "ind_parada", "punto_parada" ).forEach(ids -> {
			PuntoParada c = new PuntoParada( conexion.getConnection() );
			if (c.ObtenerInfo( ids )) {
				Rut.add( c );
			}
	    });

		conexion.close();

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.NumRuta() != 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );
			out.print( "<form action='AdminRutas' method='POST'>" );
			out.print( "<input type='hidden' name='MD' id='MD' value=2 readonly='readonly'></input><br>" );
			out.print( "<label for='ConID'>ID:</label>" );
			out.print( "<input type='text' name='ConID' id='ConID' value=" + con.NumRuta() + " readonly='readonly'></input><br>" );
			out.print( "<label for='DestInicio'>Punto de Inicio:</label>" );
			out.print( "<select type='text' name='DestInicio' id='DestInicio'>" );
			Rut.forEach(r -> {
			    out.print( String.format( "<option value='%s'>%s</option>", r.Ind_Parada() , r.NombreParada() ) );
			});
			out.print( "</select><br>" );
			out.print( "<label for='DestFinal'>Punto de Llegada:</label>" );
			out.print( "<select type='text' name='DestFinal' id='DestFinal'>" );
			Rut.forEach(r -> {
			    out.print( String.format( "<option value='%s'>%s</option>", r.Ind_Parada() , r.NombreParada() ) );
			});
			out.print( "</select><br>" );
			out.print( "<label for='Desc'>Descripción:</label>" );
			out.print( "<input type='text' name='Desc' id='Desc' value=" + con.Descripcion() + "></input><br>" );
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
		Rutas con = new Rutas( conexion.getConnection() );
		con.ObtenerInfo(val);
		try{
		    con.CerrarConexion();
		} catch( SQLException e )
		{
		    System.out.println( e );
		}

		// Ok, ya tenemos la información, hora de mostrarla.
		if( con.NumRuta() > 0 )
		{
			out.print( "<a href='index.jsp'>Regresar</a><br><br>" );

			out.print( "<h2>Desea eliminar la entrada "+ val +" ("+ con.Descripcion() +")?</h2>" );
			out.print( "<form action='AdminRutas' method='POST'>" );
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
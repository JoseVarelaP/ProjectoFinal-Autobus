package PaquetesWeb;

import java.io.IOException;
import java.io.PrintWriter;

import DAO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;

// @WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class AdminRutas extends HttpServlet{

	void Agregar( HttpServletRequest rq, HttpServletResponse rp ) throws IOException{
		PrintWriter out = rp.getWriter();

		Conexion conexion = new Conexion( "joseluis" );
		DAO administrador = new DAO( conexion.getConnection() );
		Rutas ab = new Rutas( conexion.getConnection() );
		PuntoParada n1 = new PuntoParada( conexion.getConnection() );
		PuntoParada n2 = new PuntoParada( conexion.getConnection() );

		n1.ObtenerInfo( Integer.parseInt( rq.getParameter("DestInicio") ) );
		n2.ObtenerInfo( Integer.parseInt( rq.getParameter("DestFinal") ) );

		ab.CambiarDestInicio( n1 );
		ab.CambiarDestFinal( n2 );
		ab.CambiarDescripcion( rq.getParameter("Desc") );

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
		Rutas con = new Rutas( conexion.getConnection() );
		PuntoParada n1 = new PuntoParada( conexion.getConnection() );
		PuntoParada n2 = new PuntoParada( conexion.getConnection() );

		n1.ObtenerInfo( Integer.parseInt( rq.getParameter("DestInicio") ) );
		n2.ObtenerInfo( Integer.parseInt( rq.getParameter("DestFinal") ) );

		con.CambiarNumRuta( Integer.parseInt( rq.getParameter("ConID") ) );
		con.CambiarDescripcion( rq.getParameter("Desc") );
		con.CambiarDestInicio( n1 );
		con.CambiarDestFinal( n2 );
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
		DAO administrador = new DAO( conexion.getConnection() );
		Rutas con = new Rutas( conexion.getConnection() );
		con.ObtenerInfo(val);

		ArrayList<PuntoParada> Rut = new ArrayList<>();

		for( int ids : administrador.Identificadores( "ind_parada", "punto_parada" ) )
		{
			PuntoParada c = new PuntoParada( conexion.getConnection() );
			if( c.ObtenerInfo( ids ) )
				Rut.add( c );
		}

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
				for( PuntoParada r : Rut )
					out.print( String.format( "<option value='%s'>%s</option>", r.Ind_Parada() , r.NombreParada() ) );
			out.print( "</select><br>" );
			out.print( "<label for='DestFinal'>Punto de Llegada:</label>" );
			out.print( "<select type='text' name='DestFinal' id='DestFinal'>" );
				for( PuntoParada r : Rut )
					out.print( String.format( "<option value='%s'>%s</option>", r.Ind_Parada() , r.NombreParada() ) );
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
		DAO administrador = new DAO( conexion.getConnection() );
		Rutas con = new Rutas( conexion.getConnection() );
		con.ObtenerInfo(val);
		conexion.close();

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
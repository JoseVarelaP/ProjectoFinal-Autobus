<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="DAO.*, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
	<link href="../css/estilo.css" rel="stylesheet"/>
	<head>
		<meta charset="utf-8">
		<title>Servicio de Autobuses</title>
	</head>
	<body>
		<div class="header">
			<img src="../img/autobus.png">
			<p>Creación - Viajes</p>
		</div>
		<ul>
			<%--Haz el listado de las opciones para agregar o manipular.--%>
			<div>
				<h2 class="Titulo">Listado de conductores</h2>
			</div>
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );
				ArrayList<Conductor> Con = new ArrayList<>();
				ArrayList<Rutas> Rut = new ArrayList<>();

				for( int ids : administrador.Identificadores( "num_conductor", "conductor" ) )
				{
					Conductor c = new Conductor( conexion.getConnection() );
					if( c.ObtenerInfo( ids ) )
						Con.add( c );
				}

				for( int ids : administrador.Identificadores( "num_ruta", "rutas" ) )
				{
					Rutas c = new Rutas( conexion.getConnection() );
					if( c.ObtenerInfo( ids ) )
						Rut.add( c );
				}
			%>
			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<form action="AdminViajes" method="POST">
				<input type="hidden" name='MD' id='MD' value=0></input><br>
				<label for='HoraPartidaDia'>Fecha de Partida:</label>
				<input type="date" name='HoraPartidaDia' id='HoraPartidaDia'></input>
				<input type="time" name='HoraPartidaHora' id='HoraPartidaHora'></input><br>

				<label for='HoraLlegadaDia'>Fecha de Llegada:</label>
				<input type="date" name='HoraLlegadaDia' id='HoraLlegadaDia'></input>
				<input type="time" name='HoraLlegadaHora' id='HoraLlegadaHora'></input><br>

				<label for='conductorFK'>Conductor:</label>
				<select name='conductorFK' id='conductorFK'>
				<%
					for( Conductor r : Con )
					{
						out.print( String.format( "<option value='%s'>%s</option>", r.ObtenerID() , r.ObtenerNombreCompleto() ) );
					}
				%>
				</select>
				<br>
				<label for='rutaUsarFK'>Ruta a usar:</label>
				<select name='rutaUsarFK' id='rutaUsarFK'>
				<%
					for( Rutas r : Rut )
					{
						out.print( String.format( "<option value='%s'>%s</option>", r.NumRuta() , r.Descripcion() ) );
					}
				%>
				</select>
				<br>
				<input type="submit" value="Submit">
			</form>
		</ul>
	</body>
</html>
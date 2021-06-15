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
			<p>Creación - Conductor</p>
		</div>
		<ul>
			<%--Haz el listado de las opciones para agregar o manipular.--%>
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );
				ArrayList<PuntoParada> Rut = new ArrayList<>();

				for( int ids : administrador.Identificadores( "ind_parada", "punto_parada" ) )
				{
					PuntoParada c = new PuntoParada( conexion.getConnection() );
					if( c.ObtenerInfo( ids ) )
						Rut.add( c );
				}
			%>
			<div>
				<h2 class="Titulo">Listado de conductores</h2>
			</div>
			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<form action="AdminRutas" method="POST">
				<input type="hidden" name='MD' id='MD' value=0></input><br>
				<label for='DestInicio'>Punto de Inicio:</label>
				<select name='DestInicio' id='DestInicio'>
					<%
						for( PuntoParada r : Rut )
							out.print( String.format( "<option value='%s'>%s</option>", r.Ind_Parada() , r.NombreParada() + r.Ind_Parada() ) );
					%>
				</select><br>
				<label for='DestFinal'>Punto de Llegada:</label>
				<select name='DestFinal' id='DestFinal'>
					<%
						for( PuntoParada r : Rut )
							out.print( String.format( "<option value='%s'>%s</option>", r.Ind_Parada() , r.NombreParada() + r.Ind_Parada() ) );
					%>
				</select><br>
				<label for='Desc'>Descripción:</label>
				<input type="text" name='Desc' id='Desc'></input><br>

				<input type="submit" value="Submit">
			</form>
		</ul>
	</body>
</html>
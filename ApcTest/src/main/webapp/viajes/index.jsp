<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.ArrayList" %>
<%@ page import="DAO.*, java.sql.*" %>

<!DOCTYPE html>
<html>
	<link href="../css/estilo.css" rel="stylesheet"/>
	<head>
		<meta charset="utf-8">
		<title>Servicio de Autobuses</title>
	</head>
	<% ArrayList<Viajes> vj = new ArrayList<>(); %>
	<body>
		<script src="../js/funciones.js"></script>
		<ul>
			<%--Haz el listado de las opciones para agregar o manipular.--%>
			<div>
				<h2 class="Titulo">Listado de conductores</h2>
			</div>
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );

				administrador.Identificadores( "num_viaje", "viajes" ).forEach( ids -> {
					Viajes c = new Viajes( conexion.getConnection() );
					if( c.ObtenerInfo( ids ) )
						vj.add( c );
				});
			%>

			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<center>
				<a class="button" href="./crear.jsp?MD=0">Crear Entrada</a><br>
				<table>
					<tr>
						<th>Hora Partida</th>
						<th>Hora Llegada</th>
						<th>Ruta</th>
						<th>Conductor</th>
						<th>Editar</th>
						<th>Eliminar</th>
					</tr>
					<% for( Viajes v : vj ) { %>
					<tr>
						<th><% out.print( v.HoraPartida().toString() ); %></th>
						<th><% out.print( v.HoraLlegada().toString() ); %></th>
						<th><% out.print( v.RutaAUsar().Descripcion() ); %></th>
						<th><% out.print( v.ConductorAsignado().ObtenerNombreCompleto() ); %></th>
						<th>
							<% int num = v.NumViaje(); %>
							<% String ubicacion = String.format("./editar.jsp?CID=%s&MD=2", num); %>
							<a class="button" href= <%= ubicacion %> >Editar</a>
						</th>
						<th>
							<% ubicacion = String.format("./eliminar.jsp?CID=%s&MD=1", num); %>
							<a class="button" href= <%= ubicacion %> >Eliminar</a>
						</th>
					</tr>
					<% } %>
				</table>
			</center>
		</ul>
	</body><script src="../js/funciones.js"></script>
</html>
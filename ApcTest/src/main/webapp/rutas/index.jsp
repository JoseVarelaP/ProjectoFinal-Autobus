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
	<% ArrayList<Rutas> Paradas = new ArrayList<>(); %>
	<body>
		<script src="../js/funciones.js"></script>
		<ul>
			<%--Haz el listado de las opciones para agregar o manipular.--%>
			<div>
				<h2 class="Titulo">Listado de Rutas</h2>
			</div>
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );
				
				/*
					Probablemente tengamos algun conductor por eliminar,
					asi que buscaremos por medio de la URL de la pagina para realizar
					la acción.
				*/
				for( int ids : administrador.Identificadores( "num_ruta", "rutas" ) )
				{
					Rutas c = new Rutas( conexion.getConnection() );
					if( c.ObtenerInfo( ids ) )
						Paradas.add( c );
				}
			%>

			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<center>
				<a href="./crear.jsp?MD=0">Crear Entrada</a>
				<table>
					<tr>
						<th>Inicio</th>
						<th>Final</th>
						<th>Descripción</th>
						<th>Editar</th>
						<th>Eliminar</th>
					</tr>
					<% for( Rutas c : Paradas ) { %>
					<tr>
						<th><% out.print( c.DestInicio().NombreParada() ); %></th>
						<th><% out.print( c.DestFinal().NombreParada() ); %></th>
						<th><% out.print( c.Descripcion() ); %></th>
						<th>
							<% int num = c.NumRuta(); %>
							<% String ubicacion = String.format("./editar.jsp?CID=%s&MD=2", num); %>
							<a href= <%= ubicacion %> >Editar</a>
						</th>
						<th>
							<% ubicacion = String.format("./eliminar.jsp?CID=%s&MD=1", num); %>
							<a href= <%= ubicacion %> >Eliminar</a>
						</th>
					</tr>
					<% } %>
				</table>
			</center>
		</ul>
	</body>
</html>
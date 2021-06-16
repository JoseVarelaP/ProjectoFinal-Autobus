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
	<% ArrayList<Autobus> Autobuses = new ArrayList<>(); %>
	<body>
		<script src="../js/funciones.js"></script>
		<ul>
			<%--Haz el listado de las opciones para agregar o manipular.--%>
			<div>
				<h2 class="Titulo">Listado de Autobuses</h2>
			</div>
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );
				
				/*
					Probablemente tengamos algun conductor por eliminar,
					asi que buscaremos por medio de la URL de la pagina para realizar
					la acción.
				*/
				for( int ids : administrador.Identificadores( "num_serie", "autobus" ) )
				{
					Autobus c = new Autobus( conexion.getConnection() );
					if( c.ObtenerInfo( ids ) )
						Autobuses.add( c );
				}
			%>

			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<center>
				<a href="./crear.jsp?MD=0">Crear Entrada</a><br>
				<table>
					<tr>
						<th>Numero de Serie</th>
						<th>Fabricante</th>
						<th>Fabricado</th>
						<th>Capacidad</th>
						<th>Editar</th>
						<th>Eliminar</th>
					</tr>
					<% for( Autobus c : Autobuses ) { %>
					<tr>
						<th><% out.print( c.Serie() ); %></th>
						<th><% out.print( c.Fabr() ); %></th>
						<th><% out.print( c.Fecha() ); %></th>
						<th><% out.print( c.Capacidad() ); %></th>
						<th>
							<% int num = c.Serie(); %>
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
	</body><script src="../js/funciones.js"></script>
</html>
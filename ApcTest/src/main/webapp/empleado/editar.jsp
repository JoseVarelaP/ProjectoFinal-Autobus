<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.*" %>
<%@ page import="javax.servlet.*, java.text.*" %>
<%@ page import="DAO.*" %>

<!--DOCTYPE html-->
<!DOCTYPE html>
<html>
	<link href="../css/estilo.css" rel="stylesheet"/>
	<head>
		<meta charset="utf-8">
		<title>Servicio de Autobuses</title>
	</head>
	<%--<script src="./js/empleado.js"></script>--%>
	<body>
		<div class="header">
			<img src="../img/autobus.png">
			<p>Servicio de Autobuses</p>
		</div>
		<ul>
			<%--Haz el listado de las opciones para agregar o manipular.--%>
			<div>
				<h2 class="Titulo">Listado de conductores</h2>
			</div>
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );
				
				int res = administrador.ConteoConsulta( "conductor" );
				ArrayList<Conductor> conductores = new ArrayList<>();
				
				for( int i = 1; i <= res; i++ )
				{
					Conductor c = new Conductor( conexion.getConnection() );
					if( c.ObtenerInfo( i ) )
						conductores.add( c );
				}
			%>

			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<table>
				<tr>
					<th>Nombre de Conductor</th>
					<th>Fecha Contratado</th>
					<th>Dirección</th>
					<th>Editar</th>
					<th>Eliminar</th>
				</tr>
				<% for( Conductor c : conductores ) { %>
				<tr>
					<th><% out.print( c.ObtenerNombreCompleto() ); %></th>
					<th><% out.print( c.FechaContrato() ); %></th>
					<th><% out.print( c.Direccion() ); %></th>
					<th>
						<% String ubicacion = String.format("./editar.jsp?CID=%s", c.ObtenerID()); %>
						<a href= <%= ubicacion %> >Editar</a>
					</th>
				</tr>
				<% } %>
			</table>
		</ul>
	</body>
</html>
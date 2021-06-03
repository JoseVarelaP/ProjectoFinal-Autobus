<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.*" %>
<%@ page import="javax.servlet.*, java.text.*" %>
<%@ page import="DAO.*" %>

<!--DOCTYPE html-->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Servicio de Autobuses</title>
	</head>
	<body>
		<h1>Servicio de Autobuses</h1>
		<ul>
			<!-- Haz el listado de las opcioness para agregar o manipular. -->
			<%
				Conexion conexion = new Conexion( "joseluis" );								
				DAO administrador = new DAO( conexion.getConnection() );
				System.out.println("\n-- Mostrando lista. --\n");
				String[] cons = { "nombre", "edad" };
				
				out.println( "<h2>Listado de resultados:</h2>" );
				ArrayList<ArrayList<String>> res = administrador.ProcesarConsulta( "SELECT * FROM conductor", cons );
				for( ArrayList<String> a : res )
				{
					for( String s : a )
						out.println( s );
					out.println( "\n<br>" );
				}
					// for( String s : a )
						// out.println( s + "<br>" );
			%>
		</ul>
	</body>
</html>
<!--
<html>
<head>
	<title>Qué es un JSP</title>
</head>

<body>
	<h2>Bienvenidos alumnos de DAW, vamos a probar un JSP!</h2>
	<h5>Estoy corriendo en un ambiente de tomcat integrado, gracias MAVEN</h5>
	<h5>JSP Java Server Pages - Jakarta Server Pages</h5>
	<h5>En un JSP podemos poner código JAVA, sin necesidad de un APPLET</h5>

	<%= new String("La fecha del día de hoy es:") %>
	<%= new java.util.Date().toLocaleString() %>

	<br>
	<%= application.getServerInfo() %>
	<br>
	<%= session.getId() %>

	<%
		
	%>

	<ul>
		<li>Ir a<a href="index.html"> Página Principal</a>.
	</ul>
	</body>
</html>
-->
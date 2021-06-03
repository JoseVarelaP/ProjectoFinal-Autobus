<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.*" %>
<%@ page import="javax.servlet.*, java.text.*" %>
<%@ page import="DAO.*" %>

<!--DOCTYPE html-->
<!DOCTYPE html>
<html>
	<link href="./css/estilo.css" rel="stylesheet"/>
	<head>
		<meta charset="utf-8">
		<title>Servicio de Autobuses</title>
	</head>
	<script src="./js/empleado.js"></script>
	<script>
		const GenerarListadoObjetos = ( Lista ) =>
		{
			const divconv = document.createElement("div");
			for( const elemento of Lista )
			{
				console.log( elemento )
			}				
			return divconv
		}
	</script>
	<body>
		<h1>Servicio de Autobuses</h1>
		<ul>
			<!-- Haz el listado de las opciones para agregar o manipular. -->
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );
				System.out.println("\n-- Mostrando lista. --\n");
				String[] cons = { "nombre", "edad" };
				
				out.println( "<h2>Listado de conductores:</h2>" );
				ArrayList<ArrayList<String>> res = administrador.ProcesarConsulta( "SELECT * FROM conductor", cons );
				
				// Procesa nombres a una lista.
				ArrayList<ArrayList<String>> nombres = administrador.ProcesarConsulta( "SELECT * FROM conductor", "nombre" );

				for( ArrayList<String> a : nombres )
				{
					%>
						<script>
							GenerarListadoObjetos( <%= administrador.ConvierteLista(a) %> );
						</script>
					<%
				}
				
				for( ArrayList<String> a : nombres )
				{
					out.println("<div class='ListadoObjetoContenedor'>");
						
						/////
						out.println("<div class='ListadoObjetoNombre'>");
						for( String s : a )
							out.println( String.format("<p>%s</p>",s) );
						out.println("</div>");
						/////

						// Añade un boton para editar la entrada de ese conductor.
						out.println("<div class='botones'>");
						out.println( String.format("<button>Editar</button>") );
						out.println( String.format("<button>Eliminar</button>") );
						out.println("</div>");
					out.println("</div>");
				}
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
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
		<div class="header">
			<img src="./img/autobus.png">
			<p>Servicio de Autobuses</p>
		</div>
		<ul>
			<!-- Haz el listado de las opciones para agregar o manipular. -->
			<div>
				<h2 class="Titulo">Listado de conductores</h2>
			</div>
			<%
				Conexion conexion = new Conexion( "joseluis" );
				DAO administrador = new DAO( conexion.getConnection() );
				// String[] cons = { "nombre", "edad" };
				
				int res = administrador.ConteoConsulta( "conductor" );
				ArrayList<Conductor> conductores = new ArrayList<>();
				
				for( int i = 1; i <= res; i++ )
				{
					Conductor c = new Conductor( conexion.getConnection(), i );
					if( c.ObtenerInfo() )
					{
						out.println( c.ObtenerNombreCompleto() + "\t" );
						out.println( c.PrimerNombre() );
						out.println( c.SegundoNombre() );
						out.println( c.ApellidoPaterno() );
						out.println( c.ApellidoMaterno() );
						out.println( c.FechaContrato() );
						out.println( c.Direccion() + "<br>" );
						conductores.add( c );
					}
				}
				
				// Procesa nombres a una lista.
				// out.println( administrador.ConteoConsulta( "conductor", null ) );
				/*
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
				*/
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
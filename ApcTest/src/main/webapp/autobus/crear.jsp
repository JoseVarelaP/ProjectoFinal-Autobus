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
		<script src="../js/funciones.js"></script>
		<ul>
			<%--Haz el listado de las opciones para agregar o manipular.--%>
			<div>
				<h2 class="Titulo">Listado de Autobuses</h2>
			</div>
			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<form action="AdminAutobus" method="POST">
				<input type="hidden" name='MD' id='MD' value=0></input><br>
				<label for='num_serie'>Numero de Serie:</label>
				<input type="text" name='num_serie' id='num_serie'></input><br>
				<label for='fabricante'>Fabricante:</label>
				<input type="text" name='fabricante' id='fabricante'></input><br>
				<label for='fabricado'>Fabricado en:</label>
				<input type="date" name='fabricado' id='fabricado'></input><br>
				<label for='capacidad'>Capacidad:</label>
				<input type="number" name='capacidad' id='capacidad'></input><br>

				<label for='ruta'>Ruta a operar:</label>
				<select name='ruta' id='ruta'>
				<%
					Conexion conexion = new Conexion( "joseluis" );
					DAO administrador = new DAO( conexion.getConnection() );
					ArrayList<Rutas> Rut = new ArrayList<>();
					for( int ids : administrador.Identificadores( "num_ruta", "rutas" ) )
					{
						Rutas c = new Rutas( conexion.getConnection() );
						if( c.ObtenerInfo( ids ) )
							Rut.add(c);
					}

					for( Rutas c : Rut )
					{
						out.print( String.format( "<option value='%s'>%s</option>", c.NumRuta(), c.Descripcion() ) );
					}
				%>
				</select>

				<input type="submit" value="Submit">
			</form>
		</ul>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.*" %>
<%@ page import="java.text.*" %>

<!--DOCTYPE html-->
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
			<div>
				<h2 class="Titulo">Listado de conductores</h2>
			</div>
			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<form action="RegistrarEmpleado" method="POST">
				<label for='PNombre'>Primer Nombre:</label>
				<input type="text" name='PNombre' id='PNombre'></input><br>
				<label for='SNombre'>Segundo Nombre:</label>
				<input type="text" name='SNombre' id='SNombre'></input><br>
				<label for='AplP'>Apellido Parterno:</label>
				<input type="text" name='AplP' id='AplP'></input><br>
				<label for='AplM'>Apellido Materno:</label>
				<input type="text" name='AplM' id='AplM'></input><br>
				<label for='Dir'>Dirección:</label>
				<input type="text" name='Dir' id='Dir'></input><br>

				<input type="submit" value="Submit">
			</form>
		</ul>
	</body>
</html>
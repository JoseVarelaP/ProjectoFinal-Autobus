<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
				<input type="text" name='capacidad' id='capacidad'></input><br>

				<input type="submit" value="Submit">
			</form>
		</ul>
	</body>
</html>
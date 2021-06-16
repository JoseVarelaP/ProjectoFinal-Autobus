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
			<form action="AdminPuntoParada" method="POST">
				<input type="hidden" name='MD' id='MD' value=0></input><br>
				<label for='nombreparada'>Nombre de Parada:</label>
				<input type="text" name='nombreparada' id='nombreparada'></input><br>

				<input type="submit" value="Submit">
			</form>
		</ul>
	</body>
</html>
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
				<h2 class="Titulo">Listado de Viajes</h2>
			</div>
			<%--Hora de agregar los nombres y colocarlos en sus grupos.--%>
			<jsp:include page="AdminViajes" />
		</ul>
	</body><script src="../js/funciones.js"></script>
</html>
// Genera la lista del nombre para la persona.
const ListarCabezera = () =>
{
	const contenedorTitulo = document.createElement("div");
	contenedorTitulo.className = "header";

	const logotipo = document.createElement("img");
	logotipo.src = "../img/autobus.png";

	contenedorTitulo.appendChild(logotipo);

	const vertical = document.createElement("div");
	vertical.style.width = "400px";
	vertical.style.marginBottom = "10px";

	const Titulo = document.createElement("p");
	Titulo.textContent = "Servicio de Autobuses";

	vertical.appendChild(Titulo);
	
	const divconv = document.createElement("div");
	divconv.className = "listadoPaginas";

	vertical.appendChild(divconv);

	const Paginas = {
		"Conductores" : "/conductor/index.jsp",
		"Autobuses" : "/autobus/index.jsp",
		"Paradas" : "/puntoparada/index.jsp",
		"Rutas" : "/rutas/index.jsp",
		"Viajes" : "/viajes/index.jsp",
	}

	for( const elemento in Paginas )
	{
		const l = document.createElement("a");
		l.href = Paginas[elemento];
		l.textContent = elemento + " ";
		divconv.appendChild(l);
	}

	contenedorTitulo.appendChild(vertical);
	document.body.appendChild(contenedorTitulo);
}

ListarCabezera();
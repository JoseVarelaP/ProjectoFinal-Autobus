alert("yes")

// Genera la lista del nombre para la persona.
const GenerarListadoObjetos = ( Lista ) =>
{
	const divconv = document.createElement("div");

	/*
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
	*/
	
	for( const elemento of Lista )
	{
		console.log( elemento )
	}
	
	return divconv
}
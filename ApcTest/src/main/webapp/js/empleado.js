alert("yes")

const GenerarListadoObjetos = ( Lista ) =>
{
	const divconv = document.createElement("div");

	for( const elemento of Lista )
	{
		console.log( elemento )
	}
	
	return divconv
}
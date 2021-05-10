create type nombre as(
	prim_nombre		varchar(10) not null,
	segu_nombre		varchar(10) null,
	ap_paterno		varchar(10) not null,
	ap_materno		varchar(10) null,
)

create table conductor(
	num_conductor 	int not null,
	edad			int not null,
	nombre			nombre	not null,
	fecha_contrat	date not null,
	direccion		varchar(30) not null,
	constraint pk_conductor primary key (num_conductor),
	unique(num_conductor)
);

create table autobus(
	num_serie		int	not null,
	fabricante		varchar(10) null,
	fabricado		date not null,
	capacidad		int not null,
	constraint pk_autobus primary key (num_serie),
	unique(num_serie)
);

-- Separaremos la entrada de puntos a que sean enblocados.
create table punto_parada(
	ind_parada		int not null auto increment,
	nombre_parada	varchar(40),
	constraint pk_punto_parada primary key (ind_parada)
)

create table rutas(
	num_ruta		int	not null,
	-- Los valores de destino y salida seran de una
	-- tabla que contiene los puntos de parada para los autobuses.

	-- Esto le permitira al administrador a actualizar las rutas
	-- facilmente con solo un UPDATE.
	dest_inicio		int not null,
	dest_final		int not null,

	descripcion		text null,
	constraint pk_rutas primary key (num_ruta),

	-- Aplica las llaves foraneas para agarrar esa tabla.
	foreign key (dest_inicio) references punto_parada(ind_parada),
	foreign key (dest_final) references punto_parada(ind_parada)
)

create table viajes(
	num_viaje		int not null,
	hor_partida		date not null,
	hor_llegada		date not null,
	-- Utilizara el valor de rutas para marcar el lugar de
	-- partida y llegada.
	ruta_a_usar_fk	int	not null,
	conductor_fk	int not null,
	constraint (pk_viajes) primary key(num_viaje),
	foreign key (ruta_a_usar_fk) references rutas(num_ruta),
	foreign key (conductor_fk) references conductor(num_conductor)
)
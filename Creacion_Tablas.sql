-- Dado como postgres funciona, no es posible seleccionar la base de datos directamente por un
-- query, asi que la eliminación debe ser realizada manualmente cuando se quiera reiniciar los datos.s
-- DROP DATABASE IF EXISTS joseluis;
-- CREATE DATABASE joseluis;

create type nombre as(
	prim_nombre		VARCHAR(10),
	segu_nombre		VARCHAR(10),
	ap_paterno		VARCHAR(10),
	ap_materno		VARCHAR(10)
);

create table conductor(
	num_conductor 	serial primary key,
	edad			int not null,
	nombre			nombre	not null,
	fecha_contrat	date not null,
	direccion		varchar(30) not null,
	--constraint pk_conductor primary key (num_conductor),
	unique(num_conductor)
);

-- Separaremos la entrada de puntos a que sean enblocados.
create table punto_parada(
	ind_parada		serial,
	nombre_parada	varchar(40),
	constraint pk_punto_parada primary key (ind_parada)
);

create table rutas(
	num_ruta		serial,
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
);

create table autobus(
	num_serie		int	not null,
	fabricante		varchar(10) null,
	fabricado		date not null,
	capacidad		int not null,
	ruta_fk			int	not null,
	constraint pk_autobus primary key (num_serie),
	foreign key (ruta_fk) references rutas(num_ruta),
	unique(num_serie)
);

create table viajes(
	num_viaje		serial,
	hor_partida		timestamp not null,
	hor_llegada		timestamp not null,
	-- Utilizara el valor de rutas para marcar el lugar de
	-- partida y llegada.
	ruta_a_usar_fk	int	not null,
	conductor_fk	int not null,
	constraint pk_viajes primary key(num_viaje),
	foreign key (ruta_a_usar_fk) references rutas(num_ruta),
	foreign key (conductor_fk) references conductor(num_conductor)
);
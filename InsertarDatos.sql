-- Nota: estos datos (nombres y edades) fueron autogenerados.
insert into conductor values
(DEFAULT,20,row('Marcos',null,'Martinez',null), '2010-04-12', 'Dir Uno'),
(DEFAULT,32,row('Benigno','Pastor','Vega',null), '2010-04-12', 'Dir Dos'),
(DEFAULT,27,row('Ernesto','Hernández','Díaz',null), '2010-04-12', 'Dir Tres'),
(DEFAULT,27,row('Úrsula','Pastor','Gil',null), '2010-04-12', 'Dir Cuatro'),
(DEFAULT,31,row('Gonzalo','Torres','Romero',null), '2010-04-12', 'Dir Cinco'),
(DEFAULT,38,row('Arcadio','Lorenzo','Serrano',null), '2010-04-12', 'Dir Seis'),
(DEFAULT,40,row('Ildefonso','Morales','Marín',null), '2010-04-12', 'Dir Siete'),
(DEFAULT,28,row('Leonardo','Aguilar','Blesa',null), '2010-04-12', 'Dir Ocho'),
(DEFAULT,26,row('Honorio','Nieto','Vidal',null), '2010-04-12', 'Dir Nueve'),
(DEFAULT,29,row('Carolina','González','Esteban',null), '2010-04-12', 'Dira Uno'),
(DEFAULT,25,row('Genoveva','Méndez','Soto',null), '2010-04-12', 'Dira Dos'),
(DEFAULT,29,row('Gustavo','Gutiérrez','Muñoz',null), '2010-04-12', 'Dira Tres'),
(DEFAULT,30,row('Leopoldo','Díaz','Reyes',null), '2010-04-12', 'Dira Cuatro');

insert into punto_parada values
(DEFAULT,'Chapultepec'),
(DEFAULT,'Alvarado'),
(DEFAULT,'Angel R. Cabada'),
(DEFAULT,'Zona Norte'),
(DEFAULT,'Monte de Perote'),
(DEFAULT,'Tierra Blanca'),
(DEFAULT,'Azuta'),
(DEFAULT,'Washington'),
(DEFAULT,'Las Americas'),
(DEFAULT,'Boca del Rio');

insert into rutas values
(DEFAULT,1,4,'Chapultepec a Zona norte'),
(DEFAULT,2,6,'Alvarado a Tierra Blanca'),
(DEFAULT,9,4,'Americas a Zona Norte'),
(DEFAULT,1,2,'Chapultepec a Alvarado'),
(DEFAULT,7,3,'Tierra Blanca a Angel R. Cabada'),
(DEFAULT,8,5,'Washington a Monte de Perote'),
(DEFAULT,7,8,'Azuta a Washington'),
(DEFAULT,3,2,'Angel R. Cabada a Alvarado'),
(DEFAULT,6,4,'Tierra Blanca a Zona Norte'),
(DEFAULT,1,4,'Chapultepec a Zona Norte');

insert into autobus values
(298391, 'Kia', '2004-03-21', 40, 2),
(152353, 'Grumman', '1983-02-13', 60, 1),
(985981, 'Wrightbus', '2009-06-01', 90, 5),
(873691, 'Hyundai', '1995-05-20', 40, 6),
(583187, 'Isuzu', '1999-07-10', 30, 3),
(098743, 'TransBus', '2003-08-14', 50, 7),
(051037, 'Volvo', '1987-09-30', 80, 4);
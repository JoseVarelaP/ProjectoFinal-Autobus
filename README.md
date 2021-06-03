# ProjectoFinal-Web

Repositorio para el projecto final (y las actividades relacionadas) en la materia
de Diseño de Aplicaciones Web.

## Aviso

Comenzando con la integración del sitio web, el programa debe ser compilado por NetBeans.
Si se necesita correr el programa por medio de la linea de comando, se puede realizar con los pasos siguientes, pero tome en cuenta que serán de una versión anterior del programa.

Lista de elementos por ahora:
- [X] Archivos SQL para creacion y manejo de datos
- [X] Archivos iniciales de Java para realizar consultas.

Compilacion con javac se puede realizar con `compilar.sh`.
```
./compilar.sh
```

Para correr, ejecuta Java con la bandera `classpath` para agregar la libreria de postgres.
```bash
java -classpath .:postgresql-42.2.20.jar MainBDj
```
# ProjectoFinal-Web

Repositorio para el projecto final (y las actividades relacionadas) en la materia
de Diseño de Aplicaciones Web.

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
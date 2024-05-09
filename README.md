
-  # EoloPark - webapp06

<h4 align="center">
:construction: Proyecto en construcción :construction:
</h4>

![Badge en Desarollo](https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green)



## Descripción

<em> # EoloPark </em>

  -  El proyecto se basa en la creación e implementación de distintas funcionalidades que una web sobre gestión de parques eólicos puede hacer, como la creacion de paques de forma manual y automatica, basandose en datos guardados en una base de datos. Tambien se verá como se ha implementa de la seguridad de Spring boot 3 y el manejo de usuarios, tanto administradores como usuarios normales. Además de la implementacion de una APIRest.

## Funcionalidades

:hammer:Funcionalidades del proyecto

- `Funcionalidad Parque`: Toda la web se centra en la gestion de dichos parques eólicos. Consta de diferentes funcionalidades relacionadas a los parques:
  - `Crear Parque`: Se podrán crear diferentes parques de forma Manual o Automatica. Para hacerlo manualmente, se pedirá que se especifiquen todos los datos esenciales que necesita un parque, como su nombre, ciudad, latitud, longitud y area. Para la creación Automática solo se pedirá la ciudad y el area que ocupará y un algortimo creará de forma automatica los datos del parque, la subestacion necesaria y sus aerogeneradores repartidos por él.
  - `Editar Parque`: Se podrá acceder a un parque existente para poder editar sus caracteristicas.
  - `Borrar Parque`: Tambien se podrán borrar dichos parques ya almacenados en la Base de datos.
  - `Añadir Aerogenerador`: A estos parques se les podrá añadir aerogeneradores, especificando cada una de las caracteristicas que necesita.
  - `Añadir Subestación`: Además se podrá añadir una subestación a cada parque, si no dispone de una.
  - `Visualzar Parques`: En la página principal se podrán visualizar todos los parques de la base de datos en una lista que mostrará datos clave como nombre y ciudad.
- `Funcionalidad Usuarios`: Podrá realizar cada una de las funcionalidades mencionadas pero solo con sus propios parques, no con los parques de otros usuarios y a cada usuario se le distinguirá por su nombre de usuario.
- `Funcionalidad Administrador`: Ademas de poder realizar las funciones anteriores, también podrá:
  - `Visualizar Usuarios`: Tendrá disponible una lista con todos los usuarios registrados en la web. Además de los parques que haya creado cada uno.
  - `Dar/Quitar Premium a los usuarios`: Tendrá disponible una lista con todos los usuarios registrados en la web. Además de los parques que haya creado cada uno.
- `Funcionalidad No se si falta algo lo ponen`: 

## Guía de ejecución en Windows

<h4 align="center">
📁 Acceso al proyecto
</h4>

- `1º Paso`: Una vez en el proyecto dentro de github se podrá descargar el .zip del proyecto, para ello:
  - En la pestaña ![Badge en Desarollo](https://img.shields.io/badge/<>CODE-black?color=green) aparecerá una opción "Download ZIP", desde la que se podrá descargar un zip comprimido con el proyecto completo.
- `2º Paso`: Descomprimir el .zip en una carpeta vacia.
  
<h4 align="center">
🛠️ Abre y ejecuta el proyecto
</h4>

- `3º Paso`: Iniciar VSCode o IntelliJ y abrir la carpeta del proyecto descomprimido.
- `4º Paso`: Desde la clase "EoloParkApplication.java" ejecutar mediante "Run" o click derecho sobre la clase y darle a la opción "Run Java".
- `5º Paso`: Desde el navegador en la barra de busqueda superior introducir "https://localhost:8443/"
- `6º Paso`: Aparecerá la página del login y desde aqui ya se podrá navegar en la página web. 

<h4 align="center">
🛠️ Requisitos
</h4>

- `Version de SpringBoot`: 3.2.2
- `Version de Java`: 21
- `Dependencias de JPA`: spring-boot-starter-data-jpa
- `Dependencias de MySQL`: mysql-connector-j
  
<h4 align="center">
🛠️ Referente a problemas de instalación
</h4>

  - Si aparece algún problema al descargar el proyecto, existe una forma alternativa de acceder a el.
    - **Descripción rápida**:
      - Conectarse al repositorio de GitHub mediante la etensión "GitHu Repositories" y desde una terminal de VSCode escribir el comando "git pull .".
        De esta forma se podrá bajar el proyecto del repositorio remoto. El resto de pasos una vez hecho el "pull" se puede seguir desde el paso 4.
  
## Tecnologias utilizadas

<h4 align="center">
🛠️ Tecnologías utilizadas
</h4>

tecnologías utilizadas en este proyecto:

- `Java`: El lenguaje utilizado
- `Spring Boot 3`: Herramienta de desarrollo
- `VSCode/IntelliJ`: El framework web utilizado
- `DBeaber`: Base de datos

## Diagrama de Navegación
<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/145440108/9c3aa094-534a-4f7b-ae1d-03416fecc5ae"  width="2000">

## Diagrama de entidades de la Base de Datos

<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/a8ee18df-1e1e-47e5-ac0b-dd634015f1a6" width="400">
<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/4f319d85-f563-4fe3-bd8b-6b892c297249"  width="500">

## Diagrama de clases y Templates

<h3>Server</h3>

<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/8266dbed-7451-482f-8559-ff185f31dff4" >

<h3>Planner</h3>

<h3>WindService</h3>

<h3>GeoService</h3>

## Participación de los miembros


  - Laura:`HTLMs` `Creación Manual de parques con Eric` `Editar Parque/Subestacion/Aerogenerador` `Crear Subestación/Aerogenerador` `Buscar por ciudad` `Ayudar con BD`
      - Commits Importantes:
       1. Commit de Editar Parque: clases de EoloParkController.java, SecurityConfiguration.java.
       2. Commit del Aerogenerador: clases de AerogeneratorController.java, AerogeneratorRepository.java, Aerogenerator.java, AerogeneratorSerice.java
       3. Commit de Busqueda de ciudad: clases de EoloParkController.java, EoloParkRepository.java.
  - Sandra:`ApiRest` `Paginación` `Seguridad`
     - Commits Importantes:
       1. Commit de Paginación: clases de EoloParkController.java, EoloParkService.java.
       2. Commit de ApiRest: clases de ApiController.java, SecurityConfiguration.java.
       3. Commit de Security: clases de SecurityConfiguration.java.
  - Vicente:`Usuario/Admin` `HTML de error y Success` `Relación de entidades de la BD` 
  - Eric: `Creación inicial de la base de datos con entidades y relaciones` `Despiegue de Docker` `Certificado SSL autofirmado` `Algoritmo de creacion automática de parques` `Ayudar con algunos HTMLs` `Creación de ReadMe` 
    - Commits Importantes:
       1. Commit del EoloPark: clases de EoloParkController.java, EoloParkRepository.java, EoloPark.java, EoloParkSerice.java,  SecurityConfiguration.java,
       2. Commit del Certificado SSL
       3. Commit de la creacion automática del parque: clases de Cities.java, CitiesRepository.java, CitiesService,java, etc,  SecurityConfiguration.java.



-  # EoloPark - webapp06

<h2 align="center">
:construction: Proyecto en construcción :construction:
</h2>

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

<h2 align="center">
📁 Acceso al proyecto
</h2>

- `1º Paso`: Una vez en el proyecto dentro de github se podrá descargar el .zip del proyecto, para ello:
  - En la pestaña ![Badge en Desarollo](https://img.shields.io/badge/<>CODE-black?color=green) aparecerá una opción "Download ZIP", desde la que se podrá descargar un zip comprimido con el proyecto completo.
- `2º Paso`: Descomprimir el .zip en una carpeta vacia.
  
<h2 align="center">
🛠️ Abre y ejecuta el proyecto
</h2>

- `3º Paso`: Iniciar VSCode o IntelliJ y abrir la carpeta del proyecto descomprimido.
- `4º Paso`: Desde la clase "EoloParkApplication.java" ejecutar mediante "Run" o click derecho sobre la clase y darle a la opción "Run Java".
- `5º Paso`: Desde el navegador en la barra de busqueda superior introducir "https://localhost:8443/"
- `6º Paso`: Aparecerá la página del login y desde aqui ya se podrá navegar en la página web. 

<h2 align="center">
🛠️ Requisitos
</h2>

- `Version de SpringBoot`: 3.2.2
- `Version de Java`: 21
- `Dependencias de JPA`: spring-boot-starter-data-jpa
- `Dependencias de MySQL`: mysql-connector-j
  
<h2 align="center">
🛠️ Referente a problemas de instalación
</h2>

  - Si aparece algún problema al descargar el proyecto, existe una forma alternativa de acceder a el.
    - **Descripción rápida**:
      - Conectarse al repositorio de GitHub mediante la etensión "GitHu Repositories" y desde una terminal de VSCode escribir el comando "git pull .".
        De esta forma se podrá bajar el proyecto del repositorio remoto. El resto de pasos una vez hecho el "pull" se puede seguir desde el paso 4.
        
  <h2 align="center">
🛠️ Guía de construccion y despligue de contenedores
</h2>

<h3 align="center">
SECCIÓN DE INSTRUCCIONES 📄
</h3>
  
Despliegues y empaquetados |  
Creación de imágenes para los distintos servicios | 
Server: Técnica utilizada => JIB Plugin
### Pasos:

1) Agregamos el JIB al archivo pom.xml
2) Configurar lo necesario para crear la imagen en configuración
3) Ejecutar el comando: mvn compile jib:build
   
  Planner: Tecnica utilizada--> Multistage Dockerfile
1) crear un docker file con multiples etapas
2) En la primera etapa, construir el jar
3) En la segunda etapa, copiar dicho jar en una imagen base 
4) construir la imagen con el siguiente comando: docker build -t planner-image .
WindService: Tecnica utilizada--> Multistage Dockerfile
Igual que el planner
Georservice: Tecnica utilizada--> Spring Buildpacks
1)Nos aseguramos de que la aplicacion este configurada como una aplicacion Spring Boot
2)Ejecutar el comando Spring Boot para crear la imagen de Buildpacks: ./mvnw spring-boot:build-image


Una vez creadas las imagenes se ejecutura el script--> create_docker-images.bat
comando para ejecutar dicho script: Nos aseguraremos de proporcionar la ruta completa donde se encuentra el script y luego ejecutaremos ---> .\create_docker-images.bat

<h3>DESPLIEGUE DE LA APLICACION</h3>  

# Despligue docker compose

URL: https://localhost:443 

Una vez que las imágenes están subidas al docker hub realizaremos los dockerfile del Planner y del Winservice

## Dockerfile Planner

Primera parte--> construccion del contenedor
1) Base Image: se usa Maven con JDK 17 y se etiqueta como 'builder'-->FROM maven:3.9.0-eclipse-temurin-17 as builder
2) Work Directory: Establece el directorio de trabajo en /project 
3) Copiar pom.xml: copia el pom.xml al directorio de trabajo del contenedor
4) Compilar proyecto: para compilar el proyecto sin detenerse en errores--> mvn clean compile
5) Copiar Código Fuente: copia el directorio src al directorio de trabajo del contenedor
6) Empaquetar Proyecyo : empaqueta y crea el JAR pero además, carga las dependencias necesarias y omite la pruebas--> mvn package -DskipTests

Segunda parte--> contenedor de aplicacion
1) Base Image: Usa JDK 17
2) Work Directory: Establece el directorio de trabajo en -->/usr/src/app/
3) wait-for-it Script: Descarga y da permisos de ejecucion al script wait-for-it, el cual es opcional para esperar otros servicios
4) Copiar JAR: Copia el JAR generador en el contendor de construccion al directorio de trabajo el contenedor de la aplicacion
5) Exponer Puerto: Expone el puerto 8080
6) Definir Entrypoint: Define el comando de inicio para ejecutar la aplicacion java con el JAR copiado anteriormente

## Dockerfile WindService

Primera parte--> construccion del contenedor
1) Base Image: se usa Maven con JDK 17 y se etiqueta como 'builder'-->FROM maven:3.9.0-eclipse-temurin-17 as builder
2) Work Directory: Establece el directorio de trabajo en /WindService
3) Copiar pom.xml: copia el pom.xml del servicio WindService al directorio de trabajo del contenedor
4) Compilar proyecto: Ejecuta -->mvn clean verify para limpiar y verificar el proyecyo, la opcion --fail-never hace que el comando no falle si hay errores
5) Copiar Código Fuente: copia el directorio src del servicio WindService al directorio de trabajo del contenedor
6) Empaquetar Proyecyo : empaqueta y crea el JAR pero además, carga las dependencias necesarias y omite la pruebas--> mvn package -DskipTests

Segunda parte--> contenedor de aplicacion
1) Base Image: Usa JDK 17
2) Work Directory: Establece el directorio de trabajo en -->/usr/src/app/
3) wait-for-it Script: Descarga y da permisos de ejecucion al script wait-for-it, el cual es opcional para esperar otros servicios
4) Copiar JAR: Copia el JAR generador en el contendor de construccion al directorio de trabajo el contenedor de la aplicacion
5) Exponer Puerto: Expone el puerto 8084
6) Definir Entrypoint: Define el comando de inicio para ejecutar la aplicacion java con el JAR copiado anteriormente

Una vez tenemos los dockerfile haremos el archivo dockercompose.yml, este configura y define servicios Docker, como un servidor de RabbitMQ, una base de datos MySQL
y varias aplicaciones java, para facilitar el despliegue y la gestion conjunta de los servicios, especificando sus imagnenes, variables de entorno, puertos y dependencias
Además, incluye verificaciones que nos asegura que los servicios esten funcionando antes de iniciar otros servicios que dependan de otros.

Por ultimo el comando para ejecutarlo-->  docker-compose -f docker_compose.yml up



# Desplieque con Kubernetes
URL: https://127.0.0.1

1) iniciamos MiniKube: minikube start
2) se realiza la configuracion de kubernetes: kubectl apply -f k8s/
3) una vez finalizado el despliegue hacemos un --> minikube tunnel
4) minikube service server, con esto se empezara a a levantar todos los servicios de la web, es decir Planner, Geoservice, WindService y Server
5) una vez se levantan los servicios en el navegador de la maquina ponemos la ip que da como el resultado cuando hacemos el comando--> minikube tunnel, haciendo al server y como consecuencia a la web



# Despligue con openStack
Primero debemos acceder a OpenStack con las claves disponibles y levantar la instancia webapp06, tambien 
debemos conectarnos a la VM usando -->ssh -i claveSSHOpenStack.pem ubuntu@10.100.139.6, clona el repositorio con git clone

## Despliegue 1: [Mono-nodo con docker-compose](10.100.139.6)
URL: [10.100.139.6](10.100.139.6)
1) obtenemos IP Flotante, que se encuentran dentro de las imagenes de OpenStack
2) configuramos los grupos de seguridad segun los puertos que necesite la maquina
3) Accedemos a la instancias utilizando el siguiente comando-->ssh -i <ubicacion/clave/Privada> <usuarioMaquinaVirtual>@<IpFlotanteMaquinaVirtual>
4)Una vez dentro de la instancia navegamos a la carpeta de la aplicacion: cd webapp06
5) Desplegamos la aplicacion con Docker Compose--> sudo docker-compose up

## Despliegue 2: [Multi-nodo con docker-compose](10.100.139.131)
URL: [10.100.139.131](10.100.139.131)
1) Acceder a la instancia con IP Flotante --> ssh -i <ubicacion/clave/Privada> <usuarioMaquinaVirtual>@<IpFlotanteMaquinaVirtual>
2) Acceder a otras instancias sin IP Flotante--> ssh -i <ubicacion/clave/Privada> <usuarioMaquinaVirtual>@<IpMaquinaVirtual>
3) En cada instancia navegamos a la carpeta de la aplicacion: cd webapp6
4) Ejecutamos Docker Compose en cada instancia con --> sudo docker-compose -f docker-compose-xxx.yml up

## Despliegue 3: [Kubernetes](https://localhost:443)
URL: [https://localhost:443](https://localhost:443)


-estar en la carpeta de kubeconfig, abrir powershell

1) kubectl delete service,deployment,ingress --all para limpiar si algo falla, solo si falla
2) kubectl apply -f msql-pv.yaml
3) kubectl apply -f k8s/msql-pvc.yaml
4) kubectl create -f .\k8s\msql.yaml
5) kubectl create -f .\k8s\server-Deployment.yaml
6) kubectl create -f .\k8s\geoservice-Deployment.yaml
7) kubectl create -f .\k8s\windservice-Deployment.yaml
8) kubectl create -f .\k8s\planner-Deployment.yaml
9)  kubectl create -f .\k8s\ingress.yaml
10)  kubectl create -f .\k8s\rabbitmq.yaml
Tambien se puede hacer directamente --> kubectl create -f k8s/, pero si algo falla seguimos los pasos comentadas anteriormente
11) revisamos que este todo bien con  kubectl get services y kubectl get pods
12) Por ultimo para levantar la web uso kubectl port-forward server-65d9cd874f-t5t4j 443:443

Revisar que el nombre del pod del server este bien si falla y ya en la web tirar localhost:443



### Tecnologias utilizadas

<h2 align="center">
🛠️ Tecnologías utilizadas
</h2>

tecnologías utilizadas en este proyecto:

- `Java`: El lenguaje utilizado
- `Spring Boot 3`: Herramienta de desarrollo
- `VSCode/IntelliJ`: El framework web utilizado
- `DBeaber`: Base de datos
- `Maven`
- `RabbitMQ`: Broker para el paso de mensajes entre servicios

## Diagrama de Navegación
<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/145440108/6948895f-f178-40c6-b1fc-8e0cb958a07b"  width="2000">

## Diagrama de entidades de la Base de Datos

<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/a8ee18df-1e1e-47e5-ac0b-dd634015f1a6" width="400">
<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/4f319d85-f563-4fe3-bd8b-6b892c297249"  width="500">

## Diagrama de clases y Templates

<h3>Server</h3>

<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/8266dbed-7451-482f-8559-ff185f31dff4" >

<h3>Planner</h3>

<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/dc8ecf5a-63f9-425f-bcf0-52f972d1ab6c">

<h3>WindService</h3>

<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/cf0beb52-4cd4-480f-afff-4a8026c955c1" width="600">

<h3>GeoService</h3>

<img src="https://github.com/CodeURJC-DAD-2023-24/webapp06/assets/118891317/11450467-c65c-47a4-ae26-f2d6a467b8a5">

## Participación de los miembros

  - Laura:`Comunicación por gRPC del WindService con el Planner` `Creación de los ficheros relacionados con la comunicación por gRPC en el WindService y en el Planner` 
     - Commits Importantes:
       1. 'grpc changes'
       2. 'grpc completed'
          
           Para la parte de despliegues:
          -  Creación de imagen de windservice: archivo windservice.Dockerfile.
          -  Despliegue 1:
              -  Archivo Docker_compose.yml con sus servicios.
              -  Archivo .bat para contruir las imagenes y publicarlas en la cuenta de gruipo de DockerHub.
          -  Despliegue 2:
              - Deployments necesarios para kubernetes (Con ayuda de Eric) : + geoservice-Deployment.yaml + ingress.yaml +planner-Deployment.yaml +rabbitmq.yaml +server-Deployment.yaml +windservice-Deployment.yaml
          -  Despliegue 3:
              -  Creacion de Maquinas virtuales necesarias apra cada uno de los 3 despliegues con sus respectivas caracteristicas, indicadas en el enunciado.
              -  Creación de los grupos de seguridad con los puertos necesarios para cada servicio.
              -  Levantamiento de maquinas e instalacion de herramientas necesarias en cada una de ellas, como kubernetes o clonado de repositorios.
              -  Archivo Kubeconfig
              -  Archivo claveSSHOpenStack.pem para poder conectarme a las maquinas de forma segura.
  - Sandra:`Comunicación por REST del GeoService con el Planner` `Creación de los diagramas de clases`
     - Commits Importantes:
       1. 'GeoService REST'
       2. 'Merge branch 'main' of https://github.com/CodeURJC-DAD-2023-24/webapp06'
          
        Para la parte de despliegues:
          -  Creación de imagen de Geoservice: archivo de Spring Buildpacks para geoservice.
          -  Documentacion: Readme de github con todos las caracteristicas a tener en cuenta sobre esta parte, especificando cada configuración utilizada y pasos a seguir para despliegues y demás.
  - Vicente:`Comunicación por WebSockets con el cliente desde el Server` `Implementación de javascript para mostrar al cliente en HTML el progreso de creación y el parque resultante`
     - Commits Importantes:
       1. 'WebSocket Starting_V2'
       2. 'Save New Automatic Park in DB'
       3. 'Test MessagePark data'
          
          Para la parte de despliegues:
          -  Creación de imagen de server: archivo jib de server.

          -  Despliegue 3:
              - Archivos docker-compose para el desplieue multinodo en openStack.  
              - Instalacion de herramientas necesarias para las maquinas de multinodo, como clonado de repositorios.
  - Eric: `Comunicación por RabbitMQ desde el Planner al Server` `Implementación del algoritmo automático usando RabbitMQ` `Métodos Send y Listener`
     - Commits Importantes:
       1. 'planner with rabitmq'
       2. 'RabbitMQ fixed'
       3. 'Last release'
          
          Para la parte de despliegues:  (Muchas partes estan repetidas con las de Laura ya que las hicimos a la vez juntos)
          -  Creación de imagen de planner: archivo Planner.Dockerfile.
          -  Despliegue 1:
              -  Archivo Docker_compose.yml con sus servicios.
              -  Creacion de DockerHub con credenciales para el grupo
              -  Archivo .bat para contruir las imagenes y publicarlas en la cuenta de gruipo de DockerHub.
          -  Despliegue 2:
              - Deployments necesarios para kubernetes (Con ayuda de Laura) : + geoservice-Deployment.yaml + ingress.yaml +planner-Deployment.yaml +rabbitmq.yaml +server-Deployment.yaml +windservice-Deployment.yaml
          -  Despliegue 3:
              -  Creacion de Maquinas virtuales necesarias apra cada uno de los 3 despliegues con sus respectivas caracteristicas, indicadas en el enunciado.
              -  Creación de los grupos de seguridad con los puertos necesarios para cada servicio.
              -  Levantamiento de maquinas e instalacion de herramientas necesarias en cada una de ellas, como kubernetes o clonado de repositorios.
              -  Archivo Kubeconfig
              -  Archivo claveSSHOpenStack.pem para poder conectarme a las maquinas de forma segura.
                
          
        

Además, se ha realizado la documentación y se han corregido errores de la práctica entre todos los miembros del equipo.

# Proyecto Implementando Clean Architecture

## Descripción del Proyecto

Este proyecto implementa una API REST utilizando Spring Boot y Arquitectura Limpia. Su objetivo
principal es exponer endpoints para gestionar y almacenar datos de manera desacoplada y escalable,
siguiendo buenas prácticas de organización de código.

## Estructura del Proyecto

El proyecto está organizado en las siguientes capas:
### domain: 
Contiene las entidades y modelos
principales de negocio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio. Esta capa no puede depender de ninguna otra capa. 
#### usecases: 
Este modulo contiene la lógica de negocio y los casos de uso de la aplicación,  y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.
### infrastructure: 
Implementa la lógica de acceso a datos, adaptadores y controladores REST. 

#### drivenadapters:
Contiene las implementaciones de los adaptadores que interactúan con fuentes de datos externas, como bases de datos, servicios Rest u otros servicios externos. En este caso, la persistencia se realiza sobre un archivo Json en la raiz del proyecto.

#### entrypoints:
Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio, contiene los controladores REST que exponen los endpoints de la API y manejan las solicitudes entrantes.

###  application:
Configuración general y punto de inicio de la aplicación. Es el punto mas externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. adeams contine el main de la aplicación.
**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**

## Flujo de Datos

El flujo de datos sigue los principios de la Arquitectura Limpia: 1. El cliente realiza una petición al
controlador REST (capa infrastructure). 2. El controlador invoca el caso de uso correspondiente (capa
usecases). 3. El caso de uso interactúa con el dominio y los adaptadores para procesar los datos. 4. El
repositorio o adaptador almacena o recupera la información del archivo JSON. 5. La respuesta se
envía de vuelta al cliente.

## Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)
(Imagen tomada de https://bancolombia.github.io/scaffold-clean-architecture/docs/intro)

## Diagrama de Secuencia

![Blank diagram.png](applications/app-service/src/main/resources/Blank%20diagram.png)

## Guía de Uso
Ser requiere tener instalado Java 21 y Gradle en el sistema.

### Crear un producto - Metodo POST /product

Request body (JSON):
```json
{
  "productName": "Laptop",
  "imageUrl": "https://ejemplo.com/image.jpg",
  "description": "Laptop ultradelgada ",
  "price": 899.99,
  "rating": 4.3,
  "specifications": "{\"procesador\": \"Intel Core i5-1135G7\", \"ram\": \"8GB\", \"almacenamiento\": \"256GB SSD\", \"pantalla\": \"14\\\" FHD\"}"
}
```
una vez el controlador recibe la peticion POST, se crea un objeto Product y se llama al servicio/adaptador para guardar el producto en el archivo JSON.

Response (JSON):
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "productName": "Laptop",
  "imageUrl": "https://ejemplo.com/image.jpg",
  "description": "Laptop ultradelgada ",
  "price": 899.99,
  "rating": 4.3,
  "specifications": "{\"procesador\": \"Intel Core i5-1135G7\", \"ram\": \"8GB\", \"almacenamiento\": \"256GB SSD\", \"pantalla\": \"14\\\" FHD\"}"
}
```
### Listar todos los productos - Metodo GET /product

una vez el controlador recibe la peticion GET, se llama al servicio/adaptador para leer todos los productos del archivo JSON y devolverlos en la respuesta.

Response (JSON):
```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "productName": "Laptop",
    "imageUrl": "https://ejemplo.com/image.jpg",
    "description": "Laptop ultradelgada ",
    "price": 899.99,
    "rating": 4.3,
    "specifications": "{\"procesador\": \"Intel Core i5-1135G7\", \"ram\": \"8GB\", \"almacenamiento\": \"256GB SSD\", \"pantalla\": \"14\\\" FHD\"}"
  },
  {
    "id": "223e4567-e89b-12d3-a456-426614174001",
    "productName": "Smartphone",
    "imageUrl": "https://ejemplo.com/phone.jpg",
    "description": "Smartphone de alta gama",
    "price": 699.99,
    "rating": 4.7,
    "specifications": "{\"procesador\": \"Snapdragon 888\", \"ram\": \"8GB\", \"almacenamiento\": \"128GB\", \"pantalla\": \"6.5\\\" AMOLED\"}"
  }
]
```
### Comparar productos - Método: GET /product/compare?ids=
Request parameters:
dos o mas ids, ejemplo:
/product/compare?ids=1756831302752&ids=45&ids=254

una vez el controlador recibe la peticion GET con los ids colo Request parameters, llama al usecase donde se valida la cantidad y el contenido de los ids, luego se llama al servicio/adaptador para leer los productos del archivo JSON y devolver los solicitados en la respuesta.

Response (JSON):
```json
[
  {
    "id": "45",
    "productName": "Laptop",
    "imageUrl": "https://ejemplo.com/image.jpg",
    "description": "Laptop ultradelgada ",
    "price": 899.99,
    "rating": 4.3,
    "specifications": "{\"procesador\": \"Intel Core i5-1135G7\", \"ram\": \"8GB\", \"almacenamiento\": \"256GB SSD\", \"pantalla\": \"14\\\" FHD\"}"
  },
  {
    "id": "254",
    "productName": "Smartphone",
    "imageUrl": "https://ejemplo.com/phone.jpg",
    "description": "Smartphone de alta gama",
    "price": 699.99,
    "rating": 4.7,
    "specifications": "{\"procesador\": \"Snapdragon 888\", \"ram\": \"8GB\", \"almacenamiento\": \"128GB\", \"pantalla\": \"6.5\\\" AMOLED\"}"
  }
]
```

## Herramientas usadas en el proyecto 
el desarrollo de este proyecto se realizo con las siguientes herramientas:

**IDes**: 
- Cursor: Empleado para la generación de código asistida por IA, lo que permitió agilizar la construcción de clases y componentes.
- IntelliJ IDEA: utilizado principalmente para la compilación, ejecución y depuración del proyecto, gracias a su interfaz más conocida, amigable y con mayor soporte para entornos de desarrollo en Java. Adicionalmente se utilizao plugins como SonarLint para análisis de código en tiempo real y Github Copilot para sugerencias de código asistidas por IA y para la construccion de este archivo.

**Lenguaje de programación**:
- Java 21: Lenguaje de programación principal utilizado para desarrollar la lógica de negocio y los componentes de la aplicación.

**Frameworks y Librerías**:
- Spring Boot: Framework utilizado para construir la aplicación, facilitando la creación de servicios REST y la gestión de dependencias.
- Spring Web: Librería de Spring Boot utilizada para manejar las solicitudes
- Spring Context: Librería de Spring Boot utilizada para la gestión de beans y la inyección de dependencias.
- Jackson: Librería utilizada para la serialización y deserialización de objetos JSON (contenidad en spring starter web).
- Lombok: Librería que reduce el código boilerplate mediante anotaciones para generar getters, setters, constructores, etc.

**Herramientas de Construcción y Gestión de Dependencias**:
- Gradle: Herramienta de construcción utilizada para gestionar las dependencias, compilar el código y ejecutar tareas relacionadas con el proyecto.

**Generacion de estructura y validacion de Clean Arquitecture**:
- Scaffold Clean Architecture: Plugin de Gradle utilizado para generar la estructura del proyecto siguiendo los principios de la Arquitectura Limpia y validar su correcta implementación.





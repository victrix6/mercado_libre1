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





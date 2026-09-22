# Bitacora_Corte2_SebastianGranados

- Sebastian Granados

## Blue Velvet

Sistema de gestion para el restaurante **Blue Velvet**, desarrollado como bitacora de laboratorio del segundo corte de la asignatura DOSW. El proyecto permite administrar mesas, reservas, cuentas y pedidos del restaurante.

## Problema a resolver

Actualmente el restaurante gestiona las mesas, pedidos y cuentas de forma manual (en papel o a memoria), lo que genera errores en las ordenes, demoras en la atencion al cliente y dificultad para llevar un registro confiable del estado de cada mesa y su facturacion.

## Alcance del sistema

**Dentro del alcance:**

- Gestion de mesas y su disponibilidad.
- Gestion de reservas por mesa.
- Anotacion y seguimiento de pedidos con sus items.
- Gestion de cuentas y calculo del total por mesa.
- Registro de vehiculos (parqueadero) asociado a la atencion del restaurante.
- El ciclo de una mesa: Reserva/Ocupacion -> Pedido -> Preparacion -> Cuenta -> Cierre

**Fuera del alcance:**

- Domicilios.
- Programas de fidelizacion a clientes.
- Multi-sucursales.

## Diagramas

![Diagrama de clases - Blue Velvet](docs/images/diagrama-clases.png)

---

## Funcionalidades implementadas

Version inicial de la API en memoria (sin persistencia), con las operaciones esenciales de cada dominio:

| Dominio | Funcionalidades |
|---|---|
| **Mesas** | Crear, listar y buscar mesas. |
| **Reservas** | Reservar una mesa en una fecha futura (verificando que la mesa exista y que no haya cruce de horario), listar y cancelar. |
| **Platos** | Crear, listar y buscar platos de la carta (con validacion de nombre unico). |
| **Pedidos** | Registrar un pedido con sus items (el precio de cada plato se congela al momento del pedido), listar y consultar por mesa. |
| **Cuentas** | Abrir una cuenta sobre una mesa (una mesa solo puede tener una cuenta abierta a la vez) y ver su total, calculado a partir de los pedidos de esa mesa. |
| **Parqueadero (Vehiculos)** | Registrar la entrada de un vehiculo (evitando registros duplicados de una misma placa) y consultarlos. |

### Tabla de endpoints

| Metodo | Endpoint | Descripcion | Exitos | Errores |
|---|---|---|---|---|
| GET | `/api/v1/mesas` | Listar todas las mesas | 200 | - |
| GET | `/api/v1/mesas/{id}` | Buscar mesa por id | 200 | 404 |
| POST | `/api/v1/mesas` | Crear una mesa | 201 | 400, 409 |
| GET | `/api/v1/reservas` | Listar todas las reservas | 200 | - |
| GET | `/api/v1/reservas/{id}` | Buscar reserva por id | 200 | 404 |
| POST | `/api/v1/reservas` | Crear una reserva | 201 | 400, 404, 409 |
| DELETE | `/api/v1/reservas/{id}` | Cancelar una reserva | 204 | 404 |
| GET | `/api/v1/platos` | Listar toda la carta | 200 | - |
| GET | `/api/v1/platos/{id}` | Buscar plato por id | 200 | 404 |
| POST | `/api/v1/platos` | Crear un plato | 201 | 400, 409 |
| DELETE | `/api/v1/platos/{id}` | Eliminar un plato | 204 | 404 |
| GET | `/api/v1/pedidos` | Listar todos los pedidos | 200 | - |
| GET | `/api/v1/pedidos/mesa/{idMesa}` | Listar pedidos de una mesa | 200 | - |
| GET | `/api/v1/pedidos/{id}` | Buscar pedido por id | 200 | 404 |
| POST | `/api/v1/pedidos` | Crear un pedido | 201 | 400, 404 |
| GET | `/api/v1/cuentas` | Listar todas las cuentas | 200 | - |
| GET | `/api/v1/cuentas/{id}` | Buscar cuenta por id | 200 | 404 |
| POST | `/api/v1/cuentas` | Abrir una cuenta | 201 | 404, 409 |
| GET | `/api/v1/vehiculos` | Listar todos los registros | 200 | - |
| GET | `/api/v1/vehiculos/{id}` | Buscar registro por id | 200 | 404 |
| POST | `/api/v1/vehiculos` | Registrar entrada de un vehiculo | 201 | 400, 409 |

### Manejo de errores

Todos los errores se responden con el mismo formato JSON (`ErrorResponseDTO`), gestionado centralmente por `GlobalExceptionHandler` (`@RestControllerAdvice`):

```json
{
  "timestamp": "2026-09-21T12:00:00",
  "status": 409,
  "error": "Conflicto",
  "message": "Ya existe una mesa con el numero: 5",
  "path": "/api/v1/mesas"
}
```

| Codigo | Situacion |
|---|---|
| 400 | Datos de entrada invalidos (Bean Validation en los DTOs) |
| 404 | El recurso solicitado no existe |
| 409 | Conflicto de negocio (duplicado) |
| 500 | Error interno no controlado |

---

## Estructura del proyecto

```
src/main/java/com/dosw/bluevelvet/
├── controller/       Controllers REST (uno por dominio)
├── service/          Interfaces (I*Service) e implementaciones por dominio
├── dto/               Records de entrada/salida por dominio + ErrorResponseDTO
├── mapper/            Mappers In/Out por dominio (DTO <-> dominio)
├── validator/         Regla de negocio de cada dominio
├── model/domain/      Objetos de dominio (Mesa, Pedido, Cuenta, Reserva, Plato, ItemPedido, RegistroVehiculo)
├── exception/         Excepciones personalizadas + GlobalExceptionHandler
├── util/              Utilidades estaticas (generador de ids)
└── config/            Configuracion de OpenAPI/Swagger
```

## Como ejecutar el proyecto

Requisitos: Java 17+ y Maven.

```bash
mvn clean install
mvn spring-boot:run
```

La aplicacion queda disponible en `http://localhost:8080`. Documentacion interactiva en `http://localhost:8080/swagger-ui/index.html`.

## Pruebas

```bash
mvn test
```

18 pruebas unitarias (JUnit 5 + Mockito), cubriendo para cada dominio: caso exitoso, un caso de conflicto/duplicado y un caso de recurso no encontrado.

# 🥩 Sistema de Gestión - Carnicería Don Pedro

Sistema completo de gestión para carnicería con backend REST API desarrollado con Quarkus, JPA/Hibernate y MySQL.

## 📋 Características

- ✅ Gestión completa de **Productos** con categorías y control de stock
- ✅ Administración de **Clientes** con datos de contacto
- ✅ Sistema de **Ventas** con detalles de productos y cálculo automático
- ✅ **Estadísticas** y reportes del negocio en tiempo real
- ✅ API REST completamente documentada con OpenAPI/Swagger
- ✅ Validaciones de datos y manejo de errores
- ✅ Control automático de inventario

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/alumno/proyecto/
├── entity/           → Entidades JPA (Modelos de datos)
│   ├── Categoria.java
│   ├── Producto.java
│   ├── Cliente.java
│   ├── Venta.java
│   └── DetalleVenta.java
├── repository/       → Acceso a datos con Panache
│   ├── CategoriaRepository.java
│   ├── ProductoRepository.java
│   ├── ClienteRepository.java
│   └── VentaRepository.java
├── service/          → Lógica de negocio
│   ├── CategoriaService.java
│   ├── ProductoService.java
│   ├── ClienteService.java
│   ├── VentaService.java
│   └── EstadisticasService.java
├── resource/         → Endpoints REST
│   ├── CategoriaResource.java
│   ├── ProductoResource.java
│   ├── ClienteResource.java
│   ├── VentaResource.java
│   └── EstadisticasResource.java
├── dto/              → Objetos de transferencia
│   ├── ApiResponse.java
│   └── EstadisticasDTO.java
└── exception/        → Manejo de excepciones
    └── GlobalExceptionHandler.java
```

## 🚀 Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+**
- **MySQL 8.0+**
- **XAMPP** (con MySQL configurado)

## ⚙️ Configuración

### 1. Base de Datos

Ejecuta el script SQL proporcionado para crear la base de datos y las tablas:

```bash
mysql -u root < carniceria_don_pedro.sql
```

O desde MySQL Workbench/phpMyAdmin ejecuta el script completo.

### 2. Configuración de Conexión

El archivo `application.properties` ya está configurado para XAMPP por defecto:

```properties
quarkus.datasource.db-kind=mysql
quarkus.datasource.username=root
quarkus.datasource.password=
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/carniceria_don_pedro
```

Si tu configuración es diferente, modifica estos valores.

## 🏃‍♂️ Ejecución

### Modo Desarrollo

```bash
./mvnw quarkus:dev
```

O en Windows:
```bash
mvnw.cmd quarkus:dev
```

La aplicación estará disponible en: **http://localhost:8080**

### Swagger UI

Accede a la documentación interactiva de la API en:
**http://localhost:8080/swagger-ui**

## 📡 Endpoints de la API

### Categorías
- `GET /api/categorias` - Listar todas
- `GET /api/categorias/{id}` - Obtener por ID
- `POST /api/categorias` - Crear nueva
- `PUT /api/categorias/{id}` - Actualizar
- `DELETE /api/categorias/{id}` - Eliminar

### Productos
- `GET /api/productos` - Listar todos
- `GET /api/productos/{id}` - Obtener por ID
- `GET /api/productos/buscar?nombre={nombre}` - Buscar por nombre
- `GET /api/productos/categoria/{id}` - Por categoría
- `GET /api/productos/stock-bajo?limite=10` - Stock bajo
- `POST /api/productos` - Crear nuevo
- `PUT /api/productos/{id}` - Actualizar
- `PATCH /api/productos/{id}/stock?cantidad=5` - Actualizar stock
- `DELETE /api/productos/{id}` - Eliminar

### Clientes
- `GET /api/clientes` - Listar todos
- `GET /api/clientes/{id}` - Obtener por ID
- `GET /api/clientes/buscar?nombre={nombre}` - Buscar por nombre
- `GET /api/clientes/email/{email}` - Por email
- `POST /api/clientes` - Crear nuevo
- `PUT /api/clientes/{id}` - Actualizar
- `DELETE /api/clientes/{id}` - Eliminar

### Ventas
- `GET /api/ventas` - Listar todas
- `GET /api/ventas/{id}` - Obtener por ID
- `GET /api/ventas/cliente/{id}` - Por cliente
- `GET /api/ventas/recientes?limite=10` - Recientes
- `GET /api/ventas/por-fecha?inicio=2025-10-01T00:00:00&fin=2025-10-31T23:59:59` - Por rango
- `POST /api/ventas` - Crear nueva
- `PUT /api/ventas/{id}` - Actualizar
- `DELETE /api/ventas/{id}` - Eliminar

### Estadísticas
- `GET /api/estadisticas/generales` - Estadísticas generales
- `GET /api/estadisticas/productos-mas-vendidos?limite=10` - Top productos
- `GET /api/estadisticas/ventas-por-categoria` - Ventas por categoría
- `GET /api/estadisticas/clientes-top?limite=10` - Mejores clientes
- `GET /api/estadisticas/producto/{id}` - Stats de un producto
- `GET /api/estadisticas/ventas-por-dia?dias=7` - Ventas últimos días
- `GET /api/estadisticas/stock-bajo?limite=10` - Productos con stock bajo

## 📝 Ejemplos de Uso

### Crear una Venta

```json
POST /api/ventas
{
  "cliente": {
    "idCliente": 1
  },
  "detalles": [
    {
      "producto": {
        "idProducto": 1
      },
      "cantidadKg": 2.5
    },
    {
      "producto": {
        "idProducto": 5
      },
      "cantidadKg": 1.0
    }
  ]
}
```

### Crear un Producto

```json
POST /api/productos
{
  "nombre": "Asado de tira",
  "categoria": {
    "idCategoria": 1
  },
  "precioKg": 65.00,
  "stockKg": 50.0,
  "descripcion": "Corte argentino premium"
}
```

### Crear un Cliente

```json
POST /api/clientes
{
  "nombre": "Pedro Martínez",
  "telefono": "0984-123456",
  "direccion": "Av. España 789",
  "email": "pedro@example.com"
}
```

## 🔒 Validaciones Implementadas

- ✅ Validación de campos obligatorios (@NotNull)
- ✅ Validación de longitud de textos (@Size)
- ✅ Validación de formato de email (@Email)
- ✅ Validación de valores numéricos (@DecimalMin)
- ✅ Control de stock en ventas
- ✅ Cálculo automático de subtotales y totales

## 📊 Funcionalidades de Negocio

1. **Control de Inventario**: El stock se descuenta automáticamente al crear ventas
2. **Cálculos Automáticos**: Los subtotales y totales se calculan automáticamente
3. **Restauración de Stock**: Al eliminar o modificar ventas, el stock se restaura
4. **Alertas de Stock Bajo**: Endpoint para identificar productos con stock crítico
5. **Reportes en Tiempo Real**: Estadísticas de ventas, productos y clientes

## 🛠️ Tecnologías Utilizadas

- **Quarkus 3.6.4** - Framework Java supersónico
- **Hibernate ORM with Panache** - ORM simplificado
- **MySQL** - Base de datos relacional
- **Jakarta EE** - Estándares empresariales
- **RESTEasy Reactive** - REST endpoints reactivos
- **SmallRye OpenAPI** - Documentación automática
- **Bean Validation** - Validaciones declarativas

## 📦 Compilar para Producción

```bash
./mvnw package -DskipTests
```

El JAR ejecutable estará en: `target/quarkus-app/`

Para ejecutar:
```bash
java -jar target/quarkus-app/quarkus-run.jar
```

## 🤝 Contribuciones

Este proyecto es para fines académicos - Taller IV.

## 📄 Licencia

Proyecto académico - Carnicería Don Pedro © 2025

---

**Desarrollado con ❤️ usando Quarkus + Java + MySQL**

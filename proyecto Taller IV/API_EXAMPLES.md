# Ejemplos de Peticiones HTTP - API Carnicería Don Pedro

## 🔹 CATEGORÍAS

### Listar todas las categorías
GET http://localhost:8080/api/categorias

### Obtener categoría por ID
GET http://localhost:8080/api/categorias/1

### Crear nueva categoría
POST http://localhost:8080/api/categorias
Content-Type: application/json

{
  "nombre": "Pescados"
}

### Actualizar categoría
PUT http://localhost:8080/api/categorias/1
Content-Type: application/json

{
  "nombre": "Res Premium"
}

### Eliminar categoría
DELETE http://localhost:8080/api/categorias/5

---

## 🔹 PRODUCTOS

### Listar todos los productos
GET http://localhost:8080/api/productos

### Obtener producto por ID
GET http://localhost:8080/api/productos/1

### Buscar productos por nombre
GET http://localhost:8080/api/productos/buscar?nombre=pollo

### Productos por categoría
GET http://localhost:8080/api/productos/categoria/1

### Productos con stock bajo
GET http://localhost:8080/api/productos/stock-bajo?limite=20

### Crear nuevo producto
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "nombre": "Bife de chorizo",
  "categoria": {
    "idCategoria": 1
  },
  "precioKg": 85.00,
  "stockKg": 45.5,
  "descripcion": "Corte premium de res"
}

### Actualizar producto
PUT http://localhost:8080/api/productos/1
Content-Type: application/json

{
  "nombre": "Costilla de res premium",
  "categoria": {
    "idCategoria": 1
  },
  "precioKg": 60.00,
  "stockKg": 150.0,
  "descripcion": "Corte tierno ideal para parrilla premium"
}

### Actualizar solo el stock
PATCH http://localhost:8080/api/productos/1/stock?cantidad=25.5

### Eliminar producto
DELETE http://localhost:8080/api/productos/9

---

## 🔹 CLIENTES

### Listar todos los clientes
GET http://localhost:8080/api/clientes

### Obtener cliente por ID
GET http://localhost:8080/api/clientes/1

### Buscar clientes por nombre
GET http://localhost:8080/api/clientes/buscar?nombre=juan

### Buscar cliente por email
GET http://localhost:8080/api/clientes/email/juan@example.com

### Crear nuevo cliente
POST http://localhost:8080/api/clientes
Content-Type: application/json

{
  "nombre": "Ana Rodríguez",
  "telefono": "0985-555444",
  "direccion": "Calle Nueva 456",
  "email": "ana.rodriguez@example.com"
}

### Actualizar cliente
PUT http://localhost:8080/api/clientes/1
Content-Type: application/json

{
  "nombre": "Juan Gómez Pérez",
  "telefono": "0981-123456",
  "direccion": "Av. Central 123 - Barrio Centro",
  "email": "juan.gomez@example.com"
}

### Eliminar cliente
DELETE http://localhost:8080/api/clientes/3

---

## 🔹 VENTAS

### Listar todas las ventas
GET http://localhost:8080/api/ventas

### Obtener venta por ID
GET http://localhost:8080/api/ventas/1

### Ventas por cliente
GET http://localhost:8080/api/ventas/cliente/1

### Ventas recientes
GET http://localhost:8080/api/ventas/recientes?limite=5

### Ventas por rango de fechas
GET http://localhost:8080/api/ventas/por-fecha?inicio=2025-10-01T00:00:00&fin=2025-10-31T23:59:59

### Crear nueva venta (ejemplo simple)
POST http://localhost:8080/api/ventas
Content-Type: application/json

{
  "cliente": {
    "idCliente": 1
  },
  "detalles": [
    {
      "producto": {
        "idProducto": 1
      },
      "cantidadKg": 3.0
    },
    {
      "producto": {
        "idProducto": 5
      },
      "cantidadKg": 2.5
    }
  ]
}

### Crear venta completa (múltiples productos)
POST http://localhost:8080/api/ventas
Content-Type: application/json

{
  "cliente": {
    "idCliente": 2
  },
  "detalles": [
    {
      "producto": {
        "idProducto": 2
      },
      "cantidadKg": 1.5
    },
    {
      "producto": {
        "idProducto": 7
      },
      "cantidadKg": 4.0
    },
    {
      "producto": {
        "idProducto": 8
      },
      "cantidadKg": 2.0
    }
  ]
}

### Actualizar venta
PUT http://localhost:8080/api/ventas/1
Content-Type: application/json

{
  "cliente": {
    "idCliente": 1
  },
  "detalles": [
    {
      "producto": {
        "idProducto": 1
      },
      "cantidadKg": 5.0
    }
  ]
}

### Eliminar venta
DELETE http://localhost:8080/api/ventas/3

---

## 🔹 ESTADÍSTICAS

### Estadísticas generales del negocio
GET http://localhost:8080/api/estadisticas/generales

### Top 10 productos más vendidos
GET http://localhost:8080/api/estadisticas/productos-mas-vendidos?limite=10

### Ventas agrupadas por categoría
GET http://localhost:8080/api/estadisticas/ventas-por-categoria

### Top 5 mejores clientes
GET http://localhost:8080/api/estadisticas/clientes-top?limite=5

### Estadísticas de un producto específico
GET http://localhost:8080/api/estadisticas/producto/1

### Ventas de los últimos 7 días
GET http://localhost:8080/api/estadisticas/ventas-por-dia?dias=7

### Productos con stock bajo
GET http://localhost:8080/api/estadisticas/stock-bajo?limite=15

---

## 📝 Notas Importantes

1. **Base URL**: http://localhost:8080
2. **Content-Type**: Todas las peticiones POST/PUT requieren `Content-Type: application/json`
3. **Formato de fechas**: ISO-8601 → `yyyy-MM-ddTHH:mm:ss` (ejemplo: 2025-10-15T10:30:00)
4. **IDs**: Usar IDs existentes en la base de datos
5. **Stock**: Al crear ventas, el stock se descuenta automáticamente
6. **Precios**: Se toman automáticamente del producto al momento de la venta

## 🧪 Herramientas Recomendadas para Pruebas

- **Swagger UI**: http://localhost:8080/swagger-ui (Interfaz gráfica integrada)
- **Postman**: Importar estos ejemplos
- **Thunder Client** (VS Code): Extensión ligera
- **curl**: Línea de comandos
- **HTTPie**: Alternativa moderna a curl

## 🔄 Flujo de Prueba Recomendado

1. ✅ Verificar categorías existentes (GET /api/categorias)
2. ✅ Listar productos disponibles (GET /api/productos)
3. ✅ Crear un nuevo cliente (POST /api/clientes)
4. ✅ Crear una venta con productos (POST /api/ventas)
5. ✅ Ver estadísticas generales (GET /api/estadisticas/generales)
6. ✅ Verificar productos más vendidos (GET /api/estadisticas/productos-mas-vendidos)
7. ✅ Consultar stock bajo (GET /api/estadisticas/stock-bajo)

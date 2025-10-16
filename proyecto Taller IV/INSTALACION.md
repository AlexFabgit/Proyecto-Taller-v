# 🚀 Guía de Instalación y Ejecución - Paso a Paso

## ✅ Paso 1: Verificar Requisitos

Asegúrate de tener instalado:

- [x] **Java 17 o superior**
  ```bash
  java -version
  ```

- [x] **Maven 3.8+**
  ```bash
  mvn -version
  ```

- [x] **XAMPP** con MySQL activo

## ✅ Paso 2: Configurar la Base de Datos

### 2.1 Iniciar MySQL desde XAMPP

1. Abre el Panel de Control de XAMPP
2. Haz clic en **"Start"** en la línea de MySQL
3. Verifica que el puerto sea **3306** (por defecto)

### 2.2 Crear la Base de Datos

Opción A - Desde phpMyAdmin:
1. Abre tu navegador: http://localhost/phpmyadmin
2. Haz clic en **"SQL"**
3. Copia y pega todo el contenido del archivo `carniceria_don_pedro.sql`
4. Haz clic en **"Continuar"**

Opción B - Desde Terminal/MySQL Workbench:
```bash
mysql -u root -p < carniceria_don_pedro.sql
```
(Si no tienes contraseña, solo presiona Enter)

### 2.3 Verificar la Creación

En phpMyAdmin o MySQL Workbench:
```sql
USE carniceria_don_pedro;
SHOW TABLES;
```

Deberías ver 5 tablas:
- categorias
- productos
- clientes
- ventas
- detalle_venta

## ✅ Paso 3: Configurar el Proyecto

### 3.1 Verificar Configuración de Base de Datos

Abre `src/main/resources/application.properties` y verifica:

```properties
quarkus.datasource.username=root
quarkus.datasource.password=
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/carniceria_don_pedro
```

**Si tu MySQL tiene contraseña**, cámbiala en la línea `password=`

## ✅ Paso 4: Compilar el Proyecto

Desde la terminal, en la carpeta del proyecto:

```bash
./mvnw clean install -DskipTests
```

O en Windows:
```bash
mvnw.cmd clean install -DskipTests
```

Esto descargará todas las dependencias. **Puede tardar algunos minutos la primera vez**.

## ✅ Paso 5: Ejecutar la Aplicación

### Modo Desarrollo (recomendado para desarrollo)

```bash
./mvnw quarkus:dev
```

O en Windows:
```bash
mvnw.cmd quarkus:dev
```

Verás un mensaje como:
```
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
INFO  [io.quarkus] (Quarkus Main Thread) carniceria-don-pedro 1.0.0-SNAPSHOT on JVM started in 2.345s. Listening on: http://localhost:8080
```

✅ **¡Listo! Tu aplicación está corriendo**

## ✅ Paso 6: Verificar que Funciona

### 6.1 Acceder a Swagger UI

Abre tu navegador en: **http://localhost:8080/swagger-ui**

Deberías ver la documentación interactiva de la API con todos los endpoints.

### 6.2 Probar un Endpoint Simple

En tu navegador o Postman:
```
GET http://localhost:8080/api/categorias
```

Deberías recibir una respuesta JSON con las categorías:
```json
{
  "message": "Operación exitosa",
  "code": 200,
  "data": [
    {
      "idCategoria": 1,
      "nombre": "Res"
    },
    ...
  ]
}
```

## ✅ Paso 7: Probar Funcionalidades

### 7.1 Listar Productos
```
GET http://localhost:8080/api/productos
```

### 7.2 Crear un Cliente
```
POST http://localhost:8080/api/clientes
Content-Type: application/json

{
  "nombre": "Test Usuario",
  "telefono": "0999-888777",
  "direccion": "Calle Test 123",
  "email": "test@example.com"
}
```

### 7.3 Crear una Venta
```
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
      "cantidadKg": 2.5
    }
  ]
}
```

### 7.4 Ver Estadísticas
```
GET http://localhost:8080/api/estadisticas/generales
```

## 🔧 Solución de Problemas Comunes

### ❌ Error: "Connection refused" o "Unknown database"

**Solución**: Verifica que MySQL esté corriendo y que hayas creado la base de datos.

```bash
# En terminal
mysql -u root -e "SHOW DATABASES;"
```

### ❌ Error: "Access denied for user 'root'"

**Solución**: Tu MySQL tiene contraseña. Actualiza `application.properties`:
```properties
quarkus.datasource.password=TU_CONTRASEÑA_AQUI
```

### ❌ Error: "Port 8080 already in use"

**Solución**: Otro servicio está usando el puerto. Cambia el puerto en `application.properties`:
```properties
quarkus.http.port=8081
```

### ❌ Maven no funciona

**Solución**: Usa el wrapper incluido:
```bash
# Linux/Mac
chmod +x mvnw
./mvnw quarkus:dev

# Windows
mvnw.cmd quarkus:dev
```

### ❌ Error de compilación de Java

**Solución**: Verifica tu versión de Java (debe ser 17+):
```bash
java -version
```

Si tienes Java 11 o menor, actualiza o descarga Java 17:
- https://adoptium.net/

## 📚 Recursos Adicionales

- **Swagger UI**: http://localhost:8080/swagger-ui
- **OpenAPI JSON**: http://localhost:8080/openapi
- **Health Check**: http://localhost:8080/q/health
- **Métricas**: http://localhost:8080/q/metrics

## 🎯 Siguientes Pasos

1. ✅ Explora todos los endpoints en Swagger UI
2. ✅ Prueba crear, actualizar y eliminar datos
3. ✅ Revisa las estadísticas y reportes
4. ✅ Consulta `API_EXAMPLES.md` para ejemplos de peticiones
5. ✅ Lee el código fuente para entender la arquitectura

## 🆘 ¿Necesitas Ayuda?

Consulta:
- `README.md` - Documentación completa
- `API_EXAMPLES.md` - Ejemplos de uso
- Swagger UI - Documentación interactiva

---

**¡Felicidades! Tu sistema está funcionando** 🎉

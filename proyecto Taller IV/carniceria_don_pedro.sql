-- ======================================================
-- BASE DE DATOS: Carnicería Don Pedro
-- Sistema de Gestión Completo
-- ======================================================

DROP DATABASE IF EXISTS carniceria_don_pedro;
CREATE DATABASE carniceria_don_pedro;
USE carniceria_don_pedro;

-- ======================================================
-- TABLA: Categorías de productos
-- ======================================================
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    INDEX idx_nombre (nombre)
);

INSERT INTO categorias (nombre) VALUES
('Res'),
('Cerdo'),
('Pollo'),
('Embutidos'),
('Otros');

-- ======================================================
-- TABLA: Productos
-- ======================================================
CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_categoria INT,
    precio_kg DECIMAL(10,2) NOT NULL,
    stock_kg DECIMAL(10,2) DEFAULT 0,
    descripcion TEXT,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    INDEX idx_nombre (nombre),
    INDEX idx_categoria (id_categoria),
    INDEX idx_stock (stock_kg)
);

INSERT INTO productos (nombre, id_categoria, precio_kg, stock_kg, descripcion) VALUES
('Costilla de res', 1, 55.00, 120.0, 'Corte tierno ideal para parrilla'),
('Lomo de res', 1, 75.00, 80.0, 'Corte premium para asado'),
('Bife de chorizo', 1, 85.00, 60.0, 'Corte argentino premium'),
('Chuleta de cerdo', 2, 42.00, 100.0, 'Corte magro con hueso'),
('Panceta', 2, 38.00, 60.0, 'Ideal para guisos o parrilla'),
('Pechuga de pollo', 3, 35.00, 150.0, 'Sin piel ni hueso'),
('Alitas de pollo', 3, 22.00, 100.0, 'Perfectas para freír o hornear'),
('Muslos de pollo', 3, 28.00, 120.0, 'Con hueso, jugosos'),
('Chorizo parrillero', 4, 28.00, 90.0, 'Embutido artesanal'),
('Morcilla', 4, 25.00, 70.0, 'Tradicional morcilla negra'),
('Salchichas', 4, 32.00, 80.0, 'Pack x 6 unidades'),
('Carbón vegetal (bolsa 5kg)', 5, 20.00, 40.0, 'Bolsa de carbón para parrilla');

-- ======================================================
-- TABLA: Clientes
-- ======================================================
CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(150),
    email VARCHAR(100),
    INDEX idx_nombre (nombre),
    INDEX idx_email (email)
);

INSERT INTO clientes (nombre, telefono, direccion, email) VALUES
('Juan Gómez', '0981-123456', 'Av. Central 123', 'juan@example.com'),
('María López', '0982-987654', 'Calle 14 Nº456', 'maria@example.com'),
('Carlos Duarte', '0983-564738', 'Ruta Acceso Sur 789', 'carlos@example.com'),
('Ana Martínez', '0984-111222', 'Barrio San Miguel 321', 'ana@example.com'),
('Pedro Rodríguez', '0985-333444', 'Av. España 555', 'pedro@example.com');

-- ======================================================
-- TABLA: Ventas
-- ======================================================
CREATE TABLE ventas (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    fecha DATETIME DEFAULT NOW(),
    total DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    INDEX idx_fecha (fecha),
    INDEX idx_cliente (id_cliente)
);

-- ======================================================
-- TABLA: Detalles de venta
-- ======================================================
CREATE TABLE detalle_venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT,
    id_producto INT,
    cantidad_kg DECIMAL(10,2),
    precio_unitario DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    FOREIGN KEY (id_venta) REFERENCES ventas(id_venta) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    INDEX idx_venta (id_venta),
    INDEX idx_producto (id_producto)
);

-- ======================================================
-- EJEMPLOS DE VENTAS
-- ======================================================

-- Venta 1: Juan Gómez
INSERT INTO ventas (id_cliente, fecha, total) VALUES
(1, '2025-10-14 10:30:00', 0);

SET @venta1_id = LAST_INSERT_ID();

INSERT INTO detalle_venta (id_venta, id_producto, cantidad_kg, precio_unitario, subtotal) VALUES
(@venta1_id, 1, 2.0, 55.00, 110.00),
(@venta1_id, 9, 3.0, 28.00, 84.00);

UPDATE ventas SET total = (SELECT SUM(subtotal) FROM detalle_venta WHERE id_venta = @venta1_id) WHERE id_venta = @venta1_id;

-- Venta 2: María López
INSERT INTO ventas (id_cliente, fecha, total) VALUES
(2, '2025-10-14 11:00:00', 0);

SET @venta2_id = LAST_INSERT_ID();

INSERT INTO detalle_venta (id_venta, id_producto, cantidad_kg, precio_unitario, subtotal) VALUES
(@venta2_id, 4, 2.0, 42.00, 84.00),
(@venta2_id, 12, 1.0, 20.00, 20.00);

UPDATE ventas SET total = (SELECT SUM(subtotal) FROM detalle_venta WHERE id_venta = @venta2_id) WHERE id_venta = @venta2_id;

-- Venta 3: Carlos Duarte
INSERT INTO ventas (id_cliente, fecha, total) VALUES
(3, '2025-10-14 12:00:00', 0);

SET @venta3_id = LAST_INSERT_ID();

INSERT INTO detalle_venta (id_venta, id_producto, cantidad_kg, precio_unitario, subtotal) VALUES
(@venta3_id, 6, 2.0, 35.00, 70.00),
(@venta3_id, 7, 1.5, 22.00, 33.00);

UPDATE ventas SET total = (SELECT SUM(subtotal) FROM detalle_venta WHERE id_venta = @venta3_id) WHERE id_venta = @venta3_id;

-- Venta 4: Ana Martínez
INSERT INTO ventas (id_cliente, fecha, total) VALUES
(4, '2025-10-15 09:30:00', 0);

SET @venta4_id = LAST_INSERT_ID();

INSERT INTO detalle_venta (id_venta, id_producto, cantidad_kg, precio_unitario, subtotal) VALUES
(@venta4_id, 2, 1.5, 75.00, 112.50),
(@venta4_id, 10, 2.0, 25.00, 50.00),
(@venta4_id, 11, 1.0, 32.00, 32.00);

UPDATE ventas SET total = (SELECT SUM(subtotal) FROM detalle_venta WHERE id_venta = @venta4_id) WHERE id_venta = @venta4_id;

-- Venta 5: Pedro Rodríguez
INSERT INTO ventas (id_cliente, fecha, total) VALUES
(5, '2025-10-15 14:00:00', 0);

SET @venta5_id = LAST_INSERT_ID();

INSERT INTO detalle_venta (id_venta, id_producto, cantidad_kg, precio_unitario, subtotal) VALUES
(@venta5_id, 3, 2.5, 85.00, 212.50),
(@venta5_id, 5, 1.0, 38.00, 38.00);

UPDATE ventas SET total = (SELECT SUM(subtotal) FROM detalle_venta WHERE id_venta = @venta5_id) WHERE id_venta = @venta5_id;

-- ======================================================
-- CONSULTAS ÚTILES
-- ======================================================

-- 1. Ver listado de productos con su categoría
SELECT 
    p.id_producto,
    p.nombre AS producto, 
    c.nombre AS categoria, 
    p.precio_kg, 
    p.stock_kg,
    p.descripcion
FROM productos p
JOIN categorias c ON p.id_categoria = c.id_categoria
ORDER BY c.nombre, p.nombre;

-- 2. Ver ventas con detalle de cliente
SELECT 
    v.id_venta, 
    v.fecha, 
    c.nombre AS cliente,
    c.telefono,
    v.total
FROM ventas v
JOIN clientes c ON v.id_cliente = c.id_cliente
ORDER BY v.fecha DESC;

-- 3. Detalle completo de una venta específica
SELECT 
    v.id_venta,
    v.fecha,
    c.nombre AS cliente,
    p.nombre AS producto,
    d.cantidad_kg,
    d.precio_unitario,
    d.subtotal
FROM ventas v
JOIN clientes c ON v.id_cliente = c.id_cliente
JOIN detalle_venta d ON v.id_venta = d.id_venta
JOIN productos p ON d.id_producto = p.id_producto
WHERE v.id_venta = 1;

-- 4. Productos más vendidos
SELECT 
    p.nombre AS producto,
    SUM(d.cantidad_kg) AS total_vendido_kg,
    SUM(d.subtotal) AS ingresos_totales
FROM detalle_venta d
JOIN productos p ON d.id_producto = p.id_producto
GROUP BY p.id_producto, p.nombre
ORDER BY total_vendido_kg DESC
LIMIT 10;

-- 5. Ventas por categoría
SELECT 
    c.nombre AS categoria,
    COUNT(d.id_detalle) AS cantidad_ventas,
    SUM(d.subtotal) AS total_ingresos
FROM detalle_venta d
JOIN productos p ON d.id_producto = p.id_producto
JOIN categorias c ON p.id_categoria = c.id_categoria
GROUP BY c.id_categoria, c.nombre
ORDER BY total_ingresos DESC;

-- 6. Mejores clientes
SELECT 
    c.nombre AS cliente,
    c.telefono,
    COUNT(v.id_venta) AS total_compras,
    SUM(v.total) AS total_gastado
FROM ventas v
JOIN clientes c ON v.id_cliente = c.id_cliente
GROUP BY c.id_cliente, c.nombre, c.telefono
ORDER BY total_gastado DESC
LIMIT 10;

-- 7. Productos con stock bajo (menos de 50 kg)
SELECT 
    p.nombre AS producto,
    c.nombre AS categoria,
    p.stock_kg,
    p.precio_kg
FROM productos p
JOIN categorias c ON p.id_categoria = c.id_categoria
WHERE p.stock_kg < 50
ORDER BY p.stock_kg ASC;

-- 8. Resumen de ventas por día
SELECT 
    DATE(fecha) AS dia,
    COUNT(*) AS cantidad_ventas,
    SUM(total) AS ingresos_dia
FROM ventas
GROUP BY DATE(fecha)
ORDER BY dia DESC;

-- 9. Estadísticas generales
SELECT 
    (SELECT COUNT(*) FROM ventas) AS total_ventas,
    (SELECT COUNT(*) FROM clientes) AS total_clientes,
    (SELECT COUNT(*) FROM productos) AS total_productos,
    (SELECT SUM(total) FROM ventas) AS ingresos_totales,
    (SELECT AVG(total) FROM ventas) AS ticket_promedio;

-- ======================================================
-- FIN DEL SCRIPT
-- ======================================================

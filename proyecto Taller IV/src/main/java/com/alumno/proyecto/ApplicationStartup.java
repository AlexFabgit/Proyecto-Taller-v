package com.alumno.proyecto;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ApplicationStartup {

    private static final Logger LOG = Logger.getLogger(ApplicationStartup.class);

    @Inject
    EntityManager entityManager;

    void onStart(@Observes StartupEvent ev) {
        LOG.info("═══════════════════════════════════════════════════════════════");
        LOG.info("🚀 INICIANDO APLICACIÓN - CARNICERÍA DON PEDRO");
        LOG.info("═══════════════════════════════════════════════════════════════");
        
        // Verificar conexión a la base de datos
        try {
            LOG.info("🔍 Verificando conexión a MySQL...");
            
            // Test de conexión simple
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            
            LOG.info("✅ CONEXIÓN A BASE DE DATOS: EXITOSA");
            
            // Contar registros en las tablas principales
            Long categorias = (Long) entityManager.createQuery("SELECT COUNT(c) FROM Categoria c").getSingleResult();
            Long productos = (Long) entityManager.createQuery("SELECT COUNT(p) FROM Producto p").getSingleResult();
            Long clientes = (Long) entityManager.createQuery("SELECT COUNT(c) FROM Cliente c").getSingleResult();
            Long ventas = (Long) entityManager.createQuery("SELECT COUNT(v) FROM Venta v").getSingleResult();
            
            LOG.info("───────────────────────────────────────────────────────────────");
            LOG.info("📊 DATOS EN LA BASE DE DATOS:");
            LOG.info("   ├─ Categorías: " + categorias);
            LOG.info("   ├─ Productos:  " + productos);
            LOG.info("   ├─ Clientes:   " + clientes);
            LOG.info("   └─ Ventas:     " + ventas);
            LOG.info("───────────────────────────────────────────────────────────────");
            
        } catch (Exception e) {
            LOG.error("❌ ERROR AL CONECTAR CON LA BASE DE DATOS");
            LOG.error("───────────────────────────────────────────────────────────────");
            LOG.error("⚠️  POSIBLES CAUSAS:");
            LOG.error("   1. MySQL no está ejecutándose (inicia XAMPP)");
            LOG.error("   2. La base de datos 'carniceria_don_pedro' no existe");
            LOG.error("   3. Usuario/contraseña incorrectos en application.properties");
            LOG.error("   4. Puerto 3306 bloqueado o en uso");
            LOG.error("───────────────────────────────────────────────────────────────");
            LOG.error("📝 Detalles del error: " + e.getMessage());
            LOG.error("═══════════════════════════════════════════════════════════════");
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }
        
        LOG.info("📡 ENDPOINTS DISPONIBLES:");
        LOG.info("   ├─ Swagger UI:    http://localhost:8080/swagger-ui");
        LOG.info("   ├─ OpenAPI:       http://localhost:8080/openapi");
        LOG.info("   ├─ Health Check:  http://localhost:8080/q/health");
        LOG.info("   ├─ Categorías:    http://localhost:8080/api/categorias");
        LOG.info("   ├─ Productos:     http://localhost:8080/api/productos");
        LOG.info("   ├─ Clientes:      http://localhost:8080/api/clientes");
        LOG.info("   ├─ Ventas:        http://localhost:8080/api/ventas");
        LOG.info("   └─ Estadísticas:  http://localhost:8080/api/estadisticas/generales");
        LOG.info("═══════════════════════════════════════════════════════════════");
        LOG.info("✨ APLICACIÓN LISTA - Todo funcionando correctamente!");
        LOG.info("═══════════════════════════════════════════════════════════════");
        LOG.info("");
        LOG.info("💡 TIP: Presiona Ctrl+Click en los links para abrirlos en el navegador");
        LOG.info("");
    }
}

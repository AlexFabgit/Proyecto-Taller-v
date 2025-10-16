#!/bin/bash

echo "═══════════════════════════════════════════════════════════════"
echo "🚀 INICIANDO APLICACIÓN - CARNICERÍA DON PEDRO"
echo "═══════════════════════════════════════════════════════════════"
echo ""

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Detectar Java 17
echo "🔍 Buscando Java 17..."

# Buscar Java 17 en ubicaciones comunes de macOS
JAVA17_PATHS=(
    "/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
    "/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home"
    "/Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home"
    "$HOME/.sdkman/candidates/java/17.0.11-tem"
    "$HOME/.sdkman/candidates/java/17.0.11-oracle"
)

JAVA17_HOME=""

# Buscar Java 17 en las rutas
for path in "${JAVA17_PATHS[@]}"; do
    if [ -d "$path" ]; then
        JAVA17_HOME="$path"
        echo -e "${GREEN}✅ Java 17 encontrado en: $JAVA17_HOME${NC}"
        break
    fi
done

# Si no se encontró en rutas predefinidas, buscar en todas las JVMs
if [ -z "$JAVA17_HOME" ]; then
    echo "🔍 Buscando en todas las JVMs instaladas..."
    for jvm in /Library/Java/JavaVirtualMachines/*/Contents/Home; do
        if [ -x "$jvm/bin/java" ]; then
            version=$("$jvm/bin/java" -version 2>&1 | grep "version" | cut -d'"' -f2 | cut -d'.' -f1)
            if [ "$version" = "17" ]; then
                JAVA17_HOME="$jvm"
                echo -e "${GREEN}✅ Java 17 encontrado en: $JAVA17_HOME${NC}"
                break
            fi
        fi
    done
fi

# Si no hay Java 17, usar el actual con workaround
if [ -z "$JAVA17_HOME" ]; then
    echo -e "${YELLOW}⚠️  Java 17 no encontrado${NC}"
    echo -e "${YELLOW}   Usando Java actual con configuración experimental${NC}"
    echo ""
    echo "Para mejor compatibilidad, instala Java 17:"
    echo "  brew install openjdk@17"
    echo ""
    
    # Usar Java actual con el flag experimental
    export MAVEN_OPTS="-Dnet.bytebuddy.experimental=true"
    echo -e "${BLUE}📝 Configurado MAVEN_OPTS con ByteBuddy experimental${NC}"
else
    # Usar Java 17
    export JAVA_HOME="$JAVA17_HOME"
    echo -e "${GREEN}📝 Configurado JAVA_HOME=$JAVA_HOME${NC}"
fi

echo ""
echo "═══════════════════════════════════════════════════════════════"
echo "🔧 CONFIGURACIÓN"
echo "═══════════════════════════════════════════════════════════════"

# Verificar la versión de Java que se usará
if [ -n "$JAVA_HOME" ]; then
    JAVA_VERSION=$("$JAVA_HOME/bin/java" -version 2>&1 | grep "version" | cut -d'"' -f2)
    echo "Java: $JAVA_VERSION"
    echo "JAVA_HOME: $JAVA_HOME"
else
    JAVA_VERSION=$(java -version 2>&1 | grep "version" | cut -d'"' -f2)
    echo "Java: $JAVA_VERSION"
    echo "MAVEN_OPTS: $MAVEN_OPTS"
fi

echo ""
echo "═══════════════════════════════════════════════════════════════"
echo "🚀 INICIANDO QUARKUS..."
echo "═══════════════════════════════════════════════════════════════"
echo ""
echo "📱 LINKS DE ACCESO RÁPIDO:"
echo ""
echo "   🔷 Swagger UI (Documentación Interactiva):"
echo "      http://localhost:8080/swagger-ui"
echo ""
echo "   🔷 API Endpoints:"
echo "      • Categorías:    http://localhost:8080/api/categorias"
echo "      • Productos:     http://localhost:8080/api/productos"
echo "      • Clientes:      http://localhost:8080/api/clientes"
echo "      • Ventas:        http://localhost:8080/api/ventas"
echo "      • Estadísticas:  http://localhost:8080/api/estadisticas/generales"
echo ""
echo "   🔷 Utilidades:"
echo "      • Health Check:  http://localhost:8080/q/health"
echo "      • OpenAPI JSON:  http://localhost:8080/openapi"
echo ""
echo "═══════════════════════════════════════════════════════════════"
echo ""
echo "⏳ Iniciando aplicación..."
echo ""

# Ejecutar Maven con la configuración y deshabilitar analytics
cd "$(dirname "$0")"

# Crear archivo de configuración para deshabilitar el prompt de analytics
mkdir -p .quarkus
echo "quarkus.analytics.disabled=true" > .quarkus/quarkus.properties

if [ -n "$JAVA_HOME" ]; then
    mvn quarkus:dev -Dquarkus.analytics.disabled=true
else
    MAVEN_OPTS="-Dnet.bytebuddy.experimental=true" mvn quarkus:dev -Dquarkus.analytics.disabled=true
fi

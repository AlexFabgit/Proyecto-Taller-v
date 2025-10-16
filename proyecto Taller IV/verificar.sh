#!/bin/bash

echo "═══════════════════════════════════════════════════════════════"
echo "🔍 VERIFICACIÓN DE REQUISITOS - CARNICERÍA DON PEDRO"
echo "═══════════════════════════════════════════════════════════════"
echo ""

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Verificar Java
echo "📦 Verificando Java..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo -e "${GREEN}✅ Java instalado: $JAVA_VERSION${NC}"
else
    echo -e "${RED}❌ Java NO está instalado${NC}"
    exit 1
fi

# Verificar Maven
echo ""
echo "📦 Verificando Maven..."
if [ -f "./mvnw" ]; then
    echo -e "${GREEN}✅ Maven wrapper encontrado${NC}"
else
    echo -e "${YELLOW}⚠️  Maven wrapper no encontrado, usando Maven del sistema${NC}"
fi

# Verificar MySQL
echo ""
echo "🗄️  Verificando MySQL..."
if lsof -Pi :3306 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo -e "${GREEN}✅ MySQL está ejecutándose en puerto 3306${NC}"
else
    echo -e "${RED}❌ MySQL NO está ejecutándose en puerto 3306${NC}"
    echo -e "${YELLOW}   → Inicia MySQL desde XAMPP${NC}"
    exit 1
fi

# Verificar base de datos
echo ""
echo "🔍 Verificando base de datos..."
if mysql -u root -e "USE carniceria_don_pedro;" 2>/dev/null; then
    echo -e "${GREEN}✅ Base de datos 'carniceria_don_pedro' existe${NC}"
    
    # Contar tablas
    TABLES=$(mysql -u root carniceria_don_pedro -e "SHOW TABLES;" 2>/dev/null | wc -l)
    TABLES=$((TABLES - 1))
    echo -e "${GREEN}   → $TABLES tablas encontradas${NC}"
else
    echo -e "${RED}❌ Base de datos 'carniceria_don_pedro' NO existe${NC}"
    echo -e "${YELLOW}   → Importa el archivo carniceria_don_pedro.sql${NC}"
    exit 1
fi

# Verificar puerto 8080
echo ""
echo "🌐 Verificando puerto 8080..."
if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo -e "${YELLOW}⚠️  Puerto 8080 está en uso${NC}"
    echo -e "${YELLOW}   → Cierra la aplicación existente o usa otro puerto${NC}"
else
    echo -e "${GREEN}✅ Puerto 8080 disponible${NC}"
fi

echo ""
echo "═══════════════════════════════════════════════════════════════"
echo -e "${GREEN}✨ VERIFICACIÓN COMPLETA - Todo listo para ejecutar${NC}"
echo "═══════════════════════════════════════════════════════════════"
echo ""
echo "Para iniciar la aplicación ejecuta:"
echo -e "${YELLOW}  ./mvnw quarkus:dev${NC}"
echo ""

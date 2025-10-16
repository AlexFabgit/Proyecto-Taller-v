#!/bin/bash

echo "═══════════════════════════════════════════════════════════════"
echo "🚨 SOLUCIÓN DE PROBLEMAS - CARNICERÍA DON PEDRO"
echo "═══════════════════════════════════════════════════════════════"
echo ""

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${YELLOW}PROBLEMA: MySQL no está ejecutándose${NC}"
echo ""
echo "═══════════════════════════════════════════════════════════════"
echo "📋 SOLUCIONES:"
echo "═══════════════════════════════════════════════════════════════"
echo ""

echo -e "${BLUE}OPCIÓN 1: Iniciar MySQL desde XAMPP (Recomendado)${NC}"
echo "   1. Abre XAMPP Control Panel"
echo "   2. Haz clic en 'Start' al lado de MySQL"
echo "   3. Verifica que aparezca en verde"
echo "   4. Vuelve a ejecutar: ./verificar.sh"
echo ""

echo -e "${BLUE}OPCIÓN 2: Iniciar MySQL desde Terminal${NC}"
echo "   Ejecuta:"
echo -e "   ${GREEN}/Applications/XAMPP/xamppfiles/bin/mysql.server start${NC}"
echo ""

echo -e "${BLUE}OPCIÓN 3: Verificar si MySQL está en otro puerto${NC}"
echo "   Ejecuta:"
echo -e "   ${GREEN}lsof -i -P | grep mysql${NC}"
echo ""

echo "═══════════════════════════════════════════════════════════════"
echo "🔍 VERIFICANDO PROCESOS MySQL..."
echo "═══════════════════════════════════════════════════════════════"

# Buscar procesos MySQL
MYSQL_PROCESSES=$(ps aux | grep mysql | grep -v grep)

if [ -z "$MYSQL_PROCESSES" ]; then
    echo -e "${RED}❌ No se encontraron procesos MySQL ejecutándose${NC}"
    echo ""
    echo "Para iniciar MySQL manualmente:"
    echo -e "${GREEN}/Applications/XAMPP/xamppfiles/bin/mysql.server start${NC}"
else
    echo -e "${GREEN}✅ Procesos MySQL encontrados:${NC}"
    echo "$MYSQL_PROCESSES"
    echo ""
    echo -e "${YELLOW}⚠️  MySQL está corriendo pero no responde en puerto 3306${NC}"
    echo "   Verifica la configuración de puerto en XAMPP"
fi

echo ""
echo "═══════════════════════════════════════════════════════════════"
echo "📖 AYUDA ADICIONAL"
echo "═══════════════════════════════════════════════════════════════"
echo ""
echo "Si continúa el problema:"
echo "   1. Reinicia XAMPP completamente"
echo "   2. Verifica que phpMyAdmin funcione: http://localhost/phpmyadmin"
echo "   3. Consulta: INSTALACION.md para más detalles"
echo ""

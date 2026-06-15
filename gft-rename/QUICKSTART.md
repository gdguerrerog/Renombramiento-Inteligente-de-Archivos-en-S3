# 🚀 Quick Start - OpenAPI Documentation

## Acceso Rápido a Swagger UI

### 1. Iniciar la aplicación
```bash
./mvnw spring-boot:run
```

### 2. Abrir en navegador
```
http://localhost:8080/swagger-ui.html
```

¡Listo! Ya tienes acceso a la documentación interactiva de toda la API.

---

## 📍 URLs Importantes

| Recurso | URL |
|---------|-----|
| **Swagger UI** (Interfaz interactiva) | `http://localhost:8080/swagger-ui.html` |
| **OpenAPI JSON** (Especificación) | `http://localhost:8080/v3/api-docs` |
| **OpenAPI YAML** (Especificación) | `http://localhost:8080/v3/api-docs.yaml` |

---

## 🧪 Probar Endpoints en Swagger UI

1. Expande la sección **"Rules"**
2. Haz click en cualquier endpoint (ej: POST /api/rules)
3. Haz click en **"Try it out"**
4. Completa los datos (si es necesario)
5. Haz click en **"Execute"**
6. ¡Ve la respuesta!

---

## 📚 Documentación Completa

Para más detalles, consulta:
- **OPENAPI.md**: Documentación detallada de OpenAPI
- **OPENAPI_SETUP.md**: Detalles técnicos de la implementación
- **postman/README.md**: Alternativa con Postman

---

## 🔧 Configuración

Ubicación del archivo de configuración:
```
src/main/resources/application.yaml
```

Sección OpenAPI:
```yaml
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
    operations-sorter: method
    tags-sorter: alpha
```

---

## ✨ Características

✅ Documentación automática desde anotaciones  
✅ Swagger UI - Interfaz web interactiva  
✅ Prueba endpoints directamente desde el navegador  
✅ Especificación OpenAPI 3.0  
✅ Completamente integrado con Spring Boot  

---

**¡A disfrutar de la documentación!** 🎉


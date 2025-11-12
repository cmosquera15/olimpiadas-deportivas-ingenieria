# Olimpiadas Deportivas Ingeniería

Sistema completo (frontend + backend) para la gestión de las Olimpiadas Deportivas de la Facultad de Ingeniería de la Universidad de Antioquia.

## Tabla de Contenido
- [Arquitectura General](#arquitectura-general)
- [Tecnologías Principales](#tecnologías-principales)
- [Características Funcionales](#características-funcionales)
- [Estructura del Repositorio](#estructura-del-repositorio)
- [Backend](#backend)
  - [Stack y Dependencias](#stack-y-dependencias-backend)
  - [Configuración](#configuración-backend)
  - [Seguridad y Autenticación](#seguridad-y-autenticación)
  - [Build y Ejecución](#build-y-ejecución-backend)
  - [Endpoints Principales](#endpoints-principales)
- [Frontend](#frontend)
  - [Stack y Dependencias](#stack-y-dependencias-frontend)
  - [Ejecución](#ejecución-frontend)
  - [Gestión de Estado y Datos](#gestión-de-estado-y-datos)
  - [Autorización en UI](#autorización-en-ui)
- [Roles y Permisos](#roles-y-permisos)
- [Flujos Críticos](#flujos-críticos)
  - [Creación de Olimpiada](#creación-de-olimpiada)
  - [Gestión de Equipos](#gestión-de-equipos)
  - [Registro de Partidos y Eventos](#registro-de-partidos-y-eventos)
- [Ambientes y Variables](#ambientes-y-variables)
- [Buenas Prácticas Implementadas](#buenas-prácticas-implementadas)
- [Mantenimiento y Futuras Mejores](#mantenimiento-y-futuras-mejoras)
- [Licencia](#licencia)

## Arquitectura General
El proyecto está dividido en dos módulos independientes:

```
root/
├─ backend-olimpiadas-deportivas-ingenieria/  (Spring Boot API REST)
└─ frontend-olimpiadas-deportivas-ingenieria/ (SPA React + Vite)
```

Comunicación vía HTTP/JSON. El frontend consume los endpoints `/api/...` del backend. El backend usa PostgreSQL como base de datos relacional y expone documentación OpenAPI (springdoc).

## Tecnologías Principales
- **Backend:** Java 21, Spring Boot 3.4, Spring Security, JPA/Hibernate, MapStruct, JWT, Google OAuth2 Client
- **Base de Datos:** PostgreSQL (Neon, configuración SSL requerida)
- **Frontend:** React 18 + TypeScript, Vite, TailwindCSS, Radix UI, @tanstack/react-query, Zustand, Axios

## Características Funcionales
- Gestión de Olimpiadas (crear, editar, activar/inactivar) con generación automática de torneos base (Fútbol y Baloncesto).
- Gestión de Torneos, Grupos, Fases y Jornadas (consulta pública). 
- Gestión de Equipos (creación restringida por permisos, asignación de jugadores, capitán, programas académicos). 
- Plantillas de equipos y candidatos filtrados por torneo.
- Registro y actualización de Partidos (marcador y resultados).
- Registro de Eventos dentro de cada partido (tipos de evento dependientes del deporte).
- Tabla de posiciones (cálculos consumidos desde API).
- Autenticación JWT + soporte de login con Google.
- Roles y permisos granulares embebidos en el token (ej: `Equipos_Crear`, `Partidos_Editar`).
- UI adaptativa y control de visibilidad según rol/permisos.

## Estructura del Repositorio
### Backend (Spring Boot)
```
backend-
  pom.xml
  src/main/java/com/ingenieria/olimpiadas/...
  src/main/resources/application.properties
```
### Frontend (React)
```
frontend-
  package.json
  src/
    components/
    pages/
    services/
    store/
    lib/ (axios, auth util)
    types/
```

## Backend
### Stack y Dependencias (Backend)
Incluye:
- `spring-boot-starter-data-jpa` (Persistencia)
- `postgresql` driver
- `spring-boot-starter-security` + oauth2 client (Google login)
- `springdoc-openapi-starter-webmvc-ui` (Swagger UI)
- `mapstruct` (mapeo de entidades a DTOs)
- `jjwt` (firma y validación de tokens JWT)

### Configuración (Backend)
Principal en `application.properties`:
- `spring.datasource.url` apunta a Neon PostgreSQL (sslmode=require)
- `spring.jpa.hibernate.ddl-auto=update` (migraciones automáticas en desarrollo)
- `jwt.secret`, `jwt.expiration-ms`, `jwt.issuer`
- Credenciales OAuth2 Google (client-id y secret)

> Nota: Para producción se recomienda extraer **todas** las credenciales y secretos a variables de entorno y desactivar `spring.jpa.show-sql`.

### Seguridad y Autenticación
- Filtro JWT personalizado (`JwtAuthFilter`) valida token, extrae roles y permisos.
- Roles representados como `ROLE_ADMINISTRADOR`, `ROLE_ARBITRO`, etc.
- Permisos adicionales como claims: `Equipos_Crear`, `Partidos_Editar`, etc.
- Endpoints públicos (GET) para catálogos: torneos, fases, grupos, jornadas, deportes, etc.

### Build y Ejecución (Backend)
Requisitos: JDK 21, Maven 3.9+

```powershell
# Desde la carpeta backend
mvn clean package
mvn spring-boot:run
```
Por defecto se levanta en `http://localhost:8080`.

### Endpoints Principales
(Resumen conceptual)
- `POST /api/olimpiadas` crear olimpiada + torneos base
- `GET /api/olimpiadas` listar
- `PUT /api/olimpiadas/{id}` actualizar
- `GET /api/torneos` listar torneos
- `GET /api/equipos` listar equipos (filtros por torneo/grupo)
- `POST /api/equipos` crear equipo (requiere permiso)
- `GET /api/equipos/{equipoId}/plantilla` listar plantilla
- `GET /api/equipos/{equipoId}/candidatos?torneoId=` candidatos por torneo
- `POST /api/equipos/{equipoId}/plantilla` agregar jugador
- `PATCH /api/equipos/{equipoId}/plantilla/{usuarioId}/estado` activar/inactivar jugador
- `POST /api/equipos/{equipoId}/capitan/{usuarioId}` asignar capitán
- `GET /api/partidos` listar partidos
- `PATCH /api/partidos/{id}/marcador` actualizar marcador (permiso requerido)
- `GET /api/eventos?partidoId=` listar eventos
- `POST /api/eventos` crear evento (permiso requerido)

## Frontend
### Stack y Dependencias (Frontend)
- React 18 + TS
- Vite (desarrollo rápido y build eficiente)
- TailwindCSS + Radix UI (componentes accesibles y estilizados)
- React Query (capa de fetching y caching) + Axios (HTTP)
- Zustand (estado de autenticación y usuario)
- Zod (validaciones opcionales en formularios)

### Ejecución (Frontend)
Requisitos: Node 18+

Variables esperadas (ejemplo en `.env`):
```
VITE_API_BASE_URL=http://localhost:8080/api
VITE_JWT_STORAGE_KEY=olimpiadas_jwt
VITE_GOOGLE_CLIENT_ID=<client-id-google>
```

```powershell
# Instalar dependencias
npm install
# Ejecutar en modo desarrollo
npm run dev
# Build producción
npm run build
# Previsualizar build
npm run preview
```

### Gestión de Estado y Datos
- **Autenticación:** token JWT almacenado en `localStorage` (sin prefijo `Bearer` interno). Helpers en `src/lib/auth.ts`.
- **React Query:** cada recurso define su propia `queryKey` (ej: `['equipos', torneoId]`). Invalidaciones tras mutaciones para consistencia.
- **Zustand:** store `useAuth` para usuario y limpieza de sesión.

### Autorización en UI
- Componentes condicionales según `hasPermission('Permiso')` y `getUserRole()`.
- Botones de creación/edición ocultos para roles sin privilegios (equipos, olimpiadas, partidos).

## Roles y Permisos
Ejemplos presentes en el token:
- Roles: `ROLE_ADMINISTRADOR`, `ROLE_ARBITRO`, `ROLE_JUGADOR` (implícito)
- Permisos: `Equipos_Crear`, `Equipos_Editar`, `Partidos_Editar`, `Torneos_Ver`, etc.

Reglas clave de negocio:
- Solo admin puede crear/editar olimpiadas.
- Solo usuarios con `Equipos_Crear` pueden crear equipos.
- Solo usuarios con `Partidos_Editar` pueden editar marcador y eventos de un partido.
- Árbitro no puede crear equipos.

## Flujos Críticos
### Creación de Olimpiada
1. Admin abre "Gestión de Olimpiadas".
2. Completa formulario (nombre, edición, año, activo).
3. Al confirmar se crean torneos base (Fútbol, Baloncesto) automáticamente.

### Gestión de Equipos
1. Usuario con permiso selecciona torneo y grupo.
2. Completa formulario con programas académicos.
3. Agrega jugadores desde candidatos filtrados por `torneoId`.

### Registro de Partidos y Eventos
1. Listado de partidos muestra datos generales (público).
2. Formulario de marcador visible solo para quien tiene permiso.
3. Registro de eventos (tipo + jugador) restringido según permiso.
4. Validaciones de marcador (ej: empate en baloncesto solo con W.O.).

## Ambientes y Variables
Backend (recomendado mover a entorno):
- `spring.datasource.*` credenciales DB
- `jwt.secret`, `jwt.expiration-ms`
- OAuth2 Google: client id y secret

Frontend:
- `VITE_API_BASE_URL` URL base del backend (`/api`)
- `VITE_JWT_STORAGE_KEY` clave de almacenamiento token
- `VITE_GOOGLE_CLIENT_ID` id de oauth Google

## Buenas Prácticas Implementadas
- Separación clara de capas (DTOs, mapeadores, servicios, controladores).
- Normalización de DTOs en frontend para abstraer nombres backend (`id_torneo` → `torneoId`).
- Invalidación granular de queries tras mutaciones.
- Mensajes de error consistentes (toasts) y feedback visual.
- Logs de depuración en frontend encapsulados en `import.meta.env.DEV` para no ensuciar producción.
- Seguridad por permisos en UI y backend (filtro JWT y authorities).

## Mantenimiento y Futuras Mejoras
- Migrar `ddl-auto=update` a un sistema de migraciones (Flyway/Liquibase) para producción.
- Añadir tests automatizados (unitarios y de integración) en backend y componentes críticos en frontend.
- Implementar cache HTTP (ETags / Cache-Control) para catálogos.
- Añadir métricas / observabilidad (Micrometer + Prometheus/Grafana).
- Implementar auditoría de cambios (quién creó/actualizó recursos).
- Subir gestión avanzada de torneos (fases automatizadas, generación de fixtures).
- Internacionalización (i18n) si se requiere multi-idioma.
- Hardening de seguridad: rotación de secretos JWT, rate limiting, CSP headers.

## Licencia
Proyecto interno académico. Definir licencia explícita si se abre al público (por ejemplo MIT o Apache 2.0).

---
**Creado por:** Camilo Andrés Mosquera Vega y Lizbeth Marcela Espinosa Mendoza - Estudiantes UdeA

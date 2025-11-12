# Olimpiadas Deportivas - Ingeniería UdeA

Sistema de gestión integral para las Olimpiadas Deportivas de la Facultad de Ingeniería de la Universidad de Antioquia.

## 🚀 Características

- **Autenticación con Google OAuth** - Inicio de sesión seguro con cuentas de Google
- **Gestión de Torneos** - Crear, consultar y administrar torneos deportivos
- **Programación de Partidos** - Calendario de partidos con validación de conflictos
- **Gestión de Equipos** - Administración de equipos y plantillas de jugadores
- **Tabla de Posiciones** - Posiciones en tiempo real con estadísticas completas
- **Sistema de Eventos** - Registro de eventos durante los partidos (tarjetas, goles, etc.)
- **Fair Play** - Seguimiento del juego limpio con cálculo automático
- **Roles y Permisos** - Sistema de roles (Administrador, Árbitro, Jugador)
- **Administración de Usuarios** - Gestión de usuarios y permisos (solo Admin)
- **Edición de Perfil (Self)** - Cada usuario puede actualizar su propio perfil (programa, género, EPS, tipo vínculo, foto)
- **Edición de Perfil (Admin)** - El Administrador puede editar el perfil de cualquier usuario mediante un diálogo contextual

## 🛠️ Tecnologías

- **Frontend**: React 18 + TypeScript + Vite
- **UI Framework**: TailwindCSS + shadcn/ui
- **Routing**: React Router v6 con guards de autenticación
- **State Management**: Zustand + React Query
- **HTTP Client**: Axios con interceptores
- **Forms**: React Hook Form + Zod
- **Date Handling**: date-fns + date-fns-tz (timezone America/Bogota)
- **Icons**: Lucide React

## 📋 Requisitos Previos

- Node.js 18+ y npm
- Cuenta de Google Cloud Platform (para OAuth)
- Backend API configurado y funcionando

## 🔧 Configuración

### 1. Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_GOOGLE_CLIENT_ID=tu-google-client-id
VITE_JWT_STORAGE_KEY=olimpiadas_jwt
```

### 2. Configurar Google OAuth

1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Habilita la API de Google+ 
4. Crea credenciales OAuth 2.0:
   - Tipo: Aplicación web
   - Orígenes autorizados: `http://localhost:5173` (desarrollo)
   - URIs de redirección autorizados: `http://localhost:5173/auth/login`
5. Copia el Client ID a tu archivo `.env`

### 3. Instalar Dependencias

```bash
npm install
```

### 4. Ejecutar en Desarrollo

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:8080`

## 🏗️ Estructura del Proyecto

```
src/
├── components/
│   ├── Layout/           # AppLayout, AppSidebar
│   └── ui/              # Componentes shadcn/ui + Guard
├── hooks/               # Hooks personalizados (useCatalogos, etc.)
├── lib/                 # Utilidades (axios, auth, date)
├── pages/
│   ├── auth/           # Login, CompletarPerfil
│   ├── torneos/        # ListadoTorneos, DetalleTorneo
│   ├── partidos/       # ListadoPartidos
│   ├── equipos/        # ListadoEquipos
│   ├── posiciones/     # TablaPosiciones
│   ├── admin/          # Usuarios
│   └── errors/         # Forbidden, NotFound
├── routes/             # ProtectedRoute (guards)
├── services/           # API services (axios)
├── store/              # Zustand stores (useAuth)
└── types/              # TypeScript types
```

## 👥 Roles y Permisos

### Administrador
- Acceso completo a todas las funcionalidades
- Gestión de usuarios y roles
- Generar llaves de torneos
- Crear y editar partidos

### Árbitro
- Ver y editar partidos asignados
- Registrar eventos y marcadores
- Consultar torneos, equipos y posiciones
- Generar llaves de torneos (con permiso Partidos_Crear)

### Jugador
- Ver torneos, partidos, equipos y posiciones
- Consultar información de su equipo
- Acceso de solo lectura

## ✏️ Edición de Perfiles

### Flujo Usuario (Self)
El usuario autenticado puede actualizar su información desde la página `Perfil`:
- Campos soportados: nombre, documento (solo si no existía), programa académico, género, EPS, tipo vínculo, foto.
- El documento es inmutable una vez establecido para preservar la integridad histórica.
- Sólo se envían al backend los campos modificados.

### Flujo Administrador
En `Admin > Usuarios` cada fila incluye el botón "Editar Perfil" que abre un diálogo con campos opcionales:
- El administrador puede ajustar nombre y los catálogos (programa, género, EPS, tipo vínculo) y establecer foto.
- El documento solo puede agregarse si el usuario aún no tiene uno (el backend rechaza cambios posteriores).
- Al guardar: se hace `PUT /admin/usuarios/{id}/perfil` y se invalida la caché de la lista.
- Si no se modifica un campo, no se envía en el payload.

### Consideraciones
- Validar siempre que la URL de foto sea accesible (idealmente CDN confiable).
- Las selecciones "(Sin cambios)" mantienen el valor existente en backend.
- Errores de validación se muestran como toasts amigables.

## 🗄️ Mantenimiento BD (Administrativo)
Si se realizan inserciones manuales en tablas con columnas autoincrement, verifique que las secuencias no queden desfasadas. Ejemplo (PostgreSQL):
```sql
SELECT setval('equipo_id_seq', (SELECT MAX(id) FROM equipo));
```
Mantener las secuencias sincronizadas evita errores de llave primaria duplicada en creación de entidades.

## 🔐 Flujo de Autenticación

1. Usuario hace clic en "Iniciar sesión con Google"
2. Se obtiene el token de Google y se envía al backend
3. El backend valida el token y retorna un JWT
4. Si `completo === false`, se redirige a completar perfil
5. Usuario completa información adicional (documento, EPS, programa, etc.)
6. Se redirige al Dashboard según el rol del usuario

## 📊 Reglas de Negocio

### Partidos
- No se pueden programar en el pasado (timezone America/Bogota)
- Validación de conflictos: mismo torneo, lugar, fecha y hora
- En Baloncesto: no se permiten empates salvo W.O.

### Marcador
- Solo árbitros pueden actualizar marcadores
- Requiere validación de resultados especiales (W.O.)

### Eventos
- Solo jugadores del partido pueden tener eventos
- Los eventos impactan el cálculo de Fair Play
- Puntos negativos según tipo de evento

### Posiciones
- Cálculo automático de estadísticas (PJ, PG, PE, PP, GF, GC, GD, PTS)
- Fair Play = Suma puntos negativos / Partidos jugados
- Criterios de desempate: Puntos → GD → GF → Fair Play

### Generación de Llaves
- Solo usuarios con permiso `Partidos_Crear`
- Requiere torneo activo
- Genera automáticamente los partidos según reglamento

## 🎨 Personalización

### Colores del Sistema

El sistema usa los colores institucionales de la UdeA:

- **Primary** (#0A7D71): Verde institucional
- **Secondary** (#93C53C): Verde claro (CTAs)
- **Contrast** (#803E8A): Púrpura (highlights)

Personaliza en `src/index.css` y `tailwind.config.ts`

## 🚢 Deployment

### Build para Producción

```bash
npm run build
```

Los archivos optimizados se generarán en el directorio `dist/`

### Variables de Entorno en Producción

Asegúrate de configurar:
- `VITE_API_BASE_URL`: URL del backend en producción
- `VITE_GOOGLE_CLIENT_ID`: Client ID de producción de Google
- Actualizar orígenes autorizados en Google Cloud Console

## 📝 Notas Importantes

### Zona Horaria
- Todos los cálculos de fecha/hora usan `America/Bogota`
- Formato de fecha: `dd/MM/yyyy HH:mm`
- Locale: Español Colombia (es-CO)

### Endpoints del Backend
El sistema consume los endpoints exactos especificados en el documento de requerimientos. No modifiques las rutas de la API sin actualizar también el backend.

### Persistencia del Token
El JWT se almacena en `localStorage` con la clave configurada en `VITE_JWT_STORAGE_KEY`. El token se adjunta automáticamente a todas las peticiones mediante interceptores de Axios.

### Manejo de Errores
- 401: Redirección automática a login
- 403: Página de acceso denegado
- 404: Página no encontrada
- Otros errores: Toasts informativos

## 🤝 Contribuir

Este es un proyecto académico para la Universidad de Antioquia. Para contribuir:

1. Clona el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es propiedad de la Universidad de Antioquia - Facultad de Ingeniería.

## 📞 Soporte

Para soporte o preguntas sobre el sistema, contacta al equipo de desarrollo.

---

Desarrollado con ❤️ para la Facultad de Ingeniería UdeA

# 🎮 Sistema de Gestión de Videojuegos y Desarrolladoras

Sistema CRUD en Java con persistencia en archivos CSV para la gestión de videojuegos y sus desarrolladoras.

## 📋 Características

- **Vinculación entre entidades** mediante ID de desarrolladora
- **Búsqueda parametrizada**: por título, género, nombre de desarrolladora e ID
- **Búsqueda combinada**: videojuegos por nombre de desarrolladora
- **Validación robusta** de todas las entradas del usuario
- **Interfaz de consola intuitiva** con menús anidados y mensajes claros
  
## 📂 Estructura del Proyecto
src/
├── Main.java
├── Gestion/
│ └── GestorDatos.java
├── Menus/
│ ├── MenuVideojuegos.java
│ └── MenuDesarrolladoras.java
├── Entidades/
│ ├── Videojuego.java
│ └── Desarrolladora.java
└── Excepciones/
├── VideojuegoNoEncontradoException.java
└── DesarrolladoraNoEncontradaException.java

## 🎮 Funcionalidades
### Menú Principal
- Gestionar Videojuegos
- Gestionar Desarrolladoras
- Salir del programa

### Menú de Videojuegos
- Listar todos los videojuegos 
- Añadir nuevo videojuego
- Buscar videojuegos (submenú):
  - Por título (búsqueda parcial)
  - Por género (búsqueda parcial)
  - Por nombre de desarrolladora (búsqueda combinada)
  - Por ID
- Modificar videojuego
- Eliminar videojuego

### Menú de Desarrolladoras
- Listar todas las desarrolladoras
- Añadir nueva desarrolladora
- Buscar desarrolladoras (submenú):
  - Por nombre (búsqueda parcial)
  - Por ID
- Modificar desarrolladora
- Eliminar desarrolladora (bloqueada si tiene videojuegos asociados)

## 🔒 Validaciones

- **Texto**
- **Año**: Entre 1950 y año actual + 1
- **ID**: Número entero válido
- **ID de Desarrolladora**: Debe existir (se muestra lista de disponibles)
- **Integridad referencial**: No se puede eliminar desarrolladora con videojuegos asociados

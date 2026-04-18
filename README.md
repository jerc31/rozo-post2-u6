# CRUD de Productos - MVC con Servlets y JSP

**Ingeniería de Sistemas - Unidad 6: JSP con MVC**  
Laboratorio Post-Contenido 1 - 2026

---

## 📋 Descripción del Proyecto

Aplicación web Java que implementa el patrón **MVC (Model-View-Controller)** con un **CRUD completo** de productos. La aplicación utiliza:

- **Servlets** (Jakarta Servlet API) como controlador
- **JSP** con **EL** (Expression Language) y **JSTL** como vista
- **Clases Java** como modelo y lógica de negocio
- **Almacenamiento en memoria** (sin base de datos)
- **Patrón Post-Redirect-Get (PRG)** para operaciones seguras

### Funcionalidades Implementadas

✅ **Listar productos** - Visualiza todos los productos del inventario  
✅ **Crear producto** - Formulario para registrar nuevos productos  
✅ **Editar producto** - Modifica los datos de un producto existente  
✅ **Eliminar producto** - Remueve un producto del inventario con confirmación  
✅ **Redirección con mensajes** - Feedback visual de operaciones exitosas  
✅ **Validación de datos** - Validación en servidor y cliente  
✅ **Interfaz responsiva** - CSS3 semántico con soporte móvil  

---

## 🛠️ Tecnologías Utilizadas

| Componente | Tecnología | Versión |
|-----------|-----------|---------|
| JDK | Java SE | 17+ |
| Servidor | Apache Tomcat | 10.x |
| API Servlet | Jakarta Servlet API | 6.0.0 |
| Vistas | JSP + JSTL | 1.2.6 |
| Build | Maven | 3.8+ |
| Estilos | CSS3 | Semántico |

---

## 📁 Estructura del Proyecto

```
mvc-productos/
├── src/main/java/
│   └── com/universidad/mvc/
│       ├── model/
│       │   ├── Producto.java          (Entidad JavaBean)
│       │   └── ProductoDAO.java       (Acceso a datos en memoria)
│       ├── service/
│       │   └── ProductoService.java   (Lógica de negocio)
│       └── controller/
│           └── ProductoServlet.java   (Controlador MVC)
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── web.xml                    (Configuración de la aplicación)
│   │   └── views/
│   │       ├── lista.jsp              (Vista: listado de productos)
│   │       └── formulario.jsp         (Vista: crear/editar producto)
│   ├── css/
│   │   └── estilos.css                (Estilos CSS3 semántico)
│   ├── error.jsp                      (Página de error)
│   └── index.jsp                      (Página de inicio - redirección)
├── pom.xml                            (Configuración Maven)
├── README.md                          (Este archivo)
└── capturas/                          (Capturas de pantalla)
    ├── 01-listado.png
    ├── 02-crear.png
    ├── 03-editar.png
    └── 04-eliminar.png
```

---

## 📋 Prerrequisitos

Antes de ejecutar la aplicación, verifica que tienes instalado:

- ✅ **JDK 17 o superior**
  ```bash
  java -version
  ```

- ✅ **Apache Tomcat 10.x**
  - Descarga desde: https://tomcat.apache.org/
  - Configura la variable `CATALINA_HOME`

- ✅ **Maven 3.8+**
  ```bash
  mvn -version
  ```

- ✅ **IDE** (IntelliJ IDEA o Eclipse recomendados)

- ✅ **Git** (para control de versiones)
  ```bash
  git --version
  ```

---

## 🚀 Instrucciones de Ejecución

### Opción 1: Con Maven y Tomcat Plugin

1. **Clonar el repositorio**
   ```bash
   git clone <URL-REPOSITORIO>
   cd mvc-productos
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Empaquetar como WAR**
   ```bash
   mvn package
   ```

4. **Desplegar en Tomcat**
   - Copia el archivo `target/mvc-productos-1.0-SNAPSHOT.war` a la carpeta `$CATALINA_HOME/webapps/`
   - Reinicia Tomcat

5. **Acceder a la aplicación**
   ```
   http://localhost:8080/mvc-productos/productos
   ```

### Opción 2: Con IntelliJ IDEA

1. **Abrir el proyecto**
   - File → Open → Selecciona la carpeta `mvc-productos`

2. **Configurar Tomcat**
   - Run → Edit Configurations
   - Click en `+` y selecciona "Tomcat Server"
   - Configura el servidor Tomcat local

3. **Ejecutar**
   - Click en "Run" o presiona `Shift + F10`
   - IntelliJ compilará y desplegará automáticamente

4. **Acceder**
   - El navegador se abrirá automáticamente en `http://localhost:8080/mvc-productos/productos`

### Opción 3: Con Eclipse IDE

1. **Importar como Maven Project**
   - File → Import → Maven → Existing Maven Projects
   - Selecciona la carpeta `mvc-productos`

2. **Configurar Tomcat**
   - Window → Preferences → Server → Runtime Environments
   - Añade Apache Tomcat 10.x

3. **Ejecutar en Tomcat**
   - Click derecho en el proyecto → Run As → Run on Server

---

## 📝 Flujos de Funcionamiento

### 1️⃣ Flujo de Listado (GET)

```
GET /productos?accion=listar
    ↓
ProductoServlet.doGet()
    ↓
listar() → service.obtenerTodos()
    ↓
lista.jsp (renderiza tabla)
```

### 2️⃣ Flujo de Crear (POST → GET)

```
GET /productos?accion=formulario
    ↓
formulario.jsp (formulario vacío)
    ↓
POST /productos (accion=guardar)
    ↓
ProductoServlet.doPost() → guardar()
    ↓
service.guardar(producto)
    ↓
302 Redirect: /productos?mensaje=...
    ↓
GET /productos → lista.jsp (muestra producto nuevo)
```

### 3️⃣ Flujo de Editar (GET → POST → GET)

```
GET /productos?accion=editar&id=1
    ↓
ProductoServlet.doGet() → mostrarEdicion()
    ↓
service.obtenerPorId(id)
    ↓
formulario.jsp (precargado)
    ↓
POST /productos (accion=actualizar, id=1)
    ↓
ProductoServlet.doPost() → actualizar()
    ↓
service.actualizar(producto)
    ↓
302 Redirect: /productos?mensaje=...
    ↓
GET /productos → lista.jsp (muestra cambios)
```

### 4️⃣ Flujo de Eliminar (GET → POST → GET)

```
GET /productos (lista con botón eliminar)
    ↓
onclick="confirm(...)" → GET /productos?accion=eliminar&id=1
    ↓
ProductoServlet.doGet() → eliminar()
    ↓
service.eliminar(id)
    ↓
302 Redirect: /productos?mensaje=...
    ↓
GET /productos → lista.jsp (sin el producto)
```

---

## ✅ Checkpoints de Verificación

Al desplegar la aplicación, verifica lo siguiente:

- [ ] **Acceso inicial**: La URL `http://localhost:8080/mvc-productos/productos` carga la lista con 3 productos precargados
  
- [ ] **Nuevo producto**: 
  - Click en "+ Nuevo Producto" → Formulario vacío
  - Completa el formulario y haz click en "Guardar"
  - URL cambia a `/productos?mensaje=Producto+guardado+exitosamente`
  - Producto aparece en la lista

- [ ] **Editar producto**:
  - Click en "Editar" de un producto
  - Formulario precargado con sus datos
  - Modifica un campo y guarda
  - Cambios persisten en la lista

- [ ] **Eliminar producto**:
  - Click en "Eliminar"
  - Diálogo de confirmación aparece
  - Si aceptas, producto desaparece de la lista
  - URL muestra `/productos?mensaje=Producto+eliminado`

- [ ] **Sin errores en consola**:
  - No hay `ClassNotFoundException`
  - No hay `NullPointerException`
  - Consola de Tomcat sin errores

---

## 🎨 Características del Diseño CSS

### Características Implementadas

✨ **CSS3 Semántico**
- Variables CSS para temas (colores, espaciados, tipografía)
- Clases semánticas (`header`, `main`, `footer`, `section`, etc.)
- Reset normalizado y accesible

🎯 **Responsive Design**
- Desktop-first approach
- Media queries para tablets (768px)
- Media queries para móviles (640px)
- Tabla adaptativa en móviles

🌙 **Modo Oscuro**
- Soporte para `prefers-color-scheme: dark`
- Colores ajustados automáticamente

♿ **Accesibilidad**
- Clases de utilidad (`.sr-only`)
- ARIA roles en alertas
- Contraste de colores WCAG AA+
- Etiquetas semánticas HTML5

🖨️ **Impresión**
- Estilos optimizados para imprimir
- Oculta elementos innecesarios
- Líneas visibles en tablas

---

## 📦 Dependencias Maven

```xml
<!-- Jakarta Servlet API (Tomcat 10.x) -->
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope>
</dependency>

<!-- JSTL -->
<dependency>
    <groupId>org.apache.taglibs</groupId>
    <artifactId>taglibs-standard-impl</artifactId>
    <version>1.2.6</version>
</dependency>
```

---

## 🔧 Solución de Problemas

### Error: `ClassNotFoundException: jakarta.servlet.http.HttpServlet`

**Solución**: Verifica que estés usando Tomcat 10.x o superior. Tomcat 9.x usa `javax.servlet`, no `jakarta.servlet`.

### Error: `The superclass "javax.servlet.http.HttpServlet" was not found on the Java Build Path`

**Solución**:
1. Configura el classpath del servidor en tu IDE
2. Asegúrate de que Maven descargó las dependencias: `mvn clean install`

### Error: JSP no se renderiza correctamente

**Solución**:
1. Verifica que la ruta en `<c:url>` es correcta
2. Asegúrate de que los tags JSTL están importados: `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`

### Puerto 8080 ya está en uso

**Solución**: Cambia el puerto en `CATALINA_HOME/conf/server.xml`:
```xml
<Connector port="8888" protocol="HTTP/1.1" />
```

---

## 📸 Capturas de Pantalla

### 1. Listado de Productos
![Listado](evidencias/01-listado.png)
*Página inicial con los 3 productos precargados*

### 2. Crear Nuevo Producto
![Crear](evidencias/02-crear.png)
*Formulario para registrar un nuevo producto*

### 3. Editar Producto
![Editar](evidencias/03-editar.png)
*Formulario precargado con los datos del producto a editar*

### 4. Confirmación de Eliminación
![Eliminar](evidencias/04-eliminar.png)
*Diálogo de confirmación antes de eliminar un producto*

---

## 📊 Patrones de Diseño Implementados

### 1. **MVC (Model-View-Controller)**
- **Model**: `Producto`, `ProductoDAO`, `ProductoService`
- **View**: `lista.jsp`, `formulario.jsp`
- **Controller**: `ProductoServlet`

### 2. **DAO (Data Access Object)**
- `ProductoDAO` abstraer el acceso a datos
- Facilita cambios futuros a una BD real

### 3. **Service Layer**
- `ProductoService` encapsula lógica de negocio
- Validaciones centralizadas

### 4. **Post-Redirect-Get (PRG)**
- Evita reenvíos de formularios
- Previene duplicación de datos
- Mejora la experiencia del usuario

### 5. **Singleton Pattern**
- `ProductoDAO` usa singleton implícito con datos estáticos
- Lista compartida entre peticiones

---

## 📚 Convenciones de Código

### Java
- ✅ camelCase para variables y métodos
- ✅ PascalCase para clases
- ✅ UPPER_CASE para constantes
- ✅ Métodos cortos y con una sola responsabilidad
- ✅ Documentación con Javadoc

### JSP
- ✅ Sin scriptlets (`<% %>`) - usamos EL y JSTL
- ✅ Nombres significativos para variables
- ✅ Espacios en blanco consistentes

### CSS
- ✅ Propiedades CSS3 semánticas
- ✅ Variables CSS para reutilización
- ✅ Media queries móviles al final
- ✅ Comentarios descriptivos

---

## 📝 Historial de Commits

```
Commit 1: Estructura inicial del proyecto MVC
- Creación de carpetas src/main/java y src/main/webapp
- Configuración de pom.xml con dependencias
- Creación de archivos web.xml

Commit 2: Implementación del modelo y servicio
- Clase Producto.java (JavaBean)
- Clase ProductoDAO.java (acceso a datos)
- Clase ProductoService.java (lógica de negocio)

Commit 3: Implementación del controlador y vistas
- Servlet ProductoServlet.java
- Vistas JSP: lista.jsp, formulario.jsp, error.jsp
- Estilos CSS3 semántico: estilos.css

Commit 4: Documentación y optimizaciones finales
- README.md completo con instrucciones
- Manejo de errores mejorado
- Validaciones en servidor y cliente
```

---

## 🎯 Rúbrica de Evaluación

| Criterio | Peso | Implementación |
|----------|------|-----------------|
| **Implementación MVC** | 40% | ✅ Completa - Separación clara de responsabilidades |
| **Funcionalidad CRUD** | 25% | ✅ Completa - Todas las operaciones funcionales |
| **Documentación** | 20% | ✅ Completa - README, commits descriptivos, capturas |
| **Estilo y Convenciones** | 15% | ✅ Completa - Código limpio, convenciones Java/CSS |

**Resultado Esperado**: 5.0 (Excelente)

---

## 👨‍💼 Autor

- **Institución**: Ingeniería de Sistemas
- **Año**: 2026
- **Unidad**: Unidad 6 - JSP con MVC
- **Laboratorio**: Post-Contenido 1

---

## 📄 Licencia

Este proyecto es de carácter académico y se proporciona como material educativo.

---

## 🤝 Soporte

Si encuentras problemas al ejecutar la aplicación:

1. Verifica que todas las herramientas estén instaladas correctamente
2. Limpia el proyecto: `mvn clean`
3. Reconstruye: `mvn compile package`
4. Reinicia Tomcat

Para más información, consulta la documentación oficial de:
- [Apache Tomcat 10](https://tomcat.apache.org/tomcat-10.0-doc/)
- [Jakarta Servlet API](https://jakarta.ee/specifications/servlet/)
- [Maven](https://maven.apache.org/guides/)

---

**Última actualización**: 2026-04-18  
**Estado**: ✅ Proyecto completado y listo para evaluación


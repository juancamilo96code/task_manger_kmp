# task_manger_kmp

# ✨ Kotlin Multiplatform Project – Android & Desktop ✨

## 🧠 Arquitectura y Estructura de Proyecto

El proyecto está basado en el enfoque de **Clean Architecture**, con una separación clara en capas que garantizan la mantenibilidad y testabilidad del código. Las capas implementadas son:

- **Presentation**: Contiene la lógica de presentación en torno a `ViewModels`, `UiState` y `Intents`.
- **Domain**: Se define el núcleo de la aplicación, con `UseCases`, entidades de negocio y abstracciones del `Repository`.
- **Data**: Implementación de los repositorios y fuentes de datos (local).
- **DI (Dependency Injection)**: Configuración de los módulos de inyección utilizando **Koin**, de forma multiplataforma.

---

## 🧰 Patrón Arquitectónico

El patrón arquitectónico principal adoptado es **MVVM (Model-View-ViewModel)**, donde cada `ViewModel` orquesta la lógica de presentación y se comunica con el dominio mediante `UseCases`.

Adicionalmente, se adoptó una aproximación **inspirada en MVI** para la gestión de estado, a través de dos conceptos clave:

- `UiState`: Representa el estado inmutable de la vista.
- `Intent`: Define las intenciones del usuario que son manejadas por el `ViewModel`.

Esta combinación permite una arquitectura reactiva, predecible y desacoplada, elevando la experiencia de desarrollo y depuración.

---

## 🌍 Multiplataforma y Recursos

El proyecto aprovecha el mecanismo `expect / actual` de Kotlin para resolver comportamientos específicos por plataforma. En particular:

- **Ubicación, Strings y recursos gráficos (SVG/PNG)**: Se gestionan de forma centralizada pero con implementación diferenciada en cada plataforma.
- Se respetan las convenciones y capacidades de cada entorno (Android y Desktop), sin sacrificar la reutilización.

---

## 💉 Inyección de Dependencias

Se utilizó **Koin** como solución para la inyección de dependencias. Su integración se hace de forma multiplataforma, permitiendo una construcción flexible y desacoplada de los módulos del sistema, tanto en `commonMain` como en `androidMain` y `desktopMain`.

---

## 📦 Librerías Utilizadas

### 🔁 Comunes (`commonMain`)
| Tipo | Librería |
|------|----------|
| UI | Compose (`runtime`, `foundation`, `material`, `ui`) |
| UI Multiplataforma | `compose.components.resources`, `compose.uiToolingPreview` |
| Arquitectura | `androidx.lifecycle.viewmodel`, `androidx.lifecycle.runtime.compose` |
| DI | `koin.core`, `koin.compose`, `koin.compose.viewmodel` |
| Navegación | `voyager.navigator`, `voyager.koin`, `voyager.bottom.sheet.navigator` |
| Serialización | `kotlinx.serialization.json` |
| Tiempo | `kotlinx.datetime` |
| DB | `sqldelight.coroutines` |

---

### 🤖 Android (`androidMain`)
| Tipo | Librería |
|------|----------|
| UI | `compose.preview`, `androidx.activity.compose` |
| DI | `koin.android` |
| DB | `sqldelight.android` |
| Permisos | `accompanist.permissions` |
| Localización | `play-services-location` |

---

### 🖥️ Desktop (`desktopMain`)
| Tipo | Librería |
|------|----------|
| UI | `compose.desktop.currentOs` |
| Concurrencia | `kotlinx.coroutines.swing` |
| DB | `sqldelight.desktop` |

---

## 🚀 Ejecución del Proyecto

Cada plataforma tiene su punto de entrada, pero el core de la lógica (casos de uso, repositorios, entidades, navegación, viewmodels) está definido en `commonMain`. Esto permite un desarrollo verdaderamente multiplataforma, con la mínima duplicación de código.

- Para **Android**: puedes correr directamente el módulo desde Android Studio o en la terminal ejecutar el comando ./gradlew :composeApp:assembleDebug :composeApp:installDebug en la terminal 
- Para **Desktop**: ejecuta la función `main()` en el módulo Desktop, compatible con JVM (Linux, macOS, Windows) o en la vista lateral de gradle vas a Task Manager ->composeApp ->composeDesktop->run

---

## 🔒 Notas Finales

Este proyecto demuestra cómo Kotlin Multiplatform permite construir aplicaciones multiplataforma modernas y escalables, manteniendo una arquitectura robusta y modular. Es ideal para equipos que buscan una base sólida para productos Android y Desktop, y puede escalar a otras plataformas como iOS o Web.


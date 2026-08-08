# GastoFoto — Registro de Gastos con Recibos 📸

GastoFoto es una aplicación Android moderna diseñada para ayudar a los usuarios a llevar un control detallado de sus finanzas personales. La aplicación permite registrar gastos, categorizarlos, añadir notas y, lo más importante, capturar una fotografía del recibo físico para tener un respaldo visual.

## 🚀 Requisitos Técnicos Cumplidos

Esta aplicación ha sido desarrollada siguiendo estrictamente los requisitos técnicos del curso:

1.  **UI y Navegación (Jetpack Compose):**
    *   Mínimo de 3 pantallas: Lista de Gastos, Nuevo Gasto (Detalle) y Ajustes.
    *   Uso de `LazyColumn` para la visualización eficiente de la lista.
    *   Carga de imágenes optimizada con la librería **Coil**.
2.  **Arquitectura (MVVM + Repositorio):**
    *   Separación clara en capas: **UI** (Compose/ViewModel), **Domain** (Models) y **Data** (Repository/Local/Remote).
    *   Uso de `StateFlow` y Corrutinas para una reactividad fluida.
3.  **Persistencia Local:**
    *   **Room:** Base de datos estructurada para el almacenamiento permanente de los gastos y sus rutas de imagen.
    *   **DataStore:** Persistencia de preferencias del usuario (Modo Oscuro y Moneda Preferida).
4.  **Consumo de API Remota (Retrofit):**
    *   Integración con la API de tipos de cambio (`open.er-api.com`).
    *   Manejo de estados de UI: **Cargando, Éxito y Error**.
5.  **Hardware y Permisos:**
    *   Uso de la **Cámara** para capturar recibos.
    *   Solicitud de permisos en tiempo de ejecución con manejo de denegación (Snackbar informativo).
6.  **Despliegue:**
    *   Configuración de ProGuard para optimización.
    *   Generación de archivo `.aab` firmado.

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Kotlin
*   **UI:** Jetpack Compose
*   **Base de Datos:** Room
*   **Preferencias:** DataStore
*   **Red:** Retrofit + Gson
*   **Imágenes:** Coil
*   **Navegación:** Navigation Compose
*   **Arquitectura:** MVVM + Patrón Repositorio

## 📦 Estructura del Proyecto

```text
com.example.gastofoto
├── data
│   ├── local      # Room Database, DAOs, Entities, DataStore
│   ├── remote     # API Service, Retrofit Instance, DTOs
│   └── repository # Implementación del Repositorio
├── domain
│   └── model      # Modelos de dominio (Gasto)
├── ui
│   ├── navigation # Configuración de rutas
│   ├── screens    # Pantallas (Lista, Detalle, Ajustes)
│   ├── theme      # Configuración de Material 3
│   └── viewmodel  # Lógica de negocio (GastoViewModel)
└── GastoFotoApplication.kt # Punto de entrada y DI manual
```

---
*Desarrollado para el Proyecto Final de Aplicaciones Móviles.*

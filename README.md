# HabitTrackerCLI: Aplicación de Línea de Comandos para el Seguimiento de Hábitos

## Descripción General

HabitTrackerCLI es una aplicación de línea de comandos diseñada para ayudarte a registrar y gestionar tus hábitos diarios. Ofrece funcionalidades para añadir, listar, completar, eliminar y ver estadísticas de tus hábitos, todo a través de una interfaz de texto sencilla e intuitiva.

## Arquitectura

La aplicación se basa en una arquitectura en capas, que promueve la modularidad y la separación de responsabilidades. Los componentes principales son:

*   **Capa de Dominio (`cli.domain`):** Contiene la lógica de negocio y las entidades principales. Esta capa es independiente de cualquier infraestructura.
    *   **Servicios:** Define las operaciones de negocio (por ejemplo, `HabitFinder`, `HabitStatisticsCalculator`).
    *   **Casos de Uso:** Encapsula flujos de trabajo específicos de la aplicación (por ejemplo, `AddHabitUseCase`, `CompleteHabitUseCase`).
    *   **Entidades:** Representa los conceptos centrales (por ejemplo, `Habit`).
    *   **Excepciones:** Define excepciones personalizadas para errores específicos del dominio.
    * **Utils**: Funciones de utilidad como `Clock`
    * **Handlers**: Manejadores de excepciones como `HabitErrorHandler`
*   **Capa de Infraestructura (`cli.infrastructure`):** Gestiona aspectos externos e implementaciones concretas.
    *   **UI (`cli.infrastructure.ui`):** Gestiona la interacción con el usuario y la presentación.
        *   **Acciones:** Clases que implementan comandos específicos (por ejemplo, `AddHabitAction`, `CompleteHabitAction`). Cada acción implementa la interfaz `Action`.
        *   **Menu:** Gestiona el menú interactivo para el usuario.
        *   **Presentadores:** Se encargan de formatear y presentar los datos al usuario (por ejemplo, `ConsoleMenuPresenter`).
        * **MenuInteractor**: Se encarga de la interaccion del menu con el usuario
        * **MenuConfiguration**: Se encarga de crear y configurar el menu con sus acciones.
        * **Utils:** Clases de utilidad como `ConsoleManager`.
        * **Selector**: Clases que ayudan a seleccionar como `HabitSelector`
        * **Exception Handlers**: Implementaciones para manejar excepciones como `UIErrorHandler`
        * **`MenuOptionInterface`**: Interfaz que deberan implementar las acciones que se muestran en el menu
    *   **Persistencia (`cli.infrastructure.persistence`):** Responsable del almacenamiento y recuperación de datos.
        *   **Repositorios:** Proporciona una abstracción para el acceso a los datos (por ejemplo, `HabitRepositoryImpl`).
    * **Utils**: Clases de utilidad como `Messages`
*   **`Main.kt`:** El punto de entrada de la aplicación. Configura y arranca la aplicación, conectando las distintas capas y componentes.
*   **`messages.properties`:** Archivo con las traducciones de los mensajes de la aplicación.

## Principios SOLID en Práctica

La arquitectura de HabitTrackerCLI se ha diseñado teniendo en cuenta los principios SOLID. Aquí se explica cómo se aplica cada principio:

### 1. Principio de Responsabilidad Única (SRP)

*   **Cada clase tiene una única responsabilidad bien definida.** Por ejemplo:
    *   `ConsoleManager`: Se encarga únicamente de la entrada y salida de datos a la consola.
    *   `ConsoleMenuPresenter`: Formatea el menú para su visualización.
    *   `AddHabitUseCase`: Solo se ocupa de añadir un nuevo hábito.
    *   `HabitRepositoryImpl`: Solo se ocupa de persistir los datos de los habitos.
    * `HabitErrorHandler`: Maneja unicamente las excepciones de los habitos.
    * `UIErrorHandler`: Maneja unicamente las excepciones de la UI.
    * `MenuConfiguration`: Solo configura el menu.
    * `MenuInteractor`: Solo interacciona con el usuario en el menu.
    * `HabitSelector`: Solo selecciona un habito.
    * `ExitMenuOption`: Solo ejecuta la accion de salir.
*   **Beneficios:**
    *   **Mantenibilidad:** Es más fácil entender, modificar y probar componentes individuales.
    *   **Reducción de la complejidad:** El código está más enfocado y es menos propenso a errores.
    * **Modularidad**: El codigo esta dividido en modulos con responsabilidades claras.

### 2. Principio de Abierto/Cerrado (OCP)

*   **La aplicación está abierta a la extensión, pero cerrada a la modificación.**
    *   **Acciones:** Se pueden añadir nuevas acciones sin modificar el código existente de `Menu` o `HabitTrackerController`. Simplemente se crea una nueva clase que implemente la interfaz `Action` y se añade a la configuracion del `MenuConfiguration`.
    *   **Opciones del Menú:** Añadir una nueva opción al menú se hace creando una nueva `Action` que implemente `MenuOptionInterface` y añadiéndola en la configuracion del menu, sin tocar el código ya existente.
*   **Beneficios:**
    *   **Extensibilidad:** Añadir nuevas funcionalidades es sencillo y no rompe la funcionalidad existente.
    *   **Estabilidad:** Los componentes principales permanecen intactos, minimizando el riesgo de introducir errores.
    * **Flexibilidad**: Podemos añadir nuevas acciones o modificar el flujo de trabajo sin tocar otras partes del codigo.

### 3. Principio de Sustitución de Liskov (LSP)

*   **Los subtipos deben poder sustituir a sus tipos base sin alterar la corrección del programa.**
    *   **Acciones:** Cualquier clase que implemente la interfaz `Action` puede ser usada en el menú.
    * Cualquier clase que implemente `MenuOptionInterface` puede ser usada en el `MenuConfiguration`
*   **Beneficios:**
    *   **Polimorfismo:** Mayor flexibilidad al permitir el uso de diferentes implementaciones de forma intercambiable.
    *   **Previsibilidad:** El comportamiento del sistema permanece consistente independientemente de la implementación concreta que se use.

### 4. Principio de Segregación de Interfaces (ISP)

*   **Los clientes no deberían depender de interfaces que no usan.**
    *   **Interfaces para acciones:** Cada acción tiene su propia interfaz `Action` con el método `execute()`.
    * **Interfaces para opciones del menu**: Cada opcion del menu tiene la interfaz `MenuOptionInterface` con la propiedades `descriptionKey` e `id`, y el metodo `execute`.
    * **Interfaces para repositorios**: Esto ayuda a los test y en la forma de implementar los repositorios.
    * **Interfaces para presenter**: Esto ayuda a tener diferentes implementaciones de presenters.
    * La interfaz `MenuPresenter` es un buen ejemplo de esto.
*   **Beneficios:**
    *   **Cohesión:** Las clases implementan solo las interfaces que necesitan, reduciendo dependencias innecesarias.
    *   **Mantenibilidad:** Cambios en una interfaz tienen menos probabilidades de afectar a otras partes del código.

### 5. Principio de Inversión de Dependencias (DIP)

*   **Los módulos de alto nivel no deben depender de los módulos de bajo nivel. Ambos deben depender de abstracciones.**
    *   **Inyección de Dependencias:** Las dependencias se inyectan en las clases a través de sus constructores.
    *   **Interfaces para Dependencias:** Se utiliza la interfaz `MenuPresenter` y se podrían usar interfaces para otras dependencias (por ejemplo, `HabitRepository`, `Clock`, `Messages`).
*   **Beneficios:**
    *   **Acoplamiento:** La lógica de alto nivel no está ligada a implementaciones específicas.
    *   **Testabilidad:** Es más fácil reemplazar dependencias por mocks o stubs para pruebas.
    * **Flexibilidad:** Se pueden cambiar las dependencias sin tocar otras partes del codigo.

## Cómo Ejecutar la Aplicación

1.  **Requisitos Previos:**
    *   Java Development Kit (JDK) 17 o superior.
    *   Kotlin.
2.  **Compilación:**
    *   Compilar el código Kotlin usando el compilador de Kotlin o un IDE como IntelliJ IDEA.
3.  **Ejecución:**
    *   Ejecutar la clase `Main.kt` desde el código compilado.
4. **Uso**
    * Ejecutar la app.
    * La app mostrara las opciones del menu.
    * Usar el numero de la opcion que se quiere ejecutar.

## Funcionalidades

*   **Añadir Hábito:** Añade un nuevo hábito para realizar el seguimiento.
*   **Listar Hábitos:** Muestra la lista de los hábitos que estás siguiendo.
*   **Completar Hábito:** Marca un hábito como completado para el día actual.
*   **Eliminar Hábito**: Elimina un habito.
* **Mostrar Estadisticas de Habitos**: Muestra las estadisticas de los habitos.
*   **Salir:** Sale de la aplicación.

## Mejoras Futuras

*   **Persistencia:** Implementar un mecanismo de persistencia más robusto (por ejemplo, usando una base de datos).
*   **Estadísticas Avanzadas:** Añadir estadísticas de hábitos más sofisticadas (por ejemplo, rachas, tasas de éxito).
*   **Gestión de Usuarios:** Permitir que varios usuarios puedan seguir sus hábitos.
* **Añadir mas acciones**: Se podrian añadir mas acciones para manejar los habitos.


## Contribuciones

¡Las contribuciones a HabitTrackerCLI son bienvenidas! No dudes en abrir issues o enviar pull requests en el repositorio del proyecto.
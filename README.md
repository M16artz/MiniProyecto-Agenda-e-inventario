# Taller-6-Comparaciones
# Algoritmos de Ordenamiento (Java)

**Asignatura:** Estructura de Datos (Unidad 2)   
**Versión:** 1.0

## Descripción del Proyecto

Este proyecto implementa y compara el rendimiento empírico de tres algoritmos de ordenamiento clásicos (**Burbuja, Selección e Inserción**) aplicados a casos de uso "reales" mediante objetos de dominio (`Cita`, `Paciente`, `Item`).

El objetivo es determinar qué algoritmo es más eficiente en función del **tamaño del dataset (N)**, el **grado de orden inicial** y la presencia de **duplicados**, utilizando una metodología de medición científica rigurosa.

## Metodología de Medición

Para garantizar resultados fiables y minimizar el ruido del sistema operativo y la JVM, se ha implementado la siguiente instrumentación en `SortingDemo.java`:

1.  **Repeticiones (R=10):** Cada algoritmo se ejecuta 10 veces seguidas por dataset.
2.  **Calentamiento (Warm-up):** Se descartan las **3 primeras ejecuciones** para mitigar el efecto del *Just-In-Time (JIT) Compiler* de Java y la carga inicial de clases.
3.  **Mediana Estadística:** Se reporta la **mediana** de los tiempos restantes, eliminando valores atípicos producidos por procesos en segundo plano.
4.  **Aislamiento:**
    * Se fuerza la recolección de basura (`System.gc()`) antes de cada medición.
    * Se utilizan copias profundas (`Arrays.copyOf`) del arreglo original para cada algoritmo, garantizando que todos ordenen exactamente los mismos datos desordenados.
    * No se imprimen trazas en consola durante la medición de tiempo.

## Datasets Generados
El sistema incluye un generador automático de datos (DatasetGenerator.java) que asegura la reproducibilidad del experimento utilizando una semilla aleatoria fija (Seed: 42).

Al iniciar la aplicación, se crean automáticamente los siguientes archivos en la ruta src/main/java/data/:
    * citas_100.csv : Para evaluar rendimiento general sin sesgos.
    * citas_100_casi_ordenadas.csv : Demostrar la eficiencia adaptativa de Insertion Sort.
    * pacientes_500.csv : Verificar si el algoritmo mantiene el orden relativo original de claves iguales.
    * inventario_500_inverso.csv : Escenario de estrés máximo para Bubble e Insertion.
## Instrucciones de Ejecución

**Prerrequisitos**
* Java Development Kit (JDK): Versión 17 o superior.

* Entorno: Cualquier IDE (IntelliJ IDEA, NetBeans, Eclipse) o terminal con soporte para proyectos Maven/Gradle.

## Pasos para Ejecutar

**Compilación y Generación de Datos:**

No es necesario ejecutar el generador manualmente. La clase principal SortingDemo verifica la existencia de los datos y los genera automáticamente si no existen.

**Iniciar el Proyecto:**

Ejecuta la clase principal del proyecto:

  * ed.u2.sorting.SortingDemo
  
* Navegación en el Menú: La consola mostrará un menú interactivo:

* Opción 1: Ejecuta la batería completa de pruebas (los 4 datasets secuencialmente).

* Opciones 2-5: Ejecuta un escenario específico para análisis aislado.

* Opción 0: Salir.

* Interpretación de Resultados: El sistema aplica una metodología rigurosa para cada prueba:

  *Se realizan 10 repeticiones por algoritmo.

  *Se descartan las 3 primeras (Warm-up) para evitar el ruido del JIT Compiler.

  *Se reporta la mediana del tiempo, comparaciones y movimientos.

* (Opcional) Al finalizar, el sistema preguntará: ¿Presentar los arreglos ordenados? (s/n). Responde s para verificar visualmente el ordenamiento en consola.

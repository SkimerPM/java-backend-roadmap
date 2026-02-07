# Guía Técnica: Java Stream API

La API de Streams permite transicionar de la programación imperativa (basada en el "cómo") a la programación declarativa (basada en el "qué"). Se define como un pipeline de procesamiento de datos y no como una estructura de almacenamiento.

---

## 1. Conceptos Fundamentales

### Lambdas
Son expresiones que permiten representar funciones anónimas de forma compacta. En el contexto de Streams, se utilizan para implementar la lógica de las interfaces funcionales (como Predicate, Function o Consumer) sin la verbosidad de las clases anónimas.

### Streams
Un flujo de datos que se procesa a través de una serie de etapas:
* **Fuente:** Colección, Array o I/O de donde provienen los datos.
* **Operaciones Intermedias:** Son perezosas (lazy) y devuelven un nuevo Stream. No ejecutan ninguna lógica hasta que se invoca una operación terminal.
* **Operaciones Terminales:** Inician el procesamiento, cierran el Stream y producen un resultado o un efecto colateral.

---

## 2. Mecanismos de Recolección: collect y Collectors

En la arquitectura de Streams, es fundamental distinguir entre la acción de finalizar el flujo y la estrategia de almacenamiento de los resultados.

### collect (El método)
Es la operación terminal que actúa como el receptor de los datos procesados. Su función es "reducir" los elementos del Stream en un único resultado final, ya sea una colección o un valor específico.

### Collectors (La clase de utilidad)
Es una clase que provee implementaciones predefinidas de estrategias de reducción. Define el "molde" o formato que tomarán los datos al final de la tubería.



### Estrategias comunes de empaquetado

| Herramienta | Función Técnica | Ejemplo de Implementación |
| :--- | :--- | :--- |
| **toList()** | Crea una lista con los resultados (inmutable en Java 16+). | `stream.filter(n -> n > 0).toList();` |
| **toSet()** | Agrupa elementos eliminando duplicados automáticamente. | `stream.collect(Collectors.toSet());` |
| **joining()** | Concatena elementos tipo String en una sola cadena. | `stream.collect(Collectors.joining(", "));` |
| **groupingBy()** | Clasifica los datos en un Map basándose en una clave. | `stream.collect(Collectors.groupingBy(User::getRole));` |
| **counting()** | Realiza un conteo long de los elementos procesados. | `stream.collect(Collectors.counting());` |
| **summarizing()** | Genera estadísticas (min, max, avg, sum) en un solo paso. | `stream.collect(Collectors.summarizingDouble(Product::getPrice));` |

---

## 3. Integración de Streams con Optional

En el desarrollo de backend, la combinación de Streams con la clase `Optional` es la práctica estándar para evitar valores nulos y manejar excepciones de negocio de forma controlada.

### Ejemplo: Búsqueda Segura y Lanzamiento de Excepción
Este patrón sustituye las validaciones manuales `if (obj == null)` por un flujo lineal.


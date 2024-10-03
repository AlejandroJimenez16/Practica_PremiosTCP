# PremiosTCP

Este proyecto es una practica realizada en DAM en el modulo **Programacion de Servicios y Procesos**

## Juego de Búsqueda de Premios usando Sockets TCP

Este proyecto es una implementación de un juego multijugador donde los clientes, conectados a través de sockets TCP, buscan premios en un tablero compartido de 3 filas y 4 columnas. El servidor gestiona la lógica del juego y las interacciones de los jugadores en tiempo real.

## Descripción del Juego

Cada cliente intenta adivinar la posición de premios ocultos en un tablero de tamaño 3x4. Los premios están colocados en posiciones específicas y compartidos entre todos los clientes. Cuando un cliente adivina la posición de un premio, este es retirado del tablero y ya no estará disponible para otros jugadores.

### Funcionalidades del Servidor:

- Inicializa el tablero con 4 premios en posiciones fijas.
- Cada vez que un cliente se conecta, se le asigna un ID único.
- Se crea un hilo para cada cliente conectado.
- Informa a los clientes si aún quedan premios o si el juego ha finalizado.
- Controla el número de jugadas y premios obtenidos por cada cliente.
- Desconecta a los clientes cuando han agotado sus 4 intentos o cuando ya no quedan premios disponibles.
- Registra las conexiones y desconexiones de los clientes.

### Funcionalidades del Cliente:

- Envía posiciones (fila y columna) al servidor para intentar obtener un premio.
- Recibe información sobre si la jugada ha sido exitosa o no.
- Controla y muestra el número de intentos y premios obtenidos.
- La interfaz gráfica permite al usuario:
  - Ver su ID asignado.
  - Introducir las coordenadas del tablero (fila y columna).
  - Visualizar mensajes del servidor y el progreso del juego.
  - Enviar jugadas o finalizar el juego.

## Reglas del Juego

1. Cada cliente tiene 4 intentos para adivinar la ubicación de los premios.
2. Si un premio ya ha sido ganado por otro cliente, no se cuenta como premio, pero sí como intento.
3. Si no hay premios disponibles cuando el cliente se conecta, no podrá jugar y será desconectado.
4. El juego finaliza cuando el cliente ha agotado sus intentos o se han encontrado todos los premios.

## Ejecución

### Requisitos:
- **JDK 8 o superior**.
- Bibliotecas estándar de Java como `java.net` para sockets y `javax.swing` para la interfaz gráfica.

### Instrucciones para ejecutar el servidor:

1. Clona este repositorio: 
`git clone https://github.com/AlejandroJimenez16/Practica_PremiosTCP.git`
2. Navega al directorio del proyecto:
`cd Practica_PremiosTCP`
3. Ejecuta el servidor:
`java Servidor.java`

### Instrucciones para ejecutar el cliente:

1. Navega al directorio del proyecto:
`cd Practica_PremiosTCP`
2. Ejecuta el servidor:
`java Cliente.java`

## Ejemplo del Tablero Inicial

El servidor comienza con el siguiente tablero con premios en las siguientes posiciones:

| Fila | Columna | Premio   |
|------|---------|----------|
| 0    | 0       | Crucero  |
| 1    | 2       | Entradas |
| 2    | 0       | Masaje   |
| 2    | 3       | 1000€    |

## Conexión de Clientes

Cuando un cliente se conecta, el servidor muestra:  
`Cliente conectado -> ID`

Cuando se desconecta, se muestra:  
`Cliente desconectado -> ID`

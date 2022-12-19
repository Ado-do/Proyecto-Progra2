# PROYECTO PROGRAMACIÓN II (S2 2022)

<div id="top"></div>

<!-- PROJECT LOGO -->
<div align="center">
  </a>
    <img src="/imgs/ss.png" alt="Logo">
  </a>
  <p align="center">
    "Mesa de Pool en Java"
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Tabla de contenidos</summary>
  <ol>
    <li>
      <a href="#sobre-el-proyecto">Sobre el proyecto</a>
    </li>
    <li>
      <a href="#objetivos">Objetivos</a>
    </li>
    <li>
      <a href="#informe">Informe</a>
    </li>
    <li>
      <a href="#links-útiles">Links útiles</a>
    </li>
  </ol>
</details>

## Sobre el proyecto

### Equipo (Grupo 22)

* Alonso Bustos (Ado-do)
* Cristóbal Figueroa (xKroZzar)
* Lirayen Martinez (liraa001)

### Fechas Importantes

* [X] ~~Entrega Github Proyecto (07/12) -> Entrega 12/12~~
* [ ] Entrega final (21/12)

<p align="right">(<a href="#top">volver a inicio</a>)</p>

## Objetivos

### Principales

* [X] ~~Terminar diseño final~~
  * [X] ~~**Primera versión del diseño (Prototipo de interfaz)**~~
  * [X] ~~Subir imagen de la interfaz prototipo~~
  * [X] ~~Terminar diseño final de GUI~~

* [ ] Terminar informe
  * [X] ~~Enunciado general del tema, junto a propuestas del equipo~~
  * [X] ~~"Use Case Diagram" con funciones principales de la aplicación/juego~~
  * [X] ~~UML del contenido gráfico (GUI)~~
  * [X] ~~"TO-DO List" de cosas que no sabes hacer, y nuestro avance respecto a ellas~~
  * [ ] Agregar JavaDoc
  * [ ] Agregar UnitTests

### TO-DO List

* [X] ~~Realizar interfaz de taco e input~~  _*Solucionado utilizando package "geométricas"_
  * [X] ~~Mover punto respecto a un punto central según movimiento del mouse~~
  * [X] ~~Dibujar respecto a este input~~

* [X] ~~Simular físicas~~ _*Solucionado agregando clase Vector2D_
  * [X] ~~Lanzamiento de pelota y controlar fuerza de este~~
  * [X] ~~Rebote y colisiones (Bola->Banda y Bola->Bola)~~
  * [X] ~~Inercia~~
* [X] ~~Generación de bolas~~ _*Solucionado agregando "BallsFactory" y "MenuWindow"_
  * [X] ~~Generación en posiciones aleatorias~~
  * [X] ~~Controlar cantidad generada por medio de GUI~~
* [X] ~~Sistema de puntaje~~
  * [X] ~~Lógica de troneras~~
  * [X] ~~Asignar puntaje según tipo de bola insertada en tronera~~
* [X] ~~Reiniciar por medio de GUI~~
* [ ] Nuevas Funcionalidades
  * [ ] Multijugador

<p align="right">(<a href="#top">volver a inicio</a>)</p>

## Informe

### Enunciado

> En el panel central, con vista aérea, deben aparecer bolas en posiciones randómicas, una blanca y otras de color. El taco debe aparecer automáticamente apuntando a la bola blanca y debe ser manejado con controles GUI (teclado y mouse), para golpear bola blanca. Las bolas deben tener la física de impactos, inercia y roce.  En las esquinas debe haber troneras donde pueden caer. La cantidad de bolas debe ser definible por interfaz GUI. Habrá bandas para rebote de las bolas y si caen en las troneras, otorgan puntos.  Reiniciar se debe hacer por controles GUI. Si se cae la bola de color y la blanca no hay puntaje, si cae la blanca se resta puntaje.

### Diagrama UML

<img src="/imgs/diagramaUML.png" alt="UML">

### Diagrama de casos de usos

<img src="/imgs/diagramaCasos.png" alt="CASOS">

### Decisiones tomadas como equipo

> * Crear una clase llamada **Vector2D**, que nos permite simular procesos físicos en nuestro programa que nos faciliten la tarea de crear colisiones, movimiento, etc.
> * Crear una clase llamada **MenuWindow**, que nos permite crear una ventana con un menú para poder elegir la cantidad de jugadores, modo de juego, y la cantidad de bolas que queremos en el juego.
> * Crear una clase llamada **BallsFactory**, que nos permite crear un arreglo de bolas ordenadas de manera aleatoria o estándar, esto nos facilita la tarea de poder cambiar la cantidad de bolas que queremos en el juego sin tener que modificar el código fuente.

### Problemas que tuvimos como equipo y autocrítica

> El mayor problema que tuvimos como equipo fue la organización y como repartir equitativamente las tareas, ya que esto perjudicaba y hacia que uno llevara la carga de los demás, ahora tenemos nuevos métodos para poder enfrentarnos a esta clase de problema.

<p align="right">(<a href="#top">volver a inicio</a>)</p>

## Links útiles

Aquí pondremos links útiles para el proyecto, ya sean videos, documentación de java o ideas para programar el Pool :)

### Docs

* [Pagina tutorial para juegos 2D en Java](https://zetcode.com/javagames/)
* [JavaPool](http://www.mscs.mu.edu/~mikes/174.2002/demos/feb4/JavaPool.html) del libro "[Java Game Programming for Dummies](https://theswissbay.ch/pdf/Gentoomen%20Library/Programming/Java/IDG%20-%20Java%20Game%20Programming%20for%20Dummies.pdf)"

### Videos

* [A New Beginning - Episode #01 - Java Game Development Tutorial](https://www.youtube.com/watch?v=6_N8QZ47toY&list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&index=1)
* [How to Make a 2D Game in Java #1 - The Mechanism of 2D Games](https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)
* [Programming Animation: Billiard Ball Collisions](https://youtu.be/guWIF87CmBg)
* [Programming Animation: Ball Bounces Off Angles](https://youtu.be/Ep2N0N6SB6U)
* [Programming Balls #1 Circle Vs Circle Collisions C++](https://youtu.be/LPzyNOHY3A4)

### Sobre github

* [Readme formatos y syntaxis](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax)
* [Video funcionamiento de github](https://youtu.be/8Dd7KRpKeaE)

<p align="right">(<a href="#top">volver a inicio</a>)</p>

<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  </a>
    <img src="/desing/ss.png" alt="Logo">
  </a>

  <h3 align="center">PROYECTO PROGRAMACIÓN II (S2 2022)</h3>

  <p align="center">
    Mesa de Pool en Java
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Tabla de contenidos</summary>
  <ol>
    <li>
      <a href="#sobre-el-proyecto">Sobre el proyecto</a>
    </li>
 <li><a href="#objetivos">Objetivos</a></li>
    <li>
      <a href="#instrucciones">Instrucciones</a>
      <ul>
        <li><a href="#prerequisitos">Prerequisitos</a></li>
        <li><a href="#instalación">Instalación</a></li>
  <li><a href="#controles">Controles</a></li>
      </ul>
    </li>
    <li><a href="#links-utiles">Links utiles</a></li>
  </ol>
</details>

<p align="right">(<a href="#top">volver a inicio</a>)</p>

<!-- ABOUT THE PROJECT -->
## Sobre el proyecto

### Equipo

* Alonso Bustos (Ado-do)
* Cristobal Figueroa (xKroZzar)
* Lirayen Martinez (liraa001)

### Fechas Importantes

* [ ] ~~Entrega Github Proyecto (07/12)~~ -> Entrega 12/12

<p align="right">(<a href="#top">volver a inicio</a>)</p>


<!-- ROADMAP -->
## Objetivos

### Principales

* [ ] Terminar diseño final
  * [X] ~~**Primera versión del diseño (Prototipo de interfaz)**~~
  * [X] ~~Subir imagen de la interfaz prototipo~~
  * [ ] Terminar diseño final de GUI

* [ ] Terminar informe
  * [X] ~~Enunciado general del tema, junto a propuestas del equipo~~
  * [X] ~~"Use Case Diagram" con funciones principales de la aplicacion/juego~~
  * [ ] UML del contenido grafico (GUI)
  * [X] ~~"TO-DO List" de cosas que no sabes hacer, y nuestro avance respecto a ellas~~

### TO-DO List

* [X] ~~Realizar interfaz de taco e input~~  _*Solucionado utilizando package "geometricas"_
  * [X] ~~Mover punto respecto a un punto central según movimiento del mouse~~
  * [X] ~~Dibujar respecto a este input~~

* [X] ~~Simular físicas~~ _*Solucionado agregando clase Vector2D_
  * [X] ~~Lanzamiento de pelota y controlar fuerza de este~~
  * [X] ~~Rebote y colisiones (Bola->Banda y Bola->Bola)~~
  * [X] ~~Inercia~~
* [ ] Generación de bolas
  * [ ] Generación en posiciones aleatorias
  * [ ] Controlar cantidad generada por medio de GUI
* [ ] Sistema de puntaje
  * [ ] Lógica de troneras
  * [ ] Asignar puntaje según tipo de bola insertada en tronera
* [ ] Reiniciar por medio de GUI
* [ ] Nuevas Funcionalidades
  * [ ] Multijugador

<p align="right">(<a href="#top">volver a inicio</a>)</p>

<!-- GETTING STARTED -->
## Instrucciones

Aquí dar instrucciones de como montar el repositorio localmente y como compilar el juego, ademas de como jugarlo

### Prerequisitos

Dependencias para poder compilar

* Tener java instalado¿
* ...

## INFORME

### Proyecto de Programacion 2; Pool Game

Grupo 22 – Integrantes:
-	Alonso Isaías Bustos Espinoza
-	Lirayen María Martínez Kramm
-	Cristóbal Joaquín Figueroa Burgos

### ENUNCIADO;

> En el panel central, con vista aérea, deben aparecer bolas en posiciones randómicas, una blanca y otras de color. El taco debe aparecer automáticamente apuntando a la bola blanca y debe ser manejado con troles GUI (teclado y mouse), para golpear bola blanca. Las bolas deben tener la física de impactos, inercia y roce.  En las esquinas debe haber troneras donde pueden caer. La cantidad de bolas debe ser definible por interfaz GUI. Habrá bandas para rebote de las bolas y si caen en las troneras, otorgan puntos.  Reiniciar se debe hacer por controles GUI. Si se cae la bola de color y la blanca no hay puntaje, si cae la blanca se resta puntaje.

### DIAGRAMA UML

<img src="/diagramas/UML-Proyecto-progra-2.png" alt="UML">

### DIAGRAMA DE CASOS DE USO

<img src="/diagramas/UML-casos-de-uso-.png" alt="CASOS">

### DESICIONES TOMADAS COMO EQUIPO

> Como equipo hemos tenido que tomar decisiones como crear una clase nueva llamada Vector2D para poder simular procesos físicos en nuestro programa que nos faciliten la tarea de crear colisiones, movimiento, etc.
La decisión de iniciar las bolas de manera triangular como en la vida real y agregando un botón para permitir la aparición de las bolas aleatoriamente, todo mediante una clase que creamos tipo Factory que crea un arreglo de bolas ordenadas de manera aleatoria o estándar.
También decidimos que los controles del juego sean mayoritariamente por el mouse ya que utilizar teclas para mover el taco o disparar hacen que el juego se vea menos fluido.

### PROBLEMAS QUE TUVIMOS COMO EQUIPO Y AUTOCRITICA.

> El mayor problema que tuvimos como equipo fue la organización y como repartir equitativamente las tareas, ya que esto perjudicaba y hacia que uno llevara la carga de los demás, ahora tenemos nuevos métodos para poder enfrentarnos a esta clase de problema.
> 

<!-- ACKNOWLEDGMENTS -->
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

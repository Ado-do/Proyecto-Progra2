<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  </a>
    <img src="/desing/logo.png" alt="Logo" width="550" height="360">
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
* ...
<p align="right">(<a href="#top">volver a inicio</a>)</p>

### Enunciado

* Tema 6: Mesa de pool

> En el panel central, con vista aérea, deben aparecer bolas en posiciones randómicas, una blanca y otras de color. El taco debe aparecer automáticamente apuntando a la bola blanca y debe ser manejado con troles GUI (teclado y mouse), para golpear bola blanca. Las bolas deben tener la física de impactos, inercia y roce.  En las esquinas debe haber troneras donde pueden caer. La cantidad de bolas debe ser definible por interfaz GUI. Habrá bandas para rebote de las bolas y si caen en las troneras, otorgan puntos.  Reiniciar se debe hacer por controles GUI. Si se cae la bola de color y la blanca no hay puntaje, si cae la blanca se resta puntaje. 

<!-- ROADMAP -->
## Objetivos
### Principales
- [ ] Terminar diseño final de GUI
	- [X] ~~**Primera versión del diseño (Prototipo de interfaz)**~~
	- [ ] Subir imagen de la interfaz prototipo
- [ ] Terminar informe
	- [X] ~~Enunciado general del tema, junto a propuestas del equipo~~
	- [X] ~~"Use Case Diagram" con funciones principales de la aplicacion/juego~~
	- [ ] UML del contenido grafico (GUI)
	- [ ] "TO-DO List" de cosas que no sabes hacer, y nuestro avance respecto a ellas
### TO-DO List
- [ ] Realizar interfaz de taco e input
	- [X] ~~Mover punto respecto a un punto central según movimiento del mouse~~
	- [ ] Dibujar respecto a este input
- [ ] Simular fisicas
	- [ ] Lanzamiento de pelota y controlar fuerza de este
	- [ ] Rebote y colisiones (Bola->Banda y Bola->Bola)
	- [ ] Inercia
- [ ] Generacion de bolas
	- [ ] Generacion en posiciones aleatorias
	- [ ] Controlar cantidad generada por medio de GUI
- [ ] Sistema de puntaje
	- [ ] Logica de troneras
	- [ ] Asignar puntaje según tipo de bola insertada en tronera
- [ ] Reiniciar por medio de GUI

<p align="right">(<a href="#top">volver a inicio</a>)</p>

<!-- GETTING STARTED -->
## Instrucciones
Aqui dar instrucciones de como montar el repositorio localmente y como compilar el juego, ademas de como jugarlo

### Prerequisitos

Dependencias para poder compilar

* Tener java instalado¿
* ...

### Instalación

Aqui dar instruccion para configurar, compilar e inicar juego

1. Descargar el repositorio
2. ...

### Controles

En menús:
* Mouse¿
* <kbd>↑</kbd>, <kbd>↓</kbd>, <kbd>←</kbd> y <kbd>→</kbd>: Moverse por el menú
* <kbd>ENTER</kbd> y <kbd>ESPACIO</kbd>: Confirmar
* <kbd>ESC</kbd>: Salir del juego

En partida:
* Mouse¿
* <kbd>ESC</kbd>: Pausar
* <kbd>R</kbd>: Reinciar
<p align="right">(<a href="#top">volver a inicio</a>)</p>

### Diagramas
<img src="/diagramas/Diagramauso.png" alt="DiagramaUso">
Propotipo Diagrama de uso

<!-- ACKNOWLEDGMENTS -->
## Links utiles
Aqui pondremos links utiles para el proyecto, ya sean videos, documentacion de java o ideas para programar el Pool :)

### Docs
* [Pagina tutorial para juegos 2D en Java](https://zetcode.com/javagames/)
* [JavaPool](http://www.mscs.mu.edu/~mikes/174.2002/demos/feb4/JavaPool.html) del libro "[Java Game Programming for Dummies](https://theswissbay.ch/pdf/Gentoomen%20Library/Programming/Java/IDG%20-%20Java%20Game%20Programming%20for%20Dummies.pdf)"

### Videos
* [A New Beginning - Episode #01 - Java Game Development Tutorial](https://www.youtube.com/watch?v=6_N8QZ47toY&list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&index=1)
* [How to Make a 2D Game in Java #1 - The Mechanism of 2D Games](https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)

### Sobre github
* [Readme formatos y syntaxis](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax)
* [Video funcionamiento de github](https://youtu.be/8Dd7KRpKeaE)

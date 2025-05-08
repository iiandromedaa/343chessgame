# Assignment Introduction

A Software Design Document (SDD) describes the design and architecture of your software. This type of documentation often follows the 4+1 design perspectives approach. The 4 perspectives (or views) of software are

1. *Logical View* – major components, their attributes and operations. This view also includes relationships between components and their interactions. When doing OO design, class diagrams and sequence diagrams are often used to express the logical view.
2. *Process View* – the threads of control and processes used to execute the operations identified in the logical view.
3. *Development View* – how system modules map to development organization.
4. *Physical View* – shows the system hardware and how software components are distributed across the processors in the system.  

The +1 perspective is the 

> Use Case View – the use case view is used to both motivate and validate design activity. At the start of design the requirements define the functional objectives for the design. Use cases are also used to validate suggested designs. It should be possible to walk through a use case scenario and follow the interaction between high-level components. The components should have all the necessary behavior to conceptually execute a use case.

# Assignment Requirements
 
For your project, you are only required to document the ***Logical View*** of your software, You must use markdown (.md file) to do this. 

Your documentation should have three sections: Overview, Design, Examples, and Status. Requirements for each section are described below.

# Overview of Required Sections

## Overview

![ChessGame_mainmenu](https://github.com/user-attachments/assets/ff1a2613-681a-4f7b-b28c-f74e5e7216fd)


Throughout the course of the semester, our group has been working to develop a game that takes inspiration from the concept of Chess.

The name of our software initially, "Rougelike Chess" currently, "Chess But Cooler" is an attempt at making a rougelike chess game with an incredible potential for growth and development. Our main focus was to first generate a functioning Java based chess game and then further implement our own ideas to make it something unique to us.

We made use of Visual Studio Code, Maven + Gradle, LibGDX, and other Java related tools to develop our software into a functioning project.

Currently there is a working Chess board game with several piece implementations that have allowed for us to create "levels" or variations of chess games that the user can step through and attempt to solve. The chess game itself not only has basic functionality but uses AI for the opponent allowing the game to serve as a Player VS. Enemy style rougelike chess game. The chess game also has a piece swap feature allowing the user to change there own pieces into pieces from other similar games like shogi. 

The GUI is quite fleshed out for our game. By making use of the model and view aspects of our code, the interface looks great. There are sprites, custom backgrounds, and sound implementation that really bring our chess game to life.

There are around 200 individual puzzles that the player can access and play though. Although the AI enemy isn't perfect it is still a sufficient addition to our project, allowing for a competitive side to our game. There is always room for improvement with our levels and rougelike elements.

## Design

The logical view describes the main functional components of the system. This includes modules and the static relationships between modules.

### Diagrams you might want to use

- **Box and Line Diagram**: for depicting software components and how they are connected 
- **UML Class Diagram**: for depicting code structure
- **UML State Diagram**: for depicting states of your software
- **ERD Diagram**: for depicting the tables in a database

You can use any other diagram that you find helpful for describing your software. You are expected to use diagrams and clearly describe what is depicted in your diagrams. 

This section will contain the most content. The documentation should fully document the latest version of your software. If you make changes to your software, you should update this document to reflect those changes. 

![ChessGame_LogicUML](https://github.com/user-attachments/assets/bc9a6c53-f3c0-4190-b825-55ebe762d7e3)

The above UML Class Diagram serves as a rough overview to the functionality of our Board code. We have the Board class which makes use of Piece, Move and, Board listener. Board is the main class displaying, moving, and interacting with each of the pieces depending on the board listener. Each of the individual pieces maintain their own functionality but heavily rely on our abstract class Piece. All of the regular or "Canon" pieces take inspiration from the classic Chess game using movement variables int x + int y and a Color enumeration. The pieces and board states function with a coordinates system as well as the "isValidMove" methods. Our newer implementations of additional pieces take inspiration from Shogi, we use a Shogi interface to separate the additional pieces, Ferz, Lance, ShogiPawn, Alfil. The Move and Coordinate classes as touched on before are what allow for the pieces and board states to function properly with the int x + int y variables. 

![ChessGame_GuiUML](https://github.com/user-attachments/assets/cb63c29c-c071-4895-b090-811a1787c9c9)

The above UML Class Diagram .........

## Examples

This is an image of the games mainmenu starting screen:
![ChessGame_mainmenu](https://github.com/user-attachments/assets/e0a66e89-c61a-48d3-b1ff-7f310b266485)

The following examples are images of 3 of the 200 possible chess game puzzles:
![ChessGame_boardexample](https://github.com/user-attachments/assets/83586b58-7975-4cbd-9bd8-f00cd06facfa)
![ChessGame_boardexample2](https://github.com/user-attachments/assets/01192e1b-3fe4-49bd-82c7-a3d691519d08)
![ChessGame_boardexample3](https://github.com/user-attachments/assets/824b3087-6b11-4ca7-b9d4-92b4678bffb9)

This is an image of the preview of possible moves when you select a chess piece: 
![ChessGame_piecemoveexample](https://github.com/user-attachments/assets/e30a0e87-475a-4203-bcc9-1880fc553ac2)

This is another example of a moving preview with a different piece: 
![ChessGame_piecemoveexample2](https://github.com/user-attachments/assets/50ee45f7-fa0d-4cf4-bfdd-34913c73548d)

This is an example of the result of using the piece swap button on some of the pieces:
![ChessGame_pieceswapexample](https://github.com/user-attachments/assets/a8872fdc-f570-473f-b2c2-d85ab6b071ba)


## Status

Currently the software is a fully functioning chess game with a fully polished GUI that features piece movement animations, and audio effects. It functions the same as classic chess currently but is played through level style. When playing the game you will randomly be assigned one of 200 puzzles. The game also has some additional working features including a on demand new puzzle button, piece swap button, and a fully funcitoning AI opponent. 

Here is a list of planned additions for future releases:
 - Board Obstacles
 - Board Hazards
 - Shop system inbetween levels (partially complete see image below)
 - settings
 - powerup & debuff items
 - piece hitpoints and damage
 - higher level AI capable of handling all of the additions listed above

Here is an image of the current shop screen that displays inbetween games:
![ChessGame_shopscreenexample](https://github.com/user-attachments/assets/2f8c4151-bda4-41c7-87a5-562b6dc025ac)


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
Give a high-level description of your software. The description should be clear and professional. You can use images if it helps. 

##PLACE IMAGE HERE

Throughout the course of the semester, our group has been working to develop a game that takes inspiration from the concept of Chess. 

The name of our software initially, "Rougelike Chess" currently, "Chess But Cooler" is an attempt at making a rougelike chess game with an incredible potential for growth and development. Our main focus was to first generate a functioning Java based chess game and then further implement our own ideas to make it something unique to us. 

We made use of Visual Studio Code, Maven + Gradle, LibGDX, and other Java related tools to develop our software into a functioning project.

Currently there is a working Chess board game with several piece implementations that have allowed for us to create "levels" or variations of chess games that the user can step through and attempt to solve. The chess game itself not only has basic functionality but uses AI for the opponent allowing the game to serve as a Player VS. Enemy style rougelike chess game.

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

## Examples

Give screenshots of your running project and briefly describe what is in the screenshots.

## Status

Give the current status of the software. List what is working and what is planned for future releases. When listing planned features, assume that the the project will continue even after the course ends.

# PhysicsEngine

A small, educational 2D physics engine written in Java. It currently supports circular bodies and infinite planes, with basic collision detection and impulse-based collision resolution. This project is intended as a learning toy or starting point for simple physics simulations and games.

## Table of contents
- [Features](#features)
- [Quick demo](#quick-demo)
- [Requirements](#requirements)
- [Getting started](#getting-started)
- [Usage & examples](#usage--examples)
- [Controls](#controls)
- [Design & architecture](#design--architecture)
- [Physics notes & conventions](#physics-notes--conventions)

## Features
- Circle (disk) bodies with:
  - Position, velocity, mass, restitution
  - Radius and inverse mass for stable calculations
- Infinite plane bodies (for ground and walls) with position and normal
- Circle-circle and circle-plane collision detection
- Impulse-based collision resolution with simple positional correction
- Small, self-contained Vector2D helper for vector math

## Quick demo
Open the project in your Java IDE (IntelliJ IDEA, Eclipse, NetBeans) and run the class that contains the main/game loop (the `Panel` class in this repo). The `Panel` class initializes the scene via `resetSimulation()` and runs the simulation/render loop.

## Requirements
- Java 8 or later (JDK)
- A Java IDE or command-line Java toolchain

## Getting started
1. Open the project in your IDE and locate the `Panel` class.
2. Run `Panel` (it contains the main loop / entry point for the demo).
3. Use the Add Panel (F1) to add circles at runtime; press F2 to reset to the initial scene.

If you prefer the command line and your sources are in the root `src/` folder:
```bash
# compile (example; adjust paths to match your project layout)
javac -d out $(find . -name "*.java")
# run the main class (replace `Panel` with the full package name if necessary)
java -cp out Panel
```

## Usage & examples
Create bodies in `Panel.resetSimulation()` and add them to the engine's bodies list.

Circle constructor order (as used in this project):
- positionX, positionY, velocity (Vector2D), acceleration (Vector2D), constantAcceleration (Vector2D), mass, restitution, radius

Example:
```java
// Create a circle at (100, 50) with zero initial velocity, gravity of (0, 9.8),
// mass = 1.0, restitution = 0.8, radius = 12
Circle c = new Circle(
    100, 50,
    new Vector2D(0, 0),           // velocity
    new Vector2D(0, 0),           // acceleration
    new Vector2D(0, 9.8),         // constantAcceleration (gravity)
    1.0,                          // mass
    0.8,                          // restitution
    12                            // radius
);
bodies.add(c);
```

Plane constructor order:
- positionX, positionY, width, height, normal (Vector2D)

Example:
```java
// Create a floor plane positioned at top-left (0, 400), full width 800,
// height 10 (for visual), with an upward normal (pointing -Y)
Plane2D floor = new Plane2D(
    0, 400,
    800, 10,
    new Vector2D(0, -1)           // normal pointing up
);
bodies.add(floor);
```

Note: when creating planes enter the top-left corner of the plane (not the center).

## Controls
- F1 — Open the Add Panel (UI to add circles)
- F2 — Reset the scene (calls `resetSimulation()`)

## Design & architecture
- RigidBody (base)
  - Common fields: position, velocity, mass, restitution, inverse mass
  - Uses inverse mass in calculations for convenience and stability
- Circle (extends RigidBody)
  - radius
- Plane2D (extends RigidBody)
  - width, height (visual), position, normal
  - represents an infinite wall defined by a position and normal
- PhysicsManager
  - Central place for collision checks and resolution (impulses + positional correction)
- Vector2D
  - Minimal vector library used across the codebase
- Panel / AddPanel
  - UI / main loop and helper for adding bodies interactively

## Physics notes & conventions
- Coordinate system: origin (0,0) is top-left of the window. The Y axis points downwards (increasing Y moves down).
- Gravity in this engine is represented as a constantAcceleration (Vector2D) per body; set it to (0,0) if you don't want gravity.
- Use restitution (0.0 to 1.0) to control bounciness. Mass should be > 0 for dynamic bodies; infinite/massless objects may be represented with inverse mass = 0.

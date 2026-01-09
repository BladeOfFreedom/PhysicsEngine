# PhysicsEngine
A very basic 2D physics engine written in Java that supports circular bodies and infinite planes. It provides simple collision detection and resolution.

#Features
-Circle (disk) bodies with position, velocity, mass, and restitution
-Infinite plane bodies for ground/walls (axis-aligned or arbitrary orientation)
-Collision detection between circles and between circles and planes
-Impulse-based collision resolution with simple positional correction

#Architecture
--RigidBody (base)
  Common properties: position, velocity, mass, restitution, inverse mass
  Inverse mass for easier calculations, mass is for simplicity
  Constant acceleration is used for gravitiy so it is best to initialize it as (0,0) --This is due to how the update of physics interpreted--
  Contains the conversion pixel-to-meter
--Circle (extends RigidBody)
  radius
--Plane2D (extends RigidBody)
  width, height(only for visuals)
  position and normal
  infinite wall
--PhysicsManager
  Contains all the fancy maths
  Checks and resolves collisions
--Vector2D 
  Very simple vector library
--Panel
  Main frame
  Contains the game loop
--AddPanel
  Enables circle addition
  

How To Use
In the Panel class resetSimulation() is called at the beginning. The bodies you initialize there will be the starting bodies(add the created bodies to the bodies arrayList after creating the object).
For circle the order is => position x, position y, velocity(as vector), acceleration(as vector), constantAcceleration(as vector), mass, restitution, radius
For plane the order is => position x, position y,  width, height, normal(as vector)
(Beware that (0,0) is the top left corner and y axis is reversed ( - is down ) also when entering position data for plane please enter the top left corner of the plane not the center ot the plane)
Pressing F2 resets the scene
Pressing F1 opens another panel that can add circles to scene.

# Drone Simulator Program

### FSE 100 - Introduction to Engineering

For FSE-100 as Arivona State University we had to design drones and then simulate how they would perform their functions. Rather than use MATLAB as requested I got permission from the instructor to make my own simulator in Java. 

This project allows the modular construction of drones in a scenario. Each student's drone is represented by its own class and communicates with the central hub. Each drone operates based on its own parameters designed at a previous point in the class.

This simulation allows us to deploy a team of drones to warn campers in a forest that a fire is present. The drones at the start are unaware of the location of fire or campers and must scout.

You can place locations for fire and campers in the scenario. Drones will scout the forest per their programming taking note of campers and looking for fire. When a fire is spotted they deploy drones to notify campers of the fire and guide them out.

You can press **L** to see lines indicating the destination of the drones. Their path is programmed at launch and campers and fire locations are unknown to them until they get close.

![Image](/Demo.PNG "Demonstration")

*Program in action*

### How to Run

- Ensure you use Java 11+
- Clone the repo with git clone <Link>
- Use 'gradle run'

### Known Issues:
- This is a project from one of my first classes as ASU and as such is probably very far from optimally constructed. I need to go back and make some updates.


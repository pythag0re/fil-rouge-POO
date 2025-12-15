# FIL-ROUGE-POO — Train Management System

This project is a **Java Object-Oriented Programming (OOP)** application that models and manages a **train composition system**.  
It is designed around a **realistic railway domain**, following **MVC architecture** and strong **OOP principles**, and allows interaction through either a **Graphical User Interface (Swing)** or a **Console Interface**.

---

## Project Concept

The goal of this project is to simulate the **composition and management of a train** made of different types of vehicles:
- A **locomotive**
- **Passenger cars**
- **Freight wagons**

The system allows users to:
- Define train routes (departure and destination stations)
- Dynamically add, remove, and modify vehicles
- Control the position of the locomotive (front or back)
- Visualize the train through different interfaces

This project focuses on **modeling a real-world system using clean object-oriented design**, not just UI interaction.

---
## Contributors

Thanks to the following people who have contributed to this project:

- **ABOU KHECHFE Yehya** 
- **BEN GUENDIA Yacine** 
- **ATROUCHE ALi**
----
##  UML-Based Design Overview
<img src="https://github.com/user-attachments/assets/13e4386a-3717-43c2-996a-26e177def435">

##  Features

- Choice of interface at launch:
  - Graphical User Interface (Swing)
  - Console Interface
- Object-oriented design with clear separation of concerns
- Vehicle and train modeling (locomotive, wagons, cars, etc.)
- Extensible architecture using a **Vehicle Factory**

---
## Technologies Used

- **Java**
- **Swing (javax.swing)** for GUI
- **MVC Design Pattern**
- **Factory Pattern**

---

## How to Run

### Prerequisites

- Java JDK 8+

### Run the Application

```bash
javac project/App.java
java project.App

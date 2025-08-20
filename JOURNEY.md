# Development Journal - Task Management Project

# Project Overview
A Java-based task management application that starts as a console application and will later include a JavaFX graphical user interface.

## Learning Goals
My main goal is to understand and implement the four core principles of Object-Oriented Programming (OOP):
- **Abstraction** - Hiding complex implementation details and showing only essential features
- **Encapsulation** - Keeping data and methods together in classes and controlling access to them
- **Inheritance** - Creating new classes based on existing ones to reuse code
- **Polymorphism** - Using one interface to represent different types of objects

Additionally, I want to learn how to work with databases:
- **SQL Database Integration** - Learning how to store and retrieve data using relational databases
- **Database Design** - Understanding how to structure tables and relationships for task data
- **CRUD Operations** - Implementing Create, Read, Update, and Delete functionality with SQL

## Project Evolution
- **Phase 1**: Console-based application with basic file storage (XML)
- **Phase 2**: Integrate SQL database for better data management
- **Phase 3**: JavaFX GUI for better user experience

---

## 2025-08-16 - Project Setup & Task Model
### What I accomplished:
- Created the `Task` model class with core properties (title, description, date, completion status, UUID)
- Implemented two constructors: one for user creation, one for database loading
- Added getter methods and toString() for task representation
- Used UUID for unique task identification
- Implemented automatic date assignment for new tasks

### Design Decisions:
- **UUID over incremental IDs**: Ensures uniqueness across different sessions
- **Two constructors**: Separates user creation from database loading concerns
- **Immutable properties**: Task properties can only be set during construction

### Challenges faced:
- [Add any challenges you encountered]

### Next steps:
- Implement task completion toggle functionality (next day)
- Create database/XML persistence layer
- Build user interface

---

## [18-08-2025] - [Change task status]
### What I accomplished:
- 

### Challenges faced:
- 

### Lessons learned:
- 

### Next steps:
- 

---

## Ideas for Future Enhancement
- Task priorities (High, Medium, Low)
- Task categories/tags
- Due dates and reminders
- Search and filter functionality
- Task dependencies
- Export to different formats

**## [18-08-2025] - [Change task status]
### What I accomplished:
- Database.changeStatus() blueprint is done, I need to add Task parameter 
    atm is hardcoded to change status to true. I will need to have Task.changeStatus() method.
    That method will go to TaskController.changeTaskStatus()

### Challenges faced:
- 

### Lessons learned:
-

### Next steps:
- 

---

## Ideas for Future Enhancement
- Task priorities (High, Medium, Low)
- Task categories/tags
- Due dates and reminders
- Search and filter functionality
- Task dependencies
- Export to different formats**
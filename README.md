Pour la version Française [![fr](https://img.shields.io/badge/Fr-8Db6C7.svg)](./README.fr.md)
# DnD

A console-based Java game inspired by Dungeons & Dragons (DnD). Players can create a character and embark on an adventure through 3 unique dungeons, facing monsters, challenges, and collecting treasures along the way.

## Overview

This project implements a single- or multi-player DnD-like experience entirely in the console. Players navigate through three different dungeons, making choices, fighting enemies, and managing their character's progression.

<img width="400" height="350" alt="image" src="https://github.com/user-attachments/assets/7604aebf-c1c8-4833-b29e-5e02eaf236b6" />

## Features

- Console-based gameplay
- Character creation and stats management
- Turn-based combat with monsters
- Randomized encounters and treasures
- Three progressively challenging dungeons
- Save and load game progress (if implemented)
- Modular code for easy expansion or customization

## Technologies Used

- **Java** (100%)

## Getting Started

### Prerequisites

- [Java JDK 8+](https://adoptopenjdk.net/) installed

### Running the Game

#### In a terminal 

1. **Clone the repository:**
   ```bash
   git clone https://github.com/KitsuneNoMegami/DnD.git
   cd DnD
   ```

2. **Compile and run:**
   ```bash
   javac -d bin src/*.java
   java -cp bin Main
   ```
#### An other way 

You can use an IDE like IntelliJ to make the launching easier or make sur to have all the pretty colors
   1. **Clone the repository too:**
      ```bash
      git clone https://github.com/KitsuneNoMegami/DnD.git
      cd DnD
      ```
   2. **Launch your IDE and start the main file**

      
4. **Follow the prompts in the console to create your character and begin your adventure!**

## Contributing

Contributions are welcome! If you have ideas, improvements, or bug fixes, feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Gitignore
A `.gitignore` file is included to keep compiled classes and IDE/project files out of version control. Typical entries are:
```
bin/
*.class
*.log
*.iml
.idea/
```

## Acknowledgements

- Inspired by the Dungeons & Dragons® tabletop RPG system.
- Thanks to the open-source Java community for tools and libraries.

---

Enjoy your adventure!

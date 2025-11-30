For the English version [![en](https://img.shields.io/badge/En-CA9792.svg)](./README.md)
# DnD

Un jeu Java sur console inspiré de Donjons et Dragons (DnD). Les joueurs peuvent créer un personnage et se lancer dans une aventure à travers 3 donjons uniques, affronter des monstres, relever des défis et collecter des trésors en cours de route.

## Présentation

Ce projet met en œuvre une expérience de type DnD solo ou multijoueur entièrement sur console. Les joueurs naviguent à travers trois donjons différents, font des choix, combattent des ennemis et gèrent la progression de leur personnage.

<img width="400" height="350" alt="image" src="https://github.com/user-attachments/assets/7604aebf-c1c8-4833-b29e-5e02eaf236b6" />

## Caractéristiques

- Gameplay sur console
- Création de personnages et gestion des statistiques
- Combats au tour par tour avec des monstres
- Rencontres et trésors aléatoires
- Trois donjons de difficulté progressive
- Sauvegarde et chargement de la progression du jeu (si implémenté)
- Code modulaire pour une expansion ou une personnalisation facile

## Technologies utilisées

- **Java** (100 %)

## Pour commencer

### Prérequis

- [Java JDK 8+](https://adoptopenjdk.net/) installé

### Exécution du jeu

#### Dans un terminal 

1. **Clonez le référentiel :**
```bash
   git clone https://github.com/KitsuneNoMegami/DnD.git
   cd DnD
   ```

2. **Compilez et exécutez :**
```bash
  javac -d bin src/*.java
  java -cp bin Main
  ```
#### Une autre méthode 
Vous pouvez utiliser un IDE tel qu'IntelliJ pour faciliter le lancement ou vous assurer d'avoir toutes les jolies couleurs.
   1. **Clonez également le référentiel :**
```bash
git clone https://github.com/KitsuneNoMegami/DnD.git
cd DnD
```

2. **Lancez votre IDE et démarrez le fichier principal**

      
4. **Suivez les instructions de la console pour créer votre personnage et commencer votre aventure !**

## Contribution

Les contributions sont les bienvenues ! Si vous avez des idées, des améliorations ou des corrections de bugs, n'hésitez pas à ouvrir un ticket ou à soumettre une demande de pull.

## Licence

Ce projet est sous licence [MIT License](LICENSE).

## Gitignore
Un fichier `.gitignore` est inclus pour maintenir les classes compilées et les fichiers IDE/projet hors du contrôle de version. Les entrées typiques sont :
```
bin/
*.class
*.log
*.iml
.idea/
```

## Remerciements

- Inspiré du système de jeu de rôle sur table Dungeons & Dragons®.
- Merci à la communauté Java open source pour ses outils et bibliothèques.

---

Bonne aventure !
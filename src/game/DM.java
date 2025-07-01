package game;

import game.display.DisplayDungeon;
import game.entities.Entity;
import game.entities.Player;
import game.entities.Monster;
import game.entities.groups.*;
import game.entities.races.*;
import game.items.Equipment;
import game.spells.Spell;
import game.display.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static game.display.Printer.*;
import static game.utils.Utils.*;
import static game.entities.EntityType.*;


/**
 * Classe principale du Maître du Donjon (DM).
 * Gère la création du jeu, des entités, du donjon et la boucle de jeu.
 */
public class DM {
    private Dungeon _dungeon;
    private int _turn = 1;
    private Entity _currentEntity;
    private List<Entity> _entities= new ArrayList<>();
    DisplayDungeon _displayDungeon;
    public DM() {
    }

    //? Gestion des joueurs
    //* Crée x joueurs
    /**
     * Initialise les joueurs pour la partie.
     */
    public void initialisePlayer() {
        Printer.print("How many players for this quest ?(1-10): ");
        int nbPlayers = Printer.readInt();
        if (nbPlayers > 10) {
            Printer.print("The maximum number of players is 10.");
            nbPlayers = 10;
        }
        if (nbPlayers < 1) {
            Printer.print("The minimum number of player is 1.");
            nbPlayers = 1;
        }
        for (int i = 0; i < nbPlayers; i++) {
            allPlayers.add(createPlayer(i + 1));
        }
    }

    //* Crée le joueur avec
    /**
     * Crée un joueur avec nom, race et classe.
     * @param playerNumber numéro du joueur
     * @return le joueur créé
     */
    private Player createPlayer(int playerNumber) {
        Printer.print("Player " + playerNumber + " choose a name: ");
        String name = scanner.next();
        Race playerRace = chooseRace();
        Group playerClass = chooseClass();
        return new Player(name, playerRace, playerClass);
    }
    //* Une race
    /**
     * Permet de choisir une race pour un joueur.
     * @return la race choisie
     */
    private Race chooseRace() {
        Printer.print("Choose a race (1: Dwarf, 2: Elf, 3: Human, 4: Halfling) : ");
        while (true) {
            int choice = Printer.readInt();
            switch (choice) {
                case 1: return new Dwarf();
                case 2: return new Elf();
                case 3: return new Human();
                case 4: return new Halfling();
                default: System.out.print("Invalid race. Please choose again: ");
            }
        }
    }
    //* Et une classe
    /**
     * Permet de choisir une classe pour un joueur.
     * @return la classe choisie
     */
    private Group chooseClass() {
        Printer.print("Choose a class (1: Warrior, 2: Wizard, 3: Rogue, 4: Cleric) : ");
        while (true) {
            int choice = Printer.readInt();
            switch (choice) {
                case 1: return new Warrior();
                case 2: return new Wizard();
                case 3: return new Rogue();
                case 4: return new Cleric();
                default: System.out.print("Invalid class. Please choose again: ");
            }
        }
    }

    //? Gestion des monstres
    /**
     * Permet de choisir et créer des monstres.
     */
    public void chooseMonster() {
        Printer.print("How many monsters do you want to create ? (1-10) : ");
        int number = Printer.readInt();
        if (number > 10) {
            Printer.print("You can't create more than 10 monsters.");
            number = 10;
        }
        if (number < 1) {
            Printer.print("You can't create less than 1 monster.");
            number = 1;
        }
        Printer.print("What species do you want to create ? (1: Angler Fish, 2: Nomaï , 3: Owlkin, 4: Fragon, 5: Other) : ");
        int choice = Printer.readInt();
        String species = "";
        while (choice < 1 || choice > 5) {
            System.out.print("Invalid species. Please choose again: ");
            choice = Printer.readInt();
        }
        switch (choice) {
            case 1: species = "Angler Fish"; break;
            case 2: species = "Nomaï"; break;
            case 3: species = "Owlin"; break;
            case 4: species = "Fragon"; break;
            case 5: Printer.print("Enter the name of the monster: ");
            species = scanner.next(); break;
            default: System.out.print("Boop");
        }
        createMonster(number, species);
    }
    /**
     * Crée un ou plusieurs monstres d'une espèce donnée.
     * @param number nombre de monstres
     * @param species espèce du monstre
     */
    public void createMonster(int number,String species){
        for (int i = 0; i < number; i++) {
            Monster m = new Monster(species,i+1);
        }
    }
    /**
     * Crée le jeu et affiche l'introduction.
     */
    public void createGame(){
        Printer.print(BOLD + RED + "\n\t\t\t\tWelcome to DOOnjon & Dragons\n" + RESET);
        Printer.print(YELLOW+ BOLD +"Beneath the once-peaceful kingdom of Brittle Hollow, something ancient has begun to stir.\n" +
                "Forgotten dungeon doors open, strange noises echo up from the depths, and one unlucky innkeeper swears his favorite ale mug tried to bite him.\n" +
                "Whether by coin, curiosity, or sheer bad luck, you’ve found yourselves tasked with exploring these dark, trap-ridden halls.\n" +
                "Expect danger, puzzles, suspiciously convenient treasure chests — and yes, at least one Mimic. Probably more.\n" +
                "So grab your torches, check your inventory twice… and whatever you do, don’t trust the furniture.\n"+RESET);
        this.initialisePlayer();
    }
    /**
     * Crée un donjon (manuel ou par défaut).
     * @param i numéro du donjon
     */
    public void createDungeon(int i){
        _turn=1;
        Scanner scanner = new Scanner(System.in);
        Printer.print("Dear DM, do you want to create a dungeon manually? (y/n)");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Enter the dimensions of the dungeon (min 15, max 25): ");
            int height = Printer.readInt();
            int width = Printer.readInt();
            this.createBoard(height, width,i);
        } else {
            this.createDefaultBoard(i);
        }
        for (Player player: allPlayers){
            Equipment selectedEquipment= player.chooseEquipment();
            player.equip(selectedEquipment);
        }
    }


    //? Gestion du plateau
    //* Création du donjon par défaut
    /**
     * Crée un donjon par défaut.
     * @param i numéro du donjon
     */
    public void createDefaultBoard(int i) {
            _dungeon = new Dungeon(15, 15,i);
            _dungeon.createDefaultObstacles();
            // Conversion des équipements en liste de coordonnées
            this.chooseMonster();
            _dungeon.setDefaultItemsPosition();
            _dungeon.setDefaultPlayersPosition();
            _dungeon.setDefaultMonstersPosition();
            _entities=this.initEntitiesOrder();
            _displayDungeon= new DisplayDungeon(_dungeon);
        }

    //* Création du Donjon
    /**
     * Crée un donjon avec dimensions personnalisées.
     * @param height hauteur
     * @param width largeur
     * @param i numéro du donjon
     */
    public void createBoard(int height, int width, int i) {
        _dungeon = new Dungeon(height, width, i);
        _dungeon.addObstaclesInteractive();
        _dungeon.addItemsInteractive();
        _dungeon.addMonstersInteractive();
        _dungeon.addPlayersInteractive();
        _entities=this.initEntitiesOrder();
        _displayDungeon= new DisplayDungeon(_dungeon);
    }

    //* Affichage de la Map


    //? Gestion de la partie
    /**
     * Boucle principale de la partie.
     */
    public void play() {
        // Boucle de jeu utilisant this.getPlayers(), this.getDungeon(), etc.
        boolean running = true;
        while (running) {
            int turn=1;
            for (Entity e : this.getEntities()) {
                if (e.getLife() <= 0) {
                    Printer.print(e.getName() + " is dead and cannot play this turn.");
                    continue; // Skip dead entities
                }

                int actions = 3;
                while (actions > 0 && running) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                    this.showTurn(e,turn);
                    _displayDungeon.displayMap();
                    Printer.turn(e,actions);
                    if (e.getType()==PLAYER){
                        turnPlayer((Player)e);
                    }
                    else {
                        this.turnMonster((Monster)e);}
                    if (this.checkEndCondition()) {
                        running = false;
                        break;
                    }
                    this.interact();
                    actions--;
                }
            }
            turn++;
        }
        Printer.end();


    }

    /**
     * Permet au DM d'interagir avec la partie.
     */
    public void interact() {
        Printer.print("Dm, Do you want to interact with the game ? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y")) {
            Printer.print("What do you want to do ? (1: Comment the last action, 2: Move an entity, 3: Hurt an entity, 4:Add Obstacles)");
            int choice = Printer.readInt();
            while (choice < 1 || choice > 4) {
                Printer.print("Invalid choice. Please choose again: ");
                choice = Printer.readInt();
            }
            Entity e=null;
            if (choice == 2 || choice == 3) {
                int entityIndex = chooseEntity();
                e = _entities.get(entityIndex);
            }
            switch (choice) {
                case 1:
                    Printer.print("Comment:");
                    String comment = scanner.next();
                    Printer.comment(comment);
                    break;
                case 2:
                    _dungeon.moveEntity(e);
                    break;
                case 3:
                    Printer.print("How many dice do you want to roll ? (1-20) : ");
                    int dice = Printer.readInt();
                    Printer.print("How many sides does the dice have ? (1-20) : ");
                    int sides = Printer.readInt();
                    int damage= rollDice(dice, sides);
                    Printer.print("You rolled " + damage + " damage.");
                    this.hurt(e,damage);
                    break;
                case  4:
                    _dungeon.addObstaclesInteractive();
                    break;
                default:
                    Printer.print("You destroyed the fabric of spacetime");
                    break;
            }
        }
    }

    /**
     * Permet de choisir une entité pour interaction.
     * @return l'indice de l'entité choisie
     */
    private int chooseEntity() {
        Printer.print("Which entity do you want to interact with ?");
        for (int i = 0; i < _entities.size(); i++) {
            Printer.print("[" + i + "] " + _entities.get(i));
        }
        int entityIndex = Printer.readInt();
        while (entityIndex < 0 || entityIndex >= _entities.size()) {
            Printer.print("Invalid choice. Please choose again: ");
            entityIndex = Printer.readInt();
        }
        Entity e = _entities.get(entityIndex);
        Printer.print("You chose to interact with " + e.getName() + ".");
        return entityIndex;
    }

    /**
     * Inflige des dégâts à une entité.
     * @param e entité cible
     * @param damage dégâts infligés
     */
    private void hurt(Entity e, int damage) {
        e.removeLife(damage);
        if (e.getLife() <= 0) {
            Printer.print(e.getName() + " is dead.");
            _entities.remove(e);
        } else {
            Printer.print(e.getName() + " has " + e.getLife() + " life left.");
        }
    }
    /**
     * Affiche le tour en cours.
     * @param e entité active
     * @param turn numéro du tour
     */
    public void showTurn(Entity e,int turn){
        _currentEntity = e;
        Printer.printShowTurn(_dungeon.getNumber(), turn, _currentEntity, _entities);
    }
    /**
     * Gère le tour d'un monstre.
     * @param m monstre actif
     */
    private void turnMonster(Monster m) {
        int x;
        int y;
        int[] coord;
        int choix = Printer.readInt();
        while (choix < 1 || choix > 5) {
            Printer.print("Invalid choice. Please enter a number between 1 and 4.");
            choix = Printer.readInt();
        }
        if (choix == 3) {
            Printer.print(m.toString());
            turnMonster(m); // Récursion pour autoriser une autre action pendant le tour
        }
        switch (choix) {
            case 1:
                Printer.askCoordinates();
                coord= Printer.readCoordinates();
                x= coord[0];
                y= coord[1];
                int[] size = _dungeon.getSize();
                _dungeon.move(m,size[0],size[1],x,y);
                break;
            case 2:
                Printer.askCoordinates();
                coord= Printer.readCoordinates();
                x= coord[0];
                y= coord[1];
                _dungeon.attackPlayer(m,x,y);
                break;
            case 4:
                Printer.print("DM will comment the last action.");
                Printer.print("Comment:");
                String comment = scanner.next();
                Printer.comment(comment);
                break;
            case 5:
                Printer.print("You chose to skip an action.");
                break;
            default:
                break;
        }
    }
    /**
     * Gère le tour d'un joueur.
     * @param p joueur actif
     */
    private void turnPlayer(Player p) {
        int x=0;
        int y=0;
        int[] coord;
        int choix = Printer.readInt();
        while(!(choix >= 1 && choix <= 8)) {
            Printer.print("Invalid choice. Please enter a number between 1 and 7.");
            choix = Printer.readInt();
        }
        if (choix == 7) {
            Printer.print(p.toString());
            turnPlayer(p); // Récursion pour autoriser une autre action pendant le tour
        }
        switch (choix) {
            case 1:
                Printer.askCoordinates();
                coord= Printer.readCoordinates();
                x= coord[0];
                y= coord[1];
                int[] size = _dungeon.getSize();
                _dungeon.move(p,size[0],size[1],x,y);
                break;
            case 2:
                Printer.askCoordinates();
                coord= Printer.readCoordinates();
                x= coord[0];
                y= coord[1];
                _dungeon.attackMonster(p,x,y);
                break;
            case 3:
                Spell choose= p.chooseSpell();
                if (choose == null) {
                    turnPlayer(p);
                    break;
                }
                _dungeon.casting(p,choose);
                break;
            case 4:
                Equipment selectedEquipment = p.chooseEquipment();;
                p.equip(selectedEquipment);
                break;
            case 5:
                _dungeon.collectEquipment(p);
                break;
            case 6:
                Printer.print("You chose to comment the last action.");
                Printer.print("Comment:");
                String comment = scanner.nextLine();
                Printer.comment(comment);
                break;
            case 8:
                Printer.print("You chose to skip an action.");
                break;
            default:
                break;
        }
    }

    //? Gestion des Entités
    //* Créer une liste de toutes les entités et classe le tableau en fonction de l'initiative + 1 dés 20
    /**
     * Initialise l'ordre des entités pour le tour.
     * @return liste ordonnée des entités
     */
    private List<Entity> initEntitiesOrder() {
        List<Entity> entities = new ArrayList<>();
        entities.addAll(allPlayers);
        entities.addAll(allMonsters);
        List<Entity> ordered = new ArrayList<>(entities);
        ordered.sort((e1, e2) ->
                Integer.compare(
                        rollDice(1, 20) + e2.getInitiative(),
                        rollDice(1, 20) + e1.getInitiative()
                )
        );
        return ordered;
    }

    //? Checkers
    /**
     * Vérifie si la partie doit se terminer.
     * @return true si la partie est terminée, false sinon
     */
    public boolean checkEndCondition() {
        return checkPlayerDead() || checkMonstersDead();
    }

    /**
     * Vérifie si un joueur est mort.
     * @return true si un joueur est mort, false sinon
     */
    public boolean checkPlayerDead(){
        for (Player player : allPlayers) {
            if (player.getLife() <=0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si tous les monstres sont morts.
     * @return true si tous les monstres sont morts, false sinon
     */
    public boolean checkMonstersDead(){
        if (allMonsters.isEmpty()){
            return true;
        }
        return false;
    }

    //? Getters

    /**
     * Retourne la liste des entités du jeu.
     * @return liste des entités
     */
    public List<Entity> getEntities() {
        return _entities;
    }
}
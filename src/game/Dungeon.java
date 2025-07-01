package game;

import game.entities.*;
import game.items.Equipment;
import game.spells.*;
import game.utils.EquipmentRepository;
import game.display.Printer;
import game.utils.Utils;

import java.util.*;

import static game.utils.Utils.*;

/**
 * Classe représentant un Donjon dans le jeu.
 * Gère la carte, les obstacles, les entités et les interactions.
 */
public class Dungeon {
    private final int  _nbDungeon;
    private final int _height;
    private final int _width;
    private final Map _map;
    private List<Equipment> _equipments = new ArrayList<>();

    /**
     * Crée un nouveau donjon avec les dimensions et l'indice spécifiés.
     * @param h hauteur du donjon
     * @param w largeur du donjon
     * @param i numéro du donjon
     */
    public Dungeon(int h, int w, int i) {
        if (w >= 15 && w <= 25 && h >= 15 && h <= 25) {
            _height = h;
            _width = w;
        } else {
            Printer.print("La carte doit faire entre 15 et 25 cases de large et de haut.");
            _height = 15;
            _width = 15;
        }
        _nbDungeon = i;
        _map = new Map();
    }

    /**
     * Génère une position valide et libre aléatoire sur la carte.
     * @return tableau contenant les coordonnées [x, y]
     */
    private int[] defaultPosition(){
        Random r = new Random();
        int x = -1;
        int y = -1;
        while (!_map.isValidPosition(_height,_width, x, y) || !_map.isFree(_height,_width,x,y)) {
            x = r.nextInt(2, _height - 4);
            y = r.nextInt(2, _width - 3);
        }
        return new int[]{x,y};
    }

    /**
     * Crée les obstacles par défaut sur la carte.
     */
    public void createDefaultObstacles() {
        for (int group = 0; group < 4; group++) {
            int[] coord = defaultPosition();
            int x= coord[0];
            int y= coord[1];
            for (int i = 0; i < 6; i++) {
                _map.addObstacle(_height,_width, x, y);
                int direction = (int) (Math.random() * 4);
                switch (direction) {
                    case 0:
                        if (x > 3) x--;
                        break;
                    case 1:
                        if (x < _height - 4) x++;
                        break;
                    case 2:
                        if (y > 2) y--;
                        break;
                    case 3:
                        if (y < _width - 1) y++;
                        break;
                }
            }
        }
    }

    /**
     * Place aléatoirement les équipements par défaut sur la carte.
     */
    public void setDefaultItemsPosition() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Equipment randomItem = allEquipments.get(random.nextInt(allEquipments.size()));
            int[] coord = defaultPosition();
            int x= coord[0];
            int y= coord[1];
            _map.addEquipment(_height,_width,randomItem, x, y);
        }
    }

    /**
     * Place les monstres par défaut sur la carte.
     */
    public void setDefaultMonstersPosition() {
        for (Monster monster : allMonsters) {
            int[] coord= this.defaultPosition();
            _map.addEntity(monster,coord[0],coord[1]);
        }
    }
    /**
     * Place les joueurs par défaut sur la carte.
     */
    public void setDefaultPlayersPosition() {
        for (Player player : allPlayers) {
            int[] coord= this.defaultPosition();
           _map.addEntity(player, coord[0], coord[1]);
        }
    }

    /**
     * Permet d'ajouter des obstacles de façon interactive.
     */
    public void addObstaclesInteractive() {
        Printer.print("Add your obstacles: Type 'stop' to finish.");
        while (true) {
            Printer.askCoordinates();
            String input = scanner.next().toUpperCase();
            if (input.equalsIgnoreCase("STOP")) break;
            try {
                int col = input.charAt(0) - 'A'+1;
                int row = Integer.parseInt(input.substring(1)) ;
                if (_map.isValidPosition(_height,_width, row, col)&& _map.isFree(_height,_width,row,col)) {
                    _map.addObstacle(_height,_width, row, col);
                } else {
                    Printer.print("Invalid coordinates.");
                }
            } catch (Exception e) {
                Printer.print("Invalid input format. Please use the format 'A1' or stop.");
            }
        }
    }
    /**
     * Permet d'ajouter des équipements de façon interactive.
     */
    public void addItemsInteractive() {
        Printer.print("How many equipment? (1-5) : ");
        int nbEquipment = Printer.readInt();
        while (nbEquipment < 1 || nbEquipment > 5) {
            Printer.print("Invalid number. Please enter a number between 1 and 5: ");
            nbEquipment = Printer.readInt();
        }

        EquipmentRepository.printEquipmentList();
        for (int i = 0; i < nbEquipment; i++) {
            Printer.print("Enter the index of the equipment to add (0 to " + (EquipmentRepository.getNumberOfEquipment() - 1) + "): ");
            int index = Printer.readInt();
            while (index < 0 || index >= EquipmentRepository.getNumberOfEquipment()) {
                Printer.print("Invalid choice. Please choose again: ");
                index = Printer.readInt();
            }
            Equipment selectedEquipment = EquipmentRepository.getEquipmentAt(index);
            _equipments.add(selectedEquipment);
            String input = scanner.next().toUpperCase();
            while (!input.equalsIgnoreCase("STOP")) {
                Printer.askCoordinates();
                try {
                    int col = input.charAt(0) - 'A'+1;
                    int row = Integer.parseInt(input.substring(1));
                    _map.addEquipment(_height,_width, selectedEquipment, row, col);
                } catch (Exception e) {
                    Printer.print("Invalid input format. Please use the format 'A1' or stop.");
                }
                input = scanner.next().toUpperCase();
            }
        }
    }
    /**
     * Permet d'ajouter des monstres de façon interactive.
     */
    public void addMonstersInteractive() {
        Printer.print("Add your monsters: Type 'stop' to finish.");
        while (true) {
            System.out.print("Enter monster name (or 'stop'): ");
            String monsterName = scanner.next();
            if (monsterName.equalsIgnoreCase("stop")) break;

            System.out.print("Enter monster pseudo: ");
            String monsterPseudo = scanner.next();

            System.out.print("Enter monster life: ");
            int life = Printer.readInt();

            System.out.print("Enter monster strength: ");
            int strength = Printer.readInt();

            System.out.print("Enter monster dexterity: ");
            int dexterity = Printer.readInt();

            System.out.print("Enter monster speed: ");
            int speed = Printer.readInt();

            System.out.print("Enter monster initiative: ");
            int initiative = Printer.readInt();

            System.out.print("Enter monster armor points: ");
            int armorPoints = Printer.readInt();

            System.out.print("Enter monster attack name: ");
            String attackName = scanner.next();

            System.out.print("Enter monster attack range: ");
            int attackRange = Printer.readInt();

            System.out.print("Enter monster attack value: ");
            int attack = Printer.readInt();

            while (true) {
                Printer.askCoordinates();
                String input = scanner.next().toUpperCase();
                if (input.equalsIgnoreCase("STOP")) break;
                try {
                    int col = input.charAt(0) - 'A'+1;
                    int row = Integer.parseInt(input.substring(1)) ;
                    if (_map.isValidPosition(_height,_width, row, col)&& _map.isFree(_height,_width,row,col)) {
                        Monster m = new Monster(monsterName, 1, life, strength, dexterity, speed, initiative, armorPoints, attackName, attackRange, attack);
                        _map.addEntity(m, row, col);
                        break;
                    } else {
                        Printer.print("Coordonnées invalides.");
                    }
                } catch (Exception e) {
                    Printer.print("Format invalide. Veuillez entrer une lettre suivie d'un chiffre (ex: A5) ou 'stop'.");
                }
            }
        }
    }
    /**
     * Permet d'ajouter les joueurs de façon interactive.
     */
    public void addPlayersInteractive() {
        for (Player player : allPlayers) {
            while (true) {
                Printer.askCoordinates();
                String input = scanner.next().toUpperCase();
                if (input.equalsIgnoreCase("STOP")) break;
                try {
                    int col = input.charAt(0) - 'A'+1;
                    int row = Integer.parseInt(input.substring(1));
                    if (_map.isValidPosition(_height,_width, row, col)&& _map.isFree(_height,_width,row,col)) {
                        _map.addEntity(player, row, col);
                        break;
                    } else {
                        Printer.print("Coordonnées invalides.");
                    }
                } catch (Exception e) {
                    Printer.print("Format invalide. Veuillez entrer une lettre suivie d'un chiffre (ex: A5) ou 'stop'.");
                }
            }
        }
    }

    /**
     * Permet à un joueur de ramasser un équipement à sa position.
     * @param player le joueur qui tente de ramasser l'équipement
     */
    public void collectEquipment(Player player) {
        int[] pos = _map.getEntityPosition(player);
        if (pos == null) {
            Printer.print("Le joueur n'a pas de position connue.");
            return;
        }
        Equipment found = null;
        for (Equipment eq : _map.getEquipmentsPosition().keySet()) {
            int[] eqPos = _map.getPositionOfEquipment(eq);
            if (eqPos[0] == pos[0] && eqPos[1] == pos[1]) {
                found = eq;
                break;
            }
        }
        if (found != null) {
            player.collectEquipment(found);
            _map.removeEquipment(found);
            Printer.print(player.getName() + " picked up " + found.getName());
        } else {
            Printer.print("No item found at your position.");
        }
    }

    /**
     * Permet à un joueur d'attaquer un monstre à une position donnée.
     * @param player le joueur attaquant
     * @param x abscisse de la cible
     * @param y ordonnée de la cible
     */
    public void attackMonster(Player player, int x, int y) {
        if (_map.isValidPosition(_height,_width, x, y)&& !_map.isFree(_height,_width,x,y)) {
            Monster target= _map.getMonsterAt(x,y);
            int distance = player.getRange();
            if (target != null && (Math.abs(_map.getEntityPosition(player)[0] - x) <= distance)&&(Math.abs(_map.getEntityPosition(player)[1] - y) <= distance)) {
                this.damage(player, target);
                if (target.getLife() <= 0) {
                    _map.removeEntity(target);
                }
            } else {
                Printer.print("No target at this position or out of range.");
            }
        } else {
            Printer.print("Invalid position.");
        }
    }

    /**
     * Permet à un monstre d'attaquer un joueur à une position donnée.
     * @param monster le monstre attaquant
     * @param x abscisse de la cible
     * @param y ordonnée de la cible
     */
    public void attackPlayer(Monster monster, int x, int y){
        if (_map.isValidPosition(_height,_width, x, y)&& !_map.isFree(_height,_width,x,y)){
            Player target= _map.getPlayerAt(x,y);
            int distance= monster.getRange();
            if (target != null && (Math.abs(_map.getEntityPosition(monster)[0] - x) <= distance)&&(Math.abs(_map.getEntityPosition(monster)[1] - y) <= distance)) {
                this.damage(monster, target);
            } else {
                Printer.print("No target at this position.");
            }
        } else {
            Printer.print("Invalid position.");
        }
    }

    /**
     * Déplace une entité vers une nouvelle position.
     * @param e entité à déplacer
     * @param maxX hauteur maximale
     * @param maxY largeur maximale
     * @param x nouvelle abscisse
     * @param y nouvelle ordonnée
     */
    public void move(Entity e, int maxX, int maxY, int x, int y){
        _map.move(e,maxX,maxY,x,y);
    }

    /**
     * Applique des dégâts d'une entité à une autre.
     * @param attacker entité attaquante
     * @param target entité cible
     */
    public void damage(Entity attacker, Entity target){
        System.out.println("You attack " + target.getName());
        System.out.println("Roll a d20 to attack (Press Enter)");
        scanner.nextLine();
        scanner.nextLine();
        int attackRoll = Utils.rollDice(1, 20)+ attacker.getMagicBonus();
        System.out.println("You rolled a " + attackRoll);
        if (attacker.getRange()!=1){
        attackRoll += attacker.getDexterity();
        }
        else{
        attackRoll+= attacker.getStrength();
        }
        if (attackRoll  >= target.getArmorPoints()) {
            Printer.print("Hit!");
            int damage= Utils.rollDice(1,attacker.getDamage());
            target.removeLife(damage);
        } else {
            System.out.println("Miss!");
        }
    }

    /**
     * Déplace une entité à une nouvelle position (relocation).
     * @param e entité à déplacer
     */
    public void moveEntity(Entity e) {
        Printer.askCoordinates();
        int[] coord = Printer.readCoordinates();
        int x = coord[0];
        int y = coord[1];
        _map.relocate(e,_height,_width,x,y);
    }
    /**
     * Lance un sort par un joueur.
     * @param p joueur lançant le sort
     * @param spell sort à lancer
     */
    public void casting(Player p, Spell spell) {
        Printer.print("Casting " + spell.getName() + " by " + p.getName());
        spell.castSpell(_map.getEntities());
    }

    //** Getters **/

    /**
     * Retourne la carte du donjon.
     * @return la Map du donjon
     */
    public Map getMap() {
        return _map;
    }

    /**
     * Retourne la taille du donjon.
     * @return tableau [hauteur, largeur]
     */
    public int[] getSize() {
        return new int[]{_height, _width};
    }

    /**
     * Retourne le numéro du donjon.
     * @return numéro du donjon
     */
    public int getNumber(){
        return _nbDungeon;
    }
}
package game;

import game.entities.Entity;
import game.entities.Monster;
import game.entities.Player;
import game.items.Equipment;
import game.display.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static game.utils.Utils.*;

/**
 * Classe représentant la carte du donjon.
 * Gère les positions des entités, équipements et obstacles.
 */
public class Map {
    private HashMap<Entity, int[]> _entitiesPosition;
    private HashMap<Equipment, int[]> _equipmentsPosition;
    private List<int[]> _obstacles = new ArrayList<>();
    public Map() {
        _entitiesPosition= new HashMap<>();
        _equipmentsPosition= new HashMap<>();
    }

    /**
     * Vérifie si une position est valide sur la carte.
     * @param maxX hauteur maximale
     * @param maxY largeur maximale
     * @param x abscisse
     * @param y ordonnée
     * @return true si la position est valide, false sinon
     */
    protected boolean isValidPosition(int maxX, int maxY, int x, int y) {
        return x >= 0 && x < maxX && y >= 0 && y < maxY;
    }

    /**
     * Vérifie si une position est libre (pas d'obstacle, d'entité ou d'équipement).
     * @param maxX hauteur maximale
     * @param maxY largeur maximale
     * @param x abscisse
     * @param y ordonnée
     * @return true si la position est libre, false sinon
     */
    protected boolean isFree(int maxX, int maxY,int x, int y) {
        if (isValidPosition(maxX, maxY, x, y)) {
            for (int[] obstacle : _obstacles) {
                if (obstacle[0] == x && obstacle[1] == y) {
                    return false; // un obstacles
                }
            }
            for (Entity entity : _entitiesPosition.keySet()) {
                int[] position = _entitiesPosition.get(entity);
                if (position!=null && position[0] == x && position[1] == y) {
                    return false; // une entité
                }
            }
            for (Equipment item : _equipmentsPosition.keySet()) {
                int[] position = _equipmentsPosition.get(item);
                if (position!=null && position[0] == x && position[1] == y) {
                    return false; // un equipement
                }
            }
            return true; // libre
        }
        return false; // Invalid coordinates
    }

    private boolean itemPosition (int x, int y){
        for (Equipment item : _equipmentsPosition.keySet()) {
            int[] position = _equipmentsPosition.get(item);
            if (position[0] == x && position[1] == y) {
                return true; // un equipement
            }
        }
        return false; // pas d'equipement
    }

    /**
     * Déplace une entité vers une nouvelle position.
     * @param entity entité à déplacer
     * @param maxX hauteur maximale
     * @param maxY largeur maximale
     * @param x nouvelle abscisse
     * @param y nouvelle ordonnée
     */
    public void move(Entity entity, int maxX, int maxY, int x, int y) {
        int distance = entity.getSpeed() / 3;
        int[] oldPos = this.getEntityPosition(entity);
        while (!((isValidPosition(maxX, maxY, x, y) || itemPosition(x, y))
                && Math.abs(oldPos[0] - x) <= distance
                && Math.abs(oldPos[1] - y) <= distance)) {
            Printer.print("Invalid coordinates. Please enter new coordinates: ");
            int[] coordinates = Printer.readCoordinates();
            x = coordinates[0];
            y = coordinates[1];
        }
        _entitiesPosition.put(entity, new int[]{x, y});
        // Si un équipement était là, on le remet
        Equipment oldItem = getEquipmentAt(oldPos[0], oldPos[1]);
        if (oldItem != null) {
            _equipmentsPosition.put(oldItem, oldPos);
        }
    }

    /**
     * Relocalise une entité à une nouvelle position.
     * @param entity entité à déplacer
     * @param maxX hauteur maximale
     * @param maxY largeur maximale
     * @param x nouvelle abscisse
     * @param y nouvelle ordonnée
     */
    public void relocate(Entity entity,int maxX, int maxY, int x, int y){
        int[] oldPos = this.getEntityPosition(entity);
        while (!(isValidPosition(maxX,maxY, x, y)|| itemPosition(x, y))) {
            Printer.print("Invalid coordinates. Please enter new coordinates: ");
            int[] coordinates = Printer.readCoordinates();
            x = coordinates[0];
            y = coordinates[1];
        }
        _entitiesPosition.put(entity, new int[]{x, y});
        // Remettre l'objet ou un point à l'ancienne position
        Equipment oldItem = getEquipmentAt(oldPos[0], oldPos[1]);
        if (oldItem != null) {
            _equipmentsPosition.put(oldItem, oldPos);
        }
    }

    /**
     * Ajoute un équipement à la carte à la position donnée.
     * @param maxX hauteur maximale
     * @param maxY largeur maximale
     * @param item équipement à ajouter
     * @param x abscisse
     * @param y ordonnée
     */
    public void addEquipment(int maxX, int maxY, Equipment item, int x, int y) {
        if (isValidPosition(maxX, maxY, x, y)&& isFree(maxX,maxY,x,y)) {
            if (item != null) {
                _equipmentsPosition.put(item, new int[]{x, y});
            } else {
                Printer.print("This item does not exist.");
            }
        } else {
            Printer.print("Invalid coordinates.");
        }

    }

    /**
     * Retire un équipement de la carte.
     * @param item équipement à retirer
     */
    public void removeEquipment(Equipment item) {
        if (_equipmentsPosition.containsKey(item)) {
            _equipmentsPosition.remove(item);
        } else {
            Printer.print("This item does not exist.");
        }
    }

    /**
     * Ajoute un obstacle à la carte.
     * @param maxX hauteur maximale
     * @param maxY largeur maximale
     * @param x abscisse
     * @param y ordonnée
     */
    public void addObstacle(int maxX, int maxY,int x, int y) {
        if (this.isValidPosition(maxX, maxY, x, y)) {
            _obstacles.add(new int[]{x, y});
        } else {
            Printer.print("Invalid coordinates to add an obstacle.");
        }
    }

    /**
     * Ajoute une entité à la carte à la position donnée.
     * @param entity entité à ajouter
     * @param x abscisse
     * @param y ordonnée
     */
    public void addEntity(Entity entity,int x, int y) {
        if (!_entitiesPosition.containsKey(entity)) {
            _entitiesPosition.put(entity, new int[]{x,y});
        } else {
            Printer.print("This entity already exists.");
        }
    }

    /**
     * Retire une entité de la carte.
     * @param target entité à retirer
     */
    public void removeEntity(Entity target) {
        if (_entitiesPosition.containsKey(target)) {
            _entitiesPosition.remove(target);
        }
    }



    //? Getters

    /**
     * Retourne la map des équipements et leurs positions.
     * @return HashMap des équipements et positions
     */
    public HashMap<Equipment, int[]> getEquipmentsPosition() {
        return _equipmentsPosition;
    }

    /**
     * Retourne la liste des obstacles.
     * @return liste des coordonnées des obstacles
     */
    public List<int[]> getObstacles() {
        return _obstacles;
    }

    /**
     * Retourne la position d'une entité.
     * @param entity entité recherchée
     * @return tableau [x, y] ou null si non trouvée
     */
    public int[] getEntityPosition(Entity entity) {
        if (_entitiesPosition.containsKey(entity)) {
            return _entitiesPosition.get(entity);
        } else {
            Printer.print("This entity does not exist.");
            return null;
        }
    }

    /**
     * Retourne la position d'un équipement.
     * @param item équipement recherché
     * @return tableau [x, y] ou null si non trouvé
     */
    public int[] getPositionOfEquipment(Equipment item) {
        if (_equipmentsPosition.containsKey(item)) {
            return _equipmentsPosition.get(item);
        } else {
            Printer.print("This item does not exist.");
            return null;
        }
    }

    /**
     * Retourne la map des entités et leurs positions.
     * @return HashMap des entités et positions
     */
    public HashMap<Entity, int[]> getEntities() {
        return _entitiesPosition;
    }

    private Equipment getEquipmentAt(int x, int y) {
        for (Equipment item : _equipmentsPosition.keySet()) {
            int[] position = _equipmentsPosition.get(item);
            if (position[0] == x && position[1] == y) {
                return item;
            }
        }
        return null;
    }

    /**
     * Retourne le monstre à la position donnée.
     * @param x abscisse
     * @param y ordonnée
     * @return le monstre à la position ou null
     */
    public Monster getMonsterAt(int x, int y) {
        for (Entity entity : _entitiesPosition.keySet()) {
            for (Monster m : allMonsters){
                if (entity.equals(m)){
                    int[] position = _entitiesPosition.get(entity);
                    if (position[0] == x && position[1] == y) {
                        return (Monster) entity;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retourne le joueur à la position donnée.
     * @param x abscisse
     * @param y ordonnée
     * @return le joueur à la position ou null
     */
    public Player getPlayerAt(int x, int y) {
        for (Entity entity : _entitiesPosition.keySet()) {
            for (Player player: allPlayers){
                if (entity.equals(player)){
                    int[] position = _entitiesPosition.get(entity);
                    if (position[0] == x && position[1] == y) {
                        return (Player) entity;
                    }
                }
            }
        }
        return null;
    }

}
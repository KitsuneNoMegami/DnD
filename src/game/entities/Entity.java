package game.entities;

import game.items.Weapon;

import static game.entities.EntityType.*;
import static game.utils.Utils.allMonsters;

/**
 * Classe abstraite représentant une entité du jeu (joueur ou monstre).
 */
public abstract class Entity {
    protected String _name;
    protected int _maxHP;
    protected Stats _stats;
    protected Weapon _weapon;
    protected EntityType _type;

    /**
     * Constructeur d'une entité.
     * @param stats statistiques de l'entité
     */
    protected Entity(Stats stats){
        _stats=stats;
        _maxHP=stats.getLife();
    }

    public void addLife(int lifePoints) {
        _stats.setLife(_stats.getLife() + lifePoints);
        if (_stats.getLife() > _maxHP) {
            _stats.setLife(_maxHP);
        }
    }

    public void removeLife(int damage) {
        _stats.setLife(_stats.getLife() - damage);
        if (_stats.getLife() <= 0) {
            if (_type==MONSTER){
                allMonsters.remove(this);
            }
            _stats.setLife(0);
        }
    }


    //? Méthodes pour éviter le instanceof
    public abstract String getName();
    public abstract String getPseudo();
    public abstract int getArmorPoints();
    public EntityType getType(){
        return _type;
    }

    //? Getters
    public Stats getStats() {
        return _stats;
    }
    public int getSpeed() {
        return _stats.getSpeed();
    }
    public int getStrength() {
        return _stats.getStrength();
    }
    public int getDexterity() {
        return _stats.getDexterity();
    }
    public int getInitiative() {
        return _stats.getInitiative();
    }
    public int getDamage() {
        if (_weapon == null) {
            return 1;
        }
        return _weapon.getNbSide();
    }
    public int getRange() {
        if (_weapon==null){
            return 1;
        }
        return _weapon.getRange();
    }
    public int getLife() {
        return _stats.getLife();
    }
    public int getMaxHP(){
        return _maxHP;
    }
    public int getMagicBonus() {
        if (_weapon != null) {
            return _weapon.getBonusDice();
        } else {
            return 0;
        }
    }
    public Weapon getWeapon() {
        return _weapon;
    }


    @Override
    public String toString() {
        return "Entity "+_name;
    }

}

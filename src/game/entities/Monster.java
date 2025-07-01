package game.entities;

import game.items.*;
import game.utils.Utils;

import static game.entities.EntityType.*;
import static game.utils.Utils.*;

/**
 * Classe représentant un monstre dans le jeu.
 */
public class Monster extends Entity {
    private String _species;
    private int _number;
    private int _armorPoints;

    /**
     * Constructeur complet d'un monstre.
     * @param species espèce du monstre
     * @param number numéro du monstre
     * @param life points de vie
     * @param strength force
     * @param dexterity dextérité
     * @param speed vitesse
     * @param initiative initiative
     * @param armorPoints points d'armure
     * @param weaponName nom de l'arme
     * @param range portée de l'arme
     * @param attack valeur d'attaque
     */
    public Monster(String species,int number, int life, int strength, int dexterity, int speed, int initiative, int armorPoints, String weaponName,int range, int attack) {
        super(new Stats(life, strength, dexterity, speed, initiative));
        if (species.length()<3){
            _species= "X"+species+" ";
        }
        else{
            _species = species;
        }
        _number = number;
        _armorPoints = armorPoints;
        if (range==1){
            _weapon= new ComMelee(weaponName,attack,range);
        }
        else{
            _weapon= new Ranged(weaponName,attack,range);
        }
        _type = MONSTER;
        allMonsters.add(this);
    }

    /**
     * Constructeur rapide d'un monstre avec des valeurs aléatoires.
     * @param species espèce du monstre
     * @param number numéro du monstre
     */
    public Monster(String species, int number){
        this(species, number,Utils.rollDice(4,4)+3,
                Utils.rollDice(4,4)+3,
                Utils.rollDice(4,4)+3,
                Utils.rollDice(4,4)+3,
                Utils.rollDice(4,4)+3,
                Utils.rollDice(4,4)+3,
                "MonsterAttack",
                Utils.rollDice(1,10),
                Utils.rollDice(1,10));
    }

    //*Getters
    @Override
    public String getName() {
        return _species +" ~"+_number;
    }

    @Override
    public String getPseudo() {
        return _number+_species.substring(0,2);
    }

    @Override
    public int getArmorPoints() {
        return _armorPoints;
    }

    /**
     * Affiche une représentation textuelle du monstre.
     * @return chaîne descriptive
     */
    @Override
    public String toString() {
        return this.getName() + "\n"
                + "Life ❤\uFE0F\u200B: " + _stats.getLife()+"/"+this.getMaxHP() + "\n"
                + "Strength \uD83D\uDCAA: " + _stats.getStrength() + "\n"
                + "Dexterity \uD83D\uDC0D: " + _stats.getDexterity() + "\n"
                + "Speed ⚡\uFE0F: " + _stats.getSpeed() + "\n"
                + "Initiative ❗\uFE0F: " + _stats.getInitiative() + "\n"
                + "Armor Points : " + _armorPoints + "\n"
                + "Attack Range : " + _weapon.getRange()+ "\n"
                + "Attack : " + _weapon.getName() + "\n";
    }

}

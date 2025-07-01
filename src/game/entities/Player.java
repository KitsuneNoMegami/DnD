package game.entities;

import game.spells.Spell;
import game.entities.groups.Group;
import game.entities.races.Race;
import game.items.*;
import game.display.Printer;
import game.utils.Utils;

import static game.entities.EntityType.*;
import static game.items.EquipmentType.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe représentant un joueur dans le jeu.
 */
public class Player extends Entity {
    private Race _race;
    private Group _group;
    private List<Equipment> _inventory = new ArrayList<>();
    private Armor _armor;
    private List<Spell> _spells;

    /**
     * Constructeur d'un joueur.
     * @param name nom du joueur
     * @param race race du joueur
     * @param group classe/groupe du joueur
     */
    public Player(String name, Race race, Group group) {
        super(new Stats(0, Utils.rollDice(4, 4) + 3, Utils.rollDice(4, 4) + 3, Utils.rollDice(4, 4) + 3, Utils.rollDice(4, 4) + 3));
        if (name.length()<3){
            _name= "("+name+") ";
        }
        else{
            _name = name;
        }
        _race = race;
        _group = group;
        _stats.add(_group.getStats());
        _stats.add(_race.getStats());
        _maxHP= _stats.getLife();
        _inventory.addAll(_group.getEquipments());
        _spells= _group.getSpells();
        _type=PLAYER;
    }

    public void collectEquipment(Equipment equipment) {
        _inventory.add(equipment);
    }
    public void equip(Equipment selectedEquipment) {
        if (selectedEquipment.getType()==WEAPON) {
            unequip(selectedEquipment);
            _weapon= (Weapon) selectedEquipment;
        } else if (selectedEquipment.getType()==ARMOR) {
            unequip(selectedEquipment);
            _armor = (Armor) selectedEquipment;
        } else {
            Printer.print("Equipment is neither an armor or a weapon\n");
            return;
        }
        _inventory.remove(selectedEquipment);
        _stats.add(selectedEquipment.getBonus());
    }
    public void unequip(Equipment e){
        if (e.getType()==WEAPON && _weapon!= null) {
            _stats.remove(_weapon.getBonus());
            _inventory.add(_weapon);
            _weapon=null;
        }
        else if (e.getType()==ARMOR && _armor!= null) {
            _stats.remove(_armor.getBonus());
            _inventory.add(_armor);
            _armor=null;
        }

    }

    /**
     * Permet au joueur de choisir un sort à lancer.
     * @return le sort choisi
     */
    public Spell chooseSpell() {
        Printer.print("Which spell do you want to cast?");
        String spells = "";
        if (_group.getSpells()==null) {
            Printer.print("No spells available in this class.");
            return null;
        }
        for (int i = 0; i < _group.getNumberOfSpells(); i++) {
            spells += "[" + i + "] " + _group.getSpellName(i) + ' ';
        }
        Printer.print(spells);

        int spellIndex = Printer.readInt();
        while (spellIndex < 0 || spellIndex >= _group.getNumberOfSpells()) {
            Printer.print("Invalid choice. Please choose again: ");
            spellIndex = Printer.readInt();
        }
        Spell selectedSpell = _group.getSpellsNumber(spellIndex);
        return selectedSpell;
    }

    /**
     * Permet au joueur de choisir une arme à enchanter.
     * @return l'arme choisie
     */
    public Weapon weaponChoiceEnchantment() {
        List<Equipment> playerInventory= _inventory;
        int i=0;
        String possibilities;
        Printer.print("Choose a weapon to enchant:");
        possibilities="";
        if (_weapon !=null){
            possibilities+= "[" + i + "]" + _weapon.getName();
            i++;
        }
        for (Equipment equipment : playerInventory) {
            if (equipment.getType() == WEAPON) {
                possibilities+="[" + i + "]" + playerInventory.get(i).getName();
                i++;
            }
        }
        Printer.print(possibilities);
        int weaponChoice = Printer.readInt();
        while (weaponChoice < 0 || weaponChoice >= (_weapon!=null ? playerInventory.size()+1: playerInventory.size())) {
            Printer.print("Invalid choice. Please choose again: ");
            weaponChoice = Printer.readInt();
        }
        Weapon choice;
        if (weaponChoice == 0 && _weapon != null) {
            choice = _weapon; // If the first option is the current weapon
        } else if (_weapon != null) {
            choice = (Weapon) playerInventory.get(weaponChoice - 1);
        } else {
            choice = (Weapon) playerInventory.get(weaponChoice);
        }
        return choice;
    }

    /**
     * Permet au joueur de choisir un équipement à équiper.
     * @return l'équipement choisi
     */
    public Equipment chooseEquipment() {
        Printer.print("\n"+_name);
        Printer.print("Which equipment do you want to equip?");
        Printer.print(this.loadInventory());
        int object = Printer.readInt();
        while (object < 0 || object >= _inventory.size()) {
            Printer.print("Invalid choice. Please choose again: ");
            object = Printer.readInt();
        }
        Equipment selectedEquipment = _inventory.get(object);
        return selectedEquipment;
    }

    //? Getters
    public Armor getArmor() {
        return _armor;
    }

    public List<Equipment> getInventory() {
        return Collections.unmodifiableList(_inventory);
    }
    public String loadInventory(){
        String inventory="";
        if (!_inventory.isEmpty()){
            for (int i = 0; i < _inventory.size(); i++) {
                Equipment item= _inventory.get(i);
                if ( item != null) {
                    inventory+="[" + i + "] " +  item.getName() + ' ';
                }
            }
        }
        return inventory;
    }

    @Override
    public String getName() {
        return  _name+ " The "+_group.getName()+" "+_race.getName();
    }
    @Override
    public String getPseudo() {
        return _name.substring(0,3);
    }


    @Override
    public int getArmorPoints() {
        if (_armor!=null){
        return _armor.getArmorPoints();}
        else {
            return 0;
        }
    }

    /**
     * Affiche une représentation textuelle du joueur.
     * @return chaîne descriptive
     */
    @Override
    public String toString() {
        String weapon;
        String armor;
        String inventory = this.loadInventory();
        if (_weapon == null) {
            weapon = "aucune arme";
        } else {
            weapon = _weapon.toString();
        }
        if (_armor == null) {
            armor = "aucune armure";
        } else {
            armor = _armor.toString();
        }

        return this.getName() + "\n"
                + "Life ❤\uFE0F\u200B: " + _stats.getLife()+"/"+this.getMaxHP() + "\n"
                + "Strength \uD83D\uDCAA: " + _stats.getStrength() + "\n"
                + "Dexterity \uD83D\uDC0D: " + _stats.getDexterity() + "\n"
                + "Speed ⚡\uFE0F: " + _stats.getSpeed() + "\n"
                + "Initiative ❗\uFE0F: " + _stats.getInitiative() + "\n"
                + "Armor \uD83D\uDEE1\uFE0F: " + armor + "\n"
                + "Weapon ⚔\uFE0F: " + weapon + "\n"
                + "Inventory \uD83C\uDF92: " + inventory + "\n";
    }

}

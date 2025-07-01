package game.entities.groups;

import game.spells.Spell;
import game.items.Equipment;
import game.entities.Stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Group {
    private String _name;
    private Stats _bonus;
    private List<Equipment> _equipements = new ArrayList<>();
    private List<Spell> _spellsList = new ArrayList<>();

    protected Group(String name, Stats bonus) {
        _name = name;
        _bonus = bonus;
    }

    public void addEquipment(Equipment equipment) {
        if (equipment != null) {
            _equipements.add(equipment);
        } else {
            throw new IllegalArgumentException("Equipment cannot be null");
        }
    }

    public void addSpell(Spell spell) {
        if (spell != null) {
            _spellsList.add(spell);
        } else {
            throw new IllegalArgumentException("Spell cannot be null");
        }
    }
    //* Getters
    public String getName() {
        return _name;
    }

    public Stats getStats() {
        return _bonus;
    }

    public List<Equipment> getEquipments() {
        return Collections.unmodifiableList(_equipements);
    }

    public List<Spell> getSpells() {
        if (_spellsList.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableList(_spellsList);
    }
    public String getSpellName(int i){
        return _spellsList.get(i).getName();
    }
    public int getNumberOfSpells(){
        return _spellsList.size();
    }
    public Spell getSpellsNumber(int spellIndex) {
        return _spellsList.get(spellIndex);
    }


    @Override
    public String toString() {
        String inventory = "";
        for (Equipment equipment : _equipements) {
            if (equipment == null) {
                inventory = "No equipment";
            } else {
                inventory += equipment.getName() + ", ";
            }

        }
        return _name + " \n" + _bonus.toString() + "\n " + inventory;
    }

}

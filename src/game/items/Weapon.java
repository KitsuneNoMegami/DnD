package game.items;
import game.entities.Stats;

import static game.items.EquipmentType.WEAPON;

public abstract class Weapon extends Equipment{
    protected int _range;
    protected int _nbDice;
    protected int _sideDice;
    protected int _bonus=0;

    protected Weapon(String name, Stats bonus, int sideDice, int range) {
        super(name,bonus);
        _range = range;
        _sideDice = sideDice;
        _nbDice = 1;
        _type= WEAPON;
    }

    protected Weapon(String name, Stats bonus, int sideDice, int nbDice, int range) {
        this(name, bonus, sideDice,range);
        _nbDice = nbDice;
    }

    public void addMagic() {
        _bonus+=1;
    }


    //* Getters
    public int getNbSide() {
        return _sideDice;
    }
    public int getNbDice() {
        return _nbDice;
    }
    public int getRange() {
        return _range;
    }
    public int getBonusDice(){
        return _bonus;
    }
    @Override
    public String toString() {
        return _name + " (Range: " + _range+ ", Damage: " + _nbDice+"d"+ _sideDice +")";
    }


}

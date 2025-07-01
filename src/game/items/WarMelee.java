package game.items;

import game.entities.Stats;

public class WarMelee extends Weapon{
    public WarMelee(String name, int damage, int range) {
        super(name,new Stats(0,4,0,-2,0),damage, range);
    }

    public WarMelee(String name, int damage, int range, int nbDice) {
        super(name,new Stats(0,4,0,-2,0),damage, nbDice, range);
    }

    @Override
    public String toString() {
        return _name + " (Range: " + _range+ ", Damage: " + _nbDice+"d"+ _sideDice +",Strength bonus: +4, Speed penalty: -4"+")";
    }

}

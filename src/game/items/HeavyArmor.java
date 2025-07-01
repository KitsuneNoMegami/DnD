package game.items;

import game.entities.Stats;

public class HeavyArmor extends Armor{

    public HeavyArmor(String name, Integer armorPoints) {
        super(name, armorPoints, new Stats(0,0,0,-4,0));
    }

    @Override
    public String toString() {
        return _name +" (Armor class:"+ _armorPoints + ", Speed penalty: -4 )" ;
    }
}

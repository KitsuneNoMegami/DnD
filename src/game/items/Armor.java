package game.items;
import game.entities.Stats;

import static game.items.EquipmentType.ARMOR;

public abstract class Armor extends Equipment {
    protected Integer _armorPoints;

    protected Armor(String name, Integer armorPoints,Stats bonus) {
        super(name,bonus);
        _armorPoints = armorPoints;
        _type=ARMOR;
    }

    public Integer getArmorPoints() {
        return _armorPoints;
    }


    @Override
    public String toString() {
        return _name + " (Armor Points : " + _armorPoints + " )";
    }

}

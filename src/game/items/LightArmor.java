package game.items;

import game.entities.Stats;

public class LightArmor extends Armor{

    public LightArmor(String name, Integer armorPoints) {
        super(name, armorPoints, new Stats(0,0,0,0,0));
    }

}

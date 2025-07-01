package game.items;

import game.entities.Stats;

public class ComMelee extends Weapon {
    public ComMelee(String name, Integer damage, Integer range) {
        super(name,new Stats(),damage, range);
    }

    public ComMelee(String name, Integer damage, Integer range, int nbDice) {
        super(name,new Stats(),damage, nbDice, range);
    }

}

package game.items;

import game.entities.Stats;

public class Ranged extends Weapon{
    public Ranged(String name, Integer damage, Integer range) {
        super(name,new Stats(),damage, range);
    }

    public Ranged(String name, Integer damage, Integer range, int nbDice) {
        super(name,new Stats(),damage, nbDice, range);
    }

}

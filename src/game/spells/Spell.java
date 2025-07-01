package game.spells;

import game.entities.Entity;

import java.util.HashMap;

public abstract class Spell {
    protected String _name;

    protected Spell(String name) {
        _name = name;
    }
    public abstract void castSpell(HashMap<Entity, int[]> entities);
    public String getName() {
        return _name;
    }

}

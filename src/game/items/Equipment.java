package game.items;

import game.entities.Stats;

public abstract class Equipment {
    protected String _name;
    protected Stats _bonus;
    protected EquipmentType _type;
    protected Equipment(String name, Stats bonus) {
        _name=name;
        _bonus=bonus;
    }

    public String getName(){return _name;}
    public Stats getBonus() {
        return _bonus;
    }
    public EquipmentType getType(){
        return _type;
    }

}

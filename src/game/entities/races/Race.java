package game.entities.races;
import game.entities.Stats;

public abstract class Race {
    private String _name;
    private Stats _bonus;

    protected Race(String name, Stats bonus){
        _name=name;
        _bonus=bonus;
    }

    public String getName(){
        return _name;
    }

    public Stats getStats(){
        return _bonus;
    }

    @Override
    public String toString() {
        return _name + " " + _bonus.toString();
    }
}

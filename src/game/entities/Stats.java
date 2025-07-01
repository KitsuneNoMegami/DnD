package game.entities;
public class Stats {
    private int _life;
    private int _strength;
    private int _dexterity;
    private int _speed;
    private int _initiative;

    public Stats(int life, int strength, int dexterity, int speed, int initiative){
        _life=life;
        _strength=strength;
        _dexterity=dexterity;
        _speed=speed;
        _initiative=initiative;
    }
    public Stats(int life){
        this(life,0,0,0,0);
    }
    public Stats(){
        this(0);
    }

    public void add (Stats other){
        _life+=other._life;
        _strength+=other._strength;
        _dexterity+=other._dexterity;
        _speed+=other._speed;
        _initiative+=other._initiative;
    }
    public void remove (Stats other){
        _life-=other._life;
        _strength-=other._strength;
        _dexterity-=other._dexterity;
        _speed-=other._speed;
        _initiative-=other._initiative;
    }

    //* Getters
    public Integer getLife() {
        return _life;
    }
    public Integer getStrength() {
        return _strength;
    }
    public Integer getDexterity() {
        return _dexterity;
    }
    public Integer getSpeed() {
        return _speed;
    }
    public Integer getInitiative() {
        return _initiative;
    }

    //* Setters
    public void setLife(int i) {
        _life = i;
    }

    @Override
    public String toString() {
        return "Stats{" + " life=" + _life +", strength=" + _strength +", dexterity=" + _dexterity +", speed=" + _speed + ", initiative=" + _initiative + " }";
    }


}

package game.entities.groups;

import game.entities.Stats;
import game.spells.BoogieWoogie;
import game.spells.Fireball;
import game.spells.Heal;
import game.utils.EquipmentRepository;

public class Wizard extends Group {
    public Wizard() {
        super("Wizard", new Stats(12));
        addEquipment(EquipmentRepository.STICK.get());
        addEquipment(EquipmentRepository.SLING.get());
        addSpell(new Heal());
        addSpell(new BoogieWoogie());
        addSpell(new Fireball());
    }
}
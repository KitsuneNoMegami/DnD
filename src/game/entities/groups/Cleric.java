package game.entities.groups;

import game.spells.Heal;
import game.entities.Stats;
import game.utils.EquipmentRepository;

public class Cleric extends Group {
    public Cleric() {
        super("Cleric", new Stats(16));
        addEquipment(EquipmentRepository.MACE.get());
        addEquipment(EquipmentRepository.SCALE_ARMOR.get());
        addEquipment(EquipmentRepository.LIGHT_CROSSBOW.get());
        addSpell(new Heal());
    }
}

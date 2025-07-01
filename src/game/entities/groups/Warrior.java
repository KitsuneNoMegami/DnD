package game.entities.groups;

import game.entities.Stats;
import game.utils.EquipmentRepository;

public class Warrior extends Group {
    public Warrior() {
        super("Warrior",new Stats(20));
        addEquipment(EquipmentRepository.CHAINMAIL.get());
        addEquipment(EquipmentRepository.LONGSWORD.get());
        addEquipment(EquipmentRepository.LIGHT_CROSSBOW.get());
    }

}

package game.entities.groups;

import game.entities.Stats;
import game.utils.EquipmentRepository;

public class Rogue extends Group {

    public Rogue() {
        super("Rogue",new Stats(16));
        addEquipment(EquipmentRepository.RAPIER.get());
        addEquipment(EquipmentRepository.SHORTBOW.get());
    }

}

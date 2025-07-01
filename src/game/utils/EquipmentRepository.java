package game.utils;

import game.display.Printer;
import game.items.*;

import java.util.Arrays;
import java.util.List;

public enum EquipmentRepository {
    //light Armor
    SCALE_ARMOR(new LightArmor("Scale armor", 9)),
    HALF_PLATE(new LightArmor("Half-plate", 10)),

    //heavy Armor
    CHAINMAIL(new HeavyArmor("Chainmail", 11)),
    PLATE_ARMOR(new HeavyArmor("Plate Armor", 12)),

    //melee Weapons
    STICK(new ComMelee("Stick", 1, 1, 6)),
    MACE(new ComMelee("Mace", 1, 1, 6)),

    //<ar Melee Weapons
    LONGSWORD(new WarMelee("Longsword", 1, 1, 8)),
    RAPIER(new WarMelee("Rapier", 1, 1, 8)),
    TWO_HANDED_SWORD(new WarMelee("Two-handed sword", 2, 2, 6)),

    //ranged Weapons
    LIGHT_CROSSBOW(new Ranged("Light crossbow", 16, 1, 8)),
    SLING(new Ranged("Sling", 6, 1, 4)),
    SHORTBOW(new Ranged("Shortbow", 16, 1, 6));

    private final Equipment _equipment;

    EquipmentRepository (Equipment equipment) {
        this._equipment = equipment;
    }

    public Equipment get() {
        return _equipment;
    }

    //list of all equipment
    public static List<Equipment> getAllEquipment() {
        return Arrays.asList(EquipmentRepository.values())
                .stream()
                .map(EquipmentRepository::get)
                .toList();
    }

    public static int getNumberOfEquipment() {
        return EquipmentRepository.values().length;
    }

    public static Equipment getEquipmentAt(int index) {
        if (index < 0 || index >= getNumberOfEquipment()) {
            throw new IndexOutOfBoundsException("Invalid equipment index: " + index);
        }
        return EquipmentRepository.values()[index].get();
    }

    public static void printEquipmentList() {
        Printer.print("Available Equipment:");
        for (int i = 0; i < getNumberOfEquipment(); i++) {
            Printer.print("[" + i + "] " + getEquipmentAt(i).getName());
        }
    }
}

package game;
import game.display.Printer;
import game.entities.Player;
import game.entities.groups.*;
import game.entities.races.*;
import game.items.Equipment;
import org.junit.jupiter.api.Test;

import static game.display.Printer.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void testHumanWarriorBonuses() {
        Player p = new Player("Test", new Human(), new Warrior());
        Printer.print(YELLOW+"=== Test: Création Humain Guerrier ===");
        Printer.print(BLUE+"Attendu : PV max = 22, chaque caractéristique >= 2 (bonus humain +2 partout)");
        Printer.print(GREEN+"Obtenu : PV max = " + p.getMaxHP());
        Printer.print(GREEN+"Force = " + p.getStrength());
        Printer.print(GREEN+"Dextérité = " + p.getDexterity());
        Printer.print(GREEN+"Vitesse = " + p.getSpeed());
        Printer.print(GREEN+"Initiative = " + p.getInitiative()+"\n");
        assertEquals(22, p.getMaxHP());
        assertTrue(p.getStrength() >= 2);
        assertTrue(p.getDexterity() >= 2);
        assertTrue(p.getSpeed() >= 2);
        assertTrue(p.getInitiative() >= 2);
    }

    @Test
    void testDwarfClericBonuses() {
        Player p = new Player("Test", new Dwarf(), new Cleric());
        Printer.print(YELLOW+"=== Test: Création Nain Clerc ===");
        Printer.print(BLUE+"Attendu : PV max = 16, force >= 6 (bonus nain +6 force)");
        Printer.print(GREEN+"Obtenu : PV max = " + p.getMaxHP());
        Printer.print(GREEN+"Force = " + p.getStrength()+"\n");
        assertEquals(16, p.getMaxHP());
        assertTrue(p.getStrength() >= 6);
    }

    @Test
    void testElfMagicianBonuses() {
        Player p = new Player("Test", new Elf(), new Wizard());
        Printer.print(YELLOW+"=== Test: Création Elfe Magicien ===");
        Printer.print(BLUE+"Attendu : PV max = 12, dextérité >= 6 (bonus elfe +6 dextérité)");
        Printer.print(GREEN+"Obtenu : PV max = " + p.getMaxHP());
        Printer.print(GREEN+"Dextérité = " + p.getDexterity()+"\n");
        assertEquals(12, p.getMaxHP());
        assertTrue(p.getDexterity() >= 6);
    }

    @Test
    void testHalflingRogueBonuses() {
        Player p = new Player("Test", new Halfling(), new Rogue());
        Printer.print(YELLOW+"=== Test: Création Halfelin Roublard ===");
        Printer.print(BLUE+"Attendu : PV max = 16, dextérité >= 4, vitesse >= 2 (bonus halfelin)");
        Printer.print(GREEN+"Obtenu : PV max = " + p.getMaxHP());
        Printer.print(GREEN+"Dextérité = " + p.getDexterity());
        Printer.print(GREEN+"Vitesse = " + p.getSpeed()+"\n");
        assertEquals(16, p.getMaxHP());
        assertTrue(p.getDexterity() >= 4);
        assertTrue(p.getSpeed() >= 2);
    }

    @Test
    void testInventoryAndEquipmentByClass() {
        Printer.print(YELLOW+"=== Test: Inventaire initial par classe ==="+RESET);

        Player cleric = new Player("C", new Human(), new Cleric());
        Printer.print(GREEN+"Clerc : " + cleric.getInventory().stream().map(e -> e.getName()).toList());
        assertTrue(cleric.getInventory().stream().anyMatch(e -> e.getName().contains("Mace")));
        assertTrue(cleric.getInventory().stream().anyMatch(e -> e.getName().contains("Scale armor")));
        assertTrue(cleric.getInventory().stream().anyMatch(e -> e.getName().contains("Light crossbow")));

        Player warrior = new Player("W", new Human(), new Warrior());
        Printer.print("Guerrier : " + warrior.getInventory().stream().map(e -> e.getName()).toList());
        assertTrue(warrior.getInventory().stream().anyMatch(e -> e.getName().contains("Chainmail")));
        assertTrue(warrior.getInventory().stream().anyMatch(e -> e.getName().contains("Longsword")));
        assertTrue(warrior.getInventory().stream().anyMatch(e -> e.getName().contains("Light crossbow")));

        Player magician = new Player("M", new Human(), new Wizard());
        Printer.print("Magicien : " + magician.getInventory().stream().map(e -> e.getName()).toList());
        assertTrue(magician.getInventory().stream().anyMatch(e -> e.getName().contains("Stick")));
        assertTrue(magician.getInventory().stream().anyMatch(e -> e.getName().contains("Sling")));

        Player rogue = new Player("R", new Human(), new Rogue());
        Printer.print("Roublard : " + rogue.getInventory().stream().map(e -> e.getName()).toList()+RESET+"\n");
        assertTrue(rogue.getInventory().stream().anyMatch(e -> e.getName().contains("Rapier")));
        assertTrue(rogue.getInventory().stream().anyMatch(e -> e.getName().contains("Shortbow")));
    }

    @Test
    void testWarMeleeBonusOnStrengthAndSpeed() {
        Player p = new Player("Test", new Human(), new Warrior());
        Equipment sword = p.getInventory().stream().filter(e -> e.getName().contains("Longsword")).findFirst().orElse(null);
        assertNotNull(sword);
        int baseStrength = p.getStrength();
        int baseSpeed = p.getSpeed();
        p.equip(sword);
        Printer.print(YELLOW+"=== Test: Arme de guerre équipée ===");
        Printer.print(GREEN+"Force après équipement : " + p.getStrength() + " (attendu >= " + (baseStrength+4) + ")");
        Printer.print(GREEN+"Vitesse après équipement : " + p.getSpeed() + " (attendu <= " + (baseSpeed-2) + ")\n");
        assertEquals(baseStrength + 4, p.getStrength());
        assertEquals(baseSpeed - 2, p.getSpeed());
    }

    @Test
    void testHeavyArmorPenaltyOnSpeed() {
        Player p = new Player("Test", new Human(), new Warrior());
        Equipment chainmail = p.getInventory().stream().filter(e -> e.getName().contains("Chainmail")).findFirst().orElse(null);
        assertNotNull(chainmail);
        int baseSpeed = p.getSpeed();
        p.equip(chainmail);
        Printer.print(YELLOW+"=== Test: Armure lourde équipée ===");
        Printer.print(BLUE+ "Vitessse de base : " + baseSpeed);
        Printer.print(BLUE+"Vitesse attendue : " + (baseSpeed - 4));
        Printer.print(GREEN+"Vitesse après équipement : " + p.getSpeed()+"\n");
        assertEquals(baseSpeed - 4, p.getSpeed());
    }

    @Test
    void testOnlyOneWeaponAndArmorEquipped() {
        Player p = new Player("Test", new Human(), new Warrior());
        Equipment sword = p.getInventory().stream().filter(e -> e.getName().contains("Longsword")).findFirst().orElse(null);
        Equipment chainmail = p.getInventory().stream().filter(e -> e.getName().contains("Chainmail")).findFirst().orElse(null);
        Equipment crossbow = p.getInventory().stream().filter(e -> e.getName().contains("Light crossbow")).findFirst().orElse(null);
        assertNotNull(sword);
        assertNotNull(chainmail);
        assertNotNull(crossbow);
        p.equip(sword);
        p.equip(chainmail);
        p.equip(crossbow); // Doit remplacer l'arme précédente
        Printer.print(YELLOW+"=== Test: Un seul équipement porté par type ===");
        Printer.print(GREEN+"Arme équipée : " + p.getWeapon().getName());
        Printer.print(GREEN+"Armure équipée : " + p.getArmor().getName()+"\n");
        assertEquals(crossbow, p.getWeapon());
        assertEquals(chainmail, p.getArmor());
    }
}
package game;
import game.display.Printer;
import org.junit.jupiter.api.Test;

import static game.display.Printer.*;

public class MainTest {
    @Test
    void runAllTests() {
        Printer.print(YELLOW + "\n===============================");
        Printer.print(YELLOW + "   Lancement de tous les tests  ");
        Printer.print(YELLOW + "===============================\n" + RESET);

        // Tests sur la création de personnages
        Printer.print(BLUE + "\n--- Tests de création de personnages ---\n" + RESET);
        PlayerTest playerTest = new PlayerTest();
        playerTest.testHumanWarriorBonuses();
        playerTest.testDwarfClericBonuses();
        playerTest.testElfMagicianBonuses();
        playerTest.testHalflingRogueBonuses();
        playerTest.testInventoryAndEquipmentByClass();
        playerTest.testWarMeleeBonusOnStrengthAndSpeed();
        playerTest.testHeavyArmorPenaltyOnSpeed();
        playerTest.testOnlyOneWeaponAndArmorEquipped();

        // Tests sur le donjon
        Printer.print(BLUE + "\n--- Tests de donjon (collecte, règles, mort, déplacement) ---\n" + RESET);
        DungeonTest dungeonTest = new DungeonTest();
        dungeonTest.testFullDungeonScenario();
        dungeonTest.testPickupEquipmentRemovesFromMap();
        dungeonTest.testPlayerDeath();
        dungeonTest.testMoveEntityRespectsSpeed();


        Printer.print(GREEN + "\n===============================");
        Printer.print(GREEN + "   Tous les tests sont passés ! ");
        Printer.print(GREEN + "===============================\n" + RESET);
    }
}
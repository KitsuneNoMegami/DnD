package game;

import game.display.DisplayDungeon;
import game.display.Printer;
import game.entities.Monster;
import game.entities.Player;
import game.entities.groups.Rogue;
import game.entities.groups.Warrior;
import game.entities.races.Elf;
import game.entities.races.Human;
import game.items.Equipment;
import game.items.WarMelee;
import game.utils.Utils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static game.display.Printer.*;
import static org.junit.jupiter.api.Assertions.*;

class DungeonTest {

    @Test
    void testFullDungeonScenario() {
        Printer.print(YELLOW + "\n===============================");
        Printer.print(YELLOW + "   Scénario complet de donjon   ");
        Printer.print(YELLOW + "===============================\n" + RESET);

        // Création du donjon et de la carte
        Printer.print(BLUE + "\n[1] Création du donjon et de la carte..." + RESET);
        Dungeon dungeon = new Dungeon(15, 15, 1);
        Map map = dungeon.getMap();
        DisplayDungeon displayDungeon = new DisplayDungeon(dungeon);
        Printer.print(GREEN + "Donjon créé avec une carte de taille " + 15 + "x" + 15 + RESET);
        // Affichage de la carte
        Printer.print(BLUE + "\n[1.1] Affichage de la carte vide du donjon..." + RESET);
        displayDungeon.displayMap();
        Printer.print(GREEN + "Carte affichée." + RESET);

        // Création des joueurs
        Printer.print(BLUE + "\n[2] Création des joueurs..." + RESET);
        Player player1 = new Player("Aragorn", new Human(), new Warrior());
        Player player2 = new Player("Legolas", new Elf(), new Rogue());
        Printer.print(GREEN + "Joueurs créés : " + player1.getName() + " (Humain Guerrier), " + player2.getName() + " (Elfe Roublard)" + RESET);

        // Création d'un monstre
        Printer.print(BLUE + "\n[3] Création d'un monstre..." + RESET);
        Monster monster = new Monster("Mimic",1,10,5,2,12,2,3,"Boop",5,2);
        Printer.print(GREEN + "Monstre créé : " + monster.getName() + " (PV: " + monster.getMaxHP() + ")" + RESET);

        // Création d'un équipement
        Printer.print(BLUE + "\n[4] Création d'un équipement..." + RESET);
        Equipment sword = new WarMelee("Anduril", 10, 10, 3);
        Printer.print(GREEN + "Équipement créé : " + sword.getName() + RESET);



        // Placement des entités et objets
        Printer.print(BLUE + "\n[5] Placement des entités et objets sur la carte..." + RESET);
        map.addEquipment(15, 15, sword, 8, 9);
        map.addEntity(player1, 2, 2);
        map.addEntity(player2, 8, 9);
        map.addEntity(monster, 8, 12);
        Printer.print(GREEN + "Placement effectué." + RESET);

        // Vérification du placement
        assertTrue(map.getEntities().containsKey(player1));
        assertTrue(map.getEntities().containsKey(player2));
        assertTrue(map.getEntities().containsKey(monster));
        assertTrue(map.getEquipmentsPosition().containsKey(sword));

        // Affichage de la map avec les entités et objets
        Printer.print(BLUE + "\n[5.1] Affichage de la carte avec les entités et objets..." + RESET);

        // Ajout d'obstacles à des positions visibles
        map.addObstacle(15, 15, 3, 10);
        map.addObstacle(15, 15, 3, 11);
        map.addObstacle(15, 15, 3, 12);
        map.addObstacle(15, 15, 4, 12);
        map.addObstacle(15, 15, 4, 13);
        map.addObstacle(15, 15, 12, 3);
        map.addObstacle(15, 15, 12, 4);
        map.addObstacle(15, 15, 12, 5);
        map.addObstacle(15, 15, 11, 4);
        map.addObstacle(15, 15, 13, 4);

        // Ajout d'équipments sur la carte
        map.addEquipment(15, 15, new WarMelee("Excalibur", 12, 3, 4), 3, 13);
        map.addEquipment(15, 15, new WarMelee("Glamdring", 11, 2, 3), 11, 9);
        map.addEquipment(15, 15, new WarMelee("Sting", 8, 1, 2), 4, 6);

        // Affichage de la carte avec tout le contenu
        displayDungeon.displayMap();
        Printer.print(GREEN + "Carte affichée avec joueurs, monstres, équipements et obstacles." + RESET);

        // Le joueur 2 s'équipe d'une arme
        Printer.print(BLUE + "\n[6] " + player2.getName() + " équipe une arme..." + RESET);
        player2.equip(sword);
        Printer.print(GREEN + player2.getName() + " a équipé : " + sword.getName() + RESET);
        assertFalse(player2.getInventory().contains(sword));
        assertTrue(player2.getWeapon()!=null);

        // Le joueur 2 collecte l'équipement
        Printer.print(BLUE + "\n[7] " + player2.getName() + " collecte l'équipement..." + RESET);
        Printer.print(player2.getName() + " a dans son inventaire : " +
                player2.getInventory().stream().map(Equipment::getName).toList());
        dungeon.collectEquipment(player2);
        Printer.print(GREEN + player2.getName() + " a maintenant dans son inventaire : " +
                player2.getInventory().stream().map(Equipment::getName).toList() + RESET);
        assertTrue(player2.getInventory().contains(sword));
        assertFalse(map.getEquipmentsPosition().containsKey(sword));

        // Simuler un combat (exemple simplifié)
        Printer.print(BLUE + "\n[8] " + player2.getName() + " attaque le monstre..." + RESET);
        int monsterInitialHP = monster.getMaxHP();
        System.setIn(new ByteArrayInputStream("\n\n".getBytes()));
        Utils.scanner = new Scanner(System.in);
        dungeon.attackMonster(player2,8,12);
        Printer.print(GREEN + "PV du monstre après attaque : " + monster.getLife() + " (avant : " + monsterInitialHP + ")" + RESET);
        assertTrue(monster.getLife() < monsterInitialHP);

        // Vérifier la mort du monstre si HP <= 0
        Printer.print(BLUE + "\n[9] Suppression du monstre s'il est mort..." + RESET);
        monster.getStats().setLife(0);
        map.removeEntity(monster);
        Printer.print(GREEN + "Monstre présent sur la carte ? " + map.getEntities().containsKey(monster) + RESET);
        assertFalse(map.getEntities().containsKey(monster));

        // Vérifier l'inventaire des joueurs
        Printer.print(BLUE + "\n[10] Vérification des inventaires des joueurs..." + RESET);
        Printer.print(GREEN + player1.getName() + " inventaire : " + player1.getInventory().stream().map(Equipment::getName).toList());
        Printer.print(GREEN + player2.getName() + " inventaire : " + player2.getInventory().stream().map(Equipment::getName).toList() + RESET);
        assertEquals(3, player1.getInventory().size());
        assertEquals(3, player2.getInventory().size());

        // Vérifier la vie des joueurs
        Printer.print(BLUE + "\n[11] Régénération de la vie de " + player1.getName() + "..." + RESET);
        player1.addLife(10);
        Printer.print(GREEN + player1.getName() + " PV : " + player1.getLife() + RESET);
        assertTrue(player1.getLife() > 0);

        Printer.print(YELLOW + "\n===============================");
        Printer.print(YELLOW + "   Fin du scénario de donjon    ");
        Printer.print(YELLOW + "===============================\n" + RESET);
    }

    @Test
    void testPickupEquipmentRemovesFromMap() {
        Printer.print(BLUE + "\n--- Test: Collecte d'équipement ---\n" + RESET);
        Dungeon dungeon = new Dungeon(15, 15, 1);
        Player player = new Player("Hero", new Human(), new Warrior());
        Equipment sword = new WarMelee("TestSword", 5, 2, 3);
        Map map = dungeon.getMap();
        map.addEquipment(15, 15, sword, 5, 5);
        map.addEntity(player, 5, 5);
        Printer.print(GREEN + "Équipement créé et placé au coordonneées (5, 5)." + RESET);
        dungeon.collectEquipment(player);
        assertTrue(player.getInventory().contains(sword));
        assertFalse(map.getEquipmentsPosition().containsKey(sword));
    }

    @Test
    void testPlayerDeath() {
        Printer.print(BLUE + "\n--- Test: Mort du joueur ---\n" + RESET);
        Dungeon dungeon = new Dungeon(15, 15, 1);
        Player player = new Player("Hero", new Human(), new Warrior());
        Map map = dungeon.getMap();
        map.addEntity(player, 2, 2);
        Printer.print(GREEN + "Joueur initialement en vie avec " + player.getLife() + " PV." + RESET);
        player.removeLife(player.getLife());
        assertFalse(player.getLife()<0);
    }


    @Test
    void testMoveEntityRespectsSpeed() {
        Printer.print(BLUE + "\n--- Test: Déplacement selon la vitesse ---\n" + RESET);
        Dungeon dungeon = new Dungeon(15, 15, 1);
        Player player = new Player("Hero", new Human(), new Warrior());
        Map map = dungeon.getMap();
        map.addEntity(player, 2, 2);
        int speed = player.getSpeed();
        Printer.print(RESET+ "Vitesse du joueur : " + speed);
        int maxMove = speed / 3;
        int[] start = map.getEntityPosition(player);
        Printer.print("Position initiale du joueur : (" + start[0] + ", " + start[1] + ")");
        int[] dest = {start[0] + maxMove, start[1]};
        map.move(player, 15, 15, dest[0], dest[1]);
        Printer.print(GREEN + "Position après déplacement : (" + dest[0] + ", " + dest[1] + ")" + RESET);
        int[] newPos = map.getEntityPosition(player);
        assertEquals(dest[0], newPos[0]);
        assertEquals(dest[1], newPos[1]);
    }
}
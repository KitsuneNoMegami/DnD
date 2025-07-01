package game.spells;

import game.entities.Entity;
import game.entities.Player;
import game.display.Printer;
import game.utils.Utils;

import java.util.HashMap;

import static game.utils.Utils.allPlayers;
import static game.utils.Utils.scanner;

public class Heal extends Spell {

    public Heal() {
        super("Heal");
    }

    public void castSpell(HashMap<Entity, int[]> entities) {
        Printer.print("Who do you want the heal");
        for (int i= 0; i < allPlayers.size(); i++) {
            System.out.println("[" + i + "] " + allPlayers.get(i).getName());
        }
        int choice = Printer.readInt();
        while (choice < 0 || choice >= allPlayers.size()) {
            Printer.print("Invalid choice. Please choose again: ");
            choice = Printer.readInt();
        }
        Player targetPlayer = allPlayers.get(choice);
        Printer.print("Press Enter to cast the Heal spell.");
        scanner.next(); // Consume the newline character
        int lifePoints = Utils.rollDice(1, 10);
        Printer.print("Casting Heal spell to restore " + targetPlayer.getName() + "health.");
        targetPlayer.addLife(lifePoints);
        Printer.print(_name+ " has been healed by " + lifePoints + " points. Their current life is " + targetPlayer.getLife() + "/" + targetPlayer.getMaxHP());

    }

    @Override
    public String toString() {
        return "Spell: " + _name + " - heal 1d10 life points";
    }
}

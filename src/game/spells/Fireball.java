package game.spells;

import game.display.Printer;
import game.entities.Entity;
import game.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static game.utils.Utils.scanner;

public class Fireball extends Spell {

    public Fireball() {
        super("Fireball");
    }

    public void castSpell(HashMap<Entity, int[]> entities) {
        Printer.print("Who to you want to burn ?");
        List<Entity> targets = new ArrayList<>(entities.keySet());
        for (int i = 0; i < targets.size(); i++) {
            System.out.println("[" + i + "] " + targets.get(i).getName());
        }
        int burnChoice = Printer.readInt();
        while (burnChoice < 0 || burnChoice >= targets.size()) {
            Printer.print("Invalid choice. Please choose again: ");
            int choice = Printer.readInt();
        }

        Entity target = targets.get(burnChoice);
        Printer.print("Press Enter to cast the Fireball spell.");
        scanner.nextLine();
        scanner.nextLine();// Consume the newline character
        int damage = Utils.rollDice(8, 6);
        System.out.println("Casting FIREBALL!!!!! on " + target.getName() + " for " + damage + " fire damage.");
        target.removeLife(damage);
        System.out.println(target.getName() + " has taken " + damage + " fire damage. Remaining life: " + target.getLife() + "/" + target.getMaxHP());
    }

    @Override
    public String toString() {
        return "Spell: " + _name + " - deals 8d6 fire damage";
    }

}

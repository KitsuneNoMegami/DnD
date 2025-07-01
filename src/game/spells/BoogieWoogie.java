package game.spells;

import game.display.Printer;
import game.entities.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoogieWoogie extends Spell {

    public BoogieWoogie() {
        super("Boogie Woogie");
    }

    public void castSpell(HashMap<Entity,int[]> entities) {
        // swap positions 1 and 2
        Printer.print("Who do you want to swap positions?");
        System.out.println("Choose two entity from the list:");
        List<Entity> targets = new ArrayList<>(entities.keySet());
        for (int i = 0; i < entities.size(); i++) {
            System.out.println("[" + i + "] " + targets.get(i).getName());
        }
        int choice1 = Printer.readInt();
        int choice2 = Printer.readInt();
        Entity e1= targets.get(choice1);
        Entity e2= targets.get(choice2);
        while (choice1==choice2 || (choice1 < 0 || choice1 >= entities.size())|| (choice2 < 0 || choice2 >= entities.size())) {
            Printer.print("Invalid choice. Please choose again two entities: ");
            choice1 = Printer.readInt();
            choice2 = Printer.readInt();
        }
        //swap Positions between e1 and e2
        int[] pos1 = entities.get(e1);
        int[] pos2 = entities.get(e2);
        entities.put(e1, pos2);
        entities.put(e2, pos1);
        Printer.print("You swap the positions of " + e1.getName() + " and " + e2.getName() + ".");

    }

    @Override
    public String toString() {
        return "Spell: " + _name + " - swap positions of two entities";
    }
}

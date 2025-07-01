package game.spells;

import game.display.Printer;
import game.entities.Entity;
import game.entities.Player;
import game.items.Weapon;

import java.util.HashMap;

import static game.utils.Utils.allPlayers;
import static game.utils.Utils.scanner;

public class MagicWeapon extends Spell{
    public MagicWeapon() {
        super("Magic Weapon");
    }

    public void castSpell(HashMap<Entity,int[]> entities) {
        Printer.print("Who do you want to cast the Magic Weapon spell on?");
        String choices = "";
        for (int i = 0; i < allPlayers.size(); i++) {
            choices +="[" + i + "] " + allPlayers.get(i).getName();
        }
        Printer.print(choices);
        int playerChoice = Printer.readInt();
        while (playerChoice < 0 || playerChoice >= allPlayers.size()) {
            Printer.print("Invalid choice. Please choose again: ");
            playerChoice = Printer.readInt();
        }
        Player spellTarget = allPlayers.get(playerChoice);
        Weapon weaponChoice=spellTarget.weaponChoiceEnchantment();
        Printer.print("Press Enter to cast the Magic Weapon spell.");
        scanner.nextLine();
        scanner.nextLine();// Consume the newline character
        Printer.comment("Casting Magic Weapon on " + weaponChoice.getName() + "of "+spellTarget.getName()+".");
        weaponChoice.addMagic();
        Printer.comment(weaponChoice.getName() + " is now enchanted with magic. It deals additional attack and damager.");
    }

    @Override
    public String toString() {
        return "Spell: " + getName();
    }

}

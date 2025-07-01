package game.display;

import game.entities.Entity;
import game.entities.Monster;

import java.util.List;

import static game.entities.EntityType.*;
import static game.utils.Utils.*;

public class Printer {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BOLD = "\u001B[1m";
    public static final String ITALIC = "\u001B[3m";

    public static void print(String words) {
        System.out.println(words);
    }

    public static void askCoordinates() {
        System.out.println("Enter the coordinates (e.g., A5): ");
    }

    public static int[] readCoordinates() {
        String input = scanner.next().toUpperCase();
        input = input.replaceAll("\\s+", ""); // Supprime tous les espaces
        if (input.length() < 2) throw new IllegalArgumentException("Invalid format");
        char colChar = input.charAt(0);
        int col = colChar - 'A';
        int row = Integer.parseInt(input.substring(1));
        return new int[]{row, col+1};
    }

    // Ajout : méthode utilitaire pour lire un entier avec gestion d'exception
    public static int readInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                print("Invalid input. Please enter a valid integer: ");
                scanner.nextLine();
            }
        }
    }

    public static void printShowTurn(int nbDungeon, int nbTurn, Entity currentEntity, List<Entity> entities) {
        print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        print(PURPLE+"°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°"+RESET);
        print("Dungeon : " + nbDungeon);
        if (currentEntity.getType() == PLAYER) {
            print("                              "+BLUE+currentEntity.getName()+RESET);
        } else{
            print("                              "+RED+currentEntity.getName()+RESET);
        }
        print(PURPLE+"°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°\n"+RESET);
        print("Turn " + nbTurn + " : ");
        for (Entity entity : entities) {
            String prefix = (entity == currentEntity) ? "-> " : "   ";
            String presentation = entity.getName()+ "   (" + entity.getLife() + "/" + entity.getMaxHP() + ")";
            if (entity.getType() == PLAYER) {
                presentation = BLUE + presentation + RESET;
            } else if (entity.getType() == MONSTER) {
                presentation = RED + presentation + RESET;
            }
            Printer.print(prefix +presentation);
        }
    }

    public static void turn(Entity e,int action){
        String entityColor = e.getType() == PLAYER ? BLUE : RED;
        print(entityColor+e.getName()+RESET);
        print("\n"+action + " actions left : ");
        String turn = e.getType() == PLAYER ? "1. Move\n2. Attack\n3. Spell\n4. Equip\n5. Pick up item\n6. Comment the last action\n7. Info\n8. Skip Action" : "1. Move\n2. Attacks\n3. Info\n4. Ask Dm to comment last action \n5. Skip Action";
        print(turn);
    }

    public static void comment(String comment) {
        print("\n" + YELLOW+ ITALIC + comment + RESET);
    }

    public static void end() {
        print(PURPLE+"Dungeon Cleared");
        boolean win=true;
        for (Monster monster : allMonsters) {
            if (monster.getLife() > 0) {
                print(RED+"You lose.. All players were killed..");
                print(PURPLE+"But the DM won, well played !"+RESET);
                win=false;
                break;
            }
        }
        if(win){
            print(YELLOW+"You Won ! All monsters were killed !"+RESET);
        }
    }
}

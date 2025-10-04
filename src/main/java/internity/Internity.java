package internity;

import java.util.Scanner;

import internity.cli.Parser;
import internity.commands.Command;

public class Internity {
    public static void printIntro() {
        String logo = " ___       _                  _ _\n" +
                "|_ _|_ __ | |_ ___ _ __ _ __ (_) |_ _   _\n" +
                " | || '_ \\| __/ _ \\ '__| '_ \\| | __| | | |\n" +
                " | || | | | ||  __/ |  | | | | | |_| |_| |\n" +
                "|___|_| |_|\\__\\___|_|  |_| |_|_|\\__|\\__, |\n" +
                "                                    |___/";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");
    }

    public static void printGreeting(String input) {
        System.out.println("Hello " + input);
    }

    public static void initInternity() {
        printIntro();
        Scanner in = new Scanner(System.in);
        printGreeting(in.nextLine());
        Parser parser = new Parser();
        boolean isExit = false;

        while (!isExit && in.hasNextLine()) {
            String input = in.nextLine();
            try {
                Command command = parser.parseInput(input);
                command.execute();
                isExit = command.isExit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        in.close();
    }

    /**
     * Main entry-point for the java.duke.Internity application.
     */
    public static void main(String[] args) {
        initInternity();
    }
}

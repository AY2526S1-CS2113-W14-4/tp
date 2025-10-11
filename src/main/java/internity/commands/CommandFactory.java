package internity.commands;

import internity.cli.ArgumentParser;
import internity.core.InternityException;

public class CommandFactory {
    public Command createCommand(String commandWord, String args) throws InternityException {
        switch (commandWord) {
        case "delete":
            return ArgumentParser.parseDeleteCommandArgs(args);

        case "update":
            return ArgumentParser.parseUpdateCommandArgs(args);

        case "exit":
            return new ExitCommand();

        default:
            throw InternityException.unknownCommand(commandWord);
        }
    }
}

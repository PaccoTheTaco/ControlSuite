package tictactoe;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicTacToeDenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Invalid usage. Please try again.");
            return true;
        }

        Player invitee = (Player) sender;
        Player inviter = Bukkit.getPlayer(args[0]);

        if (inviter == null) {
            invitee.sendMessage("The player who invited you is no longer online.");
            return true;
        }

        invitee.sendMessage("You have denied the Tic Tac Toe game invitation from " + inviter.getName() + ".");
        inviter.sendMessage(invitee.getName() + " has denied your Tic Tac Toe game invitation.");
        return true;
    }
}

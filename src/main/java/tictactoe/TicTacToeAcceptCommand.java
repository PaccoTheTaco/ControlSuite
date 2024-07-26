package tictactoe;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicTacToeAcceptCommand implements CommandExecutor {
    private final TicTacToeManager ticTacToeManager;
    private final TicTacToeGUI ticTacToeGUI;

    public TicTacToeAcceptCommand(TicTacToeManager ticTacToeManager, TicTacToeGUI ticTacToeGUI) {
        this.ticTacToeManager = ticTacToeManager;
        this.ticTacToeGUI = ticTacToeGUI;
    }

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

        invitee.sendMessage("You have accepted the Tic Tac Toe game invitation from " + inviter.getName() + ".");
        inviter.sendMessage(invitee.getName() + " has accepted your Tic Tac Toe game invitation.");

        ticTacToeManager.startGame(inviter, invitee);
        ticTacToeGUI.openGUI(inviter);
        ticTacToeGUI.openGUI(invitee);

        return true;
    }
}

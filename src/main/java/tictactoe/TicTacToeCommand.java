package tictactoe;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicTacToeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /tictactoe <player>");
            return true;
        }

        Player inviter = (Player) sender;
        Player invitee = Bukkit.getPlayer(args[0]);

        if (invitee == null) {
            inviter.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        invitee.sendMessage(ChatColor.YELLOW + inviter.getName() + " has invited you to play Tic Tac Toe.");
        invitee.spigot().sendMessage(createInviteMessage(inviter, invitee));
        return true;
    }

    private TextComponent createInviteMessage(Player inviter, Player invitee) {
        TextComponent message = new TextComponent("[Accept]");
        message.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tictactoeaccept " + inviter.getName()));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to accept the invitation").create()));

        TextComponent deny = new TextComponent("[Deny]");
        deny.setColor(net.md_5.bungee.api.ChatColor.RED);
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tictactoedeny " + inviter.getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to deny the invitation").create()));

        TextComponent finalMessage = new TextComponent("");
        finalMessage.addExtra(message);
        finalMessage.addExtra(" ");
        finalMessage.addExtra(deny);
        return finalMessage;
    }
}

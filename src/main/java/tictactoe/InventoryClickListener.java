package tictactoe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import tictactoe.TicTacToeGUI;

public class InventoryClickListener implements Listener {
    private TicTacToeGUI ticTacToeGUI;

    public InventoryClickListener(TicTacToeGUI ticTacToeGUI) {
        this.ticTacToeGUI = ticTacToeGUI;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Tic Tac Toe")) {
            ticTacToeGUI.handleClick(event);
        }
    }
}

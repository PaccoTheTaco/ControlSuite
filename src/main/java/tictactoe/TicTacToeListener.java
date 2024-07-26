package tictactoe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TicTacToeListener implements Listener {
    private final TicTacToeGUI ticTacToeGUI;

    public TicTacToeListener(TicTacToeGUI ticTacToeGUI) {
        this.ticTacToeGUI = ticTacToeGUI;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Tic Tac Toe")) {
            ticTacToeGUI.handleClick(event);
        }
    }
}

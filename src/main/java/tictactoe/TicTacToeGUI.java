package tictactoe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TicTacToeGUI {
    private final TicTacToeManager gameManager;
    private final ItemStack fillerItem;

    public TicTacToeGUI(TicTacToeManager gameManager) {
        this.gameManager = gameManager;

        this.fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = fillerItem.getItemMeta();
        meta.setDisplayName(" ");
        fillerItem.setItemMeta(meta);
    }

    public void openGUI(Player player) {
        Inventory gameBoard = Bukkit.createInventory(null, 27, "Tic Tac Toe");
        updateBoard(gameBoard);
        player.openInventory(gameBoard);
    }

    public void handleClick(InventoryClickEvent event) {
        if (!gameManager.isGameActive()) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= 27 || slot % 9 >= 3) {
            event.setCancelled(true);
            return;
        }

        int row = slot / 9;
        int col = slot % 3;

        if (gameManager.getCurrentPlayer() != player) {
            player.sendMessage("It's not your turn.");
            event.setCancelled(true);
            return;
        }

        gameManager.makeMove(player, row, col);
        updateBothPlayers();

        event.setCancelled(true);
    }

    private void updateBoard(Inventory gameBoard) {
        char[][] board = gameManager.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();

                if (board[i][j] == 'X') {
                    item.setType(Material.RED_STAINED_GLASS_PANE);
                    meta.setDisplayName("X");
                } else if (board[i][j] == 'O') {
                    item.setType(Material.GREEN_STAINED_GLASS_PANE);
                    meta.setDisplayName("O");
                } else {
                    meta.setDisplayName(" ");
                }

                item.setItemMeta(meta);
                gameBoard.setItem(i * 9 + j, item);
            }
        }

        for (int i = 3; i < 27; i++) {
            if (i % 9 >= 3) {
                gameBoard.setItem(i, fillerItem);
            }
        }
    }

    private void updateBothPlayers() {
        Player player1 = gameManager.getPlayer1();
        Player player2 = gameManager.getPlayer2();

        if (player1.getOpenInventory().getTitle().equals("Tic Tac Toe")) {
            updateBoard(player1.getOpenInventory().getTopInventory());
        }

        if (player2.getOpenInventory().getTitle().equals("Tic Tac Toe")) {
            updateBoard(player2.getOpenInventory().getTopInventory());
        }
    }
}

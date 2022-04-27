package me.TahaCheji.chestData;


import me.TahaCheji.GameMain;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GamePlayer;
import me.TahaCheji.gameData.PlayerLocation;
import me.TahaCheji.itemData.ItemType;
import me.TahaCheji.itemData.MasterItems;
import me.TahaCheji.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;


public class ChestInteract implements Listener {


    @EventHandler
    public void onChestOpen(InventoryOpenEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if(holder instanceof Chest) {
            Chest chest = (Chest) holder;
            Player player = (Player) e.getPlayer();
            Game game = GameMain.getInstance().getGame(player);
            if (game != null && game.getGamePlayer(player) != null) {
                if (game.isState(Game.GameState.LOBBY) || game.isState(Game.GameState.PREPARATION) || game.isState(Game.GameState.STARTING) || GameMain.getInstance().getPlayer(player).getPlayerLocation() == PlayerLocation.GAMELOBBY) {
                    e.setCancelled(true);
                    return;
                }

                GamePlayer gamePlayer = game.getGamePlayer(player);
                if (gamePlayer.getPlayer() == player) {
                    e.setCancelled(true);
                    handle(e, game);
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!e.getView().getTitle().contains("Chest")) {
            return;
        }
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().getHolder() instanceof Player) return;
        e.setCancelled(true);
        if(e.getCurrentItem() == null) {
            return;
        }
        if(e.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE ||
                e.getCurrentItem().getType() == Material.PAPER || e.getCurrentItem().getType() == Material.PURPLE_STAINED_GLASS_PANE
                || e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        MasterItems masterItems = ItemUtil.getMasterItem(e.getCurrentItem());
        if(masterItems == null) {
            return;
        }
        if(masterItems.getItemType() != ItemType.SPELL) {
            for(int i=0; i<player.getInventory().getSize(); i++) {
                if(player.getInventory().getItem(i) != null) {
                    continue;
                }
                player.getInventory().setItem(i, masterItems.getItem());
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                e.setCurrentItem(new ItemStack(Material.AIR));
                break;
            }
        } else {
            ItemStack itemStack = masterItems.getItem();
            itemStack.setAmount(e.getCurrentItem().getAmount());
            player.getInventory().addItem(itemStack);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
            e.setCurrentItem(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onClickBlock(InventoryClickEvent e) {
        if(!e.getView().getTitle().contains("Chest")) {
            return;
        }
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().getHolder() instanceof Player) return;
        e.setCancelled(true);
        if(e.getCurrentItem() == null) {
            return;
        }
        if(e.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE ||
                e.getCurrentItem().getType() == Material.PAPER || e.getCurrentItem().getType() == Material.PURPLE_STAINED_GLASS_PANE
                || e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        ItemData itemData = ItemUtil.getBlockData(e.getCurrentItem());
        if(itemData == null) {
            return;
        }
        player.getInventory().addItem(itemData.make(e.getCurrentItem().getAmount()));
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
        e.setCurrentItem(new ItemStack(Material.AIR));
    }

    private void handle(InventoryOpenEvent event, Game game) {
            InventoryHolder holder = event.getInventory().getHolder();
            Chest chest = (Chest) holder;
            if(chest == null) {
                return;
            }
            if (game.getOpened().contains(chest) || game.getEpicItems().size() == 0 || game.getNormalItems().size() == 0 || game.getPlayerBoostItems().size() == 0) {
                event.getPlayer().openInventory(GameMain.getChestGui().get(chest));
                return;
            }

            chest.getBlockInventory().clear();
            Block block = chest.getLocation().set(chest.getX(), chest.getY() - 1, chest.getZ()).getBlock();
            if(block.getType() == Material.GREEN_WOOL) {
                new ChestGUI().getGameGui(ChestRarity.NORMAL, chest).open(event.getPlayer());
            }
            if(block.getType() == Material.PURPLE_WOOL) {
                new ChestGUI().getGameGui(ChestRarity.PLAYER_BOOST, chest).open(event.getPlayer());
            }
            if(block.getType() == Material.BLACK_WOOL) {
                new ChestGUI().getGameGui(ChestRarity.EPIC, chest).open(event.getPlayer());
            }

            game.getOpened().add(chest);
    }

}



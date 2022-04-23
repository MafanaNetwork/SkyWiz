package me.TahaCheji.chestData;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.TahaCheji.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ChestGUI  implements Listener {

    private PaginatedGui gui;

    int[] emptySlots = {10, 11, 12, 13, 14, 15, 16, 19, 20 , 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public PaginatedGui getGameGui(ChestRarity rarity, Chest chest) {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text(rarity.getText() + " Chest"))
                .rows(6)
                .pageSize(54)
                .create();

        List<String> lore = new ArrayList<>();
        ItemStack greystainedglass = null;
        if(rarity == ChestRarity.NORMAL) {
            greystainedglass = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        }
        if(rarity == ChestRarity.EPIC) {
            greystainedglass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        }
        if(rarity == ChestRarity.PLAYER_BOOST) {
            greystainedglass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        }
        ItemMeta newmeta = greystainedglass.getItemMeta();
        newmeta.setDisplayName(ChatColor.GRAY + " ");
        newmeta.setLore(lore);
        greystainedglass.setItemMeta(newmeta);
        gui.setItem(0, new GuiItem(greystainedglass));
        gui.setItem(1, new GuiItem(greystainedglass));
        gui.setItem(2, new GuiItem(greystainedglass));
        gui.setItem(3, new GuiItem(greystainedglass));
        gui.setItem(4, new GuiItem(greystainedglass));
        gui.setItem(5, new GuiItem(greystainedglass));
        gui.setItem(6, new GuiItem(greystainedglass));
        gui.setItem(7, new GuiItem(greystainedglass));
        gui.setItem(8, new GuiItem(greystainedglass));
        gui.setItem(17, new GuiItem(greystainedglass));
        gui.setItem(26, new GuiItem(greystainedglass));
        gui.setItem(35, new GuiItem(greystainedglass));
        gui.setItem(45, new GuiItem(greystainedglass));
        gui.setItem(53, new GuiItem(greystainedglass));
        gui.setItem(52, new GuiItem(greystainedglass));
        gui.setItem(51, new GuiItem(greystainedglass));
        gui.setItem(50, new GuiItem(greystainedglass));
        gui.setItem(48, new GuiItem(greystainedglass));
        gui.setItem(47, new GuiItem(greystainedglass));
        gui.setItem(46, new GuiItem(greystainedglass));
        gui.setItem(44, new GuiItem(greystainedglass));
        gui.setItem(36, new GuiItem(greystainedglass));
        gui.setItem(27, new GuiItem(greystainedglass));
        gui.setItem(18, new GuiItem(greystainedglass));
        gui.setItem(9, new GuiItem(greystainedglass));
        gui.setItem(49, new GuiItem(greystainedglass));
        gui.setItem(6, 3, new GuiItem(greystainedglass));
        gui.setItem(6, 7, new GuiItem(greystainedglass));
        if (rarity == ChestRarity.NORMAL) {
            fill(gui, new NormalItems().getNormalItems());
        }
        if (rarity == ChestRarity.EPIC) {
            fill(gui, new EpicItems().getEpicItems());
        }
        if (rarity == ChestRarity.PLAYER_BOOST) {
            fill(gui, new PlayerBoostItems().getPlayerBoostItems());
        }
        this.gui = gui;
        Main.getChestGui().put(chest, gui.getInventory());
        return gui;
    }

    public void fill(PaginatedGui inventory, List<LootItem> lootItem) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Set<LootItem> used = new HashSet<>();

        for(int slotIndex = 0; slotIndex < inventory.getInventory().getSize(); slotIndex++) {
            if(inventory.getGuiItem(slotIndex) != null) {
                continue;
            }
            LootItem randomItem = lootItem.get(random.nextInt(lootItem.size()));
            if(used.contains(randomItem)) continue;
            used.add(randomItem);

            if(randomItem.shouldFill(random)) {
                ItemStack itemStack = randomItem.getItemStack(random);
                inventory.setItem(slotIndex, new GuiItem(itemStack));
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().contains("Chest")) {
            return;
        }
        if(event.getCurrentItem() == null) {
            return;
        }
        if(event.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if(event.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE ||
                event.getCurrentItem().getType() == Material.PAPER || event.getCurrentItem().getType() == Material.PURPLE_STAINED_GLASS_PANE
                || event.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(false);
    }

    public PaginatedGui getGui() {
        return gui;
    }
}

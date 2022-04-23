package me.TahaCheji.chestData;

import me.TahaCheji.gameItems.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerBoostItems {

    public List<LootItem> getPlayerBoostItems () {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(new Gapple().getItem(), .75, 1, 1));
        lootItems.add(new LootItem(new Earthquake().getItem(), .10, 1, 1));
        lootItems.add(new LootItem(new ItemStack(Material.DIAMOND_CHESTPLATE), .25, 1, 1));
        return lootItems;
    }

}

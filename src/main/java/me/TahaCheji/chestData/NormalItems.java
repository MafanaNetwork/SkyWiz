package me.TahaCheji.chestData;

import me.TahaCheji.chestData.items.Stone;
import me.TahaCheji.chestData.items.Wood;
import me.TahaCheji.chestData.items.WoodSword;
import me.TahaCheji.gameItems.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NormalItems {

    public List<LootItem> getNormalItems () {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(new WandOfRespiration().getItem(), .55, 1, 1));
        lootItems.add(new LootItem(new WandOfHell().getItem(), .10, 1, 1));
        lootItems.add(new LootItem(new Wood().make(0), 1, 16, 32));
        lootItems.add(new LootItem(new Stone().make(0), .5, 16, 32));
        lootItems.add(new LootItem(new LandMine().getItem(), .25, 1, 2));
        lootItems.add(new LootItem(new WoodSword().make(0), .50, 1, 1));
        lootItems.add(new LootItem(new Gapple().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new Charge().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new Shield().getItem(), .25, 1, 1));
        return lootItems;
    }

}

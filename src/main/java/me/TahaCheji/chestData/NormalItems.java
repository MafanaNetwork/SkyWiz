package me.TahaCheji.chestData;

import me.TahaCheji.gameItems.Gapple;
import me.TahaCheji.gameItems.ShadowWarp;
import me.TahaCheji.gameItems.WandOfRespiration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NormalItems {

    public List<LootItem> getNormalItems () {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(new WandOfRespiration().getItem(), .50, 1, 1));
        lootItems.add(new LootItem(new ItemStack(Material.OAK_PLANKS), .50, 16, 32));
        lootItems.add(new LootItem(new ItemStack(Material.STONE), .50, 16, 32));
        lootItems.add(new LootItem(new ItemStack(Material.APPLE), .50, 2, 5));
        lootItems.add(new LootItem(new Gapple().getItem(), .25, 1, 1));
        return lootItems;
    }

}

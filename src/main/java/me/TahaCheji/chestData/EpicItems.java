package me.TahaCheji.chestData;

import me.TahaCheji.chestData.items.DiamondChestPlate;
import me.TahaCheji.chestData.items.IronBoots;
import me.TahaCheji.chestData.items.Stone;
import me.TahaCheji.chestData.items.Wood;
import me.TahaCheji.gameItems.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EpicItems {


    public List<LootItem> getEpicItems () {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(new MeteorStaff().getItem(), .45, 1, 1));
        lootItems.add(new LootItem(new ShadowWarp().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new LightningWand().getItem(), .20, 1, 1));
        lootItems.add(new LootItem(new DiamondChestPlate().make(0), .75, 1, 1));
        lootItems.add(new LootItem(new IronBoots().make(0), .50, 1, 1));
        lootItems.add(new LootItem(new Wood().make(0), 1, 16, 32));
        lootItems.add(new LootItem(new Stone().make(0), .50, 16, 32));
        lootItems.add(new LootItem(new AutoBomb().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new Upseis().getItem(), .10, 1, 1));
        lootItems.add(new LootItem(new BlackHole().getItem(), .60, 1, 1));
        lootItems.add(new LootItem(new Blink().getItem(), .75, 1, 1));
        lootItems.add(new LootItem(new Oracle().getItem(), .15, 1, 1));
        return lootItems;
    }

}

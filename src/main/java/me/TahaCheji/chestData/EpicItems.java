package me.TahaCheji.chestData;

import me.TahaCheji.gameItems.LightningWand;
import me.TahaCheji.gameItems.MeteorStaff;
import me.TahaCheji.gameItems.ShadowWarp;
import me.TahaCheji.gameItems.WandOfRespiration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EpicItems {


    public List<LootItem> getEpicItems () {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(new MeteorStaff().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new ShadowWarp().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new LightningWand().getItem(), .20, 1, 1));
        return lootItems;
    }

}

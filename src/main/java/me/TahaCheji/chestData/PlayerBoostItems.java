package me.TahaCheji.chestData;

import me.TahaCheji.chestData.items.DiamondChestPlate;
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
        lootItems.add(new LootItem(new DiamondChestPlate().make(0), .25, 1, 1));
        lootItems.add(new LootItem(new GetOutOfJailFreeCard().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new SpecialCandy().getItem(), .50, 1, 1));
        lootItems.add(new LootItem(new Dash().getItem(), .45, 1, 1));
        lootItems.add(new LootItem(new Overload().getItem(), .50, 1, 1));
        lootItems.add(new LootItem(new Leap().getItem(), .25, 1, 1));
        lootItems.add(new LootItem(new JiuJitsu().getItem(), .25, 1, 1));
        return lootItems;
    }

}

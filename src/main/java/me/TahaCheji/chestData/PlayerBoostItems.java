package me.TahaCheji.chestData;

import me.TahaCheji.gameItems.Earthquake;
import me.TahaCheji.gameItems.Gapple;
import me.TahaCheji.gameItems.WandOfRespiration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerBoostItems {

    public List<ItemStack> getPlayerBoostItems () {
        List<ItemStack> itemStacks = new ArrayList<>();
        int random = ThreadLocalRandom.current().nextInt(2);
        if(random == 1) {
            itemStacks.add(new Gapple().getItem());
        }
        if(random == 0) {
            itemStacks.add(new Earthquake().getItem());
        }
        return itemStacks;
    }

}

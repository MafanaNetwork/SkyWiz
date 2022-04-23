package me.TahaCheji.chestData;

import me.TahaCheji.gameItems.LightningWand;
import me.TahaCheji.gameItems.MeteorStaff;
import me.TahaCheji.gameItems.ShadowWarp;
import me.TahaCheji.gameItems.WandOfRespiration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EpicItems {


    public List<ItemStack> getEpicItems () {
        List<ItemStack> itemStacks = new ArrayList<>();
        int random = ThreadLocalRandom.current().nextInt(3);
        if(random == 2) {
            itemStacks.add(new MeteorStaff().getItem());
        }
        if(random == 1) {
            itemStacks.add(new ShadowWarp().getItem());
        }
        if(random == 0) {
            itemStacks.add(new LightningWand().getItem());
        }
        return itemStacks;
    }

}

package me.TahaCheji.chestData;

import me.TahaCheji.gameItems.ShadowWarp;
import me.TahaCheji.gameItems.WandOfRespiration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NormalItems {

    public List<ItemStack> getNormalItems () {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(new ItemStack(Material.OAK_PLANKS, 32));
        itemStacks.add(new ItemStack(Material.STONE, 16));
        int random = ThreadLocalRandom.current().nextInt(6);
        if(random == 4) {
            itemStacks.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        }
        if(random == 3) {
            itemStacks.add(new ItemStack(Material.IRON_LEGGINGS, 1));
        }
        if(random == 2) {
            itemStacks.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
        }
        if(random == 4) {
            itemStacks.add(new ItemStack(Material.DIAMOND, 1));
        }
        if(random == 5) {
            itemStacks.add(new WandOfRespiration().getItem());
        }
        return itemStacks;
    }

}

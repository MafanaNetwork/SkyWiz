package me.TahaCheji.chestData;

import me.TahaCheji.itemData.MasterItems;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LootItem {


    private final ItemStack itemStack;

    private final double chance;

    private final int minAmount;
    private final int maxAmount;

    public LootItem(ItemStack itemStack, double chance, int minAmount, int maxAmount) {
        this.itemStack = itemStack;
        this.chance = chance;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public boolean shouldFill(Random random) {
        return random.nextDouble() < chance;
    }

    public ItemStack getItemStack(ThreadLocalRandom random) {
        ItemStack item = new ItemStack(itemStack);
        item.setAmount(random.nextInt(minAmount, maxAmount + 1));
        return item;
    }

    public double getChance() {
        return chance;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}

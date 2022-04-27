package me.TahaCheji.chestData;

import me.TahaCheji.GameMain;
import me.TahaCheji.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemData {

    private final Material material;
    private final String name;
    private final int UUID;

    public ItemData(Material material, String name) {
        super();
        this.material = material;
        this.UUID = ItemUtil.stringToSeed(material.name() + name);
        this.name = name;
    }

    public ItemStack make(int amount) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + name);
        item.setAmount(amount);
        item.setItemMeta(meta);
        item = ItemUtil.storeIntInItem(item, this.UUID, "BlockUUID");
        return item;

    }

    public void registerBlock() {
        GameMain.putBlock(name, this);
        System.out.println("Registered " + material.name());
    }

    public String getName() {
        return name;
    }

    public int getUUID() {
        return UUID;
    }

    public Material getMaterial() {
        return material;
    }
}

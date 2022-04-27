package me.TahaCheji.util;

import me.TahaCheji.GameMain;
import me.TahaCheji.chestData.ItemData;
import me.TahaCheji.itemData.MasterItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static ItemStack storeIntInItem(ItemStack host, Integer i, String key) {
        NamespacedKey k = new NamespacedKey(GameMain.getInstance(), key);
        if (host != null) {
            if (host.hasItemMeta()) {
                ItemMeta itemMeta = host.getItemMeta();
                itemMeta.getPersistentDataContainer().set(k, new StoredInt(), i);
                host.setItemMeta(itemMeta);
            }
        }
        return host;
    }

    public static double getDamageReduced(Player player) {
        PlayerInventory inv = player.getInventory();
        ItemStack helmet = inv.getHelmet();
        ItemStack chest = inv.getChestplate();
        ItemStack boots = inv.getBoots();
        ItemStack pants = inv.getLeggings();
        double red = 0.0;
        //
        if(helmet != null) {
            if (helmet.getType() == Material.LEATHER_HELMET) red = red + 0.04;
            else if (helmet.getType() == Material.GOLDEN_BOOTS) red = red + 0.08;
            else if (helmet.getType() == Material.CHAINMAIL_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.IRON_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.DIAMOND_HELMET) red = red + 0.12;
        }
        //
        if(chest != null) {
            if (chest.getType() == Material.LEATHER_CHESTPLATE)    red = red + 0.12;
            else if (chest.getType() == Material.GOLDEN_CHESTPLATE)red = red + 0.20;
            else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) red = red + 0.20;
            else if (chest.getType() == Material.IRON_CHESTPLATE) red = red + 0.24;
            else if (chest.getType() == Material.DIAMOND_CHESTPLATE) red = red + 0.32;
        }
        //
        if(pants != null) {
            if (pants.getType() == Material.LEATHER_LEGGINGS) red = red + 0.08;
            else if (pants.getType() == Material.GOLDEN_CHESTPLATE)    red = red + 0.12;
            else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) red = red + 0.16;
            else if (pants.getType() == Material.IRON_LEGGINGS)    red = red + 0.20;
            else if (pants.getType() == Material.DIAMOND_LEGGINGS) red = red + 0.24;
        }
        //
        if(boots != null) {
            if (boots.getType() == Material.LEATHER_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.GOLDEN_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.CHAINMAIL_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.IRON_BOOTS) red = red + 0.08;
            else if (boots.getType() == Material.DIAMOND_BOOTS)    red = red + 0.12;
        }
        //
        return red;
    }

    public static Integer getIntFromItem(ItemStack host, String key) {
        NamespacedKey k = new NamespacedKey(GameMain.getInstance(), key);
        if (host == null) {
            return 0;
        } else if (!host.hasItemMeta()) {
            return 0;
        } else {
            ItemMeta itemMeta = host.getItemMeta();
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            return container.has(k, new CompactInventory()) ? (Integer)container.get(k, new StoredInt()) : 0;
        }
    }

    public static boolean isMasterItem(ItemStack item) {
        return getIntFromItem(item, "MasterUUID") != 0;
    }

    public static MasterItems getMasterItem(ItemStack item) {
        int UUID = getIntFromItem(item, "MasterUUID");
        return UUID == 0 ? null : GameMain.getItemFromID(UUID);
    }

    public static ItemData getBlockData(ItemStack item) {
        int UUID = getIntFromItem(item, "BlockUUID");
        return UUID == 0 ? null : GameMain.getBlockDataID(UUID);
    }

    public static int stringToSeed(String s) {
        if (s == null) {
            return 0;
        } else {
            int hash = 0;
            char[] var2 = s.toCharArray();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char c = var2[var4];
                hash = 31 * hash + c;
            }

            return hash;
        }
    }

    public static List<String> stringToLore(String string, int characterLimit, ChatColor prefixColor) {
        String[] words = string.split(" ");
        List<String> lines = new ArrayList();
        StringBuilder currentLine = new StringBuilder();
        String[] var6 = words;
        int var7 = words.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String word = var6[var8];
            if (!word.equals("/newline")) {
                if (currentLine.toString().equals("")) {
                    currentLine = new StringBuilder(word);
                } else {
                    currentLine.append(" ").append(word);
                }
            }

            if (word.equals("/newline") || currentLine.length() + word.length() >= characterLimit) {
                String newLine = currentLine.toString();
                lines.add("" + prefixColor + newLine);
                currentLine = new StringBuilder();
            }
        }

        if (currentLine.length() > 0) {
            lines.add("" + prefixColor + currentLine);
        }

        return lines;
    }



}

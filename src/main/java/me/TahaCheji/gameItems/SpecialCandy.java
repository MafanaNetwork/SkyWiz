package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.itemData.*;
import me.TahaCheji.util.AbilityUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpecialCandy extends MasterItems {


    public SpecialCandy() {
        super(null, ChatColor.AQUA + "Special-Candy", Material.SUGAR, ItemType.SPELL, RarityType.REDSTONE, true,
                new MasterAbility("SPEEEEEEEEEEED!", AbilityType.RIGHT_CLICK, 0, 0), true, ChatColor.LIGHT_PURPLE + "This candy tastes funny.");
    }

    @Override
    public void onItemStackCreate(ItemStack var1) {

    }

    @Override
    public boolean leftClickAirAction(Player var1, ItemStack var2) {
        return false;
    }

    @Override
    public boolean leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean rightClickAirAction(Player player, ItemStack var2) {
        CoolDown coolDown = new CoolDown(this, GameMain.getInstance().getPlayer(player));
        if (coolDown.ifCanUse(this)) {
            return false;
        }
        coolDown.addPlayerToCoolDown();
        new AbilityUtil().sendAbility(player, getMasterAbility());
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
        player.getPlayer().sendMessage(ChatColor.AQUA + "This candy... it tastes so good... SPEEEEEEEEEEE");
        player.getPlayer().playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 10, 1);


        return true;
    }

    @Override
    public boolean rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean shiftLeftClickAirAction(Player var1, ItemStack var2) {
        return false;
    }

    @Override
    public boolean shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean shiftRightClickAirAction(Player var1, ItemStack var2) {
        return false;
    }

    @Override
    public boolean shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean middleClickAction(Player var1, ItemStack var2) {
        return false;
    }

    @Override
    public boolean hitEntityAction(Player var1, EntityDamageByEntityEvent var2, Entity var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean breakBlockAction(Player var1, BlockBreakEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean clickedInInventoryAction(Player var1, InventoryClickEvent var2, ItemStack var3, ItemStack var4) {
        return false;
    }
}

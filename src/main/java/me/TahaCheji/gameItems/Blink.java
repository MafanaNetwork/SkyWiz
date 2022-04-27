package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.itemData.*;
import me.TahaCheji.util.AbilityUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Set;

public class Blink extends MasterItems {


    public Blink() {
        super(null, "Blink", Material.INK_SAC, ItemType.SPELL, RarityType.COAL, false,
                new MasterAbility("If you blink you miss it!", AbilityType.RIGHT_CLICK, 0, 0, "Instantly blinks to target location."),
                true, "Yawn.");
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
    public boolean rightClickAirAction(Player var1, ItemStack var2) {
        CoolDown coolDown = new CoolDown(this, GameMain.getInstance().getPlayer(var1));
        if(coolDown.ifCanUse(this)) {
            return false;
        }
        coolDown.addPlayerToCoolDown();
        new AbilityUtil().sendAbility(var1, getMasterAbility());
        ParticleEffect.EXPLOSION_LARGE.display(var1.getLocation().add(0, 1, 0), 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
        ParticleEffect.SPELL_INSTANT.display(var1.getLocation().add(0, 1, 0), 0, 0, 0, .1f, 32, null, Bukkit.getOnlinePlayers());
        var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        Location loc = var1.getTargetBlock((Set<Material>) null, (int) 8).getLocation().add(0, 1, 0);
        loc.setYaw(var1.getLocation().getYaw());
        loc.setPitch(var1.getLocation().getPitch());
        var1.teleport(loc);
        ParticleEffect.EXPLOSION_LARGE.display(var1.getLocation().add(0, 1, 0), 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
        ParticleEffect.SPELL_INSTANT.display(var1.getLocation().add(0, 1, 0), 0, 0, 0, .1f, 32, null, Bukkit.getOnlinePlayers());
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

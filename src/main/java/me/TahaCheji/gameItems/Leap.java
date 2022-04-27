package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.itemData.*;
import me.TahaCheji.util.AbilityUtil;
import org.bukkit.Bukkit;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class Leap extends MasterItems {


    public Leap() {
        super(null, "Leap", Material.SLIME_BALL, ItemType.WAND, RarityType.LAPIS, false,
                new MasterAbility("JUMP I TELL YOU JUMP", AbilityType.RIGHT_CLICK, 5, 0, "Powerfully leaps to target location."),
                false, "Jett.");
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
        double force = 1;
        var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 0);
        ParticleEffect.EXPLOSION_NORMAL.display(var1.getLocation(),0, 0, 0, .1f, 16, null, Bukkit.getOnlinePlayers());
        Vector v = var1.getEyeLocation().getDirection().multiply(2 * force);
        v.setY(v.getY() / 2);
        var1.setVelocity(v);
        new BukkitRunnable() {
            double ti = 0;

            public void run() {
                ti++;
                if (ti > 20)
                    cancel();
                ParticleEffect.CLOUD.display(var1.getLocation().add(0, 1, 0), 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
            }
        }.runTaskTimer(GameMain.getInstance(), 0, 1);
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

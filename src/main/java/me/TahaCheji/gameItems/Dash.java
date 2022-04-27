package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.itemData.*;
import me.TahaCheji.managers.DamageManager;
import me.TahaCheji.util.AbilityUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class Dash extends MasterItems {


    public Dash() {
        super(null, "Dash", Material.FEATHER, ItemType.SPELL, RarityType.DIAMOND, true,
                new MasterAbility("Rebibe me jett", AbilityType.RIGHT_CLICK, 0, 3, "Performs a quick dash forwards, dealing damage to hit enemies."),
                true, "I got this one from valorant this time!");
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
        new BukkitRunnable() {
            int j = 0;
            Vector vec = var1.getEyeLocation().getDirection();
            List<Integer> hit = new ArrayList<>();

            public void run() {
                j++;
                if (j > 10 * Math.min(10, 1))
                    cancel();

                var1.setVelocity(vec);
                ParticleEffect.SMOKE_LARGE.display(var1.getLocation().add(0, 1, 0), 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 2);
                for (Entity entity : var1.getNearbyEntities(1, 1, 1)) {
                    if (!hit.contains(entity.getEntityId())) {
                        hit.add(entity.getEntityId());
                        new DamageManager(var1, (LivingEntity) entity, getMasterAbility()).damage();
                    }
                }
            }
        }.runTaskTimer(GameMain.getInstance(), 0, 2);
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

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


public class Charge extends MasterItems {


    public Charge() {
        super(null, "Charge", Material.GOLD_INGOT, ItemType.SPELL, RarityType.GOLD, true,
                new MasterAbility("CHARGEEEE", AbilityType.RIGHT_CLICK, 0, 5, "Dashes forwards, dealing damage and knockback to the first player you hit."),
                true, "OverWatch again lol.");
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
            double ti = 0;
            Vector vec = new AbilityUtil().getTargetDirection(var1, null).setY(-1);
            public void run() {
                ti++;
                if (ti < 9) {
                    var1.setVelocity(vec);
                    ParticleEffect.EXPLOSION_NORMAL.display(var1.getLocation().add(0, 1, 0), .13f, .13f, .13f, 0, 3, null, Bukkit.getOnlinePlayers());
                }
                if (ti > 20)
                    cancel();

                for (Entity target : var1.getNearbyEntities(1, 1, 1)) {
                    var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 1);
                    ParticleEffect.EXPLOSION_LARGE.display(target.getLocation().add(0, 1, 0), 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                    target.setVelocity(var1.getVelocity().setY(0.3).multiply(1.7 * 1));
                    var1.setVelocity(var1.getVelocity().setX(0).setY(0).setZ(0));
                    new DamageManager(var1, (LivingEntity) target, getMasterAbility()).damage();
                    cancel();
                    break;
                }
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

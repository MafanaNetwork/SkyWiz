package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.itemData.*;
import me.TahaCheji.managers.DamageManager;
import me.TahaCheji.util.AbilityUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleEffect;

public class Overload extends MasterItems {


    public Overload() {
        super(null, "Overload", Material.IRON_INGOT, ItemType.SPELL, RarityType.IRON, true,
                new MasterAbility("Over Load Them Mother F*ckers", AbilityType.RIGHT_CLICK, 5, 10,
                        "Summons an electrical shockwave around you, dealing damage to nearby enemies."), true,
                "OverWatch right?");
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
        double radius = 7;

        var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 0);
        var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 2, 0);
        var1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 254));

        for (Entity entity : var1.getNearbyEntities(radius, radius, radius)) {
            if (entity.equals(var1) || !(entity instanceof LivingEntity)) {
                continue;
            }
            new DamageManager(var1, (LivingEntity) entity, getMasterAbility()).damage();
        }
        double step = 12 + (radius * 2.5);
        for (double j = 0; j < Math.PI * 2; j += Math.PI / step) {
            Location loc = var1.getLocation().clone().add(Math.cos(j) * radius, 1, Math.sin(j) * radius);
            ParticleEffect.CLOUD.display(loc, 0, 0, 0, .05f, 4, null, Bukkit.getOnlinePlayers());
            ParticleEffect.FIREWORKS_SPARK.display(loc, 0, 0, 0, .05f, 4, null, Bukkit.getOnlinePlayers());
        }
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

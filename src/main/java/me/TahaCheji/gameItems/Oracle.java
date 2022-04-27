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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;

public class Oracle extends MasterItems {


    public Oracle() {
        super(null, "Oracle", Material.WHITE_DYE, ItemType.WAND, RarityType.REDSTONE, true,
                new MasterAbility("Fire Crystal", AbilityType.RIGHT_CLICK, 2, 2, "Summons an ice crystal that deals damage to the first enemy it hits.",
                        "Also applies Burns for a few seconds."), false, "Hazzza!");
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
        var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
        new BukkitRunnable() {
            Vector vec = new AbilityUtil().getTargetDirection(var1, null).multiply(.7);
            Location loc = var1.getEyeLocation();
            int ti = 0;

            public void run() {
                ti++;
                if (ti > 25)
                    cancel();

                loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 1);
                List<Entity> entities = AbilityUtil.getNearbyChunkEntities(loc);
                for (int j = 0; j < 3; j++) {
                    loc.add(vec);
                    if (loc.getBlock().getType().isSolid())
                        cancel();

                    for (int i = 3; i < 6; i++)
                        ParticleEffect.SNOW_SHOVEL.display(loc, new Vector(0, .7, 0), .07f * i, 3, null, Bukkit.getOnlinePlayers());
                    ParticleEffect.FIREWORKS_SPARK.display(loc, 0, 0, 0, .04f, 1, null, Bukkit.getOnlinePlayers());

                    for (Entity target : entities) {
                        Player player = (Player) target;
                        if(player.getUniqueId().toString().equalsIgnoreCase(var1.getUniqueId().toString())) {
                            continue;
                        }
                        ParticleEffect.EXPLOSION_LARGE.display(loc, 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                        ParticleEffect.SNOW_SHOVEL.display(loc,0, 0, 0, .13f, 48, null, Bukkit.getOnlinePlayers());
                        ParticleEffect.FIREWORKS_SPARK.display(loc, 0, 0, 0, .2f, 24, null, Bukkit.getOnlinePlayers());
                        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
                        new DamageManager(var1, (LivingEntity) target, getMasterAbility()).damage();
                        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) 3 * 20, (int) 1));
                        cancel();
                        return;
                    }
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

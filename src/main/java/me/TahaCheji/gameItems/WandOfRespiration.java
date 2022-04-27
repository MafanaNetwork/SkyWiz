package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.gameData.GamePlayer;
import me.TahaCheji.itemData.*;
import me.TahaCheji.managers.DamageManager;
import me.TahaCheji.util.AbilityUtil;
import org.bukkit.*;
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

public class WandOfRespiration extends MasterItems {


    public WandOfRespiration() {
        super(null,"WandOfRespiration", Material.STICK, ItemType.STAFF, RarityType.REDSTONE, true, new MasterAbility("Celestial Damage", AbilityType.RIGHT_CLICK, 1.5, 2, "Right click to summon a beam dealing 2 damage"), false, "The magic comes from you.");
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
        GamePlayer gamePlayer = GameMain.getInstance().getPlayer(player);
        CoolDown coolDown = new CoolDown(this, GameMain.getInstance().getPlayer(player));
        if(coolDown.ifCanUse(this)){
            return false;
        }
        coolDown.addPlayerToCoolDown();
            new AbilityUtil().sendAbility(player, getMasterAbility());
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
            new BukkitRunnable() {
                Vector vec = new AbilityUtil().getTargetDirection(player, null);
                Location loc = player.getEyeLocation();
                double ti = 0;

                public void run() {
                    ti++;
                    if (loc.getBlock().getType().isSolid())
                        cancel();

                    loc.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_HAT, 2, 1);
                    for (int j = 0; j < 2; j++) {
                        loc.add(vec);

                        for (double i = -Math.PI; i < Math.PI; i += Math.PI / 2) {
                            Vector v = new Vector(Math.cos(i + ti / 4), Math.sin(i + ti / 4), 0);
                            ParticleEffect.FIREWORKS_SPARK.display(loc, AbilityUtil.rotateFunc(v, loc), .09f, 0, null, Bukkit.getOnlinePlayers());
                        }
                        for (Entity target : loc.getNearbyEntities(1, 1, 1)) {
                            if (target.equals(player) || !(target instanceof LivingEntity)) {
                                continue;
                            }
                            ParticleEffect.EXPLOSION_LARGE.display(loc, 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                            ParticleEffect.FIREWORKS_SPARK.display(loc, 0, 0, 0, .2f, 32, null, Bukkit.getOnlinePlayers());
                            loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
                            new DamageManager(player, (LivingEntity) target, getMasterAbility()).damage();
                            target.setVelocity(target.getLocation().toVector().subtract(loc.toVector()).multiply(.2).setY(.5));
                            target.setVelocity(target.getLocation().toVector().subtract(loc.toVector()).multiply(.2).setZ(.5));
                            cancel();
                            return;
                        }
                        if (ti > 40)
                            cancel();
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

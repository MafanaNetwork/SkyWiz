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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import static me.TahaCheji.util.AbilityUtil.random;

public class BlackHole extends MasterItems {


    public BlackHole() {
        super(null, "BlackHole", Material.BLACK_DYE, ItemType.WAND, RarityType.OBSIDAIN, false,
                new MasterAbility("Slow them", AbilityType.RIGHT_CLICK, 5, 1, "Summons a vortex that pulls mobs in its center."),
                false, "This isnt a spell but it looks like it!");
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
        Location loc = new AbilityUtil().getTargetLocation(var1, null);
        if (loc == null)
            return false;

        double duration = 2 * 20;
        double radius = 2;

        loc.getWorld().playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 2, 1);
        new BukkitRunnable() {
            int ti = 0;
            double r = 4;

            public void run() {
                if (ti++ > Math.min(300, duration))
                    cancel();

                loc.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_HAT, 2, 2);
                ParticleEffect.EXPLOSION_LARGE.display(loc, 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                for (int j = 0; j < 3; j++) {
                    double ran = random.nextDouble() * Math.PI * 2;
                    double ran_y = random.nextDouble() * 2 - 1;
                    double x = Math.cos(ran) * Math.sin(ran_y * Math.PI * 2);
                    double z = Math.sin(ran) * Math.sin(ran_y * Math.PI * 2);
                    Location loc1 = loc.clone().add(x * r, ran_y * r, z * r);
                    Vector v = loc.toVector().subtract(loc1.toVector());
                }

                for (Entity entity : AbilityUtil.getNearbyChunkEntities(loc))
                    if (entity.getLocation().distanceSquared(loc) < Math.pow(radius, 2))
                        entity.setVelocity(normalizeIfNotNull(loc.clone().subtract(entity.getLocation()).toVector()).multiply(.5));
            }
        }.runTaskTimer(GameMain.getInstance(), 0, 1);
        return true;
    }

    private Vector normalizeIfNotNull(Vector vector) {
        return vector.length() == 0 ? vector : vector.normalize();
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

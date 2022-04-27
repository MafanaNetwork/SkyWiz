package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.gameData.GamePlayer;
import me.TahaCheji.itemData.*;
import me.TahaCheji.util.AbilityUtil;
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
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static me.TahaCheji.util.AbilityUtil.random;

public class Shield extends MasterItems {


    public Shield() {
        super(null, "Shield", Material.BRICK, ItemType.SPELL, RarityType.LAPIS, true,
                new MasterAbility("Magic Shield", AbilityType.RIGHT_CLICK, 5, 0, "Creates a shield around you, reducing damage taken for nearby players."),
                true, "This one is a apex ability lol.");
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

    public static Map<Location, Double[]> magicalShield = new HashMap<Location, Double[]>();
    public static HashMap<GamePlayer, Boolean> isOnSheild = new HashMap<>();

    @Override
    public boolean rightClickAirAction(Player var1, ItemStack var2) {
        CoolDown coolDown = new CoolDown(this, GameMain.getInstance().getPlayer(var1));
        if(coolDown.ifCanUse(this)) {
            return false;
        }
        coolDown.addPlayerToCoolDown();
        new AbilityUtil().sendAbility(var1, getMasterAbility());
        double duration = 5;
        double radius = Math.pow(5, 2);
        double power = 40 / 100;

        final Location loc = var1.getLocation().clone();
        var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3, 0);
        magicalShield.put(loc, new Double[] { radius, power });
        new BukkitRunnable() {
            int ti = 0;

            public void run() {
                ti++;
                for (double j = 0; j < Math.PI / 2; j += Math.PI / (28 + random.nextInt(5)))
                    for (double i = 0; i < Math.PI * 2; i += Math.PI / (14 + random.nextInt(5))) {
                        Location loc1 = var1.getLocation().clone().add(2.5 * Math.cos(i + j) * Math.sin(j), 2.5 * Math.cos(j), 2.5 * Math.sin(i + j) * Math.sin(j));
                        ParticleEffect.REDSTONE.display(loc1, Color.ORANGE);
                        isOnSheild.put(GameMain.getInstance().getPlayer(var1), true);
                    }
                if (ti > duration * 20 / 3) {
                    magicalShield.remove(loc);
                    isOnSheild.put(GameMain.getInstance().getPlayer(var1), false);
                    cancel();
                }
            }
        }.runTaskTimer(GameMain.getInstance(), 0, 3);
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

package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.itemData.*;
import me.TahaCheji.util.AbilityUtil;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mcmonkey.sentinel.SentinelTrait;
import org.mcmonkey.sentinel.events.SentinelAttackEvent;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class JiuJitsu extends MasterItems implements Listener {


    public JiuJitsu() {
        super(null, "JiuJitsu", Material.GRAY_DYE, ItemType.SPELL, RarityType.COAL, false,
                new MasterAbility("Shadow Clone!", AbilityType.RIGHT_CLICK, 0, 5, "Creates copy of the player."), true,
                "Guess where I got this idea from.");

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

    public NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

    @Override
    public boolean rightClickAirAction(Player var1, ItemStack var2) {
        CoolDown coolDown = new CoolDown(this, GameMain.getInstance().getPlayer(var1));
        if (coolDown.ifCanUse(this)) {
            return false;
        }
        coolDown.addPlayerToCoolDown();
        new AbilityUtil().sendAbility(var1, getMasterAbility());
        NPC npc = registry.createNPC(EntityType.PLAYER, "Enemy");
        SentinelTrait sentinel = npc.getOrAddTrait(SentinelTrait.class);
        npc.setName(var1.getDisplayName());
        for(Entity entity : var1.getNearbyEntities(5, 5,5)) {
            if(entity instanceof Player) {
                if(!entity.getUniqueId().toString().equalsIgnoreCase(var1.getUniqueId().toString())) {
                    sentinel.addTarget("uuid:" + entity.getUniqueId());
                }
            }
        }
        sentinel.setHealth(1);
        sentinel.damage = 10;
        npc.spawn(getRandomLocation(var1.getLocation(), var1.getLocation().add(3, 0, 3)));

        var1.getWorld().playSound(var1.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3, 1);
        ParticleEffect.SMOKE_LARGE.display(var1.getLocation(), Color.WHITE);
        var1.teleport(getRandomLocation(var1.getLocation(), var1.getLocation().add(3, 0, 3)));
        Bukkit.getScheduler().scheduleSyncDelayedTask(GameMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                    npc.despawn();
            }
        }, 10 * 20);
        return true;
    }

    @EventHandler
    public void onHit(SentinelAttackEvent event) {
        event.getTarget().getWorld().createExplosion(event.getTarget().getLocation(), 5);
        event.getNPC().despawn();
    }

    public static Location getRandomLocation(Location loc1, Location loc2) {
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());
        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());
        return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));
    }

    public static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
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

package me.TahaCheji.managers;

import me.TahaCheji.GameMain;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameItems.Shield;
import me.TahaCheji.itemData.MasterAbility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageManager {

    private final Player damager;
    private final LivingEntity target;
    private final MasterAbility ability;

    public DamageManager(Player damager, LivingEntity target, MasterAbility ability) {
        this.damager = damager;
        this.target = target;
        this.ability = ability;
    }

    public void damage () {
        if(target instanceof ArmorStand) {
            return;
        }
        if(Shield.isOnSheild.containsKey(GameMain.getInstance().getPlayer((Player) target))) {
            return;
        }
        double armor = target.getAttribute(Attribute.GENERIC_ARMOR).getValue();
        int damage = (int) ((ability.getAbilityDamage()) - (armor / 10));
        if(target.getHealth() <= damage) {
            Game game = GameMain.getInstance().getGame((Player) target);
            Player player = (Player) target;
            new DeathManager(GameMain.getInstance().getPlayer(damager), GameMain.getInstance().getPlayer(player), game).handle();
            return;
        }
        target.damage(damage);
        Location loc = target.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
        damager.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(ChatColor.DARK_PURPLE + "✧" + damage);
            Bukkit.getScheduler().runTaskLater(GameMain.getInstance(), armorStand::remove, 20); // Time in ticks (20 ticks = 1 second)
        });
        damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 2, 1);
    }


    public Player getDamager() {
        return damager;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public MasterAbility getAbility() {
        return ability;
    }


    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

}

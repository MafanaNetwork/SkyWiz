package me.TahaCheji.gameItems;

import me.TahaCheji.GameMain;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GamePlayer;
import me.TahaCheji.itemData.*;
import me.TahaCheji.util.AbilityUtil;
import org.bukkit.ChatColor;
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

public class GetOutOfJailFreeCard extends MasterItems {


    public GetOutOfJailFreeCard() {
        super(null, ChatColor.GOLD + "Get-Out-Of-Jail-Free-Card", Material.BOOK, ItemType.SPELL, RarityType.GOLD, true,
                new MasterAbility("GET OUT!", AbilityType.RIGHT_CLICK, 0, 5), true, "You guys remember 9/11?");
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
        //create a explosion
        //teleport player to island
        CoolDown coolDown = new CoolDown(this, GameMain.getInstance().getPlayer(var1));
        if(coolDown.ifCanUse(this)) {
            return false;
        }
        coolDown.addPlayerToCoolDown();
        new AbilityUtil().sendAbility(var1, getMasterAbility());

        Game game = GameMain.getInstance().getGame(var1);
        GamePlayer gamePlayer = game.getGamePlayer(var1);
        gamePlayer.getPlayer().getLocation().getWorld().createExplosion(var1.getLocation(), this.getMasterAbility().getAbilityDamage());
        gamePlayer.teleport(game.getGamePlayerToSpawnPoint().get(gamePlayer));
        gamePlayer.getPlayer().sendMessage(ChatColor.GOLD + "Allahu Akbar!");
        gamePlayer.getPlayer().playSound(var1.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 10);
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

package me.TahaCheji.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.BufferedReader;
import java.time.LocalDate;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        Location location = event.getBlock().getLocation();
        location.getBlock().setType(event.getBlock().getType());
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.ggens;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
    public Events() {
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Block b = event.getBlock();
        ItemStack bItem = event.getItemInHand();
        if (bItem.getItemMeta().getDisplayName().toLowerCase().contains("generator")) {
            String[] iMeta1 = ((String)bItem.getItemMeta().getLore().get(0)).split(" ");
            if (iMeta1[0].startsWith("Gen:")) {
                String uuid = iMeta1[1];
                System.out.println("Placed generator [" + uuid + "]");
                ConfigurationSection config = Main.INSTANCE.getConfig().getConfigurationSection("generators").getConfigurationSection(uuid);
                String key = key(b.getLocation());
                if (config.contains("locations")) {
                    config.set("locations", new ArrayList());
                }

                List<String> locations = config.getStringList("location");
                locations.add(key);
                config.set("locations", locations);
                Main.INSTANCE.getConfig().getConfigurationSection("locations").set(key, uuid);
                Main.INSTANCE.saveConfig();
            }
        }

    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        String key = key(event.getBlock().getLocation());
        if (Main.INSTANCE.getConfig().getConfigurationSection("locations").contains(key)) {
            String uuid = Main.INSTANCE.getConfig().getConfigurationSection("locations").getString(key);
            Main.INSTANCE.getConfig().getConfigurationSection("locations").set(key, (Object)null);
            List<String> locations = Main.INSTANCE.getConfig().getConfigurationSection("generators").getConfigurationSection(uuid).getStringList("locations");
            locations.remove(key);
            Main.INSTANCE.getConfig().getConfigurationSection("generators").getConfigurationSection(uuid).set("locations", locations);
            Main.INSTANCE.saveConfig();
        }

    }

    public static String key(Location l) {
        String var10000 = l.getWorld().getName();
        return var10000 + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
    }
}

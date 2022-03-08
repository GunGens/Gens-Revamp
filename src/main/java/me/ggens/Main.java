package me.ggens;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.Iterator;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {
    public static Main INSTANCE;

    public Main() {
    }

    public void onEnable() {
        INSTANCE = this;
        Bukkit.getLogger().log(Level.INFO, "Plugin Loaded!");
        if (!this.getConfig().isConfigurationSection("generators")) {
            this.getConfig().createSection("generators");
        }

        if (!this.getConfig().isConfigurationSection("locations")) {
            this.getConfig().createSection("locations");
        }

        this.saveConfig();
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.getCommand("generator").setExecutor(new GenCommand());
        this.getCommand("generator").setExecutor(new ReloadCommand());
        this.getCommand("generator").setTabCompleter(new GenTabComplete());
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        AtomicDouble t = new AtomicDouble(0.0D);
        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            ConfigurationSection gens = this.getConfig().getConfigurationSection("generators");
            Iterator var3 = gens.getKeys(false).iterator();

            while(true) {
                ConfigurationSection gen;
                int amount;
                Material m;
                do {
                    boolean generate;
                    do {
                        if (!var3.hasNext()) {
                            t.addAndGet(0.25D);
                            return;
                        }

                        String k = (String)var3.next();
                        gen = gens.getConfigurationSection(k);
                        double time = gen.getDouble("time");
                        generate = t.doubleValue() % time == 0.0D;
                    } while(!generate);

                    amount = gen.getInt("amount");
                    m = Material.getMaterial(gen.getString("item"));
                } while(gen.getStringList("locations") == null);

                Iterator var11 = gen.getStringList("locations").iterator();

                while(var11.hasNext()) {
                    String pos = (String)var11.next();
                    World world = Bukkit.getServer().getWorld(pos.split(",")[0]);
                    double x = Double.parseDouble(pos.split(",")[1]) + 0.5D;
                    double y = Double.parseDouble(pos.split(",")[2]) + 1.0D;
                    double z = Double.parseDouble(pos.split(",")[3]) + 0.5D;
                    Location coords = new Location(world, x, y, z);
                    for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(coords.getBlock().getLocation()) <= 2.2) {
                        p.getInventory().addItem(new ItemStack(m, amount));
                    }
                }
            }
        }, 0L, 5L);
    }
}

package me.ggens.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigWrapper {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private final String folderName;
    private final String fileName;

    public ConfigWrapper(JavaPlugin instance, String folderName, String fileName) {
        this.plugin = instance;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public void createNewFile(String message, String header) {
        this.reloadConfig();
        this.saveConfig();
        this.loadConfig(header);
        if (message != null) {
            this.plugin.getLogger().info(message);
        }

    }

    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }

        return this.config;
    }

    public void loadConfig(String header) {
        this.config.options().header(header);
        this.config.options().copyDefaults(true);
        this.saveConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder() + this.folderName, this.fileName);
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void saveConfig() {
        if (this.config != null && this.configFile != null) {
            try {
                this.getConfig().save(this.configFile);
            } catch (IOException var2) {
                this.plugin.getLogger().log(Level.SEVERE, "Couldn't save config to " + this.configFile, var2);
            }

        }
    }
}

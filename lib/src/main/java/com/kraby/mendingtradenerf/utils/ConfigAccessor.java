package com.kraby.mendingtradenerf.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigAccessor {
	protected final Configuration config;

    protected ConfigAccessor(final Configuration config) {
        this.config = config;
    }

    protected ConfigAccessor(Plugin plugin, String fileName) {
        config = loadConfig(plugin, fileName);
    }

    /**
     * Load the FileConfiguration file of a given plugin.
     * @param plugin
     * @param fileName
     * @return
     */
    public static FileConfiguration loadConfig(Plugin plugin, String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        FileConfiguration config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return config;
    }

    public Configuration getConfig() {
        return config;
    }
}

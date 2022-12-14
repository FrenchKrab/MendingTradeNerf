/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.kraby.mendingtradenerf;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.kraby.mendingtradenerf.commands.ReloadConfig;
import com.kraby.mendingtradenerf.listeners.VillagerListener;
import com.kraby.mendingtradenerf.utils.MainConfig;

public final class MendingTradeNerf extends JavaPlugin {
	public static MendingTradeNerf singleton;

	public MainConfig config;

    public void onEnable() {
		singleton = this;

		loadConfig();
		
		this.getServer().getPluginManager().registerEvents(new VillagerListener(), this);
		
		getCommand("mtnreload").setExecutor(new ReloadConfig());

		Bukkit.getLogger().info("[MendingTradeNerf] Plugin loaded successfully");
    }


	public void loadConfig()
	{
		config = new MainConfig(this, "config.yml");
	}
}
package com.kraby.mendingtradenerf.utils;

import org.bukkit.plugin.Plugin;

public class MainConfig extends ConfigAccessor {

	public MainConfig(Plugin plugin, String fileName) {
		super(plugin, fileName);
	}
	
	public int getEmeraldCost()
	{
		return config.getInt("cost", 63);
	}

	public boolean isCursedBinding()
	{
		return config.getBoolean("binding", true);
	}

	public boolean isCursedVanishing()
	{
		return config.getBoolean("vanishing", true);
	}
}

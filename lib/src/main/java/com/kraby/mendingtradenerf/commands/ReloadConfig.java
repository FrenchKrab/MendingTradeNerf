package com.kraby.mendingtradenerf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.kraby.mendingtradenerf.MendingTradeNerf;

public class ReloadConfig implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		MendingTradeNerf.singleton.loadConfig();
		sender.sendMessage("Reloaded MendingTradeNerf configuration");
		return true;
	}
	
}

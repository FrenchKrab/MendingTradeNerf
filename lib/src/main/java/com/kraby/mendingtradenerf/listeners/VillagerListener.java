package com.kraby.mendingtradenerf.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.kraby.mendingtradenerf.MendingTradeNerf;
import com.kraby.mendingtradenerf.utils.MainConfig;

import org.bukkit.entity.AbstractVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;

public class VillagerListener implements Listener {
	@EventHandler
    public void onAcquire(VillagerAcquireTradeEvent event) {
        AbstractVillager merchant = event.getEntity();
        for (int index = 0; index < merchant.getRecipeCount(); ++index) {
            MerchantRecipe recipe = this.ruinRecipe(merchant.getRecipe(index));
            if (recipe == null) continue;
            merchant.setRecipe(index, recipe);
        }
    }

    @EventHandler
    public void onExhaust(VillagerReplenishTradeEvent event) {
        AbstractVillager merchant = event.getEntity();
        for (int index = 0; index < merchant.getRecipeCount(); ++index) {
            MerchantRecipe recipe = this.ruinRecipe(merchant.getRecipe(index));
            if (recipe == null) continue;
            merchant.setRecipe(index, recipe);
        }
    }

    private MerchantRecipe ruinRecipe(MerchantRecipe recipe) {
        ItemMeta meta = recipe.getResult().getItemMeta();
		MainConfig cfg = MendingTradeNerf.singleton.config;

		int currentEmeraldCost = 0;
        for (ItemStack requiredItem : recipe.getIngredients()) {
            if (requiredItem.getType() == Material.EMERALD)
            {
                currentEmeraldCost = requiredItem.getAmount();
                break;
            }
        }

        if (
			meta instanceof EnchantmentStorageMeta 
			&& ((EnchantmentStorageMeta)meta).hasStoredEnchant(Enchantment.MENDING) 
			&& (    // if one of the conditions/settings is missing
                ((EnchantmentStorageMeta)meta).hasStoredEnchant(Enchantment.BINDING_CURSE) != cfg.isCursedBinding()
			    || ((EnchantmentStorageMeta)meta).hasStoredEnchant(Enchantment.VANISHING_CURSE) != cfg.isCursedVanishing()
                || currentEmeraldCost != cfg.getEmeraldCost()
            )
		) {
            ItemStack newItem = new ItemStack(Material.ENCHANTED_BOOK);

            EnchantmentStorageMeta newEnchantMeta = (EnchantmentStorageMeta)newItem.getItemMeta();
            newEnchantMeta.addStoredEnchant(Enchantment.MENDING, 1, false);
            if (cfg.isCursedVanishing())
                newEnchantMeta.addStoredEnchant(Enchantment.VANISHING_CURSE, 1, false);
            if (cfg.isCursedBinding())
                newEnchantMeta.addStoredEnchant(Enchantment.BINDING_CURSE, 1, false);
            newItem.setItemMeta((ItemMeta)newEnchantMeta);

            MerchantRecipe newRecipe = new MerchantRecipe(newItem, 1);
            newRecipe.addIngredient(new ItemStack(Material.EMERALD, cfg.getEmeraldCost()));
            newRecipe.addIngredient(new ItemStack(Material.BOOK));
            return newRecipe;
        }
        return null;
    }
}

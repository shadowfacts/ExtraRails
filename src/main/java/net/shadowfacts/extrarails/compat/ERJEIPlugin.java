package net.shadowfacts.extrarails.compat;

import mezz.jei.api.*;
import net.minecraft.item.ItemStack;
import net.shadowfacts.extrarails.ERConfig;
import net.shadowfacts.extrarails.ExtraRails;

import javax.annotation.Nonnull;

/**
 * @author shadowfacts
 */
@JEIPlugin
public class ERJEIPlugin implements IModPlugin {

	@Override
	public void register(@Nonnull IModRegistry registry) {
		if (!ERConfig.teleportingRailsEnabled) {
			registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ExtraRails.blocks.teleportingRail));
		}
		if (!ERConfig.woodenRailsEnabled) {
			registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ExtraRails.blocks.woodenRail));
		}
	}

}

package net.shadowfacts.extrarails.compat;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
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
			registry.getJeiHelpers().getItemBlacklist().addItemToBlacklist(new ItemStack(ExtraRails.blocks.teleportingRail));
		}
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

	}

}

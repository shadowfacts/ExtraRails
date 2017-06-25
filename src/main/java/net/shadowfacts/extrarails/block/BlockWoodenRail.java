package net.shadowfacts.extrarails.block;

import net.minecraft.block.BlockRail;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shadowfacts.extrarails.ERConfig;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.shadowmc.item.ItemModelProvider;

/**
 * @author shadowfacts
 */
public class BlockWoodenRail extends BlockRail implements ItemModelProvider {

	public BlockWoodenRail() {
		setRegistryName("wooden_rail");
		setUnlocalizedName(getRegistryName().toString());
		setHardness(0.3f);
		setSoundType(SoundType.WOOD);

		if (ERConfig.teleportingRailsEnabled) {
			setCreativeTab(CreativeTabs.TRANSPORTATION);
		}
	}

	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
		return 0.2f;
	}

	@Override
	public void initItemModel() {
		ExtraRails.proxy.registerItemModel(this, 0, getRegistryName().getResourcePath());
	}

}

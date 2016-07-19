package net.shadowfacts.extrarails.block;

import net.minecraft.block.BlockRail;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.shadowmc.item.ItemModelProvider;

/**
 * @author shadowfacts
 */
public class BlockWoodenRail extends BlockRail implements ItemModelProvider {

	public BlockWoodenRail() {
		setRegistryName("woodenRail");
		setUnlocalizedName("extrarails.woodenRail");
		setHardness(0.3f);
		setSoundType(SoundType.WOOD);
		setCreativeTab(CreativeTabs.TRANSPORTATION);
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
		return 0.2f;
	}

	@Override
	public void initItemModel() {
		ExtraRails.proxy.registerItemModel(this, 0, "woodenRail");
	}

}

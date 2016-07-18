package net.shadowfacts.extrarails.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.extrarails.block.base.BlockExtraRailPowered;

/**
 * @author shadowfacts
 */
public class BlockRailDirection extends BlockExtraRailPowered {

	public BlockRailDirection() {
		setUnlocalizedName("extrarails.directionRail");
		setRegistryName("directionRail");
	}

	@Override
	public void initItemModel() {
		ExtraRails.proxy.registerItemModel(this, 0, "directionRail");
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (entity instanceof EntityMinecart) {
			EntityMinecart minecart = (EntityMinecart)entity;
			boolean powered = state.getValue(POWERED);
			if (powered) {
				propelMinecart(world, pos, state, minecart);
			}
		}
	}

}

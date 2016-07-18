package net.shadowfacts.extrarails.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.extrarails.block.base.BlockExtraRailPowered;

import java.util.List;
import java.util.Random;

/**
 * @author shadowfacts
 */
public class BlockRailLocking extends BlockExtraRailPowered {

	public BlockRailLocking() {
		setUnlocalizedName("extrarails.lockingRail");
		setRegistryName("lockingRail");
	}

	@Override
	public void initItemModel() {
		ExtraRails.proxy.registerItemModel(this, 0, "lockingRail");
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		boolean powered = state.getValue(POWERED);
		if (powered) {
			List<? extends  EntityMinecart> minecarts = findMinecarts(world, pos);
			if (minecarts.size() > 0) {
				propelMinecart(world, pos, state, minecarts.get(0));
			}
		} else {
			world.scheduleUpdate(pos, this, tickRate(world));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (entity instanceof EntityMinecart) {
			EntityMinecart minecart = (EntityMinecart)entity;
			boolean powered = state.getValue(POWERED);
			if (powered) {
				propelMinecart(world, pos, state, minecart);
			} else {
				minecart.motionX = 0;
				minecart.motionY = 0;
				minecart.motionZ = 0;
				world.scheduleUpdate(new BlockPos(pos), this, tickRate(world));
			}
		}
	}

}

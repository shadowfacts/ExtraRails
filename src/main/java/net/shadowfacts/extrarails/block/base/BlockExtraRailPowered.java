package net.shadowfacts.extrarails.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author shadowfacts
 */
public abstract class BlockExtraRailPowered extends BlockExtraRail {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	protected BlockExtraRailPowered() {
		super(true);
		setDefaultState(blockState.getBaseState()
				.withProperty(SHAPE, EnumRailDirection.NORTH_SOUTH)
				.withProperty(FACING, EnumFacing.NORTH)
				.withProperty(POWERED, true));
	}

	@Override
	protected void updateState(IBlockState state, World world, BlockPos pos, Block block) {
		boolean flag = state.getValue(POWERED);
		boolean flag1 = world.isBlockPowered(pos) || this.findPoweredRailSignal(world, pos, state, true, 0) || this.findPoweredRailSignal(world, pos, state, false, 0);

		if (flag1 != flag) {
			world.setBlockState(pos, state.withProperty(POWERED, flag1), 3);
			world.notifyNeighborsOfStateChange(pos.down(), this, false);
		}
	}

	private boolean findPoweredRailSignal(World world, BlockPos pos, IBlockState state, boolean p_176566_4_, int p_176566_5_) {
		if (p_176566_5_ >= 8) {
			return false;
		} else {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			BlockRailBase.EnumRailDirection dir = state.getValue(SHAPE);

			switch (dir) {
				case NORTH_SOUTH:

					if (p_176566_4_) {
						++z;
					} else {
						--z;
					}

					break;
				case EAST_WEST:

					if (p_176566_4_) {
						--x;
					} else {
						++x;
					}

					break;
			}

			return isSameRailWithPower(world, new BlockPos(x, y, z), p_176566_4_, p_176566_5_, dir) || isSameRailWithPower(world, new BlockPos(x, y - 1, z), p_176566_4_, p_176566_5_, dir);
		}
	}

	private boolean isSameRailWithPower(World world, BlockPos pos, boolean p_176567_3_, int distance, EnumRailDirection dir) {
		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() != this) {
			return false;
		} else {
			EnumRailDirection shape = state.getValue(SHAPE);
			return (dir != EnumRailDirection.EAST_WEST || shape != EnumRailDirection.NORTH_SOUTH && shape != EnumRailDirection.ASCENDING_NORTH && shape != EnumRailDirection.ASCENDING_SOUTH) && ((dir != EnumRailDirection.NORTH_SOUTH || shape != EnumRailDirection.EAST_WEST && shape != EnumRailDirection.ASCENDING_EAST && shape != EnumRailDirection.ASCENDING_WEST) && (state.getValue(POWERED) && (world.isBlockPowered(pos) || findPoweredRailSignal(world, pos, state, p_176567_3_, distance + 1))));
		}
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED, SHAPE, FACING);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean powered = (meta & 1) == 1;
		EnumRailDirection shape = EnumRailDirection.byMetadata((meta >> 1) & 1);
		EnumFacing facing = EnumFacing.getHorizontal(meta >> 2);

		return getDefaultState()
				.withProperty(SHAPE, shape)
				.withProperty(FACING, facing)
				.withProperty(POWERED, powered);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumRailDirection shape = state.getValue(SHAPE);
		EnumFacing facing = state.getValue(FACING);
		boolean powered = state.getValue(POWERED);
		int meta = powered ? 1 : 0;
		meta |= shape.getMetadata() << 1;
		meta |= facing.getHorizontalIndex() << 2;
		return meta;
	}


}

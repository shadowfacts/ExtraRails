package net.shadowfacts.extrarails.block.base;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.shadowfacts.shadowmc.item.ItemModelProvider;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author shadowfacts
 */
public abstract class BlockExtraRail extends BlockRailBase implements ItemModelProvider {

	public static final PropertyEnum<EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, dir -> {
		return dir == EnumRailDirection.NORTH_SOUTH || dir == EnumRailDirection.EAST_WEST;
	});
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	protected BlockExtraRail(boolean isPowered) {
		super(isPowered);
		setHardness(0.7f);
		setSoundType(SoundType.METAL);
	}

	@Override
	public abstract void initItemModel();

	@Nonnull
	@Override
	@Deprecated
	public abstract IBlockState getStateFromMeta(int meta);

	@Override
	public abstract int getMetaFromState(IBlockState state);

	@Nonnull
	@Override
	protected abstract BlockStateContainer createBlockState();

	@Nonnull
	@Override
	public IProperty<EnumRailDirection> getShapeProperty() {
		return SHAPE;
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		EnumFacing facing = EnumFacing.fromAngle(placer.rotationYawHead);
		EnumRailDirection shape = facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? EnumRailDirection.NORTH_SOUTH : EnumRailDirection.EAST_WEST;
		return getDefaultState().withProperty(FACING, facing).withProperty(SHAPE, shape);
	}

	@Override
	public boolean canMakeSlopes(IBlockAccess world, BlockPos pos) {
		return false;
	}

	protected void propelMinecart(World world, BlockPos pos, IBlockState state, EntityMinecart minecart) {
		BlockRailBase.EnumRailDirection dir = getRailDirection(world, pos, state, minecart);
		EnumFacing facing = state.getValue(FACING);
		if (dir == BlockRailBase.EnumRailDirection.EAST_WEST) {
			if (facing == EnumFacing.EAST) {
				minecart.motionX = 0.2d;
			} else {
				minecart.motionX = -0.2d;
			}
		} else if (dir == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
			if (facing == EnumFacing.SOUTH) {
				minecart.motionZ = 0.2d;
			} else {
				minecart.motionZ = -0.2d;
			}
		}
	}

	protected List<? extends EntityMinecart> findMinecarts(World world, BlockPos pos) {
		return world.getEntitiesWithinAABB(EntityMinecart.class, getDectectionBox(pos));
	}

	private AxisAlignedBB getDectectionBox(BlockPos pos) {
		return new AxisAlignedBB((double)((float)pos.getX() + 0.2F), (double)pos.getY(), (double)((float)pos.getZ() + 0.2F), (double)((float)(pos.getX() + 1) - 0.2F), (double)((float)(pos.getY() + 1) - 0.2F), (double)((float)(pos.getZ() + 1) - 0.2F));
	}

}

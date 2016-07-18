package net.shadowfacts.extrarails.block;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.extrarails.block.base.BlockExtraRail;
import net.shadowfacts.extrarails.tileentity.TileEntityRailTeleporting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author shadowfacts
 */
public class BlockRailTeleporting extends BlockExtraRail {

	protected BlockRailTeleporting() {
		super(true);
		setUnlocalizedName("extrarails.teleportingRail");
		setRegistryName("teleportingRail");
	}

	@Override
	public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityRailTeleporting) {
				((TileEntityRailTeleporting)te).remove();
			}
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void onBlockExploded(World world, @Nonnull BlockPos pos, @Nonnull Explosion explosion) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityRailTeleporting) {
				((TileEntityRailTeleporting)te).remove();
			}
		}
		super.onBlockExploded(world, pos, explosion);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(ExtraRails.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityRailTeleporting) {
				((TileEntityRailTeleporting)te).handle(entity);
			}
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileEntityRailTeleporting();
	}

	@Override
	public void initItemModel() {
		ExtraRails.proxy.registerItemModel(this, 0, "teleportingRail");
	}

	@Nonnull
	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta) {
		EnumRailDirection shape = EnumRailDirection.byMetadata(meta & 1);
		EnumFacing facing = EnumFacing.getHorizontal(meta >> 1);

		return getDefaultState()
				.withProperty(SHAPE, shape)
				.withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumRailDirection shape = state.getValue(SHAPE);
		EnumFacing facing = state.getValue(FACING);
		int meta = 0;
		meta |= shape.getMetadata();
		meta |= facing.getHorizontalIndex() << 1;
		return meta;
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE, FACING);
	}

}

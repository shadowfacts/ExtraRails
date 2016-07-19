package net.shadowfacts.extrarails.block;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.extrarails.block.base.BlockExtraRail;
import net.shadowfacts.extrarails.tileentity.TileEntityRailComparator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * @author shadowfacts
 */
public class BlockRailComparator extends BlockExtraRail {

	protected BlockRailComparator() {
		super(true);
		setRegistryName("comparatorRail");
		setUnlocalizedName("extrarails.comparatorRail");
	}

	private void updatePower(World world, BlockPos pos, EntityMinecart minecart) {
		int level = 0;
		if (minecart instanceof IInventory) {
			level = Container.calcRedstoneFromInventory((IInventory)minecart);
		} else if (minecart.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) || minecart instanceof IItemHandler) {
			IItemHandler handler = minecart.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH) ? minecart.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) : (IItemHandler)minecart;
			int slots = 0;
			float filled = 0;
			for (int i = 0; i < handler.getSlots(); i++) {
				ItemStack stack = handler.getStackInSlot(i);
				if (stack != null) {
					filled += stack.stackSize / (float)stack.getMaxStackSize();
					slots++;
				}
			}
			filled = filled / (float)slots;
			level = MathHelper.floor_float(filled * 14) + (slots > 0 ? 1 : 0);
		}
		TileEntityRailComparator te = (TileEntityRailComparator)world.getTileEntity(pos);
		if (te.getPower() != level) {
			te.setPower(level);
			world.notifyNeighborsOfStateChange(pos, this);
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		List<? extends EntityMinecart> minecarts = findMinecarts(world, pos);
		if (minecarts.isEmpty()) {
			((TileEntityRailComparator)world.getTileEntity(pos)).setPower(0);
			world.notifyNeighborsOfStateChange(pos, this);
			world.markBlockRangeForRenderUpdate(pos, pos);
		} else {
			updatePower(world, pos, minecarts.get(0));
			world.scheduleUpdate(pos, this, tickRate(world));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (entity instanceof EntityMinecart) {
			updatePower(world, pos, (EntityMinecart)entity);
			world.scheduleUpdate(pos instanceof BlockPos.MutableBlockPos ? new BlockPos(pos) : pos, this, tickRate(world));
		}
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	@Deprecated
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return ((TileEntityRailComparator)world.getTileEntity(pos)).getPower();
	}

	@Override
	@Deprecated
	public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return ((TileEntityRailComparator)world.getTileEntity(pos)).getPower();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileEntityRailComparator();
	}

	@Override
	public void initItemModel() {
		ExtraRails.proxy.registerItemModel(this, 0, "comparatorRail");
	}

	@Nonnull
	@Override
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

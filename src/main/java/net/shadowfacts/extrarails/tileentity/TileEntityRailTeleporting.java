package net.shadowfacts.extrarails.tileentity;

import lombok.Getter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.extrarails.TeleportingManager;
import net.shadowfacts.extrarails.TeleportingManager.Pos;
import net.shadowfacts.extrarails.block.base.BlockExtraRail;
import net.shadowfacts.shadowmc.ShadowMC;
import net.shadowfacts.shadowmc.network.PacketRequestTEUpdate;
import net.shadowfacts.shadowmc.tileentity.BaseTileEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * @author shadowfacts
 */
public class TileEntityRailTeleporting extends BaseTileEntity {

	@Getter
	private int color;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("color", color);
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		color = tag.getInteger("color");
	}



	public void handle(Entity e) {
		if (e instanceof EntityMinecart) {
			EntityMinecart entity = (EntityMinecart)e;
			Collection<Pos> positions = TeleportingManager.get(color);
			Optional<Pos> pos = positions.stream()
					.filter(p -> p.dim != worldObj.provider.getDimension() || (!p.pos.equals(this.pos)))
					.findFirst();
			if (pos.isPresent()) {
				Pos dest = pos.get();

				if (entity.dimension != dest.dim) return;

				WorldServer newWorld = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dest.dim);

				IBlockState state = newWorld.getBlockState(dest.pos);
				if (state.getBlock() != ExtraRails.blocks.teleportingRail) return;

				EnumFacing facing = state.getValue(BlockExtraRail.FACING);

				BlockPos teleportTo = dest.pos.offset(facing);
				entity.setPosition(teleportTo.getX() + 0.5, teleportTo.getY(), teleportTo.getZ() + 0.5);
				entity.setLocationAndAngles(teleportTo.getX() + 0.5, teleportTo.getY(), teleportTo.getZ() + 0.5, facing.getHorizontalAngle(), entity.rotationPitch);

				propelMinecart(facing, entity);

			}
		}
	}

	private static void propelMinecart(EnumFacing direction, EntityMinecart minecart) {
		if (direction == EnumFacing.EAST) {
			minecart.motionX = 0.2d;
		} else if (direction == EnumFacing.WEST) {
			minecart.motionX = -0.2d;
		} else if (direction == EnumFacing.SOUTH) {
			minecart.motionZ = 0.2d;
		} else if (direction == EnumFacing.NORTH) {
			minecart.motionZ = -0.2d;
		}
	}

	public void setColor(int color) {
		TeleportingManager.remove(this.color, worldObj.provider.getDimension(), pos);
		TeleportingManager.add(color, worldObj.provider.getDimension(), pos);
		this.color = color;
		markDirty();
	}

	@Override
	public void onLoad() {
		if (!worldObj.isRemote) {
			TeleportingManager.add(color, worldObj.provider.getDimension(), pos);
		} else {
			ShadowMC.network.sendToServer(new PacketRequestTEUpdate(this));
		}
	}

	@Override
	public void onChunkUnload() {
		remove();
	}

	public void remove() {
		TeleportingManager.remove(color, worldObj.provider.getDimension(), pos);
	}

}

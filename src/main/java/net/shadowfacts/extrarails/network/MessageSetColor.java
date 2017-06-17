package net.shadowfacts.extrarails.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.shadowfacts.extrarails.tileentity.TileEntityRailTeleporting;

/**
 * @author shadowfacts
 */
@NoArgsConstructor
@AllArgsConstructor
public class MessageSetColor implements IMessage {

	private int dimension;
	private BlockPos pos;
	private int color;

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimension);
		buf.writeLong(pos.toLong());

		buf.writeInt(color);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		dimension = buf.readInt();
		pos = BlockPos.fromLong(buf.readLong());

		color = buf.readInt();
	}

	public static class Handler implements IMessageHandler<MessageSetColor, IMessage> {

		@Override
		public IMessage onMessage(MessageSetColor message, MessageContext ctx) {
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
			TileEntity te = world.getTileEntity(message.pos);
			if (te instanceof TileEntityRailTeleporting) {
				TileEntityRailTeleporting rail = (TileEntityRailTeleporting)te;
				rail.setColor(message.color);
			}

			return null;
		}

	}

}

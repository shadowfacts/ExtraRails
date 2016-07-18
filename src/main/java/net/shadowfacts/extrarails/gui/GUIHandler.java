package net.shadowfacts.extrarails.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.shadowfacts.extrarails.ExtraRails;
import net.shadowfacts.extrarails.network.MessageSetColor;
import net.shadowfacts.extrarails.tileentity.TileEntityRailTeleporting;
import net.shadowfacts.shadowmc.inventory.ContainerPlayerInv;
import net.shadowfacts.shadowmc.ui.element.button.UIButtonDyeColor;
import net.shadowfacts.shadowmc.ui.element.button.UIImage;
import net.shadowfacts.shadowmc.ui.element.view.UIFixedView;
import net.shadowfacts.shadowmc.ui.element.view.UIStackView;
import net.shadowfacts.shadowmc.ui.util.UIBuilder;

/**
 * @author shadowfacts
 */
public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPlayerInv(new BlockPos(x, y, z), player.inventory);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos pos = new BlockPos(x, y, z);
		TileEntityRailTeleporting rail = (TileEntityRailTeleporting)world.getTileEntity(pos);

		UIFixedView view = new UIFixedView(176, 166, "root");

		UIImage bg = new UIImage(new ResourceLocation(ExtraRails.MODID, "textures/gui/teleportingRail.png"), 176, 177, "bg");
		view.add(bg);

		UIFixedView top = new UIFixedView(176, 166 / 2, "top");

		UIStackView stack = new UIStackView("colors");

		UIButtonDyeColor button1 = new UIButtonDyeColor(EnumDyeColor.byMetadata(rail.getColor() >> 8), color1 -> {

			EnumDyeColor color2 = EnumDyeColor.byMetadata((rail.getColor() >> 4) & 15);
			EnumDyeColor color3 = EnumDyeColor.byMetadata(rail.getColor() & 15);

			int color = color1.getMetadata() << 8 | color2.getMetadata() << 4 | color3.getMetadata();
			rail.setColor(color);

			ExtraRails.network.sendToServer(new MessageSetColor(world.provider.getDimension(), pos, color));

		}, "button1");
		stack.add(button1);

		UIButtonDyeColor button2 = new UIButtonDyeColor(EnumDyeColor.byMetadata((rail.getColor() >> 4) & 15), color2 -> {

			EnumDyeColor color1 = EnumDyeColor.byMetadata(rail.getColor() >> 8);
			EnumDyeColor color3 = EnumDyeColor.byMetadata(rail.getColor() & 15);

			int color = color1.getMetadata() << 8 | color2.getMetadata() << 4 | color3.getMetadata();
			rail.setColor(color);

			ExtraRails.network.sendToServer(new MessageSetColor(world.provider.getDimension(), pos, color));

		}, "button2");
		stack.add(button2);

		UIButtonDyeColor button3 = new UIButtonDyeColor(EnumDyeColor.byMetadata(rail.getColor() & 15), color3 -> {

			EnumDyeColor color1 = EnumDyeColor.byMetadata(rail.getColor() >> 8);
			EnumDyeColor color2 = EnumDyeColor.byMetadata((rail.getColor() >> 4) & 15);

			int color = color1.getMetadata() << 8 | color2.getMetadata() << 4 | color3.getMetadata();
			rail.setColor(color);

			ExtraRails.network.sendToServer(new MessageSetColor(world.provider.getDimension(), pos, color));

		}, "button3");
		stack.add(button3);

		top.add(stack);

		view.add(top);

		return new UIBuilder().add(view).style(ExtraRails.MODID + ":teleportingRail").createContainer(new ContainerPlayerInv(pos, player.inventory));
	}

}

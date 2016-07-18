package net.shadowfacts.extrarails.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.shadowfacts.extrarails.ExtraRails;

/**
 * @author shadowfacts
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemModel(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ExtraRails.MODID + ":" + id, "inventory"));
	}

}

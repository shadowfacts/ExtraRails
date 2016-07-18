package net.shadowfacts.extrarails.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @author shadowfacts
 */
public class CommonProxy {

	public void registerItemModel(Item item, int meta, String id) {

	}

	public void registerItemModel(Block block, int meta, String id) {
		registerItemModel(Item.getItemFromBlock(block), meta, id);
	}

}

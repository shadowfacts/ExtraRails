package net.shadowfacts.extrarails.tileentity;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.shadowfacts.shadowmc.tileentity.BaseTileEntity;

/**
 * @author shadowfacts
 */
public class TileEntityRailComparator extends BaseTileEntity {

	@Getter @Setter
	private int power;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("power", power);
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		power = tag.getInteger("power");
	}

}

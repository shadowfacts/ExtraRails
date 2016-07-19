package net.shadowfacts.extrarails.block;

/**
 * @author shadowfacts
 */
public class ModBlocks extends net.shadowfacts.shadowmc.block.ModBlocks {

	public BlockRailLocking lockingRail;
	public BlockRailDirection directionRail;
	public BlockRailTeleporting teleportingRail;
	public BlockRailComparator comparatorRail;
	public BlockWoodenRail woodenRail;

	@Override
	public void init() {
		lockingRail = register(new BlockRailLocking());
		directionRail = register(new BlockRailDirection());
		teleportingRail = register(new BlockRailTeleporting());
		comparatorRail = register(new BlockRailComparator());
		woodenRail = register(new BlockWoodenRail());
	}

}

package net.shadowfacts.extrarails;

import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

/**
 * @author shadowfacts
 */
public class ConfigConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String config = json.get("config").getAsString();
		switch (config) {
			case "teleportingRailsEnabled":
				return () -> ERConfig.teleportingRailsEnabled;
			case "woodenRailsEnabled":
				return () -> ERConfig.woodenRailsEnabled;
		}
		return () -> false;
	}

}

package net.shadowfacts.extrarails;

import net.minecraftforge.common.config.Configuration;
import net.shadowfacts.config.Config;
import net.shadowfacts.config.ConfigManager;

import java.io.File;

/**
 * @author shadowfacts
 */
@Config(name = ExtraRails.MODID)
public class ERConfig {

	public static Configuration config;

	@Config.Prop(description = "Enable the Teleporting Rails. If false, the recipe will be disabled and they will be hidden from the creative tab & JEI")
	public static boolean teleportingRailsEnabled = true;

	@Config.Prop(description = "Enable the Wooden Rails. If false, the recipe will be disabled and they will be hidden from the creative tab & JEI")
	public static boolean woodenRailsEnabled = true;

	public static void init(File configDir) {
		config = new Configuration(new File(configDir, "shadowfacts/ExtraRails.cfg"));
	}

	public static void load() {
		ConfigManager.load(ERConfig.class, Configuration.class, config);
		if (config.hasChanged()) config.save();
	}

}

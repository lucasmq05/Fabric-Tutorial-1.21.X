package net.luvas.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.luvas.tutorialmod.block.ModBlocks;
import net.luvas.tutorialmod.item.ModItemGroups;
import net.luvas.tutorialmod.item.ModItems;
import net.luvas.tutorialmod.item.ModFuelItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// comentário muito importante
public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModFuelItems.registerModFuelItems();
	}
}
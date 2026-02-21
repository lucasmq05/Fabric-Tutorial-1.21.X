package net.luvas.tutorialmod.item;

import net.fabricmc.fabric.api.registry.FuelRegistry;

public class ModFuelItems {
    public static void registerModFuelItems() {
        FuelRegistry.INSTANCE.add(ModItems.STARLIGHT_ASHES, 2000);
    }
}

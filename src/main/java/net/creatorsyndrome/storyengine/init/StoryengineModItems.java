
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.creatorsyndrome.storyengine.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

import net.creatorsyndrome.storyengine.StoryengineMod;

public class StoryengineModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, StoryengineMod.MODID);
	public static final RegistryObject<Item> NPC_SPAWN_EGG = REGISTRY.register("npc_spawn_egg", () -> new ForgeSpawnEggItem(StoryengineModEntities.NPC, -1, -1, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}

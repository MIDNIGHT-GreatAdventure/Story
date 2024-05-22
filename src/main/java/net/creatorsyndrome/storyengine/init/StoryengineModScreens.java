
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.creatorsyndrome.storyengine.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.creatorsyndrome.storyengine.client.gui.DialogWindowScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StoryengineModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(StoryengineModMenus.DIALOG_WINDOW, DialogWindowScreen::new);
		});
	}
}

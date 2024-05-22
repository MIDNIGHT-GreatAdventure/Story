
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.creatorsyndrome.storyengine.init;

import net.creatorsyndrome.storyengine.client.renderer.ModernNPCRender;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.creatorsyndrome.storyengine.client.renderer.NPCRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StoryengineModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(StoryengineModEntities.NPC.get(), NPCRenderer::new);
		event.registerEntityRenderer(StoryengineModEntities.ModernNPC.get(), ModernNPCRender::new);
	}
}

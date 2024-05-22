
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.creatorsyndrome.storyengine.init;

import net.creatorsyndrome.storyengine.entity.CameraEntity;
import net.creatorsyndrome.storyengine.entity.ModernNPCEntity;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.creatorsyndrome.storyengine.entity.NPCEntity;
import net.creatorsyndrome.storyengine.StoryengineMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StoryengineModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, StoryengineMod.MODID);
	public static final RegistryObject<EntityType<NPCEntity>> NPC = register("npc",
			EntityType.Builder.<NPCEntity>of(NPCEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(NPCEntity::new)

					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<ModernNPCEntity>> ModernNPC = register("modernnpc",
			EntityType.Builder.of(ModernNPCEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<CameraEntity>> CAMERA = register("camera",
			EntityType.Builder.<CameraEntity>of(CameraEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			NPCEntity.init();
			ModernNPCEntity.init();
			CameraEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(NPC.get(), NPCEntity.createAttributes().build());
		event.put(ModernNPC.get(), ModernNPCEntity.createMobAttributes().build());
		event.put(CAMERA.get(), CameraEntity.createMobAttributes().build());
	}
}
package net.creatorsyndrome.storyengine.init;

import net.creatorsyndrome.storyengine.StoryengineMod;
import net.creatorsyndrome.storyengine.entity.ModernNPCEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StoryengineEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, StoryengineMod.MODID);

    public static final RegistryObject<EntityType<ModernNPCEntity>> MODERNNPC =
            ENTITY_TYPES.register("modernnpc",
                    () -> EntityType.Builder.of(ModernNPCEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f)
                            .build(new ResourceLocation(StoryengineMod.MODID, "modernnpc").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
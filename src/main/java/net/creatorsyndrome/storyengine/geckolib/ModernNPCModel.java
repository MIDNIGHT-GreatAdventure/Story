package net.creatorsyndrome.storyengine.geckolib;

import net.creatorsyndrome.storyengine.StoryengineMod;
import net.creatorsyndrome.storyengine.entity.ModernNPCEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModernNPCModel extends AnimatedGeoModel<ModernNPCEntity> {
    @Override
    public ResourceLocation getModelLocation(ModernNPCEntity object) {
        return new ResourceLocation(StoryengineMod.MODID, "geo/rig_muzh.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ModernNPCEntity object) {
        return new ResourceLocation(StoryengineMod.MODID, "textures/entities/persik.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ModernNPCEntity animatable) {
        return new ResourceLocation(StoryengineMod.MODID, "animations/rig_muzh.animation.json");
    }
}
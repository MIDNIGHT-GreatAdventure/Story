package net.creatorsyndrome.storyengine.models;

import net.creatorsyndrome.storyengine.StoryengineMod;
import net.creatorsyndrome.storyengine.entity.CameraEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CameraModel extends HumanoidModel<CameraEntity> {
    public CameraModel(ModelPart part) {
        super(part);
    }

    public ResourceLocation getModelLocation(CameraEntity object) {
        return new ResourceLocation(StoryengineMod.MODID, "models/camera.json");
    }

    public ResourceLocation getTextureLocation(CameraEntity object) {
        return new ResourceLocation(StoryengineMod.MODID, "textures/entities/camera.png");
    }
}
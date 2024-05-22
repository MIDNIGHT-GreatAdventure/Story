package net.creatorsyndrome.storyengine.client.renderer;

import net.creatorsyndrome.storyengine.entity.CameraEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class CameraRender {
    public CameraRender(EntityRendererProvider.Context context) {
        super();
    }

    public ResourceLocation getTextureLocation(CameraEntity entity) {
        return new ResourceLocation("storyengine:textures/entities/camera.png");
    }
}

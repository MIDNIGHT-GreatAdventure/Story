package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.entity.Entity;

public class CurrentAnimationEqualsTo {

    public static boolean execute(Entity entity, String anim_name) {
        if (entity == null)
            return false;
        return (entity.getPersistentData().getString("animation")).equals(anim_name);
    }

}

package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
public class NPCMoveToProcedure {
    static public void excecute(Entity entity, double x, double y, double z, double speed_factor) {
        if (entity instanceof Mob _entity) {
            _entity.getNavigation().moveTo(x, y, z, speed_factor);
        }
    }
}

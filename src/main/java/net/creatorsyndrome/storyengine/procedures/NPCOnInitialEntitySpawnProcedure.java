package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.entity.Entity;

public class NPCOnInitialEntitySpawnProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("mode", "walker");
		entity.getPersistentData().putString("name", "npc1");
	}
}

package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.entity.Entity;

public class IfModeWalkerProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity.getPersistentData().getString("mode")).contains("walker");
	}
}

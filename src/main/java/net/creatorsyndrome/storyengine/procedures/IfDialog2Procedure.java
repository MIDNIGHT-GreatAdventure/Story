package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.creatorsyndrome.storyengine.network.StoryengineModVariables;

public class IfDialog2Procedure {
	public static boolean execute(LevelAccessor world) {
		return !(StoryengineModVariables.MapVariables.get(world).variant_2).equals("");
	}
}

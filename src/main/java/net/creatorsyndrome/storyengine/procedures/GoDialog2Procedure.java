package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

import net.creatorsyndrome.storyengine.network.StoryengineModVariables;

public class GoDialog2Procedure {
	public static void execute(LevelAccessor world, Player entity) {
		if (entity == null)
			return;
		StoryengineModVariables.MapVariables.get(world).current_story_id = StoryengineModVariables.MapVariables.get(world).variant_2_to;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
		entity.closeContainer();
	}
}

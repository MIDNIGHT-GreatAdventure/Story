package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.creatorsyndrome.storyengine.network.StoryengineModVariables;

public class SetDialogVariantsProcedure {
	public static void execute(LevelAccessor world, double var1to, double var2to, double var3to, String var1, String var2, String var3, String phrase) {
		if (var1 == null || var2 == null || var3 == null)
			return;
		StoryengineModVariables.MapVariables.get(world).variant_1 = var1;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
		StoryengineModVariables.MapVariables.get(world).variant_2 = var2;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
		StoryengineModVariables.MapVariables.get(world).variant_3 = var3;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
		StoryengineModVariables.MapVariables.get(world).variant_1_to = var1to;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
		StoryengineModVariables.MapVariables.get(world).variant_2_to = var2to;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
		StoryengineModVariables.MapVariables.get(world).variant_3_to = var3to;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
		StoryengineModVariables.MapVariables.get(world).phrase = phrase;
		StoryengineModVariables.MapVariables.get(world).syncData(world);
	}
}

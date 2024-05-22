package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.client.gui.components.EditBox;

import net.creatorsyndrome.storyengine.network.StoryengineModVariables;

import java.util.HashMap;

public class DialogWindowThisGUIIsOpenedProcedure {
	public static void execute(LevelAccessor world, HashMap guistate) {
		if (guistate == null)
			return;
		if (guistate.get("text:label_variant_1") instanceof EditBox _tf)
			_tf.setValue(StoryengineModVariables.MapVariables.get(world).variant_1);
		if (guistate.get("text:label_variant_2") instanceof EditBox _tf)
			_tf.setValue(StoryengineModVariables.MapVariables.get(world).variant_2);
		if (guistate.get("text:label_variant_3") instanceof EditBox _tf)
			_tf.setValue(StoryengineModVariables.MapVariables.get(world).variant_3);
		if (guistate.get("text:label_text") instanceof EditBox _tf)
			_tf.setValue(StoryengineModVariables.MapVariables.get(world).phrase);
	}
}

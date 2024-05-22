package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.Util;

public class SendMessageProcedure {
	public static void execute(LevelAccessor world, String Message, String NPC_name) {
		if (Message == null || NPC_name == null)
			return;
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastMessage(new TextComponent(("[" + NPC_name + "] " + Message)), ChatType.SYSTEM, Util.NIL_UUID);
	}
}

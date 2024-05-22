
package net.creatorsyndrome.storyengine.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.creatorsyndrome.storyengine.world.inventory.DialogWindowMenu;
import net.creatorsyndrome.storyengine.procedures.GoDialog3Procedure;
import net.creatorsyndrome.storyengine.procedures.GoDialog2Procedure;
import net.creatorsyndrome.storyengine.procedures.GoDialog1Procedure;
import net.creatorsyndrome.storyengine.StoryengineMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DialogWindowButtonMessage {
	private final int buttonID, x, y, z;

	public DialogWindowButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public DialogWindowButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(DialogWindowButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(DialogWindowButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level;
		HashMap guistate = DialogWindowMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			GoDialog1Procedure.execute(world, entity);
		}
		if (buttonID == 1) {

			GoDialog2Procedure.execute(world, entity);
		}
		if (buttonID == 2) {

			GoDialog3Procedure.execute(world, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		StoryengineMod.addNetworkMessage(DialogWindowButtonMessage.class, DialogWindowButtonMessage::buffer, DialogWindowButtonMessage::new, DialogWindowButtonMessage::handler);
	}
}

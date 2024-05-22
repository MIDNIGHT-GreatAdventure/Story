package net.creatorsyndrome.storyengine.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

public class ChangeNPCModeProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		(new Object() {
			public Entity getEntity() {
				try {
					return EntityArgument.getEntity(arguments, "entity");
				} catch (CommandSyntaxException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.getEntity()).getPersistentData().putString("mode", (StringArgumentType.getString(arguments, "mode")));
	}
}

package com.hiroki19990625.zgamemod.handler;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class InputKeyBindingHandler {

	public static KeyBinding reloadKey;

	public InputKeyBindingHandler() {
		reloadKey = new KeyBinding("key.gun_reload.desc", Keyboard.KEY_R, "key.zombie_game_mod.category");
		ClientRegistry.registerKeyBinding(reloadKey);
	}

}

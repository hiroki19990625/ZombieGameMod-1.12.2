package com.hiroki19990625.zgamemod.handler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MainMenuHandler {

	public GuiButton button;

	@SubscribeEvent
	public void onGuiOpenEvent(InitGuiEvent event) {
		GuiScreen screen = event.getGui();
		if (screen instanceof GuiMainMenu) {
			this.button = new GuiButton(15, screen.width / 2 + 110, 108 + 24 * 2,
					I18n.format("gui.zombie_game_mod.main_menu.button"));
			this.button.width = 70;
			event.getButtonList().add(this.button);
		}
	}
}

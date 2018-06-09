package com.hiroki19990625.zgamemod;

import org.apache.logging.log4j.Logger;

import com.hiroki19990625.zgamemod.handler.InputKeyBindingHandler;
import com.hiroki19990625.zgamemod.handler.MainMenuHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModCore.MOD_ID, name = ModCore.MOD_NAME, version = ModCore.MOD_VERSION)
public class ModCore {

	public static final String MOD_ID = "zombie_game_mod";
	public static final String MOD_NAME = "Zombie Game Mod";
	public static final String MOD_VERSION = "0.0.1";

	@Instance(MOD_ID)
	public static ModCore instance;

	public static Logger logger;

	public static RegisterManager manager;

	public static InputKeyBindingHandler inputKeyBindingHandler = new InputKeyBindingHandler();
	public static MainMenuHandler mainMenuHandler = new MainMenuHandler();

	@EventHandler
	public void construct(FMLConstructionEvent event) {
		MinecraftForge.EVENT_BUS.register(mainMenuHandler);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		ModCore.manager = new RegisterManager();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		this.registerModel(modelMesher, Item.getItemFromBlock(RegisterManager.stageBorderBlock));

		this.registerModel(modelMesher, RegisterManager.mp5);
	}

	private void registerModel(ItemModelMesher modelMesher, Item item) {
		modelMesher.register(item, 0,
				new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}

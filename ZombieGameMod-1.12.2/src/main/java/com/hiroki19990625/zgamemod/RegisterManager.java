package com.hiroki19990625.zgamemod;

import com.hiroki19990625.zgamemod.block.GameSetupBlock;
import com.hiroki19990625.zgamemod.block.StageBorderBlock;
import com.hiroki19990625.zgamemod.entity.ammo.NormalAmmoEntity;
import com.hiroki19990625.zgamemod.item.gun.MP5;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RegisterManager {

	//Blocks
	public static StageBorderBlock stageBorderBlock;
	public static GameSetupBlock gameSetupBlock;

	//Items
	public static MP5 mp5;

	//Sounds
	public static SoundEvent mp5_shot_sound;
	public static SoundEvent mp5_reload_sound;

	public RegisterManager() {
		this.registerBlocks();
		this.registerItems();
		this.registerEntities();
		this.registerSounds();
	}

	public void registerBlocks() {
		stageBorderBlock = new StageBorderBlock();
		gameSetupBlock = new GameSetupBlock();
	}

	public void registerItems() {
		mp5 = new MP5();
	}

	public void registerEntities() {
		EntityRegistry.registerModEntity(new ResourceLocation("zombie_game_mod", "normal_ammo_entity"),
				NormalAmmoEntity.class, "NormalAmmo", 0, ModCore.instance, 64, 1, true);
	}

	public void registerSounds() {
		mp5_shot_sound = new SoundEvent(new ResourceLocation("zombie_game_mod", "mp5_shot_sound"));
		mp5_reload_sound = new SoundEvent(new ResourceLocation("zombie_game_mod", "mp5_reload_sound"));
	}
}

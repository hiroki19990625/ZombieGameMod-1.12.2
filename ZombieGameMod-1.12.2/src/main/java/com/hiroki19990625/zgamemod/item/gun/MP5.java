package com.hiroki19990625.zgamemod.item.gun;

import com.hiroki19990625.zgamemod.RegisterManager;

import net.minecraft.util.SoundEvent;

public class MP5 extends GunBaseItem {

	public MP5() {
		super("gun_mp5");
	}

	@Override
	public int getMaxAmmo() {
		return 30;
	}

	@Override
	public int getMaxStackAmmo() {
		return 120;
	}

	public SoundEvent getShotSound() {
		return RegisterManager.sound_mp5_shot_sound;
	}

	public SoundEvent getReloadSound() {
		return RegisterManager.sound_mp5_reload_sound;
	}

}

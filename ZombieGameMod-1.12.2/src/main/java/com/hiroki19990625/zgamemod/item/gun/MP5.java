package com.hiroki19990625.zgamemod.item.gun;

public class MP5 extends GunBaseItem {

	public static MP5 gun;

	public static void createInstance() {
		gun = new MP5();
	}

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

}

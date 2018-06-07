package com.hiroki19990625.zgamemod.tab;

import com.hiroki19990625.zgamemod.RegisterManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ZombieGameModGunTab extends CreativeTabs {
	public static ZombieGameModGunTab zombieGameModGunTab = new ZombieGameModGunTab();

	public ZombieGameModGunTab() {
		super("zombie_game_mod_gun_tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(RegisterManager.mp5);
	}
}

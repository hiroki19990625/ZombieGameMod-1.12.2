package com.hiroki19990625.zgamemod.tab;

import com.hiroki19990625.zgamemod.item.gun.MP5;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ZombieGameModTab extends CreativeTabs {

	public static ZombieGameModTab zombieGameModTab = new ZombieGameModTab();

	public ZombieGameModTab() {
		super("zombie_game_mod_tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(MP5.gun);
	}
}

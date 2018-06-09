package com.hiroki19990625.zgamemod.block;

import com.hiroki19990625.zgamemod.ModCore;
import com.hiroki19990625.zgamemod.tab.ZombieGameModTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GameSetupBlock extends Block {

	public GameSetupBlock() {
		super(Material.ROCK, MapColor.YELLOW);

		this.setRegistryName(ModCore.MOD_ID, "game_setup_block");
		this.setUnlocalizedName("game_setup_block");
		this.setSoundType(SoundType.STONE);
		ForgeRegistries.BLOCKS.register(this);

		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(ModCore.MOD_ID, "game_setup_block"));

		this.setCreativeTab(ZombieGameModTab.zombieGameModTab);
	}
}

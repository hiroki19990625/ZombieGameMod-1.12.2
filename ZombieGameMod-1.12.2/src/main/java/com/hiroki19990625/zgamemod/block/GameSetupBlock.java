package com.hiroki19990625.zgamemod.block;

import com.hiroki19990625.zgamemod.ModCore;
import com.hiroki19990625.zgamemod.gui.GameSetupGui;
import com.hiroki19990625.zgamemod.tab.ZombieGameModTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GameSetupBlock extends Block {

	public GameSetupBlock() {
		super(Material.ROCK, MapColor.YELLOW);

		this.setRegistryName(ModCore.MOD_ID, "game_setup_block");
		this.setUnlocalizedName("game_setup_block");
		this.setSoundType(SoundType.STONE);
		this.setBlockUnbreakable();
		this.setResistance(6000001.0F);
		ForgeRegistries.BLOCKS.register(this);

		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(ModCore.MOD_ID, "game_setup_block"));

		this.setCreativeTab(ZombieGameModTab.zombieGameModTab);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockPowered(pos)) {
				this.execute(worldIn, pos);
			} else {
			}
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockPowered(pos)) {
				this.execute(worldIn, pos);
			} else {

			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Minecraft.getMinecraft().displayGuiScreen(new GameSetupGui());
		return true;
	}

	public void execute(World world, BlockPos pos) {
		ModCore.logger.info("On");
	}
}

package com.hiroki19990625.zgamemod.block;

import com.hiroki19990625.zgamemod.ModCore;
import com.hiroki19990625.zgamemod.tab.ZombieGameModTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StageBorderBlock extends Block {

	public StageBorderBlock() {
		super(Material.ROCK, MapColor.YELLOW);

		this.setRegistryName(ModCore.MOD_ID, "stageborder_block");
		this.setUnlocalizedName("stageborder_block");
		this.setSoundType(SoundType.STONE);
		ForgeRegistries.BLOCKS.register(this);

		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(ModCore.MOD_ID, "stageborder_block"));

		this.setCreativeTab(ZombieGameModTab.zombieGameModTab);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0f, 0f, 0f, 1f, 1f, 1f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.attackEntityFrom(DamageSource.GENERIC, 5f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return new AxisAlignedBB(0.0125f, 0f, 0.0125f, 0.9875f, 0.9875f, 0.9875f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

}

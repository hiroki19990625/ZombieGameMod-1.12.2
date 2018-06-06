package com.hiroki19990625.zgamemod.item.gun;

import java.util.List;

import com.hiroki19990625.zgamemod.ModCore;
import com.hiroki19990625.zgamemod.entity.ammo.GunAmmoBaseEntity;
import com.hiroki19990625.zgamemod.entity.ammo.NormalAmmoEntity;
import com.hiroki19990625.zgamemod.handler.InputKeyBindingManager;
import com.hiroki19990625.zgamemod.tab.ZombieGameModTab;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class GunBaseItem extends Item {

	public GunBaseItem(String name) {
		this.setRegistryName(ModCore.MOD_ID, name);
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setCreativeTab(ZombieGameModTab.zombieGameModTab);
		this.setFull3D();

		ForgeRegistries.ITEMS.register(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

		NBTTagCompound nbt = this.getGunNBT(stack);
		NBTTagCompound gun = nbt.getCompoundTag("Gun");
		int maxAmmo = this.getMaxAmmo();
		int ammo = gun.getInteger("Ammo");
		int stackAmmo = gun.getInteger("StackAmmo");

		if (InputKeyBindingManager.reloadKey.isKeyDown()) {
			if (ammo < maxAmmo) {
				if (stackAmmo <= 0) {
					stackAmmo = 0;
					return;
				} else if (stackAmmo >= maxAmmo) {
					int ammoCal = maxAmmo - ammo;
					ammo = maxAmmo;
					stackAmmo -= ammoCal;
				} else {
					int ammoCal = stackAmmo - ammo;
					ammo = ammoCal;
					stackAmmo -= ammoCal;
				}
			}
		}

		this.setGunNBT(stack, gun, ammo, stackAmmo);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return this.getUseDuration();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, final EntityPlayer par3EntityPlayer,
			EnumHand hand) {
		par3EntityPlayer.setActiveHand(hand);
		ItemStack par1ItemStack = par3EntityPlayer.getHeldItem(hand);

		NBTTagCompound nbt = this.getGunNBT(par1ItemStack);
		NBTTagCompound gun = nbt.getCompoundTag("Gun");
		int ammo = gun.getInteger("Ammo");
		int stackAmmo = gun.getInteger("StackAmmo");

		if (ammo != 0) {
			ammo--;

			float f = 1.0F;
			GunAmmoBaseEntity entityarrow = this.getAmmoEntity(par2World, par3EntityPlayer);
			entityarrow.shoot(par3EntityPlayer.getLookVec().x, par3EntityPlayer.getLookVec().y,
					par3EntityPlayer.getLookVec().z, f * 2.0F, 0);

			int i = (int) par3EntityPlayer.posX;
			int j = (int) par3EntityPlayer.posY;
			int k = (int) par3EntityPlayer.posZ;
			par2World.playSound((EntityPlayer) null, (double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D,
					(net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY
							.getObject(new ResourceLocation(("entity.arrow.shoot"))),
					SoundCategory.NEUTRAL, 1.0F, 1.0F
							/ (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			if (!par2World.isRemote) {
				par2World.spawnEntity(entityarrow);
			}
		} else {

		}

		this.setGunNBT(par1ItemStack, nbt, ammo, stackAmmo);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, par1ItemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		NBTTagCompound nbt = this.getGunNBT(stack);
		NBTTagCompound gun = nbt.getCompoundTag("Gun");
		int maxAmmo = this.getMaxAmmo();
		int ammo = gun.getInteger("Ammo");
		int stackAmmo = gun.getInteger("StackAmmo");

		tooltip.add("");
		tooltip.add(I18n.format("tooltip.gun.ammoStat", ammo, maxAmmo));
		tooltip.add(I18n.format("tooltip.gun.stackAmmo", stackAmmo));
	}

	public NBTTagCompound getGunNBT(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		if (!nbt.hasKey("Gun")) {
			NBTTagCompound gun = new NBTTagCompound();
			gun.setInteger("Ammo", this.getMaxAmmo());
			gun.setInteger("StackAmmo", this.getMaxStackAmmo());

			nbt.setTag("Gun", gun);
		}

		return nbt;
	}

	public void setGunNBT(ItemStack stack, NBTTagCompound nbt, int ammo, int stackAmmo) {
		NBTTagCompound gun = nbt.getCompoundTag("Gun");
		gun.setInteger("Ammo", ammo);
		gun.setInteger("StackAmmo", stackAmmo);

		nbt.setTag("Gun", gun);
		stack.setTagCompound(nbt);
	}

	public abstract int getMaxAmmo();

	public abstract int getMaxStackAmmo();

	public int getUseDuration() {
		return 0;
	}

	public GunAmmoBaseEntity getAmmoEntity(World worldIn, EntityLivingBase shooter) {
		NormalAmmoEntity entity = new NormalAmmoEntity(worldIn, shooter);
		return entity;
	}
}

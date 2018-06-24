package com.hiroki19990625.zgamemod.item.gun;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.hiroki19990625.zgamemod.ModCore;
import com.hiroki19990625.zgamemod.entity.ammo.GunAmmoBaseEntity;
import com.hiroki19990625.zgamemod.entity.ammo.NormalAmmoEntity;
import com.hiroki19990625.zgamemod.handler.InputKeyBindingHandler;
import com.hiroki19990625.zgamemod.tab.ZombieGameModGunTab;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class GunBaseItem extends Item {

	public GunBaseItem(String name) {
		this.setRegistryName(ModCore.MOD_ID, name);
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setCreativeTab(ZombieGameModGunTab.zombieGameModGunTab);
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
		int duration = gun.getInteger("Duration");
		int reloadDuration = gun.getInteger("ReloadDuration");
		boolean isReload = gun.getBoolean("IsReload");
		int i = (int) entityIn.posX;
		int j = (int) entityIn.posY;
		int k = (int) entityIn.posZ;

		if (entityIn instanceof EntityPlayer) {
			ItemStack hand = ((EntityPlayer) entityIn).getHeldItemMainhand();
			if (hand == stack) {
				if (stackAmmo <= 0) {
					stackAmmo = 0;
					this.setGunNBT(stack, gun, ammo, stackAmmo, duration, reloadDuration, isReload);
				} else {
					if (InputKeyBindingHandler.reloadKey.isKeyDown()) {
						if (ammo < maxAmmo && !isReload) {
							reloadDuration = this.getMaxReloadDuration();
							isReload = true;
							if (stackAmmo >= maxAmmo) {
								int ammoCal = maxAmmo - ammo;
								ammo = maxAmmo;
								stackAmmo -= ammoCal;
							} else {
								int ammoCal = maxAmmo - ammo;
								int ammoCal2 = stackAmmo - ammoCal;
								if (ammoCal2 <= 0) {
									ammo += ammoCal + ammoCal2;
									stackAmmo -= ammoCal + ammoCal2;
								} else {
									ammo += ammoCal;
									stackAmmo -= ammoCal;
								}
							}

							worldIn.playSound((EntityPlayer) null, (double) i + 0.5D, (double) j + 0.5D,
									(double) k + 0.5D,
									this.getReloadSound(),
									SoundCategory.NEUTRAL, 1.0F, 1.0F
											/ (itemRand.nextFloat() * 0.4F + 1.2F));

							this.setGunNBT(stack, gun, ammo, stackAmmo, duration, reloadDuration, isReload);
						}
					}
				}

				if (isReload) {
					reloadDuration--;

					this.setGunNBT(stack, gun, ammo, stackAmmo, duration, reloadDuration, isReload);
				}

				if (reloadDuration < 0 && isReload) {
					isReload = false;

					this.setGunNBT(stack, gun, ammo, stackAmmo, duration, reloadDuration, isReload);
				}

				if (Mouse.isButtonDown(1) && duration < 0 && !isReload) {
					if (ammo != 0) {
						duration = this.getMaxDuration();
						ammo -= this.getDiffAmmo();

						float f = this.getAmmoSpeed();
						worldIn.playSound((EntityPlayer) null, (double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D,
								this.getShotSound(),
								SoundCategory.NEUTRAL, 1.0F, 1.0F
										/ (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
						if (!worldIn.isRemote) {
							GunAmmoBaseEntity entity = this.shot(worldIn, (EntityPlayer) entityIn);
							worldIn.spawnEntity(entity);
						}
					}

					this.setGunNBT(stack, gun, ammo, stackAmmo, duration, reloadDuration, isReload);
				} else if (!(duration < 0)) {
					--duration;
					this.setGunNBT(stack, gun, ammo, stackAmmo, duration, reloadDuration, isReload);
				}
			}
		}
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
			gun.setInteger("Duration", this.getMaxDuration());
			gun.setInteger("ReloadDuration", this.getMaxReloadDuration());
			gun.setBoolean("IsReload", false);

			nbt.setTag("Gun", gun);
		}

		return nbt;
	}

	public void setGunNBT(ItemStack stack, NBTTagCompound nbt, int ammo, int stackAmmo, int duration,
			int reloadDuration, boolean isReload) {
		NBTTagCompound gun = nbt.getCompoundTag("Gun");
		gun.setInteger("Ammo", ammo);
		gun.setInteger("StackAmmo", stackAmmo);
		gun.setInteger("Duration", duration);
		gun.setInteger("ReloadDuration", reloadDuration);
		gun.setBoolean("IsReload", isReload);

		nbt.setTag("Gun", gun);
		stack.setTagCompound(nbt);
	}

	public GunAmmoBaseEntity shot(World par2World, EntityPlayer par3EntityPlayer) {
		GunAmmoBaseEntity entityarrow = this.getAmmoEntity(par2World, par3EntityPlayer);
		entityarrow.shoot(par3EntityPlayer.getLookVec().x, par3EntityPlayer.getLookVec().y,
				par3EntityPlayer.getLookVec().z, this.getAmmoSpeed() * 2.0F, 0);

		return entityarrow;
	}

	public abstract int getMaxAmmo();

	public abstract int getMaxStackAmmo();

	public abstract SoundEvent getShotSound();

	public abstract SoundEvent getReloadSound();

	public int getMaxDuration() {
		return 2;
	}

	public int getMaxReloadDuration() {
		return 20;
	}

	public float getAmmoSpeed() {
		return 5;
	}

	public int getDiffAmmo() {
		return 1;
	}

	public GunAmmoBaseEntity getAmmoEntity(World worldIn, EntityLivingBase shooter) {
		NormalAmmoEntity entity = new NormalAmmoEntity(worldIn, shooter);
		return entity;
	}
}

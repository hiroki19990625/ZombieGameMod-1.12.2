package com.hiroki19990625.zgamemod.entity.ammo;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class NormalAmmoEntity extends GunAmmoBaseEntity {

	public NormalAmmoEntity(World worldIn) {
		super(worldIn);
	}

	public NormalAmmoEntity(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
	}

}

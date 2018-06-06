package com.hiroki19990625.zgamemod.entity.ammo;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.world.World;

public abstract class GunAmmoBaseEntity extends EntityTippedArrow {

	public GunAmmoBaseEntity(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
	}

	@Override
	public void onUpdate() {
		// TODO 自動生成されたメソッド・スタブ
		super.onUpdate();

		if (this.inGround) {
			this.setDead();
		}

	}

}

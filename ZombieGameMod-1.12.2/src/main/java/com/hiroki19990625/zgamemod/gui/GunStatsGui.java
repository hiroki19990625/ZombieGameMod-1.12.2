package com.hiroki19990625.zgamemod.gui;

import java.awt.Color;

import com.hiroki19990625.zgamemod.item.gun.GunBaseItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GunStatsGui extends Gui {

	private Minecraft mc;

	private int width;

	public GunStatsGui(Minecraft mc) {
		super();
		this.mc = mc;
	}

	@SubscribeEvent
	public void onRenderGUI(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.HOTBAR) {
			return;
		}

		EntityPlayer props = this.mc.player;
		ItemStack itemStack = props.inventory.getCurrentItem();
		Item item = itemStack.getItem();

		if (item instanceof GunBaseItem) {
			NBTTagCompound nbt = ((GunBaseItem) item).getGunNBT(itemStack);
			NBTTagCompound gun = nbt.getCompoundTag("Gun");
			int ammo = gun.getInteger("Ammo");
			int stackAmmo = gun.getInteger("StackAmmo");
			boolean isReload = gun.getBoolean("IsReload");

			this.width = event.getResolution().getScaledWidth();

			String s1 = I18n.format("tooltip.gun.ammoStat", ammo, ((GunBaseItem) item).getMaxAmmo());
			String s2 = I18n.format("tooltip.gun.stackAmmo", stackAmmo);
			String s3 = "Reloading...";
			int i = 75;
			int j = 0;
			this.mc.fontRenderer.drawStringWithShadow(s1, this.width / 2 + this.width / 5,
					i + j++ * this.mc.fontRenderer.FONT_HEIGHT, Color.WHITE.getRGB());
			this.mc.fontRenderer.drawStringWithShadow(s2, this.width / 2 + this.width / 5,
					i + j++ * this.mc.fontRenderer.FONT_HEIGHT, Color.WHITE.getRGB());
			if (isReload) {
				this.mc.fontRenderer.drawStringWithShadow(s3, this.width / 2 + this.width / 5,
						i + j++ * this.mc.fontRenderer.FONT_HEIGHT, Color.RED.getRGB());
			}
		}
	}
}
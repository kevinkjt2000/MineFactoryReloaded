package powercrystals.minefactoryreloaded.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import powercrystals.minefactoryreloaded.MFRRegistry;

public class ItemFactoryFood extends ItemFood
{
	public ItemFactoryFood(int foodRestored, float sustenance)
	{
		super(foodRestored, sustenance, false);
	}

	@Override
	public Item setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		MFRRegistry.registerItem(this, getUnlocalizedName());
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon("minefactoryreloaded:" + getUnlocalizedName());
	}
}

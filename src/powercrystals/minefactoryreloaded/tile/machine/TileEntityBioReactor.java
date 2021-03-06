package powercrystals.minefactoryreloaded.tile.machine;

import cofh.core.util.CoreUtils;
import cofh.core.util.fluid.FluidTankAdv;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Map;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiBioReactor;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerBioReactor;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TileEntityBioReactor extends TileEntityFactoryInventory implements ITankContainerBucketable
{
	private int _burnTime;
	private static final int _burnTimeMax = 8000;
	private static final int _bioFuelPerTick = 1;
	private static final int _burnTimeDecreasePerTick = 1;

	// start at 0 for 0 slots; increase by 5, then an additional 10 each time (upward-sloping curve)
	private static final int[] _outputValues = { 0, 5, 25, 70, 150, 275, 455, 700, 1020, 1425 };

	public TileEntityBioReactor()
	{
		super(Machine.BioReactor);
		setManageSolids(true);
	}

	public int getBurnTime()
	{
		return _burnTime;
	}

	public void setBurnTime(int burnTime)
	{
		_burnTime = burnTime;
	}

	public int getBurnTimeMax()
	{
		return _burnTimeMax;
	}

	public int getOutputValue()
	{
		int occupiedSlots = 0;
		for(int i = 9; i < 18; i++)
		{
			if(_inventory[i] != null)
			{
				occupiedSlots++;
			}
		}

		return _outputValues[occupiedSlots];
	}

	public int getOutputValueMax()
	{
		return _outputValues[9];
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (!worldObj.isRemote)
		{
			Map<Item, IFactoryPlantable> plantables = MFRRegistry.getPlantables();
			for (int i = 0; i < 9; i++)
			{
				ItemStack item = _inventory[i];
				if (item == null)
					continue;
				if (plantables.containsKey(item.getItem()) &&
						plantables.get(item.getItem()).canBePlanted(item, true))
				{
					int targetSlot = findMatchingSlot(_inventory[i]);
					if (targetSlot < 0)
						continue;

					if (_inventory[targetSlot] == null)
					{
						_inventory[targetSlot] = _inventory[i];
						_inventory[i] = null;
					}
					else
					{
						UtilInventory.mergeStacks(_inventory[targetSlot], _inventory[i]);
						if (_inventory[i].stackSize <= 0)
							_inventory[i] = null;
					}
				}
			}

			if (CoreUtils.isRedstonePowered(this))
				return;

			int newBurn = getOutputValue();
			if (_burnTimeMax - _burnTime >= newBurn)
			{
				_burnTime += newBurn;
				for (int i = 9; i < 18; i++)
					if (_inventory[i] != null)
						decrStackSize(i, 1);
			}

			if (_burnTime > 0 && _tanks[0].getFluidAmount() <= _tanks[0].getCapacity() - _bioFuelPerTick)
			{
				_burnTime -= _burnTimeDecreasePerTick;
				_tanks[0].fill(FluidRegistry.getFluidStack("biofuel", _bioFuelPerTick), true);
			}
		}
	}

	private int findMatchingSlot(ItemStack s)
	{
		int emptySlot = -1;
		for (int i = 9; i < 18; i++)
		{
			if (_inventory[i] == null)
			{
				if (emptySlot == -1)
					emptySlot = i;
			}
			else if (_inventory[i].isItemEqual(s))
			{
				return i;
			}
		}
		return emptySlot;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiBioReactor(getContainer(inventoryPlayer), this);
	}

	@Override
	public ContainerBioReactor getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerBioReactor(this, inventoryPlayer);
	}

	@Override
	protected boolean shouldPumpLiquid()
	{
		return true;
	}

	@Override
	public int getSizeInventory()
	{
		return 18;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 9;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int sideordinal)
	{
		if (stack != null)
			if(slot < 9)
			{
				IFactoryPlantable p = MFRRegistry.getPlantables().get(stack.getItem());
				return p != null && p.canBePlanted(stack, true);
			}
		return false;
	}

	@Override
	public boolean allowBucketDrain(ItemStack stack)
	{
		return true;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return drain(maxDrain, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return drain(resource, doDrain);
	}

	@Override
	protected FluidTankAdv[] createTanks()
	{
		return new FluidTankAdv[]{new FluidTankAdv(4 * BUCKET_VOLUME)};
	}

	@Override
	public void writeItemNBT(NBTTagCompound nbttagcompound)
	{
		super.writeItemNBT(nbttagcompound);
		nbttagcompound.setInteger("burnTime", _burnTime);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_burnTime = nbttagcompound.getInteger("burnTime");
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return true;
	}
}

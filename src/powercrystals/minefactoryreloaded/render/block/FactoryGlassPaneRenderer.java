package powercrystals.minefactoryreloaded.render.block;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.block.decor.BlockFactoryGlassPane;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class FactoryGlassPaneRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block tile, int metadata, int modelID, RenderBlocks renderer)
	{
		BlockFactoryGlassPane block = (BlockFactoryGlassPane)tile;

		Tessellator tessellator = Tessellator.instance;
		int color = block.getRenderColor(metadata);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float anaglyphRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
			float anaglyphGreen = (red * 30.0F + green * 70.0F) / 100.0F;
			float anaglyphBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
			red = anaglyphRed;
			green = anaglyphGreen;
			blue = anaglyphBlue;
		}
		IIcon iconGlass, iconStreaks, iconSide, iconOverlay;

		iconGlass = block.getIcon(0, metadata);
		iconStreaks = block.getIcon(0, 16 | metadata);
		iconSide = block.func_150097_e();
		iconOverlay = block.getIcon(0, 32 | metadata);

		double minXGlass = iconGlass.getMinU();
		double maxXGlass = iconGlass.getMaxU();
		double minYGlass = iconGlass.getMinV();
		double maxYGlass = iconGlass.getMaxV();

		double minXStreaks = iconStreaks.getMinU();
		double maxXStreaks = iconStreaks.getMaxU();
		double minYStreaks = iconStreaks.getMinV();
		double maxYStreaks = iconStreaks.getMaxV();

		double minXSide = iconSide.getInterpolatedU(7.0D);
		double maxXSide = iconSide.getInterpolatedU(9.0D);
		double minYSide = iconSide.getMinV();
		double maxYSide = iconSide.getMaxV();

		double minXOverlay = iconOverlay.getMinU();
		double maxXOverlay = iconOverlay.getMaxU();
		double minYOverlay = iconOverlay.getMinV();
		double maxYOverlay = iconOverlay.getMaxV();

		double offset = 0.001D;

		double xMin = 0, xMax = 1;
		double yMin = 0, yMax = 1;
		double zMid = 0.5;

		double negSideXOffset = zMid - 0.0625D;
		double posSideXOffset = zMid + 0.0625D;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(red, green, blue);
		tessellator.addVertexWithUV(xMin, yMax, zMid, minXGlass, minYGlass);
		tessellator.addVertexWithUV(xMin, yMin, zMid, minXGlass, maxYGlass);
		tessellator.addVertexWithUV(xMax, yMin, zMid, maxXGlass, maxYGlass);
		tessellator.addVertexWithUV(xMax, yMax, zMid, maxXGlass, minYGlass);

		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMax, zMid, minXStreaks, minYStreaks);
		tessellator.addVertexWithUV(xMin, yMin, zMid, minXStreaks, maxYStreaks);
		tessellator.addVertexWithUV(xMax, yMin, zMid, maxXStreaks, maxYStreaks);
		tessellator.addVertexWithUV(xMax, yMax, zMid, maxXStreaks, minYStreaks);

		tessellator.addVertexWithUV(xMin, yMax, zMid, minXOverlay, minYOverlay);
		tessellator.addVertexWithUV(xMin, yMin, zMid, minXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(xMax, yMin, zMid, maxXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(xMax, yMax, zMid, maxXOverlay, minYOverlay);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMax, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMin, yMin, negSideXOffset, minXSide, maxYSide);
		tessellator.addVertexWithUV(xMin, yMin, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMin, yMax, posSideXOffset, maxXSide, minYSide);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMax, yMax, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMax, yMin, negSideXOffset, minXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMin, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMax, posSideXOffset, maxXSide, minYSide);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMax + offset, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMax + offset, posSideXOffset, maxXSide, minYSide);
		tessellator.addVertexWithUV(xMax, yMax + offset, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMin, yMax + offset, negSideXOffset, minXSide, maxYSide);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMin - offset, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMin - offset, posSideXOffset, maxXSide, minYSide);
		tessellator.addVertexWithUV(xMax, yMin - offset, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMin, yMin - offset, negSideXOffset, minXSide, maxYSide);
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
			Block tile, int modelId, RenderBlocks renderer)
	{
		BlockPane block = (BlockPane)tile;

		int worldHeight = blockAccess.getHeight();
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		Tessellator tess = Tessellator.instance;
		tess.setBrightness(tile.getMixedBrightnessForBlock(blockAccess, x, y, z));
		int color = block.getRenderColor(metadata);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float anaglyphRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
			float anaglyphGreen = (red * 30.0F + green * 70.0F) / 100.0F;
			float anaglyphBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
			red = anaglyphRed;
			green = anaglyphGreen;
			blue = anaglyphBlue;
		}

		IIcon iconGlass, iconStreaks, iconSide, iconOverlaySouth, iconOverlayWest;

		if (renderer.hasOverrideBlockTexture())
		{
			iconGlass = iconStreaks = iconSide = 
			iconOverlaySouth = iconOverlayWest =
					renderer.overrideBlockTexture;
		}
		else
		{
			iconGlass = block.getIcon(0, metadata);
			iconStreaks = block.getIcon(0, 16 | metadata);
			iconSide = block.func_150097_e();
			iconOverlaySouth = block.getIcon(blockAccess, x, y, z, 2);
			iconOverlayWest = block.getIcon(blockAccess, x, y, z, 5);
		}

		double minUGlass = iconGlass.getMinU();
		double midUGlassA = iconGlass.getInterpolatedU(9.0D);
		double midUGlassB = iconGlass.getInterpolatedU(7.0D);
		double maxUGlass = iconGlass.getMaxU();
		double minVGlass = iconGlass.getMinV();
		double maxVGlass = iconGlass.getMaxV();

		double minUStreaks = iconStreaks.getMinU();
		double midUStreaksA = iconStreaks.getInterpolatedU(9.0D);
		double midUStreaksB = iconStreaks.getInterpolatedU(7.0D);
		double maxUStreaks = iconStreaks.getMaxU();
		double minVStreaks = iconStreaks.getMinV();
		double maxVStreaks = iconStreaks.getMaxV();

		double minUEdge = iconSide.getInterpolatedU(7.0D);
		double maxUEdge = iconSide.getInterpolatedU(9.0D);
		double minVEdge = iconSide.getMinV();
		double midVEdgeA = iconSide.getInterpolatedV(9.0D);
		double midVEdgeB = iconSide.getInterpolatedV(7.0D);
		double maxVEdge = iconSide.getMaxV();

		double minUSouth = iconOverlaySouth.getMinU();
		double midUSouthA = iconOverlaySouth.getInterpolatedU(9.0D);
		double midUSouthB = iconOverlaySouth.getInterpolatedU(7.0D);
		double maxUSouth = iconOverlaySouth.getMaxU();
		double minVSouth = iconOverlaySouth.getMinV();
		double maxVSouth = iconOverlaySouth.getMaxV();

		double minUWest = iconOverlayWest.getMinU();
		double midUWestA = iconOverlayWest.getInterpolatedU(9.0D);
		double midUWestB = iconOverlayWest.getInterpolatedU(7.0D);
		double maxUWest = iconOverlayWest.getMaxU();
		double minVWest = iconOverlayWest.getMinV();
		double maxVWest = iconOverlayWest.getMaxV();
		
		double xMin = x;
		double xMid = x + 0.5D;
		double xMax = x + 1;

		double zMin = z;
		double zMid = z + 0.5D;
		double zMax = z + 1;

		double yMin = y;
		double yMax = y + 1;

		final double vertSideZOffset = 0.00D;
		final double vertSideXOffset = 0.00D;
		final double offset = 1 / 16f; 

		//double yMinZ = yMin - vertSideZOffset;
		//double yMaxZ = yMax + vertSideZOffset;
		//double yMinX = yMin - vertSideXOffset;
		//double yMaxX = yMax + vertSideXOffset;

		double minEdgeZ = xMid - offset;
		double maxEdgeZ = xMid + offset;
		double minEdgeX = zMid - offset;
		double maxEdgeX = zMid + offset;

		boolean connectedNegZ = block.canPaneConnectTo(blockAccess, x, y, z - 1, ForgeDirection.NORTH);
		boolean connectedPosZ = block.canPaneConnectTo(blockAccess, x, y, z + 1, ForgeDirection.SOUTH);
		boolean connectedNegX = block.canPaneConnectTo(blockAccess, x - 1, y, z, ForgeDirection.WEST);
		boolean connectedPosX = block.canPaneConnectTo(blockAccess, x + 1, y, z, ForgeDirection.EAST);

		boolean renderTop = y >= worldHeight || block.shouldSideBeRendered(blockAccess, x, y + 1, z, 1);
		boolean renderBottom = y <= 0 || block.shouldSideBeRendered(blockAccess, x, y - 1, z, 0);

		if ((!connectedNegX | !connectedPosX) & (connectedNegX | connectedPosX | connectedNegZ | connectedPosZ))
		{
			if (connectedNegX & !connectedPosX)
			{
				tess.setColorOpaque_F(red, green, blue);
				drawPlane(tess, xMin, yMin, zMid, xMid + offset, yMax, zMid, minUGlass, minVGlass, midUGlassA, maxVGlass, 0, offset);
				drawPlane(tess, xMin, yMin, zMid, xMid + offset, yMax, zMid, minUGlass, minVGlass, midUGlassA, maxVGlass, 0, -offset);

				tess.setColorOpaque_F(1, 1, 1);
				drawPlane(tess, xMin, yMin, zMid, xMid + offset, yMax, zMid, minUStreaks, minVStreaks, midUStreaksA, maxVStreaks, 0, offset);
				drawPlane(tess, xMin, yMin, zMid, xMid + offset, yMax, zMid, minUStreaks, minVStreaks, midUStreaksA, maxVStreaks, 0, -offset);
				
				drawPlane(tess, xMin, yMin, zMid, xMid + offset, yMax, zMid, minUSouth, minVSouth, midUSouthA, maxVSouth, 0, offset);
				drawPlane(tess, xMin, yMin, zMid, xMid + offset, yMax, zMid, minUSouth, minVSouth, midUSouthA, maxVSouth, 0, -offset);

				if (!connectedPosZ & !connectedNegZ)
				{
					tess.addVertexWithUV(xMid + offset, yMax, maxEdgeX, minUEdge, minVEdge);
					tess.addVertexWithUV(xMid + offset, yMin, maxEdgeX, minUEdge, maxVEdge);
					tess.addVertexWithUV(xMid + offset, yMin, minEdgeX, maxUEdge, maxVEdge);
					tess.addVertexWithUV(xMid + offset, yMax, minEdgeX, maxUEdge, minVEdge); 
				}

				if (renderTop)
				{
					tess.addVertexWithUV(xMin, yMax + vertSideXOffset, maxEdgeX, maxUEdge, midVEdgeA);
					tess.addVertexWithUV(xMid + offset, yMax + vertSideXOffset, maxEdgeX, maxUEdge, maxVEdge);
					tess.addVertexWithUV(xMid + offset, yMax + vertSideXOffset, minEdgeX, minUEdge, maxVEdge);
					tess.addVertexWithUV(xMin, yMax + vertSideXOffset, minEdgeX, minUEdge, midVEdgeA);
				}

				if (renderBottom)
				{
					tess.addVertexWithUV(xMin, yMin - vertSideXOffset, minEdgeX, minUEdge, midVEdgeA);
					tess.addVertexWithUV(xMid + offset, yMin - vertSideXOffset, minEdgeX, minUEdge, maxVEdge);
					tess.addVertexWithUV(xMid + offset, yMin - vertSideXOffset, maxEdgeX, maxUEdge, maxVEdge);
					tess.addVertexWithUV(xMin, yMin - vertSideXOffset, maxEdgeX, maxUEdge, midVEdgeA);
				}
			}
			else if (!connectedNegX & connectedPosX)
			{
				tess.setColorOpaque_F(red, green, blue);
				drawPlane(tess, xMid - offset, yMin, zMid, xMax, yMax, zMid, midUGlassB, minVGlass, maxUGlass, maxVGlass, 0, offset);
				drawPlane(tess, xMid - offset, yMin, zMid, xMax, yMax, zMid, midUGlassB, minVGlass, maxUGlass, maxVGlass, 0, -offset);

				tess.setColorOpaque_F(1, 1, 1);
				drawPlane(tess, xMid - offset, yMin, zMid, xMax, yMax, zMid, midUStreaksB, minVStreaks, maxUStreaks, maxVStreaks, 0, offset);
				drawPlane(tess, xMid - offset, yMin, zMid, xMax, yMax, zMid, midUStreaksB, minVStreaks, maxUStreaks, maxVStreaks, 0, -offset);
				
				drawPlane(tess, xMid - offset, yMin, zMid, xMax, yMax, zMid, midUSouthB, minVSouth, maxUSouth, maxVSouth, 0, offset);
				drawPlane(tess, xMid - offset, yMin, zMid, xMax, yMax, zMid, midUSouthB, minVSouth, maxUSouth, maxVSouth, 0, -offset);

				if (!connectedPosZ & !connectedNegZ)
				{
					tess.addVertexWithUV(xMid - offset, yMax, minEdgeX, minUEdge, minVEdge);
					tess.addVertexWithUV(xMid - offset, yMin, minEdgeX, minUEdge, maxVEdge);
					tess.addVertexWithUV(xMid - offset, yMin, maxEdgeX, maxUEdge, maxVEdge);
					tess.addVertexWithUV(xMid - offset, yMax, maxEdgeX, maxUEdge, minVEdge);
				}

				if (renderTop)
				{
					tess.addVertexWithUV(xMid - offset, yMax + vertSideXOffset, maxEdgeX, maxUEdge, minVEdge);
					tess.addVertexWithUV(xMax, yMax + vertSideXOffset, maxEdgeX, maxUEdge, midVEdgeB);
					tess.addVertexWithUV(xMax, yMax + vertSideXOffset, minEdgeX, minUEdge, midVEdgeB);
					tess.addVertexWithUV(xMid - offset, yMax + vertSideXOffset, minEdgeX, minUEdge, minVEdge);
				}

				if (renderBottom)
				{
					tess.addVertexWithUV(xMid - offset, yMin - vertSideXOffset, minEdgeX, minUEdge, minVEdge);
					tess.addVertexWithUV(xMax, yMin - vertSideXOffset, minEdgeX, minUEdge, midVEdgeB);
					tess.addVertexWithUV(xMax, yMin - vertSideXOffset, maxEdgeX, maxUEdge, midVEdgeB);
					tess.addVertexWithUV(xMid - offset, yMin - vertSideXOffset, maxEdgeX, maxUEdge, minVEdge);
				}
			}
		}
		else
		{
			tess.setColorOpaque_F(red, green, blue);
			drawPlane(tess, xMin, yMin, zMid, xMax, yMax, zMid, minUGlass, minVGlass, maxUGlass, maxVGlass, 0, offset);
			drawPlane(tess, xMin, yMin, zMid, xMax, yMax, zMid, minUGlass, minVGlass, maxUGlass, maxVGlass, 0, -offset);

			tess.setColorOpaque_F(1, 1, 1);
			drawPlane(tess, xMin, yMin, zMid, xMax, yMax, zMid, minUStreaks, minVStreaks, maxUStreaks, maxVStreaks, 0, offset);
			drawPlane(tess, xMin, yMin, zMid, xMax, yMax, zMid, minUStreaks, minVStreaks, maxUStreaks, maxVStreaks, 0, -offset);
			
			drawPlane(tess, xMin, yMin, zMid, xMax, yMax, zMid, minUSouth, minVSouth, maxUSouth, maxVSouth, 0, offset);
			drawPlane(tess, xMin, yMin, zMid, xMax, yMax, zMid, minUSouth, minVSouth, maxUSouth, maxVSouth, 0, -offset);

			if (!connectedPosX & !connectedNegX)
			{
				drawPlane(tess, xMin, yMin, minEdgeX, xMin, yMax, maxEdgeX, minUEdge, minVEdge, maxUEdge, maxVEdge, false);
				
				drawPlane(tess, xMax, yMin, minEdgeX, xMax, yMax, maxEdgeX, minUEdge, minVEdge, maxUEdge, maxVEdge, true);
			}

			if (renderTop)
			{
				tess.addVertexWithUV(xMin, yMax + vertSideXOffset, maxEdgeX, maxUEdge, minVEdge);
				tess.addVertexWithUV(xMax, yMax + vertSideXOffset, maxEdgeX, maxUEdge, maxVEdge);
				tess.addVertexWithUV(xMax, yMax + vertSideXOffset, minEdgeX, minUEdge, maxVEdge);
				tess.addVertexWithUV(xMin, yMax + vertSideXOffset, minEdgeX, minUEdge, minVEdge);
			}

			if (renderBottom)
			{
				tess.addVertexWithUV(xMin, yMin - vertSideXOffset, minEdgeX, minUEdge, minVEdge);
				tess.addVertexWithUV(xMax, yMin - vertSideXOffset, minEdgeX, minUEdge, maxVEdge);
				tess.addVertexWithUV(xMax, yMin - vertSideXOffset, maxEdgeX, maxUEdge, maxVEdge);
				tess.addVertexWithUV(xMin, yMin - vertSideXOffset, maxEdgeX, maxUEdge, minVEdge);
			}
		}

		if ((!connectedNegZ | !connectedPosZ) & (connectedNegX | connectedPosX | connectedNegZ | connectedPosZ))
		{
			if (connectedNegZ & !connectedPosZ)
			{
				tess.setColorOpaque_F(red, green, blue);
				drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMid + offset, minUGlass, minVGlass, midUGlassA, maxVGlass, offset, 0);
				drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMid + offset, minUGlass, minVGlass, midUGlassA, maxVGlass, -offset, 0);

				tess.setColorOpaque_F(1, 1, 1);
				drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMid + offset, minUStreaks, minVStreaks, midUStreaksA, maxVStreaks, offset, 0);
				drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMid + offset, minUStreaks, minVStreaks, midUStreaksA, maxVStreaks, -offset, 0);
				
				drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMid + offset, minUWest, minVWest, midUWestA, maxVWest, offset, 0);
				drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMid + offset, minUWest, minVWest, midUWestA, maxVWest, -offset, 0);

				if (!connectedPosX & !connectedNegX)
				{
					tess.addVertexWithUV(minEdgeZ, yMax, zMid + offset, minUEdge, minVEdge);
					tess.addVertexWithUV(minEdgeZ, yMin, zMid + offset, minUEdge, maxVEdge);
					tess.addVertexWithUV(maxEdgeZ, yMin, zMid + offset, maxUEdge, maxVEdge);
					tess.addVertexWithUV(maxEdgeZ, yMax, zMid + offset, maxUEdge, minVEdge);
				}

				if (renderTop)
				{
					tess.addVertexWithUV(minEdgeZ, yMax + vertSideZOffset, zMin, maxUEdge, minVEdge);
					tess.addVertexWithUV(minEdgeZ, yMax + vertSideZOffset, zMid + offset, maxUEdge, midVEdgeA);
					tess.addVertexWithUV(maxEdgeZ, yMax + vertSideZOffset, zMid + offset, minUEdge, midVEdgeA);
					tess.addVertexWithUV(maxEdgeZ, yMax + vertSideZOffset, zMin, minUEdge, minVEdge);
				}

				if (renderBottom)
				{
					tess.addVertexWithUV(maxEdgeZ, yMin - vertSideZOffset, zMin, minUEdge, minVEdge);
					tess.addVertexWithUV(maxEdgeZ, yMin - vertSideZOffset, zMid + offset, minUEdge, midVEdgeA);
					tess.addVertexWithUV(minEdgeZ, yMin - vertSideZOffset, zMid + offset, maxUEdge, midVEdgeA);
					tess.addVertexWithUV(minEdgeZ, yMin - vertSideZOffset, zMin, maxUEdge, minVEdge);
				}
			}
			else if (!connectedNegZ & connectedPosZ)
			{
				tess.setColorOpaque_F(red, green, blue);
				drawPlane(tess, xMid, yMin, zMid - offset, xMid, yMax, zMax, midUGlassB, minVGlass, maxUGlass, maxVGlass, offset, 0);
				drawPlane(tess, xMid, yMin, zMid - offset, xMid, yMax, zMax, midUGlassB, minVGlass, maxUGlass, maxVGlass, -offset, 0);

				tess.setColorOpaque_F(1, 1, 1);
				drawPlane(tess, xMid, yMin, zMid - offset, xMid, yMax, zMax, midUStreaksB, minVStreaks, maxUStreaks, maxVStreaks, offset, 0);
				drawPlane(tess, xMid, yMin, zMid - offset, xMid, yMax, zMax, midUStreaksB, minVStreaks, maxUStreaks, maxVStreaks, -offset, 0);
				
				drawPlane(tess, xMid, yMin, zMid - offset, xMid, yMax, zMax, midUWestB, minVWest, maxUWest, maxVWest, offset, 0);
				drawPlane(tess, xMid, yMin, zMid - offset, xMid, yMax, zMax, midUWestB, minVWest, maxUWest, maxVWest, -offset, 0);

				if (!connectedPosX & !connectedNegX)
				{
					tess.addVertexWithUV(maxEdgeZ, yMax, zMid - offset, minUEdge, minVEdge);
					tess.addVertexWithUV(maxEdgeZ, yMin, zMid - offset, minUEdge, maxVEdge);
					tess.addVertexWithUV(minEdgeZ, yMin, zMid - offset, maxUEdge, maxVEdge);
					tess.addVertexWithUV(minEdgeZ, yMax, zMid - offset, maxUEdge, minVEdge);
				}

				if (renderTop)
				{
					tess.addVertexWithUV(minEdgeZ, yMax + vertSideZOffset, zMid - offset, minUEdge, midVEdgeB);
					tess.addVertexWithUV(minEdgeZ, yMax + vertSideZOffset, zMax, minUEdge, maxVEdge);
					tess.addVertexWithUV(maxEdgeZ, yMax + vertSideZOffset, zMax, maxUEdge, maxVEdge);
					tess.addVertexWithUV(maxEdgeZ, yMax + vertSideZOffset, zMid - offset, maxUEdge, midVEdgeB);
				}

				if (renderBottom)
				{
					tess.addVertexWithUV(maxEdgeZ, yMin - vertSideZOffset, zMid - offset, maxUEdge, midVEdgeB);
					tess.addVertexWithUV(maxEdgeZ, yMin - vertSideZOffset, zMax, maxUEdge, maxVEdge);
					tess.addVertexWithUV(minEdgeZ, yMin - vertSideZOffset, zMax, minUEdge, maxVEdge);
					tess.addVertexWithUV(minEdgeZ, yMin - vertSideZOffset, zMid - offset, minUEdge, midVEdgeB);
				}
			}
		}
		else
		{
			tess.setColorOpaque_F(red, green, blue);
			drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMax, minUGlass, minVGlass, maxUGlass, maxVGlass, offset, 0);
			drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMax, minUGlass, minVGlass, maxUGlass, maxVGlass, -offset, 0);

			tess.setColorOpaque_F(1, 1, 1);
			drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMax, minUStreaks, minVStreaks, maxUStreaks, maxVStreaks, offset, 0);
			drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMax, minUStreaks, minVStreaks, maxUStreaks, maxVStreaks, -offset, 0);
			
			drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMax, minUWest, minVWest, maxUWest, maxVWest, offset, 0);
			drawPlane(tess, xMid, yMin, zMin, xMid, yMax, zMax, minUWest, minVWest, maxUWest, maxVWest, -offset, 0);

			if (!connectedPosZ & !connectedNegZ)
			{
				drawPlane(tess, minEdgeZ, yMin, zMin, maxEdgeZ, yMax, zMin, minUEdge, minVEdge, maxUEdge, maxVEdge, true);
				
				drawPlane(tess, minEdgeZ, yMin, zMax, maxEdgeZ, yMax, zMax, minUEdge, minVEdge, maxUEdge, maxVEdge, false);
			}

			if (renderTop)
			{
				tess.addVertexWithUV(maxEdgeZ, yMax + vertSideZOffset, zMax, maxUEdge, maxVEdge);
				tess.addVertexWithUV(maxEdgeZ, yMax + vertSideZOffset, zMin, maxUEdge, minVEdge);
				tess.addVertexWithUV(minEdgeZ, yMax + vertSideZOffset, zMin, minUEdge, minVEdge);
				tess.addVertexWithUV(minEdgeZ, yMax + vertSideZOffset, zMax, minUEdge, maxVEdge);
			}
			// TODO: fix slightly awkward rendering when pane on top/bottom doesn't connect on both sides
			if (renderBottom)
			{
				tess.addVertexWithUV(minEdgeZ, yMin - vertSideZOffset, zMax, minUEdge, maxVEdge);
				tess.addVertexWithUV(minEdgeZ, yMin - vertSideZOffset, zMin, minUEdge, minVEdge);
				tess.addVertexWithUV(maxEdgeZ, yMin - vertSideZOffset, zMin, maxUEdge, minVEdge);
				tess.addVertexWithUV(maxEdgeZ, yMin - vertSideZOffset, zMax, maxUEdge, maxVEdge);
			}
		}

		return true;
	}

	private void drawPlane(Tessellator t, double xMin, double yMin, double zMin,
											double xMax, double yMax, double zMax,
													double uMin, double vMin,
													double uMax, double vMax,
													double xOff, double zOff)
	{
		drawPlane(t, xMin, yMin, zMin, xMax, yMax, zMax, uMin, vMin, uMax, vMax, xOff, zOff, (xOff > 0) | zOff < 0);
	}
	
	private void drawPlane(Tessellator t, double xMin, double yMin, double zMin,
											double xMax, double yMax, double zMax,
													double uMin, double vMin,
													double uMax, double vMax,
													double xOff, double zOff,
													boolean backwards)
	{
		drawPlane(t, xMin + xOff, yMin, zMin + zOff, xMax + xOff, yMax, zMax + zOff, uMin, vMin, uMax, vMax, backwards);
	}
	
	private void drawPlane(Tessellator t, double xMin, double yMin, double zMin,
											double xMax, double yMax, double zMax,
													double uMin, double vMin,
													double uMax, double vMax,
													boolean backwards)
	{
		if (!backwards)
		{
			t.addVertexWithUV(xMin, yMax, zMin, uMin, vMin);
			t.addVertexWithUV(xMin, yMin, zMin, uMin, vMax);
			t.addVertexWithUV(xMax, yMin, zMax, uMax, vMax);
			t.addVertexWithUV(xMax, yMax, zMax, uMax, vMin);
		}
		else
		{
			t.addVertexWithUV(xMin, yMin, zMin, uMin, vMax);
			t.addVertexWithUV(xMin, yMax, zMin, uMin, vMin);
			t.addVertexWithUV(xMax, yMax, zMax, uMax, vMin);
			t.addVertexWithUV(xMax, yMin, zMax, uMax, vMax);
		}
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return MineFactoryReloadedCore.renderIdFactoryGlassPane;
	}
}

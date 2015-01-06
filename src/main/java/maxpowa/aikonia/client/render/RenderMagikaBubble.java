package maxpowa.aikonia.client.render;

import maxpowa.aikonia.Aikonia;
import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagikaBubble extends Render
{
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Aikonia.MODID, "textures/entity/magika_bubble.png");
    
    private static Profiler profiler = null;
    
    public RenderMagikaBubble()
    {
        this.shadowSize = 0.10F;
        this.shadowOpaque = 0.75F;
        profiler = Minecraft.getMinecraft().mcProfiler;
    }

    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.doRender((EntityMagikaBubble)entity, x, y, z, p_76986_8_, partialTicks);
    }

    public void doRender(EntityMagikaBubble entity, double x, double y, double z, float f, float partialTicks)
    {
    	profiler.startSection("magika_bubble_render");
        //System.out.println("Rendering now...");
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        this.bindTexture(this.getEntityTexture(entity));
        
        long diff = System.currentTimeMillis() - entity.getInceptionTime();
        long i = diff/100 % (256);
        float f2 = (float)(i % 4 * 16 + 0) / 64.0F;
        float f3 = (float)(i % 4 * 16 + 16) / 64.0F;
        float f4 = (float)(i / 4 * 16 + 0) / 64.0F;
        float f5 = (float)(i / 4 * 16 + 16) / 64.0F;
        
        int j = entity.getBrightnessForRender(partialTicks);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - 0.5F), (double)(0.0F - 0.25F), 0.0D, (double)f2, (double)f5);
        tessellator.addVertexWithUV((double)(1.0F - 0.5F), (double)(0.0F - 0.25F), 0.0D, (double)f3, (double)f5);
        tessellator.addVertexWithUV((double)(1.0F - 0.5F), (double)(1.0F - 0.25F), 0.0D, (double)f3, (double)f4);
        tessellator.addVertexWithUV((double)(0.0F - 0.5F), (double)(1.0F - 0.25F), 0.0D, (double)f2, (double)f4);
        tessellator.draw();
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        profiler.endSection();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMagikaBubble entity)
    {
        return TEXTURE_LOCATION;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityMagikaBubble)entity);
    }
}
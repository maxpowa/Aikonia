package maxpowa.aikonia.common.entity;

import maxpowa.aikonia.Aikonia;
import maxpowa.aikonia.common.event.MagikaBubbleCollideEvent;
import maxpowa.aikonia.common.packet.MagikaBubbleSoundPacket;
import maxpowa.aikonia.common.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityMagikaBubble extends Entity
{
    /** A constantly increasing value that RenderXPOrb uses to control the colour shifting (Green / yellow) */
    public int xpColor;
    /** The age of the XP orb in ticks. */
    public int xpOrbAge;
    /** This is how much XP this orb has. */
    public int xpValue;
    /** The closest EntityPlayer to this orb. */
    private EntityLiving closestEntity;
    /** Threshold color for tracking players */
    private int xpTargetColor;

    public EntityMagikaBubble(World world, double x, double y, double z, int value)
    {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(x, y, z);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.motionY = (double)((float)(Math.random() * 0.2D) * 2.0F);
        this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.xpValue = value;
    }

    protected boolean canTriggerWalking() { 
    	return false;
    }

    public EntityMagikaBubble(World p_i1586_1_)
    {
        super(p_i1586_1_);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
    }

    protected void entityInit() {}

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        float f1 = 0.5F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(p_70070_1_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746D;

        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
        double d0 = 8.0D;

        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100)
        {
            if (this.closestEntity == null || this.closestEntity.getDistanceSqToEntity(this) > d0 * d0)
            {
                this.closestEntity = Util.getClosestLivingEntityToEntity(this, d0);
            }

            this.xpTargetColor = this.xpColor;
        }

        if (this.closestEntity != null)
        {
            double d1 = (this.closestEntity.posX - this.posX) / d0;
            double d2 = (this.closestEntity.posY + (double)this.closestEntity.getEyeHeight() - this.posY) / d0;
            double d3 = (this.closestEntity.posZ - this.posZ) / d0;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

            if (d5 > 0.0D)
            {
                d5 *= d5;
                this.motionX += d1 / d4 * d5 * 0.1D;
                this.motionY += d2 / d4 * d5 * 0.1D;
                this.motionZ += d3 / d4 * d5 * 0.1D;
            }
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround)
        {
            f = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
        }

        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround)
        {
            this.motionY *= -0.8999999761581421D;
        }

        ++this.xpColor;
        ++this.xpOrbAge;

        if (this.xpOrbAge >= 6000)
        {
            this.setDead();
        }
    }
    
    /**
     * Magika Bubbles shouldn't be affected by anything
     */
    public boolean handleWaterMovement()
    {
        return false;
    }

    /**
     * Magika Bubbles shouldn't be affected by anything
     */
    protected void dealFireDamage(int amountDamage)
    {
        return;
    }

    /**
     * Magika Bubbles shouldn't be affected by anything
     */
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        return false;
    }

    public void writeEntityToNBT(NBTTagCompound cmp)
    {
        cmp.setShort("Age", (short)this.xpOrbAge);
        cmp.setShort("Value", (short)this.xpValue);
    }

    public void readEntityFromNBT(NBTTagCompound cmp)
    {
        this.xpOrbAge = cmp.getShort("Age");
        this.xpValue = cmp.getShort("Value");
    }
    
    @Override
    public void applyEntityCollision(Entity entity) {
        if (!this.worldObj.isRemote)
        {
        	if (!(entity instanceof EntityLivingBase)) 
        		return;
        	
        	EntityLivingBase livingEntity = (EntityLivingBase) entity;
            if (livingEntity != null)
            {
            	if (MinecraftForge.EVENT_BUS.post(new MagikaBubbleCollideEvent(livingEntity, this))) return;
                this.worldObj.playSoundAtEntity(entity, "random.hiss", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
                if (livingEntity instanceof EntityPlayerMP)
                	Aikonia.net.sendTo(new MagikaBubbleSoundPacket("random.hiss"), (EntityPlayerMP)livingEntity);
                this.setDead();
                System.out.println("You killed me billy!");
            }
        }
    }
    
    @Override
    public void setDead() {
    	//System.out.println("I've been set dead.");
    	super.setDead();
    }

    /**
     * Returns the XP value of this XP orb.
     */
    public int getXpValue()
    {
        return this.xpValue;
    }

    /**
     * Returns a number from 1 to 10 based on how much XP this orb is worth. This is used by RenderXPOrb to determine
     * what texture to use.
     */
    @SideOnly(Side.CLIENT)
    public int getTextureByXP()
    {
        return this.xpValue >= 2477 ? 10 : (this.xpValue >= 1237 ? 9 : (this.xpValue >= 617 ? 8 : (this.xpValue >= 307 ? 7 : (this.xpValue >= 149 ? 6 : (this.xpValue >= 73 ? 5 : (this.xpValue >= 37 ? 4 : (this.xpValue >= 17 ? 3 : (this.xpValue >= 7 ? 2 : (this.xpValue >= 3 ? 1 : 0)))))))));
    }

    /**
     * Get a fragment of the maximum experience points value for the supplied value of experience points value.
     */
    public static int getXPSplit(int p_70527_0_)
    {
        return p_70527_0_ >= 2477 ? 2477 : (p_70527_0_ >= 1237 ? 1237 : (p_70527_0_ >= 617 ? 617 : (p_70527_0_ >= 307 ? 307 : (p_70527_0_ >= 149 ? 149 : (p_70527_0_ >= 73 ? 73 : (p_70527_0_ >= 37 ? 37 : (p_70527_0_ >= 17 ? 17 : (p_70527_0_ >= 7 ? 7 : (p_70527_0_ >= 3 ? 3 : 1)))))))));
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }
}
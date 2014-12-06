package maxpowa.aikonia.common.entity;

import java.util.List;

import maxpowa.aikonia.Aikonia;
import maxpowa.aikonia.common.event.MagikaBubbleCollideEvent;
import maxpowa.aikonia.common.packet.MagikaBubbleSoundPacket;
import maxpowa.aikonia.common.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityMagikaBubble extends Entity
{
    /** A constantly increasing value that RenderXPOrb uses to control the colour shifting (Green / yellow) */
    public int bubbleColor;
    /** The age of the XP orb in ticks. */
    public int bubbleAge;
    /** This is how much XP this orb has. */
    public int bubbleSize;
    /** The closest EntityPlayer to this orb. */
    private EntityLivingBase closestEntity;
    /** Threshold color for tracking players */
    private int bubbleTargetColor;

    public EntityMagikaBubble(World world, double x, double y, double z, int value)
    {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(x, y, z);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)((Math.random()-0.5D) * 0.20000000298023224D) * 2.0F);
        this.motionY = (double)((float)((Math.random()-0.5D) * 0.2D) * 2.0F);
        this.motionZ = (double)((float)((Math.random()-0.5D) * 0.20000000298023224D) * 2.0F);
        this.bubbleSize = value;
        this.noClip = true;
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
        return 180;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @SuppressWarnings("unchecked")
	public void onUpdate()
    {
    	this.noClip = true;
        super.onUpdate();
        
        this.motionY += 0.01;

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.02D;

        double d0 = 16.0D;

        if (this.bubbleTargetColor < this.bubbleColor - 20 + this.getEntityId() % 100)
        {
            if (this.closestEntity == null || this.closestEntity.getDistanceSqToEntity(this) > d0 * d0 || this.closestEntity.isDead)
            {
                this.closestEntity = Util.getClosestLivingEntityToEntity(this, d0);
                //System.out.println("Getting close entity");
            }

            this.bubbleTargetColor = this.bubbleColor;
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

        this.motionX *= (double)f;
        this.motionY *= 0.5800000190734863D;
        this.motionZ *= (double)f;

        ++this.bubbleColor;
        ++this.bubbleAge;
        
        AxisAlignedBB box = boundingBox.expand(0.1D, 0.1D, 0.1D);
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, box);
        

        if (list != null && !list.isEmpty()) {
            for (Entity entity : list) {
                if (entity != this.riddenByEntity) {
                    this.applyEntityCollision(entity);
                }
            }
        }

        if (this.bubbleAge >= 6000)
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
        cmp.setShort("Age", (short)this.bubbleAge);
        cmp.setShort("Size", (short)this.bubbleSize);
    }

    public void readEntityFromNBT(NBTTagCompound cmp)
    {
        this.bubbleAge = cmp.getShort("Age");
        this.bubbleSize = cmp.getShort("Size");
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
            	MagikaExtension magika = MagikaExtension.get(livingEntity);
            	magika.channeling_pool += this.bubbleSize;
                this.worldObj.playSoundAtEntity(entity, "random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
                if (livingEntity instanceof EntityPlayerMP)
                	Aikonia.net.sendTo(new MagikaBubbleSoundPacket("random.orb"), (EntityPlayerMP)livingEntity);
                this.setDead();
                //System.out.println("You killed me billy!");
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
        return this.bubbleSize;
    }

    /**
     * Returns a number from 1 to 10 based on how much XP this orb is worth. This is used by RenderXPOrb to determine
     * what texture to use.
     */
    @SideOnly(Side.CLIENT)
    public int getTextureByXP()
    {
        return this.bubbleSize >= 2477 ? 10 : (this.bubbleSize >= 1237 ? 9 : (this.bubbleSize >= 617 ? 8 : (this.bubbleSize >= 307 ? 7 : (this.bubbleSize >= 149 ? 6 : (this.bubbleSize >= 73 ? 5 : (this.bubbleSize >= 37 ? 4 : (this.bubbleSize >= 17 ? 3 : (this.bubbleSize >= 7 ? 2 : (this.bubbleSize >= 3 ? 1 : 0)))))))));
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
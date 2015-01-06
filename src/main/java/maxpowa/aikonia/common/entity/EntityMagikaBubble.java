package maxpowa.aikonia.common.entity;

import java.util.List;

import maxpowa.aikonia.Aikonia;
import maxpowa.aikonia.common.event.MagikaBubbleCollideEvent;
import maxpowa.aikonia.common.packet.MagikaBubbleSoundPacket;
import maxpowa.aikonia.common.util.Util;
import maxpowa.aikonia.common.util.WorldData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityMagikaBubble extends Entity
{
    public static int MAX_BUBBLE_SIZE = 10;
	/** A constantly increasing value that RenderXPOrb uses to control the colour shifting (Green / yellow) */
    public int bubbleColor;
    /** The age of the XP orb in ticks. */
    public int bubbleAge;
    /** This is how much XP this orb has. */
    public int bubbleSize;
    /** The closest EntityPlayer to this orb. */
    private EntityLivingBase closestEntity;
    /** Time the orb was created **/
	private long inceptionTime = System.currentTimeMillis();

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

    public EntityMagikaBubble(World world)
    {
        super(world);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
    }

    public void setTargetEntity(EntityLivingBase target) {
    	this.closestEntity = target;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        return 240;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @SuppressWarnings("unchecked")
	public void onUpdate()
    {
    	this.worldObj.theProfiler.startSection("magika_bubble");

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.01D;

        double range = 16.0D;
        
        this.worldObj.theProfiler.startSection("entity_check");
        
        if (this.bubbleColor % 50 == 0) {
	        if (this.closestEntity == null || this.closestEntity.isDead || this.closestEntity.getDistanceSqToEntity(this) > range * range) {
	            this.closestEntity = Util.getClosestLivingEntityToEntity(this, range);
	        }
        }

        this.worldObj.theProfiler.endStartSection("pathing");
        
        if (this.closestEntity != null) {
            double d1 = (this.closestEntity.posX - this.posX) / range;
            double d2 = (this.closestEntity.posY + (double)this.closestEntity.getEyeHeight()/2 - this.posY) / range;
            double d3 = (this.closestEntity.posZ - this.posZ) / range;
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

        this.worldObj.theProfiler.endStartSection("movement");
        
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        this.motionX *= (double)f;
        this.motionY *= 0.5800000190734863D;
        this.motionZ *= (double)f;

        ++this.bubbleColor;
        ++this.bubbleAge;
        
        this.worldObj.theProfiler.endStartSection("collision");
        
        if (!worldObj.isRemote && this.bubbleColor % 50 == 0) {
	        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.25D, 0.25D, 0.25D));
	        if (list != null && !list.isEmpty()) {
	            for (Entity entity : list) {
	                if (entity != this.riddenByEntity) {
	                    this.applyEntityCollision(entity);
	                }
	            }
	        }
        }

        this.worldObj.theProfiler.endStartSection("age");
        
        if (this.bubbleAge >= 6000 || this.posY < -16.0D) {
            this.setDead(true);
        }
        
        this.worldObj.theProfiler.endSection();
    	this.worldObj.theProfiler.endSection();
    }
    
    @Override
    public void moveEntity(double x, double y, double z) {
        this.boundingBox.offset(x, y, z);
        this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
        this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
        this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
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
        cmp.setLong("Inception_Time", (long)this.inceptionTime);
    }

    public void readEntityFromNBT(NBTTagCompound cmp)
    {
        this.bubbleAge = cmp.getShort("Age");
        this.bubbleSize = cmp.getShort("Size");
        this.inceptionTime = cmp.getLong("Inception_Time");
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
            }
        }
    }
    
    public void setDead(boolean damageVeil) {
    	if (!damageVeil) this.setDead();
    	
    	if (!this.worldObj.isRemote) {
			WorldData data = WorldData.forWorld(this.worldObj);
			NBTTagCompound tag = data.getData();
			Chunk currentChunk = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
		    ChunkCoordIntPair chunkLoc = currentChunk.getChunkCoordIntPair();
		    
		    String tagKey = "magika_pools_DIM"+this.dimension;
			if (!tag.hasKey(tagKey)) tag.setTag(tagKey, new NBTTagCompound());
				
			NBTTagCompound pools = tag.getCompoundTag(tagKey);
			String coords = chunkLoc.chunkXPos+","+chunkLoc.chunkZPos;
			
		    double currentPool = 0D;
			if (pools.hasKey(coords)) currentPool = pools.getDouble(coords);
			
			currentPool += this.bubbleSize;
			pools.setDouble(coords, currentPool);
			
			//System.out.println(String.format("\nCoords: %s\nPool: %s", coords, currentPool));
			
			data.setData(tag);
			data.markDirty();
			this.setDead();
		}
    }

    public int getMagikaValue()
    {
        return this.bubbleSize;
    }

    @SideOnly(Side.CLIENT)
    public int getTexture()
    {
        return Math.round((this.bubbleSize / EntityMagikaBubble.MAX_BUBBLE_SIZE) * 10);
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

	public long getInceptionTime() {
		return this.inceptionTime;
	}

	@Override
	protected void entityInit() {
		this.inceptionTime = System.currentTimeMillis();
	}
}
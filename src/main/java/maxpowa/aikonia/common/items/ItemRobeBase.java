package maxpowa.aikonia.common.items;

import org.lwjgl.opengl.GL11;

import maxpowa.aikonia.Aikonia;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemRobeBase extends ItemArmor
{
	public String textureName;

	public ItemRobeBase(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
	    super(material, 0, type);
	    this.textureName = textureName;
	    this.setUnlocalizedName(unlocalizedName);
	    this.setTextureName(Aikonia.MODID + ":" + unlocalizedName);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
	    //return Aikonia.MODID + ":textures/models/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
		return "textures/entity/villager/villager.png";
	}
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int slotID) {
		//return new ModelRobe(1.0F);
		ModelRobe modelRobe = new ModelRobe(1.0F);
		modelRobe.villagerHead.isHidden = true;
        return modelRobe;
	}
	
	public class ModelRobe extends ModelBiped
	{

	    /** The head box of the VillagerModel */
	    public ModelRenderer villagerHead;
	    /** The body of the VillagerModel */
	    public ModelRenderer villagerBody;
	    /** The arms of the VillagerModel */
	    public ModelRenderer villagerArms;

	    public ModelRobe(float scale)
	    {
	        this(scale, 0.75F, 64, 64);
	    }

	    public ModelRobe(float scale, float center, int texWidth, int texHeight)
	    {
	        this.villagerHead = (new ModelRenderer(this)).setTextureSize(texWidth, texHeight);
	        this.villagerHead.setRotationPoint(0.0F, 0.0F + center, 0.0F);
	        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scale);
	        this.villagerBody = (new ModelRenderer(this)).setTextureSize(texWidth, texHeight);
	        this.villagerBody.setRotationPoint(0.0F, 0.0F + center, 0.0F);
	        this.villagerBody.setTextureOffset(16, 20).addBox(-3.0F, 0.0F, -1.5F, 6, 12, 3, scale);
	        this.villagerBody.setTextureOffset(0, 38).addBox(-3.0F, 0.0F, -1.5F, 6, 18, 3, scale + 0.5F);
	        this.villagerArms = (new ModelRenderer(this)).setTextureSize(texWidth, texHeight);
	        this.villagerArms.setRotationPoint(0.0F, 0.0F + center + 2.0F, 0.0F);
	        //this.villagerArms.setTextureOffset(44, 22).addBox(5F, -2.0F, -2.0F, 3, 8, 3, scale);
	        this.bipedRightArm = new ModelRenderer(this).setTextureSize(texWidth, texHeight);
	        this.bipedRightArm.setTextureOffset(44, 22).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
	        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + center, 0.0F);
	        this.bipedLeftArm = new ModelRenderer(this).setTextureSize(texWidth, texHeight);
	        this.bipedLeftArm.mirror = true;
	        this.bipedLeftArm.setTextureOffset(44, 22).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
	        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + center, 0.0F);
	        //this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, scale);
	    }

	    /**
	     * Sets the models various rotation angles then renders the model.
	     */
	    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	    {
	        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
	        this.villagerHead.render(p_78088_7_);
	        this.villagerBody.render(p_78088_7_);
	        this.villagerArms.render(p_78088_7_);
	    }

	    /**
	     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	     * "far" arms and legs can swing at most.
	     */
	    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	    {
	        this.villagerHead.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
	        this.villagerHead.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
	        
	        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
	        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
	        this.bipedRightArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 2.0F * p_78087_2_ * 0.5F;
	        this.bipedLeftArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
	        this.bipedRightArm.rotateAngleZ = 0.0F;
	        this.bipedLeftArm.rotateAngleZ = 0.0F;
	        this.bipedRightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
	        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
	        this.bipedRightLeg.rotateAngleY = 0.0F;
	        this.bipedLeftLeg.rotateAngleY = 0.0F;

	        if (this.isRiding)
	        {
	            this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
	            this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
	            this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
	            this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
	            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
	            this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
	        }

	        if (this.heldItemLeft != 0)
	        {
	            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
	        }

	        if (this.heldItemRight != 0)
	        {
	            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
	        }

	        this.bipedRightArm.rotateAngleY = 0.0F;
	        this.bipedLeftArm.rotateAngleY = 0.0F;
	        float f6;
	        float f7;

	        if (this.onGround > -9990.0F)
	        {
	            f6 = this.onGround;
	            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
	            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
	            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
	            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
	            f6 = 1.0F - this.onGround;
	            f6 *= f6;
	            f6 *= f6;
	            f6 = 1.0F - f6;
	            f7 = MathHelper.sin(f6 * (float)Math.PI);
	            float f8 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
	            this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
	            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
	            this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
	        }

	        if (this.isSneak)
	        {
	            this.bipedBody.rotateAngleX = 0.5F;
	            this.bipedRightArm.rotateAngleX += 0.4F;
	            this.bipedLeftArm.rotateAngleX += 0.4F;
	            this.bipedRightLeg.rotationPointZ = 4.0F;
	            this.bipedLeftLeg.rotationPointZ = 4.0F;
	            this.bipedRightLeg.rotationPointY = 9.0F;
	            this.bipedLeftLeg.rotationPointY = 9.0F;
	            this.bipedHead.rotationPointY = 1.0F;
	            this.bipedHeadwear.rotationPointY = 1.0F;
	        }
	        else
	        {
	            this.bipedBody.rotateAngleX = 0.0F;
	            this.bipedRightLeg.rotationPointZ = 0.1F;
	            this.bipedLeftLeg.rotationPointZ = 0.1F;
	            this.bipedRightLeg.rotationPointY = 12.0F;
	            this.bipedLeftLeg.rotationPointY = 12.0F;
	            this.bipedHead.rotationPointY = 0.0F;
	            this.bipedHeadwear.rotationPointY = 0.0F;
	        }

	        this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
	        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
	        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
	        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;

	        if (this.aimedBow)
	        {
	            f6 = 0.0F;
	            f7 = 0.0F;
	            this.bipedRightArm.rotateAngleZ = 0.0F;
	            this.bipedLeftArm.rotateAngleZ = 0.0F;
	            this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + this.bipedHead.rotateAngleY;
	            this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
	            this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
	            this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
	            this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
	            this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
	            this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
	            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
	            this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
	            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
	        }
	    }
		
	    
	    
	}
}

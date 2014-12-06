package maxpowa.aikonia.common.items;

import maxpowa.aikonia.Aikonia;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

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
	    return Aikonia.MODID + ":textures/models/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
		//return "textures/entity/villager/villager.png";
	}
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int slotID) {
		//return new ModelRobe(1.0F);
		ModelRobe modelRobe = new ModelRobe(1.0F);
		modelRobe.bipedBody.isHidden = true;
		modelRobe.bipedCloak.isHidden = true;
		modelRobe.bipedEars.isHidden = true;
		modelRobe.bipedHead.isHidden = true;
		modelRobe.bipedHeadwear.isHidden = true;
		modelRobe.bipedLeftArm.isHidden = true;
		modelRobe.bipedLeftLeg.isHidden = true;
		modelRobe.bipedRightArm.isHidden = true;
		modelRobe.bipedRightLeg.isHidden = true;
		switch (slotID) {
		case 0:
			modelRobe.bipedHead.isHidden = false;
			break;
		case 1:
			modelRobe.bipedBody.isHidden = false;
			modelRobe.bipedLeftArm.isHidden = false;
			modelRobe.bipedRightArm.isHidden = false;
			break;
		case 3:
			modelRobe.bipedLeftLeg.isHidden = false;
			modelRobe.bipedRightLeg.isHidden = false;
			break;
		default:
			break;
		}
			
        return modelRobe;
	}
	
	public class ModelRobe extends ModelBiped
	{
	    public ModelRobe(float scale) {
	        super(scale);
	    }

	    public ModelRobe(float scale, float center, int texWidth, int texHeight) {
	        super(scale, center, texWidth, texHeight);
	    }
	    
	    public void setRotationAngles(float ticks, float maxArmSwing, float armSwingSideRad, float headPitch, float headYaw, float p_78087_6_, Entity p_78087_7_) {
	    	
	    	super.setRotationAngles(ticks, maxArmSwing, armSwingSideRad, headPitch, headYaw, p_78087_6_, p_78087_7_);
	    }
	}
}

package maxpowa.aikonia.common.entity;

import maxpowa.aikonia.Aikonia;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class MagikaExtension implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = Aikonia.MODID+"_Magika_Extension";
	public float casting_pool;
	public float channeling_pool;
	
	public MagikaExtension(EntityLivingBase entity) {}

	public static final void register(EntityLivingBase entity) {
		entity.registerExtendedProperties(MagikaExtension.EXT_PROP_NAME, new MagikaExtension(entity));
	}

	public static final MagikaExtension get(EntityLivingBase entity) {
		return (MagikaExtension) entity.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound aikonia_data = new NBTTagCompound();
		aikonia_data.setFloat("Casting_Pool", this.casting_pool);
		aikonia_data.setFloat("Channeling_Pool", this.channeling_pool);
		compound.setTag("Aikonia", aikonia_data);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound aikonia_data = compound.getCompoundTag("Aikonia");
		this.casting_pool = aikonia_data.getFloat("Casting_Pool");
		this.channeling_pool = aikonia_data.getFloat("Channeling_Pool");
		//System.out.println("[LIVING BASE] Casting Pool from NBT: " + this.casting_pool);
		//System.out.println("[LIVING BASE] Channelling Pool from NBT: " + this.channeling_pool);
	}
	
	@Override
	public void init(Entity entity, World world)
	{
		// Gives a random amount of gold between 0 and 15
		this.casting_pool = world.rand.nextInt(16);
		//System.out.println("[LIVING BASE] Gold: " + this.casting_pool);
	}
}
package maxpowa.aikonia.common.util;

import maxpowa.aikonia.Aikonia;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class WorldData extends WorldSavedData {

   final static String key = Aikonia.MODID;
   
   public static WorldData forWorld(World world) {
      WorldData result = (WorldData) world.perWorldStorage.loadData(WorldData.class, key);
      if (result == null)
          world.perWorldStorage.setData(key, new WorldData(key));
      return result;
   }
   
   private NBTTagCompound data = new NBTTagCompound();

   public WorldData(String tagName) {
       super(tagName);
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
  	 data = compound.getCompoundTag(key);
   }

   @Override
   public void writeToNBT(NBTTagCompound compound) {
       compound.setTag(key, data);
   }

   public NBTTagCompound getData() {
       return data;
   }
}

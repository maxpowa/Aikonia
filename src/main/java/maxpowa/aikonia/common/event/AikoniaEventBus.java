package maxpowa.aikonia.common.event;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;
import maxpowa.aikonia.common.util.WorldData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;

public class AikoniaEventBus {

    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent event) {
    	if (event.side == Side.SERVER && event.phase == Phase.START) {
    		event.player.getExtendedProperties("");
    		WorldData data = WorldData.forWorld(event.player.worldObj);
    		NBTTagCompound tag = data.getData();
    		Chunk currentChunk = event.player.worldObj.getChunkFromBlockCoords(event.player.playerLocation.posX, event.player.playerLocation.posZ);
    	    ChunkCoordIntPair chunkLoc = currentChunk.getChunkCoordIntPair();
    	    long currentPool = 0L;
    		if (tag.hasKey("akonia_magika_pools")) {
    			NBTTagCompound pools = tag.getCompoundTag("akonia_magika_pools");
    			String coords = chunkLoc.chunkXPos+","+chunkLoc.chunkZPos;
    			if (pools.hasKey(coords)) {
    				currentPool = pools.getLong(coords);
    			}
    		}
    		data.markDirty();
    	}
    }
	
}

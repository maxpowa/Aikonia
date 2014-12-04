package maxpowa.aikonia.common.event;

import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;

public class AikoniaEventBus {
	
	long timeout = 0L;

    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent event) {
    	if (event.side == Side.SERVER && event.phase == Phase.END) {
    		if (timeout < System.nanoTime()) {
    			//System.out.println("Spawning Magika Bubble");
                event.player.worldObj.spawnEntityInWorld(new EntityMagikaBubble(event.player.worldObj, event.player.posX, event.player.posY+5, event.player.posZ, 1237));
    			timeout = System.nanoTime() + 500000000L;
    		}
    	}
//    	if (event.side == Side.SERVER && event.phase == Phase.START) {
//    		event.player.getExtendedProperties("");
//    		WorldData data = WorldData.forWorld(event.player.worldObj);
//    		NBTTagCompound tag = data.getData();
//    		Chunk currentChunk = event.player.worldObj.getChunkFromBlockCoords(event.player.playerLocation.posX, event.player.playerLocation.posZ);
//    	    ChunkCoordIntPair chunkLoc = currentChunk.getChunkCoordIntPair();
//    	    long currentPool = 0L;
//    		if (tag.hasKey("akonia_magika_pools")) {
//    			NBTTagCompound pools = tag.getCompoundTag("akonia_magika_pools");
//    			String coords = chunkLoc.chunkXPos+","+chunkLoc.chunkZPos;
//    			if (pools.hasKey(coords)) {
//    				currentPool = pools.getLong(coords);
//    			}
//    		}
//    		data.markDirty();
//    	}
    }
	
}

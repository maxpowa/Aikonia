package maxpowa.aikonia.common.event;

import maxpowa.aikonia.common.entity.MagikaExtension;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class AikoniaEventBus {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

    }
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
    	if (event.entity instanceof EntityLivingBase)
    		MagikaExtension.register((EntityLivingBase) event.entity);
    }
    
    @SubscribeEvent
	public void onEntityRightClicked(EntityInteractEvent event) {
    	if (event.entity instanceof EntityLivingBase) {
    		MagikaExtension me = MagikaExtension.get((EntityLivingBase) event.entityPlayer);
    		MagikaExtension mob = MagikaExtension.get((EntityLivingBase) event.target);
    		System.out.println(String.format("\nMob Casting Pool: %s\nMob Channeling Pool: %s\nPlayer Casting Pool: %s\nPlayer Channeling Pool: %s", mob.casting_pool, mob.channeling_pool, me.casting_pool, me.channeling_pool));
    	}
	}
	
}

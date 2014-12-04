package maxpowa.aikonia.common;

import maxpowa.aikonia.Aikonia;
import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import maxpowa.aikonia.common.event.AikoniaEventBus;
import maxpowa.aikonia.common.items.AikoniaItems;
import maxpowa.aikonia.common.packet.MagikaBubbleSoundPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		//Network
		Aikonia.net = NetworkRegistry.INSTANCE.newSimpleChannel(Aikonia.MODID);
		Aikonia.net.registerMessage(MagikaBubbleSoundPacket.Handler.class, MagikaBubbleSoundPacket.class, 0, Side.CLIENT);
		
		//Events
		FMLCommonHandler.instance().bus().register(new AikoniaEventBus());
		
		//Items
		AikoniaItems.init();
		
		//Entities
		EntityRegistry.registerModEntity(EntityMagikaBubble.class, Aikonia.MODID+"magikaBubble", EntityRegistry.findGlobalUniqueEntityId(), this, 64, 10, true);
		
		//Renders for Entites
		this.registerRenderers();
	}
	
	public void registerRenderers() {
		// NO-OP
	}

	public void playSound(String snd) {
		// NO-OP
	}

}

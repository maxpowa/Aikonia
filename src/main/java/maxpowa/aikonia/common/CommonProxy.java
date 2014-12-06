package maxpowa.aikonia.common;

import net.minecraftforge.common.MinecraftForge;
import maxpowa.aikonia.Aikonia;
import maxpowa.aikonia.common.blocks.AikoniaBlocks;
import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import maxpowa.aikonia.common.event.AikoniaEventBus;
import maxpowa.aikonia.common.items.AikoniaItems;
import maxpowa.aikonia.common.packet.MagikaBubbleSoundPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
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
		AikoniaEventBus eventBus = new AikoniaEventBus();
		FMLCommonHandler.instance().bus().register(eventBus);
		MinecraftForge.EVENT_BUS.register(eventBus);
		
		//Items
		AikoniaItems.init();
		//Blocks
		AikoniaBlocks.init();
		
		//Entities
		EntityRegistry.registerModEntity(EntityMagikaBubble.class, "magikaBubble", EntityRegistry.findGlobalUniqueEntityId(), Aikonia.instance, 64, 10, true);
		
		//Renders for Entites
		this.registerRenderers();
	}

	public void load(FMLInitializationEvent event) {
		
	}
	
	public void registerRenderers() {}

	public void playSound(String snd) {}

	public int registerArmor(String armor) {
		return 0;
	}

}

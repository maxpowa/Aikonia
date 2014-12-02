package maxpowa.akonia;

import maxpowa.akonia.common.CommonProxy;
import maxpowa.akonia.common.event.AkoniaEventBus;
import maxpowa.akonia.common.items.AkoniaItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Akonia.MODID, name = Akonia.MOD_NAME, version = Akonia.VERSION)
public class Akonia
{
	public static final String MODID = "mysteriousloot";
	public static final String MOD_NAME = "Mysterious Loot";
	public static final String VERSION = "${version}";

	@Instance(value = Akonia.MODID)
	public static Akonia instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="maxpowa.mysteriousloot.client.ClientProxy", serverSide="maxpowa.mysteriousloot.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new AkoniaEventBus());
		AkoniaItems.init();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}
}

package maxpowa.aikonia;

import maxpowa.aikonia.common.CommonProxy;
import maxpowa.aikonia.common.event.AikoniaEventBus;
import maxpowa.aikonia.common.items.AikoniaItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * https://docs.google.com/document/d/1JYxnktvTJ-OIi00ZcdHThALEHv_EJTVlvu8FguYZAf0/edit
 * 
 * @author Max
 *
 */

@Mod(modid = Aikonia.MODID, name = Aikonia.MOD_NAME, version = Aikonia.VERSION)
public class Aikonia
{
	public static final String MODID = "aikonia";
	public static final String MOD_NAME = "Aikonia";
	public static final String VERSION = "${version}";

	@Instance(value = Aikonia.MODID)
	public static Aikonia instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="maxpowa.aikonia.client.ClientProxy", serverSide="maxpowa.aikonia.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new AikoniaEventBus());
		AikoniaItems.init();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}
}

package maxpowa.aikonia.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import maxpowa.aikonia.client.render.RenderMagikaBubble;
import maxpowa.aikonia.common.CommonProxy;
import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		System.out.println("Registering client renderers!");
		RenderingRegistry.registerEntityRenderingHandler(EntityMagikaBubble.class, new RenderMagikaBubble());
		//MinecraftForgeClient.registerItemRenderer(MysteriousItems.uselessBook, new CustomSpellbookRenderer());
	}
	
	@Override
	public void playSound(String soundId) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.thePlayer != null && mc.theWorld != null)
			mc.thePlayer.playSound(soundId, 1.0F, 1.0F);
	}
	
}

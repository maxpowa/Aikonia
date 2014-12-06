package maxpowa.aikonia.common.blocks;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class AikoniaBlocks {

	public static BlockMagikaEmitter magikaEmitter = null;
	
	public static void init() {
		magikaEmitter = new BlockMagikaEmitter(Material.piston);
		
		GameRegistry.registerBlock(magikaEmitter, "magikaEmitter");
	}
	
}

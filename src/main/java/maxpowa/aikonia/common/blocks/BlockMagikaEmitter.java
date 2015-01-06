package maxpowa.aikonia.common.blocks;

import java.util.Random;

import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import maxpowa.aikonia.common.items.AikoniaItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockMagikaEmitter extends Block
{
	long timeout = 0L;
	
	public BlockMagikaEmitter(Material material) {
		super(material);
		setBlockName("magikaEmitter");
		setBlockTextureName("minecraft:glass");
		setCreativeTab(AikoniaItems.tabAikonia);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack from) {
		world.scheduleBlockUpdate(x, y, z, this, 1);
		world.markBlockForUpdate(x, y, z);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, 1);
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		world.theProfiler.startSection("aikonia");
    	if (!world.isRemote) {
    		if (timeout < System.nanoTime()) {
    			//System.out.println("Spawning Magika Bubble");
                world.spawnEntityInWorld(new EntityMagikaBubble(world, x+0.5D, y+0.5D, z+0.5D, MathHelper.floor_double(Math.random()*EntityMagikaBubble.MAX_BUBBLE_SIZE)));
                world.spawnEntityInWorld(new EntityMagikaBubble(world, x+0.5D, y+0.5D, z+0.5D, MathHelper.floor_double(Math.random()*EntityMagikaBubble.MAX_BUBBLE_SIZE)));
                world.spawnEntityInWorld(new EntityMagikaBubble(world, x+0.5D, y+0.5D, z+0.5D, MathHelper.floor_double(Math.random()*EntityMagikaBubble.MAX_BUBBLE_SIZE)));
    			//timeout = System.nanoTime() + 10000L;
    		}
    	}
    	world.scheduleBlockUpdate(x, y, z, this, 1);
		world.markBlockForUpdate(x, y, z);
		world.theProfiler.endSection();
	}
	
}
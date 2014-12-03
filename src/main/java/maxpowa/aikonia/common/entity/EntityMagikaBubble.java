package maxpowa.aikonia.common.entity;

import maxpowa.aikonia.common.event.PlayerPickupMagikaBubbleEvent;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

public class EntityMagikaBubble extends EntityXPOrb {

	public EntityMagikaBubble(World world, double x, double y, double z, int value) {
		super(world, x, y, z, value);
	}

	@Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        if (!this.worldObj.isRemote)
        {
            if (player.xpCooldown == 0)
            {
                if (MinecraftForge.EVENT_BUS.post(new PlayerPickupMagikaBubbleEvent(player, this))) return;
                player.xpCooldown = 2;
                this.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
                player.onItemPickup(this, 1);
                player.addExperience(this.xpValue);
                this.setDead();
            }
        }
    }
	
}

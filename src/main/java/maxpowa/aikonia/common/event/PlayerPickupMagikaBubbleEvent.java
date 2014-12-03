package maxpowa.aikonia.common.event;

import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

/**
 * This event is called when a player collides with a EntityXPOrb on the ground.
 * The event can be canceled, and no further processing will be done.  
 */
@Cancelable
public class PlayerPickupMagikaBubbleEvent extends PlayerEvent
{
    public final EntityMagikaBubble orb;

    public PlayerPickupMagikaBubbleEvent(EntityPlayer player, EntityMagikaBubble orb)
    {
        super(player);
        this.orb = orb;
    }
}
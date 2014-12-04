package maxpowa.aikonia.common.event;

import maxpowa.aikonia.common.entity.EntityMagikaBubble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

/**
 * This event is called when a player collides with a EntityXPOrb on the ground.
 * The event can be canceled, and no further processing will be done.  
 */
@Cancelable
public class MagikaBubbleCollideEvent extends LivingEvent
{
    public final EntityMagikaBubble orb;

    public MagikaBubbleCollideEvent(EntityLivingBase entity, EntityMagikaBubble orb)
    {
        super(entity);
        this.orb = orb;
    }
}
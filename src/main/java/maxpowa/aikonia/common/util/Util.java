package maxpowa.aikonia.common.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class Util {

    @SuppressWarnings("unchecked")
	public static EntityLiving getClosestLivingEntityToEntity(Entity ent, double range) {
        double closestDistance = -1.0D;
        EntityLiving entity = null;
        
        for (Entity e : (List<Entity>)ent.worldObj.getEntitiesWithinAABBExcludingEntity(ent, ent.boundingBox.expand(range, range, range))) {
        	if (e instanceof EntityLiving) {
	            double currentDist = e.getDistanceSq(ent.posX, ent.posY, ent.posZ);
	
	            if (closestDistance == -1.0D || currentDist < closestDistance) {
	                closestDistance = currentDist;
	                entity = (EntityLiving) e;
	            }
        	}
        }

        return entity;
    }
	
}

package maxpowa.aikonia.common.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Util {

    @SuppressWarnings("unchecked")
	public static EntityLivingBase getClosestLivingEntityToEntity(Entity ent, double range) {
        double closestDistance = -1.0D;
        EntityLivingBase entity = null;
        
        List<Entity> nearbyEntities = (List<Entity>)ent.worldObj.getEntitiesWithinAABBExcludingEntity(ent, ent.boundingBox.expand(range, range, range));
        nearbyEntities.addAll(ent.worldObj.playerEntities);
        for (Entity e : nearbyEntities) {
        	if (e instanceof EntityLivingBase) {
	            double currentDist = e.getDistanceSq(ent.posX, ent.posY, ent.posZ);
	
	            if (closestDistance == -1.0D || currentDist < closestDistance) {
	                closestDistance = currentDist;
	                entity = (EntityLivingBase) e;
	            }
        	}
        }

        return entity;
    }
	
}

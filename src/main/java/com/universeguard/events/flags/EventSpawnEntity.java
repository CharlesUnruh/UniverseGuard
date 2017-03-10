package com.universeguard.events.flags;

import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventSpawnEntity {
	
	@Listener
	public void onEntitySpawn(SpawnEntityEvent event) {
		Region r = RegionUtils.load(event.getEntities().get(0).getLocation());
			if (!(event.getEntities().get(0) instanceof Monster)) {
				if (r != null) {
					event.setCancelled(!r.getFlag("animals"));
				} else {
					event.setCancelled(!RegionUtils.getGlobalFlag("animals"));
				}
			} else {
				if (r != null) {
					event.setCancelled(!r.getFlag("mobs"));
				} else {
					event.setCancelled(!RegionUtils.getGlobalFlag("mobs"));
				}
			}
	}
}

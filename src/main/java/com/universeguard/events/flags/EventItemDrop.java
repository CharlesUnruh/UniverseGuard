package com.universeguard.events.flags;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventItemDrop {

	@Listener
	public void onItemDrop(SpawnEntityEvent event, @First EntitySpawnCause spawnCause) {
		Region r = RegionUtils.load(spawnCause.getEntity().getLocation());
		if (spawnCause.getEntity() instanceof Player) {
			Player p = (Player) spawnCause.getEntity();
			if (r != null) {
				if (!RegionUtils.hasPermission(p, r))
					event.setCancelled(!r.getFlag("itemdrop"));
			} else {
				if (!RegionUtils.hasGlobalPermission(p))
					event.setCancelled(!RegionUtils.getGlobalFlag("itemdrop"));
			}
		}

	}

}

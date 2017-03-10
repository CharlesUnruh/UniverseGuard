package com.universeguard.events.flags;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.complex.EnderDragon;
import org.spongepowered.api.entity.living.complex.EnderDragonPart;
import org.spongepowered.api.entity.living.monster.Enderman;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventBlockBreak {

	@Listener
	public void onBlockBreakByEntity(ChangeBlockEvent.Break event) {
		if (event.getCause().root() instanceof Enderman) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("endermangrief"));
			} else {
				event.setCancelled(!RegionUtils.getGlobalFlag("endermangrief"));
			}
		} else if (event.getCause().root() instanceof EnderDragon || event.getCause().root() instanceof EnderDragonPart) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("enderdragonblockdamage"));
			} else {
				event.setCancelled(!RegionUtils.getGlobalFlag("enderdragonblockdamage"));
			}
		}
		else if (!(event.getCause().root() instanceof Player)) {
			Region r = RegionUtils.load(event.getTransactions().get(0).getOriginal().getLocation().get());
			if (r != null) {
				event.setCancelled(!r.getFlag("build"));
			} else {
				event.setCancelled(!RegionUtils.getGlobalFlag("build"));
			}
		}

	}

	@Listener
	public void onBlockBreak(ChangeBlockEvent.Break event, @Root Player player) {
		Region r = RegionUtils.load(player.getLocation());
		if (r != null) {
			if (!RegionUtils.hasPermission(player, r))
				event.setCancelled(!r.getFlag("build"));
		} else {
			if (!RegionUtils.hasGlobalPermission(player))
				event.setCancelled(!RegionUtils.getGlobalFlag("build"));
		}
	}

	@Listener
	public void onInteract(InteractEntityEvent.Primary event, @Root Player player) {
		Region r = RegionUtils.load(event.getTargetEntity().getLocation());
		if (event.getTargetEntity().getType() == EntityTypes.BOAT
				|| event.getTargetEntity().getType() == EntityTypes.RIDEABLE_MINECART) {
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("vehicledestroy"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player))
					event.setCancelled(!RegionUtils.getGlobalFlag("vehicledestroy"));
			}
		} else {
			if (r != null) {
				if (!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("build"));
			} else {
				if (!RegionUtils.hasGlobalPermission(player))
					event.setCancelled(!RegionUtils.getGlobalFlag("build"));
			}
		}

	}
}

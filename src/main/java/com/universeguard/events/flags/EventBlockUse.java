package com.universeguard.events.flags;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventBlockUse {
	
	@Listener
	public void onChestUse(InteractBlockEvent.Secondary event, @First Player player) {
		BlockSnapshot block = event.getTargetBlock();
		Region r;
		if (block.getState().getType().equals(BlockTypes.CHEST) || block.getState().getType().equals(BlockTypes.TRAPPED_CHEST)) {
			r = RegionUtils.load(block.getLocation().get());
			if(r != null) {
				if(!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("chests"));
			} else {
				if(!RegionUtils.hasGlobalPermission(player))
					event.setCancelled(!RegionUtils.getGlobalFlag("chests"));
			}
		} else if (block.getState().getType().equals(BlockTypes.ENDER_CHEST)) {
			r = RegionUtils.load(block.getLocation().get());
			if(r != null) {
				if(!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("enderchests"));
			} else {
				if(!RegionUtils.hasGlobalPermission(player))
					event.setCancelled(!RegionUtils.getGlobalFlag("enderchests"));
			}
		} else if (!block.getState().getType().equals(BlockTypes.AIR) && !block.getState().getType().equals(BlockTypes.CRAFTING_TABLE)
				&& !block.getState().getType().equals(BlockTypes.ANVIL) && !block.getState().getType().equals(BlockTypes.ENCHANTING_TABLE)) {
			r = RegionUtils.load(block.getLocation().get());
			if(r != null) {
				if(!RegionUtils.hasPermission(player, r))
					event.setCancelled(!r.getFlag("use"));
			} else {
				if(!RegionUtils.hasGlobalPermission(player))
					event.setCancelled(!RegionUtils.getGlobalFlag("use"));
			}
			
		}
		
	}

}

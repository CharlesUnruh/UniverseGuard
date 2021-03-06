package com.universeguard.events.flags;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventDecay {

	@Listener
	public void onDecay(ChangeBlockEvent.Decay e) {
		Region r = RegionUtils.load(e.getTransactions().get(0).getOriginal().getLocation().get());
		if(r != null) {
			e.setCancelled(!r.getFlag("leafdecay"));
		}
		else
			e.setCancelled(!RegionUtils.getGlobalFlag("leafdecay"));
	}
}

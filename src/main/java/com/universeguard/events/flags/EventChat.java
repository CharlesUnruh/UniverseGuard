package com.universeguard.events.flags;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventChat {

	@Listener
	public void onChat(MessageChannelEvent.Chat e, @First Player p) {
		Region r = RegionUtils.load(p.getLocation());
		if(r != null) {
			if(!RegionUtils.hasPermission(p, r))
				e.setCancelled(!r.getFlag("sendchat"));
		} else {
			if(!RegionUtils.hasGlobalPermission(p))
				e.setCancelled(!RegionUtils.getGlobalFlag("sendchat"));
		}
	}
}

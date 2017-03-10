package com.universeguard.events.flags;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;

import com.universeguard.region.Region;
import com.universeguard.utils.RegionUtils;

public class EventPvp {

	@Listener
	public void onPvp(InteractEntityEvent.Primary event, @Root Player player) {
		if (event.getTargetEntity() instanceof Player) {
			Player p = (Player) event.getTargetEntity();
			Region r1 = RegionUtils.load(player.getLocation());
			Region r2 = RegionUtils.load(p.getLocation());
			if (r1 == null && r2 == null)
				event.setCancelled(!RegionUtils.getGlobalFlag("pvp"));
			if (r1 != null && r2 == null) {
				event.setCancelled(!r1.getFlag("pvp"));
			} else if (r1 == null && r2 != null)
				event.setCancelled(!r2.getFlag("pvp"));
			else if (r1 != null && r2 != null)
				event.setCancelled(!r1.getFlag("pvp") || !r2.getFlag("pvp"));
		}
	}

	@Listener
	public void onDamageByEntity(DamageEntityEvent event, @Root DamageSource src, @First EntityDamageSource entity) {
		if (event.getTargetEntity() instanceof Player) {
			Entity e = entity.getSource();
			Player p = (Player) event.getTargetEntity();
			Region r = RegionUtils.load(p.getLocation());
			if (src.getType() == DamageTypes.FIRE || src.getType() == DamageTypes.MAGMA) {
				if (r == null)
					event.setCancelled(!RegionUtils.getGlobalFlag("pvp"));
				else
					event.setCancelled(!r.getFlag("pvp"));
			} else if (src.getType() == DamageTypes.EXPLOSIVE) {
				if (e.getType() == EntityTypes.DRAGON_FIREBALL || e.getType() == EntityTypes.ENDER_CRYSTAL
						|| e.getType() == EntityTypes.SMALL_FIREBALL || e.getType() == EntityTypes.FIREBALL) {
					if (r == null)
						event.setCancelled(!RegionUtils.getGlobalFlag("otherexplosions"));
					else
						event.setCancelled(!r.getFlag("otherexplosions"));
				} else if (e.getType() == EntityTypes.TNT_MINECART || e.getType() == EntityTypes.PRIMED_TNT) {
					if (r == null)
						event.setCancelled(!RegionUtils.getGlobalFlag("tnt"));
					else
						event.setCancelled(!r.getFlag("tnt"));
				} else {
					if (r == null)
						event.setCancelled(!RegionUtils.getGlobalFlag("mobdamage"));
					else
						event.setCancelled(!r.getFlag("mobdamage"));
				}

			} else if (src.getType() == DamageTypes.PROJECTILE || src.getType() == DamageTypes.ATTACK) {
				if (e.getType() == EntityTypes.SPECTRAL_ARROW || e.getType() == EntityTypes.TIPPED_ARROW) {
					Arrow projectile = (Arrow) e;
					if (projectile != null) {
						if (projectile.getShooter() instanceof Monster) {
							if (r == null)
								event.setCancelled(!RegionUtils.getGlobalFlag("mobdamage"));
							else
								event.setCancelled(!r.getFlag("mobdamage"));
						} else {
							if (r == null)
								event.setCancelled(!RegionUtils.getGlobalFlag("pvp"));
							else
								event.setCancelled(!r.getFlag("pvp"));
						}

					}
				}

			} 
			
			else {
				if (r == null)
					event.setCancelled(!RegionUtils.getGlobalFlag("mobdamage"));
				else
					event.setCancelled(!r.getFlag("mobdamage"));
			}

		}
	}
	
	@Listener
	public void onDamage(DamageEntityEvent event, @Root DamageSource src) {
		if (event.getTargetEntity() instanceof Player) {
			Player p = (Player) event.getTargetEntity();
			Region r = RegionUtils.load(p.getLocation());
			if (src.getType() == DamageTypes.FALL) {
				if (r == null)
					event.setCancelled(!RegionUtils.getGlobalFlag("falldamage"));
				else
					event.setCancelled(!r.getFlag("falldamage"));
			}
			else if (src.getType() == DamageTypes.DROWN) {
				if (r == null)
					event.setCancelled(!RegionUtils.getGlobalFlag("drown"));
				else
					event.setCancelled(!r.getFlag("drown"));
			}
			else if (src.getType() == DamageTypes.HUNGER) {
				if (r == null)
					event.setCancelled(!RegionUtils.getGlobalFlag("hunger"));
				else
					event.setCancelled(!r.getFlag("hunger"));
			}
			else if (src.getType() == DamageTypes.SUFFOCATE) {
				if (r == null)
					event.setCancelled(!RegionUtils.getGlobalFlag("walldamage"));
				else
					event.setCancelled(!r.getFlag("walldamage"));
			}
			
			else {
				if (r == null)
					event.setCancelled(!RegionUtils.getGlobalFlag("mobdamage"));
				else
					event.setCancelled(!r.getFlag("mobdamage"));
			}

		}
	}
}

package club.frozed.snow.handler;

import club.frozed.snow.FrozedSnow;
import club.frozed.snow.util.PacketUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Elb1to
 * @since 24/Dec/2025
 */
public class ParticleHandler extends BukkitRunnable {

	private final FrozedSnow plugin;
	private final ConfigurationSection cfg;
	private final Set<UUID> disabledPlayers = new HashSet<>();
	private com.github.retrooper.packetevents.protocol.particle.Particle<?> particle;

	public ParticleHandler(FrozedSnow plugin) {
		this.plugin = plugin;
		this.cfg = plugin.getConfig().getConfigurationSection("particle-settings");

		try {
			String particleType = cfg.getString("type", "FIREWORK");
			Particle bukkitParticle = Particle.valueOf(particleType);
			this.particle = PacketUtil.getParticle(bukkitParticle);
			plugin.getLogger().info("Loaded particle type: " + particleType);
		} catch (IllegalArgumentException e) {
			plugin.getLogger().severe("Invalid particle type in config: " + cfg.getString("type"));
			plugin.getLogger().severe("Using default particle: FIREWORK");
			this.particle = PacketUtil.getParticle(Particle.FIREWORK);
		}

		this.runTaskTimerAsynchronously(plugin, 0L, 1L);
	}

	@Override
	public void run() {
		int amount = cfg.getInt("amount");
		double radius = cfg.getDouble("radius-distance-in-blocks");
		double offsetX = cfg.getDouble("offset-x");
		double offsetY = cfg.getDouble("offset-y");
		double offsetZ = cfg.getDouble("offset-z");
		double speed = cfg.getDouble("speed");

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (disabledPlayers.contains(player.getUniqueId())) {
				continue;
			}

			for (int i = 0; i < amount; i++) {
				double angle = ThreadLocalRandom.current().nextDouble() * 2 * Math.PI;
				double distance = ThreadLocalRandom.current().nextDouble() * radius;
				double x = player.getLocation().getX() + (Math.cos(angle) * distance);
				double z = player.getLocation().getZ() + (Math.sin(angle) * distance);
				double y = player.getLocation().getY() + ThreadLocalRandom.current().nextDouble() * radius - (radius / 4);

				Location particleLocation = new Location(player.getWorld(), x, y, z);
				PacketUtil.sendParticle(
						player,
						particleLocation,
						particle,
						1,
						offsetX,
						offsetY,
						offsetZ,
						speed
				);
			}
		}
	}

	public boolean toggleParticles(UUID playerId) {
		if (disabledPlayers.contains(playerId)) {
			disabledPlayers.remove(playerId);
			return true;
		} else {
			disabledPlayers.add(playerId);
			return false;
		}
	}
}

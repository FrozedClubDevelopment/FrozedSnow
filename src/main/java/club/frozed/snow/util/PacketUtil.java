package club.frozed.snow.util;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Elb1to
 * @since 24/Dec/2025
 */
public class PacketUtil {

	public static void sendParticle(Player player, Location location, Particle<?> particle, int count,
	                                double offsetX, double offsetY, double offsetZ, double speed) {
		WrapperPlayServerParticle packet = new WrapperPlayServerParticle(
				particle,
				true,
				new Vector3d(location.getX(), location.getY(), location.getZ()),
				new Vector3f((float) offsetX, (float) offsetY, (float) offsetZ),
				(float) speed,
				count
		);

		PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
	}

	/**
	 * Converts Bukkit particle types to PacketEvents particle types.
	 * Supported particles: ENCHANT, HAPPY_VILLAGER, FIREWORK, END_ROD,
	 * EXPLOSION_EMITTER, CAMPFIRE_COSY_SMOKE, TOTEM_OF_UNDYING, SNOWFLAKE
	 * Default fallback: FLAME
	 */
	public static Particle<?> getParticle(org.bukkit.Particle bukkitParticle) {
		return switch (bukkitParticle) {
			case ENCHANT -> new Particle<>(ParticleTypes.ENCHANT);
			case HAPPY_VILLAGER -> new Particle<>(ParticleTypes.HAPPY_VILLAGER);
			case FIREWORK -> new Particle<>(ParticleTypes.FIREWORK);
			case END_ROD -> new Particle<>(ParticleTypes.END_ROD);
			case EXPLOSION_EMITTER -> new Particle<>(ParticleTypes.EXPLOSION_EMITTER);
			case CAMPFIRE_COSY_SMOKE -> new Particle<>(ParticleTypes.CAMPFIRE_COSY_SMOKE);
			case TOTEM_OF_UNDYING -> new Particle<>(ParticleTypes.TOTEM_OF_UNDYING);
			case SNOWFLAKE -> new Particle<>(ParticleTypes.SNOWFLAKE);
			default -> new Particle<>(ParticleTypes.FLAME);
		};
	}
}

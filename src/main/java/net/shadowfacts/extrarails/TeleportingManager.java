package net.shadowfacts.extrarails;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

/**
 * @author shadowfacts
 */
public class TeleportingManager {

	private static Multimap<Integer, Pos> map = HashMultimap.create();

	public static void add(int color, int dimension, BlockPos pos) {
		map.put(color, new Pos(dimension, pos));
	}

	public static void remove(int color, int dimension, BlockPos pos) {
		map.remove(color, new Pos(dimension, pos));
	}

	public static Collection<Pos> get(int color) {
		return map.get(color);
	}

	@AllArgsConstructor
	public static class Pos {
		public final int dim;
		public final BlockPos pos;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Pos pos1 = (Pos) o;

			if (dim != pos1.dim) return false;
			return pos.equals(pos1.pos);

		}

		@Override
		public int hashCode() {
			int result = dim;
			result = 31 * result + pos.hashCode();
			return result;
		}
	}

}

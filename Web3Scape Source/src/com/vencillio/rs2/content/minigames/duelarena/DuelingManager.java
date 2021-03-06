package com.vencillio.rs2.content.minigames.duelarena;

import com.vencillio.core.cache.map.Region;
import com.vencillio.core.util.Utility;
import com.vencillio.rs2.entity.Location;

public class DuelingManager {
	private static int[] activeObstacleDuelCount = new int[3];

	private static int[] activeNonObstacleDuelCount = new int[3];

	public static final Location[] OBSTACLE_ARENA_LOCATIONS = { new Location(3376, 3251), new Location(3345, 3231), new Location(3376, 3213) };

	public static final Location[] NON_OBSTACLE_ARENA_LOCATIONS = { new Location(3345, 3251), new Location(3376, 3232), new Location(3345, 3213) };

	public static Location getCoordinates(int arenaId, boolean obstacles, boolean secondPlayer, boolean noMovement) {
		if (noMovement) {
			Location p = NON_OBSTACLE_ARENA_LOCATIONS[arenaId];
			return new Location(p.getX() + (5 + Utility.randomNumber(5)) * (Utility.randomNumber(1) == 0 ? 1 : -1), p.getY() + Utility.randomNumber(6) * (Utility.randomNumber(1) == 0 ? 1 : -1));
		}
		if (obstacles) {
			Location p = OBSTACLE_ARENA_LOCATIONS[arenaId];
			int x = p.getX() + (5 + Utility.randomNumber(5)) * (secondPlayer ? 1 : -1);
			int y = p.getY() + Utility.randomNumber(5) * (Utility.randomNumber(1) == 0 ? 1 : -1);

			while (Region.getStaticClip(x, y, 0) != 0) {
				x = p.getX() + (5 + Utility.randomNumber(5)) * (secondPlayer ? 1 : -1);
				y = p.getY() + Utility.randomNumber(5) * (Utility.randomNumber(1) == 0 ? 1 : -1);
			}
			return new Location(x, y);
		}
		Location p = NON_OBSTACLE_ARENA_LOCATIONS[arenaId];
		return new Location(p.getX() + (5 + Utility.randomNumber(5)) * (secondPlayer ? 1 : -1), p.getY() + Utility.randomNumber(5) * (Utility.randomNumber(1) == 0 ? 1 : -1));
	}

	public static final int getDuelArenaId(boolean obstacles) {
		int arena = -1;
		if (obstacles) {
			for (int i = 0; i < 3; i++) {
				if ((arena == -1) || (activeObstacleDuelCount[i] < activeObstacleDuelCount[arena])) {
					arena = i;
				}
			}
			activeObstacleDuelCount[arena] += 1;
			return arena;
		}
		for (int i = 0; i < 3; i++) {
			if ((arena == -1) || (activeNonObstacleDuelCount[i] < activeNonObstacleDuelCount[arena])) {
				arena = i;
			}
		}
		activeNonObstacleDuelCount[arena] += 1;
		return arena;
	}

	public static void onFinishDuel(int arenaId, boolean obstacles) {
		if (obstacles)
			activeObstacleDuelCount[arenaId] -= 1;
		else
			activeNonObstacleDuelCount[arenaId] -= 1;
	}
}

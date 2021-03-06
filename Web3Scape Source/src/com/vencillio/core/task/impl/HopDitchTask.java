package com.vencillio.core.task.impl;

import com.vencillio.rs2.entity.Location;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.controllers.ControllerManager;

public class HopDitchTask extends ForceMovementTask {

	public static Location getLocation(Location loc) {
		if (loc.getY() < 3522) {
			return new Location(loc.getX(), loc.getY() + 3);
		} else {
			return new Location(loc.getX(), loc.getY() - 3);
		}
	}

	private byte stage = 0;

	public HopDitchTask(Player player) {
		super(player, getLocation(player.getLocation()), ControllerManager.DEFAULT_CONTROLLER);
	}

	@Override
	public void execute() {
		if (stage == 0) {
			player.getUpdateFlags().sendAnimation(6132, 0);
		} else if (stage == 1) {
			player.teleport(dest);
			player.setController(ControllerManager.DEFAULT_CONTROLLER);
			player.getUpdateFlags().sendFaceToDirection(player.getLocation().getX() - xMod, player.getLocation().getY() - yMod);
			stop();
		}

		stage++;
	}

	@Override
	public void onDestination() {
	}
}

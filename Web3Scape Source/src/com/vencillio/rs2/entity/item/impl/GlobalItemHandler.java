package com.vencillio.rs2.entity.item.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.vencillio.core.task.Task;
import com.vencillio.core.task.TaskQueue;
import com.vencillio.core.util.Utility;
import com.vencillio.rs2.entity.Location;
import com.vencillio.rs2.entity.item.Item;

/**
 * Handles the global ground items
 * 
 * @author Arithium
 * 
 */
public class GlobalItemHandler {

	private static Logger logger = Logger.getLogger(GlobalItemHandler.class.getName());

	/**
	 * A list containing all of the ground items
	 */
	private static final List<GroundItem> groundItems = new ArrayList<GroundItem>();

	/**
	 * Adds a global ground item to the list
	 * 
	 * @param item
	 */
	private static void add(GroundItem item) {
		groundItems.add(item);
	}

	/**
	 * Creates a respawn task for the ground item
	 * 
	 * @param groundItem
	 */
	public static void createRespawnTask(final GroundItem groundItem) {
		final GroundItem tempItem = new GroundItem(groundItem.getItem(), groundItem.getLocation(), groundItem.getRespawnTimer());
		TaskQueue.queue(new Task(tempItem.getRespawnTimer()) {
			@Override
			public void execute() {
				GroundItemHandler.add(tempItem);
				GroundItemHandler.globalize(tempItem);
				this.stop();
			}

			@Override
			public void onStop() {

			}
		});
	}

	/**
	 * Spawns global ground items
	 */
	public static void spawnGroundItems() {
		add(new GroundItem(new Item(952, 1), new Location(3563, 3305), 50));

		logger.info("Successfully loaded " + Utility.format(groundItems.size()) + " global ground items.");
		for (GroundItem item : groundItems) {
			if (item == null)
				continue;
			GroundItemHandler.add(item);
		}
	}

}

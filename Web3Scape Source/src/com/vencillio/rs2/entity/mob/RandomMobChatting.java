package com.vencillio.rs2.entity.mob;

import java.util.HashMap;
import java.util.Map;

import com.vencillio.core.util.Utility;

/**
 * Handles npcs with random forced messages to send their individual messages to
 * the players at a random interval
 * 
 * @author Arithium 8/30/2013
 * 
 */
public enum RandomMobChatting {

	MAKEOVER_MAGE(new int[] { 599, 2676 }, 150, "I can change your appearance.", "Welcome to the world of Vencillio"),
	COMBAT_TUTOR(new int[] { 705 }, 120, "Welcome to Vencillio!", "Become the number one pker of Vencillio!")

	;
	/**
	 * Stores the messages into a hashmap upon constructing this class
	 */
	public static final void declare() {
		for (RandomMobChatting mob : values()) {
			for (Integer k : mob.getMobId()) {
				mobs.put(k, mob);
			}
		}
	}

	/**
	 * A list of mobs with the same messages
	 */
	private int[] mobId;

	/**
	 * The list of random messages a mob will send
	 */
	private String[] messages;

	/**
	 * How often the mob will send a random message
	 */
	private int random;

	/**
	 * A list containing all of the mobs random messages
	 */
	private static Map<Integer, RandomMobChatting> mobs = new HashMap<Integer, RandomMobChatting>();

	/**
	 * Returns the mobs data based on the id
	 * 
	 * @param id
	 *            The id of the mob
	 * @return
	 */
	public static RandomMobChatting getMob(int id) {
		return mobs.get(id);
	}

	/**
	 * Handles sending a random forced message from a mob
	 * 
	 * @param mob
	 *            The mob sending the message
	 */
	public static void handleRandomMobChatting(Mob mob) {
		RandomMobChatting chat = getMob(mob.getId());
		if (chat == null)
			return;
		if (Utility.randomNumber(chat.getRandom()) == 1)
			mob.getUpdateFlags().sendForceMessage(chat.getMessages()[Utility.randomNumber(chat.getMessages().length - 1)]);
	}

	/**
	 * 
	 * @param mobId
	 *            The id of the mob sending the message
	 * @param random
	 *            The random amount of time to send the message
	 * @param messages
	 *            The messages that can be sent
	 */
	RandomMobChatting(int[] mobId, int random, String... messages) {
		this.mobId = mobId;
		this.random = random;
		this.messages = messages;
	}

	/**
	 * Returns the messages available to be sent from the mob
	 * 
	 * @return
	 */
	public String[] getMessages() {
		return messages;
	}

	/**
	 * Returns the id of the mobs in the list
	 * 
	 * @return The id of the mob
	 */
	public int[] getMobId() {
		return mobId;
	}

	/**
	 * The random time it will take to send a message
	 * 
	 * @return
	 */
	public int getRandom() {
		return random;
	}

}

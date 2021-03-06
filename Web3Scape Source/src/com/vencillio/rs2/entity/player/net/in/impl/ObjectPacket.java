package com.vencillio.rs2.entity.player.net.in.impl;

import com.vencillio.core.cache.map.Region;
import com.vencillio.core.network.StreamBuffer;
import com.vencillio.core.task.TaskQueue;
import com.vencillio.rs2.entity.WalkToActions;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.PlayerConstants;
import com.vencillio.rs2.entity.player.net.in.IncomingPacket;
import com.vencillio.rs2.entity.player.net.out.impl.SendRemoveInterfaces;

@SuppressWarnings("all")
public class ObjectPacket extends IncomingPacket {
	public static final int ITEM_ON_OBJECT = 192;
	public static final int FIRST_CLICK = 132;
	public static final int SECOND_CLICK = 252;
	public static final int THIRD_CLICK = 70;
	public static final int FOURTH_CLICK = 234;
	public static final int CAST_SPELL = 35;

	@Override
	public int getMaxDuplicates() {
		return 1;
	}

	@Override
	public void handle(Player player, StreamBuffer.InBuffer in, int opcode, int length) {
		if ((player.isDead()) || (!player.getController().canClick())) {
			return;
		}

		player.getClient().queueOutgoingPacket(new SendRemoveInterfaces());
		TaskQueue.onMovement(player);

		player.getCombat().reset();

		switch (opcode) {
		case 192:
			int interfaceId = in.readShort();
			int id = in.readShort(true, StreamBuffer.ByteOrder.LITTLE);
			int y = in.readShort(true, StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			int slot = in.readShort(StreamBuffer.ByteOrder.LITTLE);
			int x = in.readShort(true, StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			int z = player.getLocation().getZ();
			int itemId = in.readShort();

			if ((!Region.objectExists(id, x, y, z)) && (!PlayerConstants.isOverrideObjectExistance(player, id, x, y, z))) {
				System.out.println("Object found to be non-existent: id: " + id + " at x:" + x + "  y:" + y + "  z:" + z);
				return;
			}
			
			if (!player.getInventory().slotContainsItem(slot, itemId)) {
				return;
			}

			WalkToActions.itemOnObject(player, itemId, id, x, y);
			break;
		case 132:
			x = in.readShort(true, StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			id = in.readShort();
			y = in.readShort(StreamBuffer.ValueType.A);
			z = player.getLocation().getZ();
			

			if ((!Region.objectExists(id, x, y, z)) && (!PlayerConstants.isOverrideObjectExistance(player, id, x, y, z))) {
				System.out.println("Object found to be non-existent: id: " + id + " at x:" + x + "  y:" + y + "  z:" + z);
//				System.out.println("case " + id + ":");
				return;
			}

			WalkToActions.clickObject(player, 1, id, x, y);
			break;
		case 252:
			id = in.readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			y = in.readShort(true, StreamBuffer.ByteOrder.LITTLE);
			x = in.readShort(StreamBuffer.ValueType.A);
			z = player.getLocation().getZ();

			if ((!Region.objectExists(id, x, y, z)) && (!PlayerConstants.isOverrideObjectExistance(player, id, x, y, z))) {
				System.out.println("Object found to be non-existent: id: " + id + " at x:" + x + "  y:" + y + "  z:" + z);
				return;
			}

			WalkToActions.clickObject(player, 2, id, x, y);
			break;
		case 70:
			x = in.readShort(true, StreamBuffer.ByteOrder.LITTLE);
			y = in.readShort();
			id = in.readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			z = player.getLocation().getZ();

			if ((!Region.objectExists(id, x, y, z)) && (!PlayerConstants.isOverrideObjectExistance(player, id, x, y, z))) {
				System.out.println("Object found to be non-existent: id: " + id + " at x:" + x + "  y:" + y + "  z:" + z);
				return;
			}

			WalkToActions.clickObject(player, 3, id, x, y);
			break;
		case 234:
			x = in.readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			id = in.readShort(StreamBuffer.ValueType.A);
			y = in.readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			z = player.getLocation().getZ();

			if ((!Region.objectExists(id, x, y, z)) && (!PlayerConstants.isOverrideObjectExistance(player, id, x, y, z))) {
				System.out.println("Object found to be non-existent: id: " + id + " at x:" + x + "  y:" + y + "  z:" + z);
				return;
			}

			WalkToActions.clickObject(player, 4, id, x, y);
			break;
		case 35:
			x = in.readShort(StreamBuffer.ByteOrder.LITTLE);

			int magicId = in.readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.BIG);
			y = in.readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.BIG);
			id = in.readShort(StreamBuffer.ByteOrder.LITTLE);
			z = player.getLocation().getZ();

			if ((!Region.objectExists(id, x, y, z)) && (!PlayerConstants.isOverrideObjectExistance(player, id, x, y, z))) {
				System.out.println("Object found to be non-existent: id: " + id + " at x:" + x + "  y:" + y + "  z:" + z);
				return;
			}
			break;
		}
	}
}

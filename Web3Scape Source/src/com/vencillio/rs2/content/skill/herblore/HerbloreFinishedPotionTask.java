package com.vencillio.rs2.content.skill.herblore;

import com.vencillio.core.task.Task;
import com.vencillio.core.task.TaskQueue;
import com.vencillio.core.task.impl.TaskIdentifier;
import com.vencillio.rs2.entity.Animation;
import com.vencillio.rs2.entity.item.Item;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.net.out.impl.SendChatBoxInterface;
import com.vencillio.rs2.entity.player.net.out.impl.SendEnterXInterface;
import com.vencillio.rs2.entity.player.net.out.impl.SendItemOnInterface;
import com.vencillio.rs2.entity.player.net.out.impl.SendMessage;
import com.vencillio.rs2.entity.player.net.out.impl.SendMoveComponent;
import com.vencillio.rs2.entity.player.net.out.impl.SendRemoveInterfaces;
import com.vencillio.rs2.entity.player.net.out.impl.SendSound;
import com.vencillio.rs2.entity.player.net.out.impl.SendString;

public class HerbloreFinishedPotionTask extends Task {

	public static final String HERBLORE_ITEM_1_KEY = "herbloreitem1";
	public static final String HERBLORE_ITEM_2_KEY = "herbloreitem2";

	public static void attemptPotionMaking(Player player, int amount) {
		FinishedPotionData data = FinishedPotionData.forIds(((Item) player.getAttributes().get("herbloreitem1")).getId(), ((Item) player.getAttributes().get("herbloreitem2")).getId());
		if (data == null) {
			data = FinishedPotionData.forId(((Item) player.getAttributes().get("herbloreitem2")).getId());
		}
		if (data == null) {
			player.getClient().queueOutgoingPacket(new SendRemoveInterfaces());
			return;
		}
		if (!meetsRequirements(player, data, false)) {
			player.getClient().queueOutgoingPacket(new SendRemoveInterfaces());
			return;
		}
		createPotion(player, data);
		player.getUpdateFlags().sendAnimation(new Animation(363));
		amount--;
		TaskQueue.queue(new HerbloreFinishedPotionTask(player, data, amount));
		player.getClient().queueOutgoingPacket(new SendRemoveInterfaces());
	}

	private static void createPotion(Player player, FinishedPotionData data) {
		player.getInventory().remove(new Item(data.getUnfinishedPotion(), 1));
		player.getInventory().remove(new Item(data.getItemNeeded(), 1));
		player.getInventory().add(new Item(data.getFinishedPotion(), 1));
		player.getSkill().addExperience(15, data.getExpGained());

	}

	public static boolean displayInterface(Player player, Item used, Item usedWith) {
		FinishedPotionData data = FinishedPotionData.forIds(used.getId(), usedWith.getId());

		if (data == null) {
			return false;
		}

		Item finishedPotion = new Item(data.getFinishedPotion(), 1);

		if (finishedPotion.getDefinition() == null) {
			player.getClient().queueOutgoingPacket(new SendMessage("This item is not supported."));
			return true;
		}
		player.getClient().queueOutgoingPacket(new SendChatBoxInterface(4429));
		player.getClient().queueOutgoingPacket(new SendMoveComponent(0, 25, 1746));
		player.getClient().queueOutgoingPacket(new SendItemOnInterface(1746, 170, finishedPotion.getId()));

		if (finishedPotion.getDefinition() != null && finishedPotion.getDefinition().getName() != null) {
			player.getClient().queueOutgoingPacket(new SendString("\\n \\n \\n \\n \\n \\n \\n" + finishedPotion.getDefinition() != null ? finishedPotion.getDefinition().getName() : "null", 2799));
		} else {
			player.getClient().queueOutgoingPacket(new SendString("\\n \\n \\n \\n \\n \\n \\n Invalid Item", 2799));
		}

		player.getAttributes().set("herbloreitem1", used);
		player.getAttributes().set("herbloreitem2", usedWith);
		return true;
	}

	public static boolean handleHerbloreButtons(Player player, int buttonId) {
		int amount = 0;
		for (int i = 0; i < BUTTON_IDS.length; i++) {
			if (BUTTON_IDS[i][0] == buttonId) {
				amount = BUTTON_IDS[i][1];
				break;
			}
		}
		if (amount == 0) {
			return false;
		}
		if (amount != 100)
			attemptPotionMaking(player, amount);
		else {
			player.getClient().queueOutgoingPacket(new SendEnterXInterface(4430, ((Item) player.getAttributes().get("herbloreitem1")).getId()));
		}
		return true;
	}

	private static boolean meetsRequirements(Player player, FinishedPotionData data, boolean running) {
		if (player.getSkill().getLevels()[15] < data.getLevelReq()) {
			player.getClient().queueOutgoingPacket(new SendMessage("You need an herblore level of " + data.getLevelReq() + " to make this potion."));
			return false;
		}
		if ((!player.getInventory().hasItemId(data.getUnfinishedPotion())) || (!player.getInventory().hasItemId(data.getItemNeeded()))) {
			player.getClient().queueOutgoingPacket(new SendMessage(!running ? "You don't have the ingredients required to make this potion." : "You have run out of ingredients to make this potion."));
			return false;
		}
		return true;
	}

	private final Player player;

	private final FinishedPotionData data;

	private int amountToMake;

	private static final int[][] BUTTON_IDS = { { 10239, 1 }, { 10238, 5 }, { 6211, 28 }, { 6212, 100 } };

	public HerbloreFinishedPotionTask(Player player, FinishedPotionData data, int amount) {
		super(player, 2, false, Task.StackType.NEVER_STACK, Task.BreakType.ON_MOVE, TaskIdentifier.CURRENT_ACTION);
		this.player = player;
		this.data = data;
		amountToMake = amount;
	}

	@Override
	public void execute() {
		if (!meetsRequirements(player, data, true)) {
			stop();
			return;
		}

		if (amountToMake == 0) {
			stop();
			return;
		}

		player.getClient().queueOutgoingPacket(new SendSound(281, 0, 0));

		player.getUpdateFlags().sendAnimation(new Animation(363));

		createPotion(player, data);

		amountToMake -= 1;
		if (amountToMake == 0)
			stop();
	}

	@Override
	public void onStop() {
	}
}

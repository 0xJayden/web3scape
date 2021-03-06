package com.vencillio.rs2.content.skill.magic;

import com.vencillio.core.util.GameDefinitionLoader;
import com.vencillio.core.util.Utility;
import com.vencillio.rs2.entity.item.Item;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.net.out.impl.SendConfig;
import com.vencillio.rs2.entity.player.net.out.impl.SendMessage;
import com.vencillio.rs2.entity.player.net.out.impl.SendSidebarInterface;
import com.vencillio.rs2.entity.player.net.out.impl.SendString;

public class Autocast {
	
	public static boolean clickButton(Player player, int id) {
		switch (id) {
		case 1093:
		case 1094:
			
			if (player.getMagic().getSpellCasting().isAutocasting()) {
				player.getMagic().getSpellCasting().disableAutocast();
				resetAutoCastInterface(player);
			} else {
				Item weapon = player.getEquipment().getItems()[3];
				if (weapon == null) {
					return true;
				}

				sendSelectionInterface(player, weapon.getId());
			}
			return true;
		case 7038:
			setAutocast(player, 1152);
			return true;
		case 7039:
			setAutocast(player, 1154);
			return true;
		case 7040:
			setAutocast(player, 1156);
			return true;
		case 7041:
			setAutocast(player, 1158);
			return true;
		case 7042:
			setAutocast(player, 1160);
			return true;
		case 7043:
			setAutocast(player, 1163);
			return true;
		case 7044:
			setAutocast(player, 1166);
			return true;
		case 7045:
			setAutocast(player, 1169);
			return true;
		case 7046:
			setAutocast(player, 1172);
			return true;
		case 7047:
			setAutocast(player, 1175);
			return true;
		case 7048:
			setAutocast(player, 1177);
			return true;
		case 7049:
			setAutocast(player, 1181);
			return true;
		case 7050:
			setAutocast(player, 1183);
			return true;
		case 7051:
			setAutocast(player, 1185);
			return true;
		case 7052:
			setAutocast(player, 1188);
			return true;
		case 7053:
			setAutocast(player, 1189);
			return true;
		case 7212:
			player.getClient().queueOutgoingPacket(new SendSidebarInterface(0, 328));
			return true;
		case 51133:
			setAutocast(player, 12939);
			return true;
		case 51185:
			setAutocast(player, 12987);
			return true;
		case 51091:
			setAutocast(player, 12901);
			return true;
		case 24018:
			setAutocast(player, 12861);
			return true;
		case 51159:
			setAutocast(player, 12963);
			return true;
		case 51211:
			setAutocast(player, 13011);
			return true;
		case 51111:
			setAutocast(player, 12919);
			return true;
		case 51069:
			setAutocast(player, 12881);
			return true;
		case 51146:
			setAutocast(player, 12951);
			return true;
		case 51198:
			setAutocast(player, 12999);
			return true;
		case 51102:
			setAutocast(player, 12911);
			return true;
		case 51058:
			setAutocast(player, 12871);
			return true;
		case 51172:
			setAutocast(player, 12975);
			return true;
		case 51224:
			setAutocast(player, 13023);
			return true;
		case 51122:
			setAutocast(player, 12929);
			return true;
		case 51080:
			setAutocast(player, 12891);
			return true;
		case 24017:
			player.getClient().queueOutgoingPacket(new SendSidebarInterface(0, 328));
			return true;
		}

		return false;
	}

	public static void resetAutoCastInterface(Player player) {
		if (!player.getMagic().getSpellCasting().isAutocasting()) {
			player.getClient().queueOutgoingPacket(new SendConfig(108, 0));
			player.getClient().queueOutgoingPacket(new SendString("Spell", 18584));
			player.getEquipment().updateAttackStyle();
		} else {
			player.getClient().queueOutgoingPacket(new SendConfig(43, 3));
			player.getClient().queueOutgoingPacket(new SendConfig(108, 1));
		}
	}
	
	public final static int[] ANCIENT_STAFFS = { 
		4675, 4710, 11791, 12904, 6914
	};
	
	public final static int[] NO_AUTOCAST = {
		12899, 11907, 11908
	};

	public static void sendSelectionInterface(Player player, int weaponId) {
		for (int i = 0; i < NO_AUTOCAST.length; i++) {
			if (weaponId == NO_AUTOCAST[i]) {
				player.send(new SendMessage("You can't autocast with this weapon!"));
				return;				
			}
		}
		
		switch (player.getMagic().getSpellBookType()) {
		case ANCIENT:
			boolean hasStaff = false;
			for (int index = 0; index < ANCIENT_STAFFS.length; index++) {
				if (player.getEquipment().isWearingItem(ANCIENT_STAFFS[index])) {
					hasStaff = true;
					break;
				}
			}
			
			if (!hasStaff) {	
				if (player.getEquipment().getItems()[3] == null) {
					return;
				}
				
				String def = GameDefinitionLoader.getItemDef(player.getEquipment().getItems()[3].getId()).getName().toLowerCase();
				
				player.send(new SendMessage("You can't autocast ancient magiks with " + Utility.getAOrAn(def) + " " +def + "."));
				return;
			}
			
			player.getClient().queueOutgoingPacket(new SendSidebarInterface(0, 1689));
			break;
		case LUNAR:
			resetAutoCastInterface(player);
			break;
		case MODERN:
			if (player.getEquipment().isWearingItem(4675)) {
				player.send(new SendMessage("You can't autocast normal magiks with this staff!"));
				return;
			}
			player.getClient().queueOutgoingPacket(new SendSidebarInterface(0, 1829));
			break;
		}
	}

	public static void setAutocast(Player player, int id) {
		player.getMagic().getSpellCasting().enableAutocast(id);
		player.getClient().queueOutgoingPacket(new SendSidebarInterface(0, 328));
		resetAutoCastInterface(player);
	}
}

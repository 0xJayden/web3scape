package com.vencillio.rs2.entity;

import com.vencillio.core.cache.map.RSInterface;
import com.vencillio.rs2.entity.player.PlayerConstants;

public class InterfaceManager {

	private int[] tabs = new int[PlayerConstants.SIDEBAR_INTERFACE_IDS.length];

	public int main = -1;
	public int sub = -1;
	public int chat = -1;

	public int[] getParents(int id) {
		int[] parents = new int[25];

		int parent = RSInterface.interfaceCache[id].parentID;
		int c = 0;
		parents[c] = parent;

		if (parent == 0) {
			return new int[] { id };
		}

		while ((parent != 0) && (c != 24)) {
			if ((RSInterface.interfaceCache[parent] == null) || (RSInterface.interfaceCache[parent].parentID == 0) || (parent == RSInterface.interfaceCache[parent].parentID)) {
				break;
			}
			parent = RSInterface.interfaceCache[parent].parentID;
			parents[c] = parent;
			c++;
		}

		return parents;
	}

	private int getTab(int id) {
		for (int i = 0; i < tabs.length; i++) {
			if (i == id)
				return i;
		}
		return -1;
	}

	public int[] getTabs() {
		return tabs;
	}

	public boolean hasBankOpen() {
		return (main == 5292) && (sub == 5063);
	}

	public boolean hasBOBInventoryOpen() {
		return (main == 2700) && (sub == 5063);
	}

	public boolean hasInterfaceOpen() {
		return main != -1 || sub != -1 || chat != -1;
	}

	public void reset() {
		main = -1;
		sub = -1;
		chat = -1;
	}

	public void setActive(int main, int sub) {
		this.main = main;
		this.sub = sub;
	}

	public void setChat(int chat) {
		this.chat = chat;
	}

	public void setTabId(int slot, int id) {
		tabs[slot] = id;
	}
	
	public int getMain() {
		return main;
	}

	public boolean verify(int id) {
		if ((main == id) || (sub == id) || (chat == id) || (getTab(id) != -1)) {
			return true;
		}

		for (int p : getParents(id)) {
			if ((main == p) || (sub == p) || (chat == p) || (getTab(p) != -1)) {
				return true;
			}
		}

		return false;
	}

	public boolean verifyButton(int button) {
		if ((button > RSInterface.interfaceCache.length - 1) || (button < 0)) {
			System.out.println("button out of bounds");
			return false;
		}

		System.out.println("verify button " + button);

		if (RSInterface.interfaceCache[button] == null) {
			System.out.println("no def " + button);
			return false;
		}

		return false;
	}
}

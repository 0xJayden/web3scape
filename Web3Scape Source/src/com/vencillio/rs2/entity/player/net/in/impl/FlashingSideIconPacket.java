package com.vencillio.rs2.entity.player.net.in.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.net.in.IncomingPacket;

public class FlashingSideIconPacket extends IncomingPacket {
	
	@Override
	public int getMaxDuplicates() {
		return 1;
	}

	@Override
	public void handle(Player player, StreamBuffer.InBuffer in, int opcode, int length) {
	}
}

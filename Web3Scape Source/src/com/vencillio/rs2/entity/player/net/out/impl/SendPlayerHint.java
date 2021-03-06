package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendPlayerHint extends OutgoingPacket {

	private final int type;

	private final int id;

	public SendPlayerHint(boolean player, int id) {
		super();
		type = (player ? 10 : 1);
		this.id = id;
	}

	@Override
	public void execute(Client client) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(7);
		out.writeHeader(client.getEncryptor(), 254);
		out.writeByte(type);
		out.writeShort(id);
		out.writeByte(0 >> 16);
		out.writeByte(0 >> 8);
		out.writeByte(0);
		client.send(out.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 254;
	}

}

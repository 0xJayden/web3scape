package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendNPCDialogueHead extends OutgoingPacket {

	private final int npc;

	private final int id;

	public SendNPCDialogueHead(int npc, int id) {
		super();
		this.npc = npc;
		this.id = id;
	}

	@Override
	public void execute(Client client) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(5);
		out.writeHeader(client.getEncryptor(), 75);
		out.writeShort(npc, StreamBuffer.ValueType.A,
				StreamBuffer.ByteOrder.LITTLE);
		out.writeShort(id, StreamBuffer.ValueType.A,
				StreamBuffer.ByteOrder.LITTLE);
		client.send(out.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 75;
	}

}

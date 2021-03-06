package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendInventoryInterface extends OutgoingPacket {

	private final int invId;

	private final int id;

	public SendInventoryInterface(int id, int invId) {
		super();
		this.invId = invId;
		this.id = id;
	}

	@Override
	public void execute(Client client) {
		if (client.getPlayer().getMovementHandler().moving()) {
			client.getPlayer().getMovementHandler().reset();
		}
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(5);
		out.writeHeader(client.getEncryptor(), 248);
		out.writeShort(id, StreamBuffer.ValueType.A);
		out.writeShort(invId);
		client.send(out.getBuffer());
		client.getPlayer().getInterfaceManager().setActive(id, invId);
	}

	@Override
	public int getOpcode() {
		return 248;
	}
}

package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendConfig extends OutgoingPacket {

	private final int id;

	private final int value;

	public SendConfig(int id, int value) {
		this.id = id;
		this.value = value;
	}

	@Override
	public void execute(Client client) {
		if (value < 128) {
			StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
			out.writeHeader(client.getEncryptor(), 36);
			out.writeShort(id, StreamBuffer.ByteOrder.LITTLE);
			out.writeByte(value);
			client.send(out.getBuffer());
		} else {
			StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(7);
			out.writeHeader(client.getEncryptor(), 87);
			out.writeShort(id, StreamBuffer.ByteOrder.LITTLE);
			out.writeInt(value, StreamBuffer.ByteOrder.MIDDLE);
			client.send(out.getBuffer());
		}
	}

	@Override
	public int getOpcode() {
		return 36;
	}

}

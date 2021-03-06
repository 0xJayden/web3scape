package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendSpecialBar extends OutgoingPacket {

	private final int main;

	private final int sub;

	public SendSpecialBar(int main, int sub) {
		super();
		this.main = main;
		this.sub = sub;
	}

	@Override
	public void execute(Client client) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
		out.writeHeader(client.getEncryptor(), 171);
		out.writeByte(main);
		out.writeShort(sub);
		client.send(out.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 171;
	}

}

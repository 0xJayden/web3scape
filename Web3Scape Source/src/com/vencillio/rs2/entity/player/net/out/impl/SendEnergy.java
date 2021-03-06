package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendEnergy extends OutgoingPacket {

	private final int energy;

	public SendEnergy(int energy) {
		super();
		this.energy = energy;
	}

	@Override
	public void execute(Client client) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(2);
		out.writeHeader(client.getEncryptor(), 110);
		out.writeByte(energy);
		client.send(out.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 110;
	}

}

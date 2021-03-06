package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendSkill extends OutgoingPacket {

	private final int id;

	private final int level;

	private final int exp;

	public SendSkill(int id, int level, int exp) {
		this.id = id;
		this.level = level;
		this.exp = exp;
	}

	@Override
	public void execute(Client client) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(8);
		out.writeHeader(client.getEncryptor(), 134);
		out.writeByte(id);
		out.writeByte(client.getPlayer().getSkillPrestiges()[id]);
		out.writeInt(exp, StreamBuffer.ByteOrder.MIDDLE);
		out.writeByte(level);
		client.send(out.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 134;
	}

}

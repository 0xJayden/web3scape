package com.vencillio.rs2.entity.player.net.out.impl;

import com.vencillio.core.network.StreamBuffer;
import com.vencillio.rs2.entity.Location;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.net.Client;
import com.vencillio.rs2.entity.player.net.out.OutgoingPacket;

public class SendAltCoordinates extends OutgoingPacket {

	private final Location p;
	private final Location base;

	public SendAltCoordinates(Location p, Player player) {
		super();
		this.p = p;
		base = player.getCurrentRegion();
	}

	@Override
	public void execute(Client client) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(3);
		out.writeHeader(client.getEncryptor(), 85);
		int y = p.getY() - base.getRegionY() * 8 - 2;
		int x = p.getX() - base.getRegionX() * 8 - 3;
		out.writeByte(y, StreamBuffer.ValueType.C);
		out.writeByte(x, StreamBuffer.ValueType.C);
		client.send(out.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 85;
	}

}

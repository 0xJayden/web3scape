package com.vencillio.rs2.content.combat.special.effects;

import com.vencillio.rs2.content.combat.impl.CombatEffect;
import com.vencillio.rs2.entity.Entity;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.net.out.impl.SendMessage;

public class BarrelchestAnchorEffect implements CombatEffect {
	private static final int[] EFFECTED_SKILLS = { 0, 1, 4, 6 };

	@Override
	public void execute(Player p, Entity e) {
		int eff = (int) Math.ceil(p.getLastDamageDealt() * 0.01D);
		Player p2 = null;

		if (eff == 0) {
			return;
		}

		if (!e.isNpc()) {
			p2 = com.vencillio.rs2.entity.World.getPlayers()[e.getIndex()];
		}

		for (int i = 0; i < EFFECTED_SKILLS.length; i++) {
			int tmp55_54 = EFFECTED_SKILLS[i];
			short[] tmp55_46 = e.getLevels();
			tmp55_46[tmp55_54] = ((short) (tmp55_46[tmp55_54] - eff));
			if (e.getLevels()[EFFECTED_SKILLS[i]] < 0) {
				e.getLevels()[EFFECTED_SKILLS[i]] = 0;
			}
			if (p2 != null) {
				p2.getSkill().update(EFFECTED_SKILLS[i]);
			}
		}

		p.getClient().queueOutgoingPacket(new SendMessage("You drain some of your opponents combat skills."));
	}
}

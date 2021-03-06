package com.vencillio.rs2.content.combat.special.specials;

import com.vencillio.rs2.content.combat.special.Special;
import com.vencillio.rs2.entity.Animation;
import com.vencillio.rs2.entity.Graphic;
import com.vencillio.rs2.entity.player.Player;

public class AbyssalWhipSpecialAttack implements Special {
	
	@Override
	public boolean checkRequirements(Player player) {
		return true;
	}

	@Override
	public int getSpecialAmountRequired() {
		return 50;
	}

	@Override
	public void handleAttack(Player player) {
		player.getCombat().getAttacking().getUpdateFlags().sendGraphic(Graphic.highGraphic(341, 0));
		player.getCombat().getMelee().setAnimation(new Animation(1658, 0));
	}
}

package com.vencillio.rs2.content.combat.special.specials;

import com.vencillio.rs2.content.combat.special.Special;
import com.vencillio.rs2.entity.Animation;
import com.vencillio.rs2.entity.Graphic;
import com.vencillio.rs2.entity.player.Player;

public class DragonDaggerSpecialAttack implements Special {
	@Override
	public boolean checkRequirements(Player player) {
		return true;
	}

	@Override
	public int getSpecialAmountRequired() {
		return 25;
	}

	@Override
	public void handleAttack(Player player) {
		player.getCombat().getMelee().setAnimation(new Animation(1062, 0));
		player.getUpdateFlags().sendGraphic(Graphic.highGraphic(252, 0));
		player.getCombat().getMelee().execute(player.getCombat().getAttacking());
	}
}

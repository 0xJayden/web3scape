package com.vencillio.rs2.content.skill.summoning;

import com.vencillio.rs2.content.combat.Combat.CombatTypes;
import com.vencillio.rs2.content.combat.impl.Attack;
import com.vencillio.rs2.entity.Animation;
import com.vencillio.rs2.entity.Entity;
import com.vencillio.rs2.entity.Graphic;
import com.vencillio.rs2.entity.Location;
import com.vencillio.rs2.entity.mob.Mob;
import com.vencillio.rs2.entity.mob.MobAbilities;
import com.vencillio.rs2.entity.mob.Walking;
import com.vencillio.rs2.entity.player.Player;

public class FamiliarMob extends Mob {
	private final Familiar familiar;

	public FamiliarMob(Familiar familiar, Player owner, Location l) {
		super(owner, null, familiar.mob, false, false, true, l);

		getFollowing().setFollow(owner);
		getFollowing().setIgnoreDistance(true);

		this.familiar = familiar;

		getCombat().getMelee().setAttack(new Attack(1, 5), new Animation(familiar.attackAnimation));
		getCombat().getMagic().setAttack(new Attack(3, 5), new Animation(familiar.attackAnimation), null, null, null);

		if (familiar.wildMob > 0) {
			getMaxLevels()[0] = ((short) familiar.attack);
			getMaxLevels()[1] = ((short) familiar.defence);
			getMaxLevels()[3] = ((short) (Mob.getDefinition(familiar.wildMob).getLevel() * 2));

			getLevels()[0] = ((short) familiar.attack);
			getLevels()[1] = ((short) familiar.defence);
			getLevels()[3] = ((short) (Mob.getDefinition(familiar.wildMob).getLevel() * 2));
		}
	}

	@Override
	public void afterCombatProcess(Entity attack) {
		if (attack.isDead())
			getCombat().reset();
		else {
			MobAbilities.executeAbility(getId(), this, attack);
		}

		getCombat().setCombatType(CombatTypes.MELEE);
	}

	@Override
	public void doAliveMobProcessing() {
		if ((inWilderness()) && (getId() != familiar.wildMob))
			transform(familiar.wildMob);
		else if ((!inWilderness()) && (getId() != familiar.mob)) {
			transform(familiar.mob);
		}

		if (getOwner().getSummoning().isAttack()) {
			if ((getOwner().getCombat().getAttacking() != null) && (inMultiArea()) && (getOwner().getCombat().getAttacking().inMultiArea())) {
				getCombat().setAttack(getOwner().getCombat().getAttacking());
			} else if (getCombat().getAttacking() != null) {
				getCombat().reset();
				getFollowing().setFollow(getOwner());
			}
		} else if (getCombat().getAttacking() != null) {
			getCombat().reset();
			getFollowing().setFollow(getOwner());
		}
	}

	public Familiar getData() {
		return familiar;
	}

	@Override
	public Animation getDeathAnimation() {
		return new Animation(familiar.deathAnimation);
	}

	@Override
	public int getMaxHit(CombatTypes type) {
		if (getAttributes().get("summonfammax") != null) {
			int max = getAttributes().getInt("summonfammax");
			getAttributes().remove("summonfammax");
			return max;
		}

		return getData().max;
	}

	@Override
	public void onDeath() {
		getOwner().getSummoning().onFamiliarDeath();
	}

	@Override
	public void teleport(Location p) {
		Walking.setNpcOnTile(this, false);
		getMovementHandler().getLastLocation().setAs(new Location(p.getX(), p.getY() + 1));
		getLocation().setAs(p);
		Walking.setNpcOnTile(this, true);
		setPlacement(true);
		getMovementHandler().resetMoveDirections();
		getUpdateFlags().sendGraphic(new Graphic(getSize() == 1 ? 1314 : 1315, 0, false));
	}

	@Override
	public void updateCombatType() {
	}
}

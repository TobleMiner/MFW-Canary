package TobleMiner.MineFightWeapons.Weapons.Explosive;

import org.bukkit.Location;
import org.bukkit.entity.Item;

import TobleMiner.MineFight.GameEngine.GameEngine;
import TobleMiner.MineFight.GameEngine.Match.Match;
import TobleMiner.MineFight.GameEngine.Player.PVPPlayer;
import TobleMiner.MineFight.Util.SyncDerp.EntitySyncCalls;
import TobleMiner.MineFight.Weapon.TickControlled.TickControlledWeapon;
import TobleMiner.MineFightWeapons.Main;
import TobleMiner.MineFightWeapons.Weapons.Definitions.Frag;

public class WpFrag extends TickControlledWeapon
{
	public final Item item;
	public final PVPPlayer owner;
	private final Match match;
	private final Frag frag;
	private int timer = 0;
	private final float fuse;
	
	public WpFrag(Item item, PVPPlayer owner, Match match, Frag frag, float throwSpeed)
	{
		super(match);
		this.match = match;
		this.frag = frag;
		this.item = item;
		this.owner = owner;
		this.fuse = (float)Main.config.frag.getFuseTime(match.getWorld());
		double fact = throwSpeed/item.getVelocity().clone().length();
		item.setVelocity(item.getVelocity().clone().multiply(fact));
	}
	
	public void explode()
	{
		Location loc = item.getLocation();
		EntitySyncCalls.removeEntity(item);
		this.match.createExplosion(owner, loc, Main.config.frag.getBlastPower(this.match.getWorld()), this.getLocName());
		this.unregisterTickControlled();
		frag.remove(this);
	}

	@Override
	public void doUpdate()
	{
		timer++;
		if(timer > (this.fuse * GameEngine.tps))
		{
			this.explode();
		}
	}
	
	public String getLocName()
	{
		return TobleMiner.MineFight.Main.gameEngine.dict.get("frag");
	}
}

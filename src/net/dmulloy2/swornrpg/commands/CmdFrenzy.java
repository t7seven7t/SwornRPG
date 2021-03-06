package net.dmulloy2.swornrpg.commands;

import net.dmulloy2.swornrpg.SwornRPG;
import net.dmulloy2.swornrpg.data.PlayerData;

import org.bukkit.potion.PotionEffectType;

/**
 * @author dmulloy2
 */

public class CmdFrenzy extends SwornRPGCommand
{
	public CmdFrenzy (SwornRPG plugin)
	{
		super(plugin);
		this.name = "frenzy";
		this.description = "Enter Beastmode!";
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform()
	{
		if(!plugin.frenzyenabled)
			return;
		final PlayerData data = getPlayerData(player);
		if (!(data.isFcooldown()))
		{
			sendpMessage("&eEntering frenzy mode!");
			int strength = 0;
			int level = data.getLevel();
			/**Duration = frenzy base duraton + (frenzy multiplier x level)**/
			final int duration = (20*(plugin.frenzyd + (level*plugin.frenzym)));
			player.addPotionEffect(PotionEffectType.SPEED.createEffect((int) duration, strength));
			player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect((int) duration, strength));
			player.addPotionEffect(PotionEffectType.REGENERATION.createEffect((int) duration, strength));
			player.addPotionEffect(PotionEffectType.JUMP.createEffect((int) duration, strength));
			player.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect((int) duration, strength));
			player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect((int) duration, strength));
			if (plugin.debug) plugin.outConsole(player.getName() + " has entered frenzy mode. Duration: " + duration);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
			{
				@Override
				public void run()
				{
					sendpMessage("&eFrenzy mode has worn off");
					
					/**Cooldown = duraton x cooldown multiplier**/
					int cooldown = (20*(duration*plugin.frenzycd));
					data.setFrenzycd(cooldown);
					data.setFcooldown(true);
					if (plugin.debug) plugin.outConsole(player.getName() + "has a cooldown of " + cooldown + " for frenzy");
				}				
			},(duration));
		}
		else
		{
			sendpMessage("&cError, you are still recovering from frenzy");
			sendpMessage("&cYou have " + (data.getFrenzycd()/20) + " seconds left");
		}
	}
}
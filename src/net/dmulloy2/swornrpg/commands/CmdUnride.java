package net.dmulloy2.swornrpg.commands;

import net.dmulloy2.swornrpg.SwornRPG;
import net.dmulloy2.swornrpg.data.PlayerData;
import net.dmulloy2.swornrpg.permissions.PermissionType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class CmdUnride extends SwornRPGCommand
{
	public CmdUnride (SwornRPG plugin)
	{
		super(plugin);
		this.name = "unride";
		this.description = "Get off of a player's head";
		this.permission = PermissionType.CMD_RIDE.permission;
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform()
	{
		if (player.getVehicle() != null)
		{
			Entity target = player.getVehicle();
			if (target instanceof Player)
			{
				Player targetp = (Player)player.getVehicle();
				PlayerData data = getPlayerData(targetp);
				data.setVehicle(false);
			}
			player.leaveVehicle();
			PlayerData data = getPlayerData(player);
			data.setRiding(false);
		}
		else
		{
			sendpMessage("&cError, you are not riding anyone");
		}
	}
}
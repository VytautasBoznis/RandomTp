package lt.skilas.randomtp.actions;

import java.util.HashMap;
import java.util.Random;

import lt.skilas.randomtp.RandomTp;
import lt.skilas.randomtp.configs.Configs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TeleportManager
{
	RandomTp _instance;
	HashMap <Player,Integer> playerTpDistance = new HashMap<Player,Integer>();
	HashMap <Player,Boolean> playerTpPending = new HashMap<Player,Boolean>();
	
	public TeleportManager(RandomTp plugin)
	{
		_instance = plugin;
	}
	
	public boolean teleportPlayer(Player player)
	{
		if(Configs.ASK_FOR_CONFIRM && playerTpPending.containsKey(player))
		{
			player.sendMessage(Configs.CONFIRM_ASK_MESSAGE);
			return false;
		}
		
		double X = player.getLocation().getX();
		double Y = player.getLocation().getY();
		double Z = player.getLocation().getZ();
		World playerWorld = player.getWorld();
		int playerTpDist = 0;
		
		if(playerTpDistance.get(player) == null)
			playerTpDist = Configs.MIN_TP_DISTANCE;
		else if(playerTpDistance.get(player).intValue() < Configs.MIN_TP_DISTANCE && Configs.MIN_TP_DISTANCE_ENABLED)
			playerTpDist = Configs.MIN_TP_DISTANCE;
		else if(playerTpDistance.get(player).intValue() > 0)
			playerTpDist = playerTpDistance.get(player);
		
		Random rnd = new Random();
		
		//moves in any of the 4 directions in a random order
		for(int i = 0; i < playerTpDist;i++)
		{
			int direction = rnd.nextInt(3);
			
			if(direction == 0)
				Z++;
			else if (direction == 1)
				Z--;
			else if (direction == 2)
				X++;
			else if (direction == 3)
				X--;
		}
		
		boolean canTeleport = false;
		//if there is nowhere to stand after the set amount of movement is set  move in any direction till standing ground is found
		while(!canTeleport)
		{
			for(int i=0;i<3;i++)
				if((playerWorld.getBlockAt((int)X,(int) Y-i,(int) Z).getType() == Material.DIRT 
						|| playerWorld.getBlockAt((int)X,(int) Y-i,(int) Z).getType() == Material.GRASS 
						|| playerWorld.getBlockAt((int)X,(int) Y-i,(int) Z).getType() == Material.STONE)&&
						(playerWorld.getBlockAt((int)X, (int) Y, (int) Z).getType() == Material.AIR &&
						playerWorld.getBlockAt((int)X, (int) Y+1, (int) Z).getType() == Material.AIR))
				canTeleport = true;
			
			int direction = rnd.nextInt(3);
			
			if(direction == 0)
				Z++;
			else if (direction == 1)
				Z--;
			else if (direction == 2)
				X++;
			else if (direction == 3)
				X--;
		}
		
		Location newPlayerLocation = new Location(playerWorld,X,Y,Z);
		
		playerTpDistance.remove(player);
		return player.teleport(newPlayerLocation);
	}
	
	public void setTpDistance(Player player,int distance)
	{
		playerTpDistance.put(player, distance);
	}
	
	public void setPendingTp(Player player)
	{
		playerTpPending.put(player, true);
	}
	
}

package lt.skilas.randomtp.listeners;

import lt.skilas.randomtp.RandomTp;
import lt.skilas.randomtp.configs.Configs;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerEventListener implements Listener
{
	RandomTp _instance;
	
	public PlayerEventListener(RandomTp plugin)
	{
		_instance = plugin;
	}
	
	/**
	 * On Interact event triggering.
	 * 
	 * Listens for player interaction with sign.
	 * Once the interaction checks for RandomTp related signs and if they are
	 * Start teleportation process.
	 */
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Block target = event.getClickedBlock();
			
			if(target.getType() == Material.SIGN || target.getType() == Material.SIGN_POST)
			{
				Sign sign = (Sign)event.getClickedBlock().getState();	
				String[] signText = sign.getLines();
				
				if(signText[0].equalsIgnoreCase(Configs.SIGN_LINE_1) 
				&& signText[1].equalsIgnoreCase(Configs.SIGN_LINE_2) 
				&& signText[2].equalsIgnoreCase(Configs.SIGN_LINE_3) 
				&& signText[3].equalsIgnoreCase(Configs.SIGN_LINE_4))
					_instance.tpManager.teleportPlayer(event.getPlayer());
			}
		}
	}
}

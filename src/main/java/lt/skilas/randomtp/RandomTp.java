package lt.skilas.randomtp;

import java.util.logging.Level;

import lt.skilas.randomtp.actions.TeleportManager;
import lt.skilas.randomtp.commands.PlayerCommandExecutor;
import lt.skilas.randomtp.configs.Configs;
import lt.skilas.randomtp.listeners.PlayerEventListener;

import org.bukkit.plugin.java.JavaPlugin;

public class RandomTp extends JavaPlugin
{
	public Configs configs = new Configs(this);
	public PlayerEventListener eventListener = new PlayerEventListener(this);
	public PlayerCommandExecutor cmdExecutor = new PlayerCommandExecutor(this);
	public TeleportManager tpManager = new TeleportManager(this);
	
	/**
	 * Default Bukkit plugin enabling method.
	 * Called on Bukkit load.
	 * 
	 * All classes that need to be loaded go here.
	 * 
	 * This function:
	 * Loads configs.
	 * Loads listeners.
	 * Loads command executors.
	 */
	
	@Override
	public void onEnable()
	{
		getLogger().log(Level.FINE, "RandomTp Starting UP");
		
		//Config loading.
		configs.loadConfigs();
		
		//Listener loading.
		getServer().getPluginManager().registerEvents(eventListener,this);
		
		//Command executor loading. reikia i plugin.yml definint sitas commandas nes bus null pointeris
		getCommand("randomtp").setExecutor(cmdExecutor);
		getCommand("rtp").setExecutor(cmdExecutor);
		
		getLogger().log(Level.FINE, "RandomTp ONLINE");				
	}
	
	/**
	 * Default Bukkit plugin disabling method.
	 * Called on Bukkit close.
	 * 
	 * All closing jobs go here.
	 */
	
	@Override
	public void onDisable()
	{
		getLogger().log(Level.FINE, "RandomTp Turning off");
	}
	
	public Configs getConfigs()
	{
		return configs;
	}
}

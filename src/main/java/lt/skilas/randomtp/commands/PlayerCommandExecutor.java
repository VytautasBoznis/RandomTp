package lt.skilas.randomtp.commands;

import lt.skilas.randomtp.RandomTp;
import lt.skilas.randomtp.configs.Configs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommandExecutor implements CommandExecutor
{
	RandomTp _instance;
	
	public PlayerCommandExecutor(RandomTp plugin)
	{
		_instance = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length < 1)
		{
			sender.sendMessage(Configs.ARGUMENT_NUM_ERROR);
			showHelp(sender,cmd,label,args);
			return true;
		}
		else if(args[0].equalsIgnoreCase("set"))
		{
			setTpDistance(sender,cmd,label,args);
			return true;
		}
		else if(Configs.ASK_FOR_CONFIRM && args[0].equalsIgnoreCase("confirm"))
		{
			confirmTp(sender,cmd,label,args);
			return true;
		}
		else
		{
			sender.sendMessage(Configs.OTHER_ERROR);
			showHelp(sender,cmd,label,args);
			return true;
		}
	}

	private void confirmTp(CommandSender sender, Command cmd, String label,	String[] args)
	{
		if(sender instanceof Player)
		{
			_instance.tpManager.setPendingTp((Player) sender);
			_instance.tpManager.teleportPlayer((Player) sender);
		}
		else
		{
			sender.sendMessage("You cant use this command from the console...");
			showHelp(sender,cmd,label,args);
		}
	}

	public void setTpDistance(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			if(args.length >= 2)
			{
				try
				{
					int distance = Integer.parseInt(args[1]);
					_instance.tpManager.setTpDistance((Player) sender, distance);
					sender.sendMessage("Random teleport distance set to " + distance);
				}
				catch(NumberFormatException e)
				{
					sender.sendMessage(Configs.NOT_A_NUMBER_ERROR);
				}
			}
		}
	}
	
	public void showHelp(CommandSender sender,Command cmd, String label, String[] args)
	{
		sender.sendMessage(Configs.HELP_MESSAGE_1);
		sender.sendMessage(Configs.HELP_MESSAGE_2);
		
		if(!Configs.ASK_FOR_CONFIRM)
			sender.sendMessage(Configs.HELP_MESSAGE_3);
		else
		{
			sender.sendMessage(Configs.HELP_MESSAGE_4);
			sender.sendMessage(Configs.HELP_MESSAGE_5);
		}
	}
}

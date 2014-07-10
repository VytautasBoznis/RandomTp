package lt.skilas.randomtp.configs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import lt.skilas.randomtp.RandomTp;

public class Configs
{
	public static String FOLDER_NAME = "./plugins/RandomTp";
	public static String CONFIG_FILE = "./plugins/RandomTp/RandomTp.properties";
	
	public static boolean MIN_TP_DISTANCE_ENABLED;
	public static int MIN_TP_DISTANCE;
	public static boolean ASK_FOR_CONFIRM;
	public static String SIGN_LINE_1;
	public static String SIGN_LINE_2;
	public static String SIGN_LINE_3;
	public static String SIGN_LINE_4;
	public static String CONFIRM_ASK_MESSAGE;
	public static String HELP_MESSAGE_1;
	public static String HELP_MESSAGE_2;
	public static String HELP_MESSAGE_3;
	public static String HELP_MESSAGE_4;
	public static String HELP_MESSAGE_5;
	public static String ARGUMENT_NUM_ERROR;
	public static String NOT_A_NUMBER_ERROR;
	public static String OTHER_ERROR;
	
	RandomTp _instance;
	
	public Configs(RandomTp plugin)
	{
		_instance = plugin;
		loadConfigs();
		_instance.getLogger().log(Level.FINE, "RandomTp: Configs Loaded!");
	}
	
	public void loadConfigs()
	{
		File directory = new File(FOLDER_NAME);
		File config = new File(CONFIG_FILE);
		
		if(!directory.exists())
			directory.mkdir();
					
		if(!config.exists())
			try
			{
				config.createNewFile();
				writeConfigs();
				readConfigs();
			} 
			catch (IOException e)
			{
				_instance.getLogger().log(Level.SEVERE, "RandomTp: Failed to create config file (and/or) directory");
				e.printStackTrace();
			}
		else
			readConfigs();
			
	}
	
	public void writeConfigs()
	{
		try
		{
			File file = new File(CONFIG_FILE);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			writer.write("#RandomTp Plugin Configuration file V0.1");
			writer.newLine();
			writer.write("#If True there will be a minimum teleportation distance set:");
			writer.newLine();
			writer.write("MinTpDistanceEnabled=false");
			writer.newLine();
			writer.newLine();
			writer.write("#The minimal distance to teleport if enabled:");
			writer.newLine();
			writer.write("MinTpDistance=100");
			writer.newLine();
			writer.newLine();
			writer.write("#Ask for confirmation command before teleport (if disabled teleports on sign interaction)");
			writer.newLine();
			writer.write("AskForConfirmation=true");
			writer.newLine();
			writer.newLine();
			writer.write("#The first lines of the teleportation sign (the lines must mach exactly to work)");
			writer.newLine();
			writer.write("SignLine1Text=-----");
			writer.newLine();
			writer.write("SignLine2Text=RANDOM");
			writer.newLine();
			writer.write("SignLine3Text=TELEPORT");
			writer.newLine();
			writer.write("SignLine4Text=-----");
			writer.newLine();
			writer.newLine();
			writer.write("#Message that will be sent to the player if confirmation is needed for teleporting:");
			writer.newLine();
			writer.write("ConfirmAskMessage=Please use /rtp confirm  command to confirm your teleport request");
			writer.newLine();
			writer.newLine();
			writer.write("#Messages that will be sent if the player uses /rtp help command:");
			writer.newLine();
			writer.write("HelpMessage1=To use the RandomTp plugin you have to:");
			writer.newLine();
			writer.write("HelpMessage2=Set the distance you wish to teleport using /rtp set <distance>");
			writer.newLine();
			writer.newLine();
			writer.write("#if there is no confirmation set:");
			writer.newLine();
			writer.write("HelpMessage3=And hit the random teleport sign");
			writer.newLine();
			writer.newLine();
			writer.write("#if there is a confirmation request set:");
			writer.newLine();
			writer.write("HelpMessage4=Hit the random teleport sign");
			writer.newLine();
			writer.write("HelpMessage5=And confirm the teleportation request with /rtp confirm");
			writer.newLine();
			writer.newLine();
			writer.write("#Error that will be sent to the command user if there is to low or to many arguments given:");
			writer.newLine();
			writer.write("ArgumentNumError=Too (few or many) arguments in command");
			writer.newLine();
			writer.newLine();
			writer.write("#Error that will be sent to the command user if the /rtp set <distance> command gets non numeric distance");
			writer.newLine();
			writer.write("NotANumberError=Not a number given to the /rtp set command");
			writer.newLine();
			writer.newLine();
			writer.write("#Error that will be sent to the command user on every other exception");
			writer.newLine();
			writer.write("OtherError=Something went wrong");
			writer.newLine();		
			
			writer.close();
		} 
		catch (IOException e)
		{
			_instance.getLogger().log(Level.SEVERE, "RandomTp: Failed to write config file");
			e.printStackTrace();
		}
	}
	
	public void readConfigs()
	{
		try
		{
			Properties prop = new Properties();
			InputStream is = new FileInputStream(CONFIG_FILE);
			prop.load(is);
			is.close();
			
			MIN_TP_DISTANCE_ENABLED = Boolean.parseBoolean(prop.getProperty("MinTpDistanceEnabled","false"));
			MIN_TP_DISTANCE = Integer.parseInt(prop.getProperty("MinTpDistance", "100"));
			ASK_FOR_CONFIRM = Boolean.parseBoolean(prop.getProperty("AskForConfirmation", "true"));
			SIGN_LINE_1 = prop.getProperty("SignLine1Text", "-----");
			SIGN_LINE_2 = prop.getProperty("SignLine2Text", "RANDOM");
			SIGN_LINE_3 = prop.getProperty("SignLine3Text", "TELEPORT");
			SIGN_LINE_4 = prop.getProperty("SignLine4Text", "-----");
			CONFIRM_ASK_MESSAGE = prop.getProperty("ConfirmAskMessage", "Please use /rtp confirm  command to confirm your teleport request");
			HELP_MESSAGE_1 = prop.getProperty("HelpMessage1", "To use the RandomTp plugin you have to:");
			HELP_MESSAGE_2 = prop.getProperty("HelpMessage2", "Set the distance you wish to teleport using /rtp set <distance>");
			HELP_MESSAGE_3 = prop.getProperty("HelpMessage3", "And hit the random teleport sign");
			HELP_MESSAGE_4 = prop.getProperty("HelpMessage4", "Hit the random teleport sign");
			HELP_MESSAGE_5 = prop.getProperty("HelpMessage5", "And confirm the teleportation request with /rtp confirm");
			ARGUMENT_NUM_ERROR = prop.getProperty("ArgumentNumError", "Too (few or many) arguments in command");
			NOT_A_NUMBER_ERROR = prop.getProperty("NotANumberError", "Not a number given to the /rtp set command");
			OTHER_ERROR = prop.getProperty("OtherError", "Something went wrong");
			} 
		catch (IOException e)
		{
			_instance.getLogger().log(Level.SEVERE, "RandomTp: Failed to read config file");
			e.printStackTrace();
		}
	}
}

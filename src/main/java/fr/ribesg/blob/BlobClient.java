package fr.ribesg.blob;
import fr.ribesg.alix.api.Client;
import fr.ribesg.alix.api.Server;
import fr.ribesg.alix.api.bot.command.CommandManager;
import fr.ribesg.alix.api.network.ssl.SSLType;
import fr.ribesg.blob.command.bot.JoinCommand;
import fr.ribesg.blob.command.bot.PartCommand;
import fr.ribesg.blob.command.bot.QuitCommand;
import fr.ribesg.blob.command.minecraft.bukkitdev.AuthorCommand;
import fr.ribesg.blob.command.minecraft.bukkitdev.PluginCommand;
import fr.ribesg.blob.command.minecraft.mcstats.GlobalMCStatsCommand;
import fr.ribesg.blob.command.minecraft.mcstats.MCStatsCommand;
import fr.ribesg.blob.command.util.ShortenCommand;

import java.util.HashSet;
import java.util.Set;

public class BlobClient extends Client {

	public BlobClient(final String name) {
		super(name);
	}

	@Override
	protected void load() {
		// EsperNet
		final Server esperNet = new Server(this, "irc.esper.net", 6697, SSLType.TRUSTING);
		esperNet.addChannel("#blob");
		this.getServers().add(esperNet);

		final Set<String> admins = new HashSet<>();
		admins.add("Ribesg");

		this.createCommandManager("+", admins);

		final CommandManager manager = getCommandManager();

		// Minecraft
		manager.registerCommand(new MCStatsCommand(manager));
		manager.registerCommand(new GlobalMCStatsCommand(manager));
		manager.registerCommand(new PluginCommand(manager));
		manager.registerCommand(new AuthorCommand(manager));

		// Bot
		manager.registerCommand(new JoinCommand(manager));
		manager.registerCommand(new PartCommand(manager));
		manager.registerCommand(new QuitCommand(manager, this));

		// Util
		manager.registerCommand(new ShortenCommand(manager));
	}
}

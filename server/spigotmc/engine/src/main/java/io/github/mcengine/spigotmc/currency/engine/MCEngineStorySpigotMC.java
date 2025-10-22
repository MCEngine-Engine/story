package io.github.mcengine.spigotmc.story.engine;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MCEngineStorySpigotMC extends JavaPlugin {
    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        new Metrics(this, 26183);
        saveDefaultConfig(); // Save config.yml if it doesn't exist

        boolean enabled = getConfig().getBoolean("enable", false);
        if (!enabled) {
            getLogger().warning("Plugin is disabled in config.yml (enable: false). Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        String license = getConfig().getString("licenses.license", "free"); 
        if (!license.equalsIgnoreCase("free")) { 
            getLogger().warning("Plugin is disabled in config.yml.");
            getLogger().warning("Invalid license.");
            getLogger().warning("Check license or use \"free\".");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Load extensions
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.story.addon.IMCEngineStoryAddOn",
            "addons",
            "AddOn"
            );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.story.dlc.IMCEngineStoryDLC",
            "dlcs",
            "DLC"
            );

        MCEngineCoreApi.checkUpdate(this, getLogger(), "github", "MCEngine-Engine", "story", getConfig().getString("github.token", "null"));
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {}
}

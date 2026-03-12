package com.howlstudio.titlemanager;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/** TitleManager — Send titles and subtitles to players. Broadcast or target individual players. */
public final class TitleManagerPlugin extends JavaPlugin {
    public TitleManagerPlugin(JavaPluginInit init){super(init);}
    @Override protected void setup(){
        System.out.println("[TitleManager] Loading...");
        TitleManager mgr=new TitleManager();
        CommandManager.get().register(mgr.getTitleCommand());
        CommandManager.get().register(mgr.getBroadcastTitleCommand());
        System.out.println("[TitleManager] Ready.");
    }
    @Override protected void shutdown(){System.out.println("[TitleManager] Stopped.");}
}

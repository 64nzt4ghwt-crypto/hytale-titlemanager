package com.howlstudio.titlemanager;
import com.hypixel.hytale.component.Ref; import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
public class TitleManager {
    private String translate(String s){return s.replace("&0","§0").replace("&1","§1").replace("&2","§2").replace("&3","§3").replace("&4","§4").replace("&5","§5").replace("&6","§6").replace("&7","§7").replace("&8","§8").replace("&9","§9").replace("&a","§a").replace("&b","§b").replace("&c","§c").replace("&d","§d").replace("&e","§e").replace("&f","§f").replace("&l","§l").replace("&o","§o").replace("&r","§r");}
    private void sendTitle(PlayerRef ref, String title, String subtitle){
        // Titles are sent as chat messages in this API (no native title packet visible in API)
        if(!title.isBlank()) ref.sendMessage(Message.raw("§8[Title] "+translate(title)));
        if(!subtitle.isBlank()) ref.sendMessage(Message.raw("§8[Subtitle] "+translate(subtitle)));
    }
    public AbstractPlayerCommand getTitleCommand(){
        return new AbstractPlayerCommand("title","[Admin] Send title to player. /title <player> <title>|<subtitle>"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                String[]args=ctx.getInputString().trim().split("\\s+",2);
                if(args.length<2){playerRef.sendMessage(Message.raw("Usage: /title <player> <title>|<subtitle>"));return;}
                String[]parts=args[1].split("\\|",2);String title=parts[0];String sub=parts.length>1?parts[1]:"";
                PlayerRef target=null;for(PlayerRef p:Universe.get().getPlayers())if(p.getUsername().equalsIgnoreCase(args[0])){target=p;break;}
                if(target==null){playerRef.sendMessage(Message.raw("[Title] Player not found: "+args[0]));return;}
                sendTitle(target,title,sub);playerRef.sendMessage(Message.raw("[Title] Sent to "+target.getUsername()));
            }
        };
    }
    public AbstractPlayerCommand getBroadcastTitleCommand(){
        return new AbstractPlayerCommand("btitle","[Admin] Broadcast title to all. /btitle <title>|<subtitle>"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                String input=ctx.getInputString().trim();if(input.isEmpty()){playerRef.sendMessage(Message.raw("Usage: /btitle <title>|<subtitle>"));return;}
                String[]parts=input.split("\\|",2);String title=parts[0];String sub=parts.length>1?parts[1]:"";
                int count=0;for(PlayerRef p:Universe.get().getPlayers()){sendTitle(p,title,sub);count++;}
                playerRef.sendMessage(Message.raw("[Title] Broadcast to "+count+" players."));
            }
        };
    }
}

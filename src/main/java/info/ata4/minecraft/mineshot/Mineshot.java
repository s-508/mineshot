/*
 ** 2013 April 15
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.mineshot;

import info.ata4.minecraft.mineshot.client.OrthoViewHandler;
import info.ata4.minecraft.mineshot.client.ScreenshotHandler;
import info.ata4.minecraft.mineshot.client.config.MineshotConfig;
import info.ata4.minecraft.mineshot.client.command.HugeScreenshotCommand;
import info.ata4.minecraft.mineshot.client.command.MinimapCommand;
import info.ata4.minecraft.mineshot.client.command.ZoomCommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Mineshot mod container class.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
@Mod(
    modid = Mineshot.ID,
    name = Mineshot.NAME,
    version = Mineshot.VERSION,
    useMetadata = true,
    guiFactory = "info.ata4.minecraft.mineshot.client.config.MineshotConfigGuiFactory"
)
public class Mineshot {
    
    public static final String NAME = "Mineshot";
    public static final String ID = NAME;
    public static final String VERSION = "@VERSION@";
    
    @Instance(ID)
    public static Mineshot instance;
    public static OrthoViewHandler ovh;
    public static ScreenshotHandler sch;
    
    private ModMetadata metadata;
    private MineshotConfig config;
    
    public MineshotConfig getConfig() {
        return config;
    }

    public ModMetadata getMetadata() {
        return metadata;
    }
    
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.modID.equals(ID)) {
            config.update(false);
        }
    }

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent evt) {
        config = new MineshotConfig(new Configuration(evt.getSuggestedConfigurationFile()));
        metadata = evt.getModMetadata();
    }

    @EventHandler
    public void onInit(FMLInitializationEvent evt) {
        sch = new ScreenshotHandler(config);
        MinecraftForge.EVENT_BUS.register(sch);

        ovh = new OrthoViewHandler();
        MinecraftForge.EVENT_BUS.register(ovh);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        ClientCommandHandler.instance.registerCommand(new MinimapCommand(config));
        ClientCommandHandler.instance.registerCommand(new ZoomCommand());
        ClientCommandHandler.instance.registerCommand(new HugeScreenshotCommand(config));
    }
}

package keystrokesmod.client.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.google.common.eventbus.EventBus;

import keystrokesmod.client.clickgui.kv.KvCompactGui;
import keystrokesmod.client.clickgui.raven.ClickGui;
import keystrokesmod.client.clickgui.raven.ModernClickGui;
import keystrokesmod.client.command.CommandManager;
import keystrokesmod.client.config.ConfigManager;
import keystrokesmod.client.event.forge.ForgeEventListener;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.notifications.NotificationRenderer;
import keystrokesmod.client.utils.DebugInfoRenderer;
import keystrokesmod.client.utils.MouseManager;
import keystrokesmod.client.utils.PingChecker;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class Raven {
    public static boolean debugger;
    public static CommandManager commandManager;
    
    public static ConfigManager configManager;
    public static ClientConfig clientConfig;

    public static final ModuleManager moduleManager = new ModuleManager();

    public static ClickGui clickGui;
    public static ModernClickGui modernClickGui;
    public static KvCompactGui kvCompactGui;

    private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);

    public static ResourceLocation mResourceLocation;

    public static final String osName, osArch;
    public static final List<Object> registered = new ArrayList<Object>();
    public static final EventBus eventBus = new EventBus(); 
    public static final Minecraft mc = Minecraft.getMinecraft();

    static {
        osName = System.getProperty("os.name").toLowerCase();
        osArch = System.getProperty("os.arch").toLowerCase();
    }

    public static void init() {
        register(new Raven());
        register(new DebugInfoRenderer());
        register(new MouseManager());
        register(new PingChecker());
        register(new ForgeEventListener());
        eventBus.register(NotificationRenderer.notificationRenderer);

        FontUtil.bootstrap();

        Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));

        // You will eventually change this to point to your gurney logo
        mResourceLocation = RenderUtils.getResourcePath("/assets/keystrokesmod/raven.png");

        commandManager = new CommandManager();
        clickGui = new ClickGui();
        modernClickGui = new ModernClickGui();
        kvCompactGui = new KvCompactGui();
        configManager = new ConfigManager();
        clientConfig = new ClientConfig();
        clientConfig.applyConfig();
    }

    public static void register(Object obj) {
        registered.add(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }

    public static ScheduledExecutorService getExecutor() {
        return ex;
    }
}
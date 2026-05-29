package keystrokesmod.client.module.modules.client;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.Utils;

public class UpdateCheck extends Module {
    public static DescriptionSetting howToUse;

    public UpdateCheck() {
        super("Update", ModuleCategory.client);
        this.registerSetting(howToUse = new DescriptionSetting("Edmann Client is offline."));
    }

    @Subscribe
    public void onGameLoop(GameLoopEvent e) {
        // Completely disabled background thread executions and browser actions
        if (this.isEnabled()) {
            Utils.Player.sendMessageToSelf("Edmann Client is running on the latest permanent local build.");
            this.disable();
        }
    }
}
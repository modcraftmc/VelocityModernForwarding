package be.manugame.velocitymodernforwarding;

import be.manugame.velocitymodernforwarding.config.VelocityModernForwardingConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;

@Mod("velocitymodernforwarding")
public class VelocityModernForwarding {

    private static final Logger LOGGER = LogUtils.getLogger();

    public VelocityModernForwarding() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        VelocityModernForwardingConfig.register();
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
    }

    public void onServerStarted(ServerStartedEvent event) {
        if (VelocityModernForwardingConfig.isEnabled()) {
            if (event.getServer().usesAuthentication())
                LOGGER.error("Your need to disable online-mode in the server.properties files if you want to connect to this server via velocity. Don't forget to setup your firewall correctly");
            if (VelocityModernForwardingConfig.getSecretToken().equals("CHANGEME"))
                LOGGER.error("You need to set the velocity secret token in the mod configuration file (<world-directory>/serverconfig/velocitymodernforwarding-server.conf).");
        }
    }
}

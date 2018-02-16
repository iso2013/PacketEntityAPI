package net.blitzcube.peapi;

import net.blitzcube.peapi.api.IPacketEntityAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by iso2013 on 2/13/2018.
 */
public class PacketEntityAPI implements IPacketEntityAPI {
    private static final String FULL_VERSION = "${project.version}";
    private static final String MAJOR_VERSION = FULL_VERSION.substring(0, FULL_VERSION.indexOf("."));

    public static void initialize(JavaPlugin parent, Consumer<IPacketEntityAPI> onLoad) {
        ServicesManager m = Bukkit.getServicesManager();

        Collection<RegisteredServiceProvider<IPacketEntityAPI>> r = m.getRegistrations(IPacketEntityAPI.class);
        Optional<RegisteredServiceProvider<IPacketEntityAPI>> top = r.stream()
                .filter(p -> MAJOR_VERSION.equals(p.getProvider().getMajorVersion())).findFirst();
        if (top.isPresent()) {
            if (compareVersions(top.get().getProvider().getFullVersion(), FULL_VERSION)) {
                m.unregister(IPacketEntityAPI.class, top.get());
            } else return;
        }
        m.register(IPacketEntityAPI.class, new PacketEntityAPI(), parent, ServicePriority.Normal);

        if (onLoad != null)
            Bukkit.getScheduler().runTask(parent, () -> onLoad.accept(m.getRegistration(IPacketEntityAPI.class)
                    .getProvider()));
    }

    private static boolean compareVersions(String fullVersion, String replaceVersion) {
        String[][] input = new String[][]{fullVersion.split("\\."), replaceVersion.split("\\.")};
        int size = Math.max(input[0].length, input[1].length);
        for (int s = 0; s < size; s++) {
            if (input[0].length - 1 < s) return true;
            if (input[1].length - 1 < s) return false;
            if (input[0][s].equals(input[1][s])) continue;
            return Integer.valueOf(input[0][s]) < Integer.valueOf(input[1][s]);
        }
        return false;
    }

    @Override
    public String getMajorVersion() {
        return MAJOR_VERSION;
    }

    @Override
    public String getFullVersion() {
        return FULL_VERSION;
    }
}

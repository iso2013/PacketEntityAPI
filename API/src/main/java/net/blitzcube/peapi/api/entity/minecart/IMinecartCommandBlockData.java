package net.blitzcube.peapi.api.entity.minecart;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.blitzcube.peapi.api.entity.IMinecartData;

public interface IMinecartCommandBlockData extends IMinecartData {
    String getCommand();

    void setCommand(String value);

    WrappedChatComponent getOutput();

    void setOutput(WrappedChatComponent value);

    void unsetCommand();

    void unsetOutput();

    void resetToDefault();

    void clearAll();
}

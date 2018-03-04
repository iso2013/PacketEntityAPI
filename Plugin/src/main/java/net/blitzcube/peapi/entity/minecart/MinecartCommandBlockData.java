package net.blitzcube.peapi.entity.minecart;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.blitzcube.peapi.api.entity.minecart.IMinecartCommandBlockData;
import net.blitzcube.peapi.entity.MinecartData;

public class MinecartCommandBlockData extends MinecartData implements IMinecartCommandBlockData {
    public String getCommand() {
        return (String) this.dataMap.get(12);
    }

    public void setCommand(String value) {
        if (value == null) {
            unsetCommand();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public WrappedChatComponent getOutput() {
        return (WrappedChatComponent) this.dataMap.get(13);
    }

    public void setOutput(WrappedChatComponent value) {
        if (value == null) {
            unsetOutput();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void unsetCommand() {
        unsetCommand(false);
    }

    public void unsetCommand(boolean restoreDefault) {
        if (restoreDefault) {
            setCommand("");
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetOutput() {
        unsetOutput(false);
    }

    public void unsetOutput(boolean restoreDefault) {
        if (restoreDefault) {
            setOutput(WrappedChatComponent.fromJson("{\"text\":\"\"}"));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetCommand();
        unsetOutput();
    }
}

package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal;

import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.ITameableAnimalData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.AnimalData;
import org.bukkit.entity.*;

import java.util.UUID;

public class TameableAnimalData extends AnimalData implements ITameableAnimalData {
    public Boolean isSitting() {
        return (((Byte) super.dataMap.get(13)) & 0x1) > 0;
    }

    public Boolean isAngry() {
        return (((Byte) super.dataMap.get(13)) & 0x2) > 0;
    }

    public Boolean isTamed() {
        return (((Byte) super.dataMap.get(13)) & 0x4) > 0;
    }

    public Optional<UUID> getOwner() {
        return (Optional<UUID>) this.dataMap.get(14);
    }

    public void setOwner(Optional<UUID> value) {
        if (value == null) {
            unsetOwner();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public void setSitting(boolean value) {
        setBitmask(13, (byte) 0x01, value);
    }

    public void setAngry(boolean value) {
        setBitmask(13, (byte) 0x02, value);
    }

    public void setTamed(boolean value) {
        setBitmask(13, (byte) 0x04, value);
    }

    public void unsetSitting(Entity e) {
        unsetBitmask(13, (byte) 0x01, e != null && ((e instanceof Wolf && ((Wolf) e).isSitting())
                || (e instanceof Parrot && ((Parrot) e).isSitting())
                || (e instanceof Ocelot && ((Ocelot) e).isSitting())));
    }

    public void unsetAngry(Entity e) {
        unsetBitmask(13, (byte) 0x02, e != null && e instanceof Wolf && ((Wolf) e).isAngry());
    }

    public void unsetTamed(Entity e) {
        unsetBitmask(13, (byte) 0x04, e != null && e instanceof Tameable && ((Tameable) e).isTamed());
    }

    public void unsetOwner() {
        unsetOwner(false);
    }

    public void unsetOwner(boolean restoreDefault) {
        if (restoreDefault) {
            setOwner(Optional.absent());
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSitting(null);
        unsetAngry(null);
        unsetTamed(null);
        unsetOwner();
    }
}

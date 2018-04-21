package net.blitzcube.peapi;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.blitzcube.peapi.api.event.IPacketEntityDataEvent;
import net.blitzcube.peapi.api.event.IPacketEntityEvent;
import net.blitzcube.peapi.api.event.IPacketEntitySpawnEvent;
import net.blitzcube.peapi.api.event.IPacketObjectSpawnEvent;
import net.blitzcube.peapi.api.listener.IListener;
import net.blitzcube.peapi.entity.modifier.ModifiableEntity;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public final class PacketEntityAPIPlugin extends JavaPlugin implements Listener {

    private Map<Integer, EntityType> ids = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        PacketEntityAPI.initialize(this, iPacketEntityAPI -> {
            IEntityModifier<Integer> size = iPacketEntityAPI.getModifierRegistry().lookup(EntityType.SLIME, "SIZE");
            IEntityModifier<String> name = iPacketEntityAPI.getModifierRegistry().lookup(EntityType.SLIME,
                    "CUSTOM_NAME");
            IEntityModifier<Boolean> nameVisible = iPacketEntityAPI.getModifierRegistry().lookup(EntityType.SLIME,
                    "CUSTOM_NAME_VISIBLE");
            IEntityModifier<Byte> color = iPacketEntityAPI.getModifierRegistry().lookup(EntityType.SHEEP, "COLOR");
            IEntityModifier<Boolean> burning = iPacketEntityAPI.getModifierRegistry().lookup(EntityType.SHEEP,
                    "BURNING");
            iPacketEntityAPI.addListener(new IListener() {
                @Override
                public ListenerPriority getPriority() {
                    return ListenerPriority.HIGH;
                }

                @Override
                public void onEvent(IPacketEntityEvent e) {
                    if (!((e instanceof IPacketEntityDataEvent) || (e instanceof
                            IPacketEntitySpawnEvent) || (e instanceof IPacketObjectSpawnEvent))) return;
                    List<WrappedWatchableObject> objs;
                    if (e instanceof IPacketEntityDataEvent) {
                        objs = ((IPacketEntityDataEvent) e).getMetadata();
                    } else if (e instanceof IPacketEntitySpawnEvent) {
                        objs = ((IPacketEntitySpawnEvent) e).getMetadata();
                        ids.put(e.getIdentifier().getEntityID(), ((IPacketEntitySpawnEvent) e).getEntityType());
                    } else {
                        ids.put(e.getIdentifier().getEntityID(), ((IPacketObjectSpawnEvent) e).getEntityType());
                        return;
                    }

                    EntityType t = ids.get(e.getIdentifier().getEntityID());
                    IModifiableEntity m = new ModifiableEntity.ListBased(objs);
                    if (t == EntityType.SHEEP) {
                        name.setValue(m, "Yay!");
                        nameVisible.setValue(m, true);
                        color.setValue(m, (byte) DyeColor.PURPLE.ordinal());
                        burning.setValue(m, true);
                    } else if (t == EntityType.SLIME) {
                        size.setValue(m, 100);
                    } else return;
                    if (e instanceof IPacketEntityDataEvent) {
                        ((IPacketEntityDataEvent) e).setMetadata(m.getWatchableObjects());
                    } else {
                        ((IPacketEntitySpawnEvent) e).setMetadata(m.getWatchableObjects());
                    }
                }

                @Override
                public EntityType[] getTargets() {
                    return EntityType.values();
                }
            });
        });
    }

    @Override
    public void onDisable() {

    }
}
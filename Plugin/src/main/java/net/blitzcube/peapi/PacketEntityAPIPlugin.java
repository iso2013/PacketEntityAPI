package net.blitzcube.peapi;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.listener.IListener;
import net.blitzcube.peapi.entity.modifier.ModifiableEntity;
import net.blitzcube.peapi.packet.EntityDataPacket;
import net.blitzcube.peapi.packet.EntitySpawnPacket;
import net.blitzcube.peapi.packet.ObjectSpawnPacket;
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
                public void onEvent(IEntityPacketEvent e) {
                    List<WrappedWatchableObject> objs;
                    switch (e.getPacketType()) {
                        case DATA:
                            objs = ((EntityDataPacket) e.getPacket()).getMetadata();
                            break;
                        case ENTITY_SPAWN:
                            objs = ((EntitySpawnPacket) e.getPacket()).getMetadata();
                            ids.put(e.getPacket().getIdentifier().getEntityID(), ((EntitySpawnPacket) e.getPacket())
                                    .getEntityType());
                            break;
                        case OBJECT_SPAWN:
                            ids.put(e.getPacket().getIdentifier().getEntityID(), ((ObjectSpawnPacket) e.getPacket())
                                    .getEntityType());
                        default:
                            return;
                    }

                    EntityType t = ids.get(e.getPacket().getIdentifier().getEntityID());
                    IModifiableEntity m = new ModifiableEntity.ListBased(objs);
                    if (t == EntityType.SHEEP) {
                        name.setValue(m, "Yay!");
                        nameVisible.setValue(m, true);
                        color.setValue(m, (byte) DyeColor.ORANGE.ordinal());
                        burning.setValue(m, true);
                    } else if (t == EntityType.SLIME) {
                        size.setValue(m, 100);
                    } else return;
                    if (e.getPacketType().equals(IEntityPacketEvent.EntityPacketType.DATA)) {
                        ((EntityDataPacket) e.getPacket()).rewriteMetadata();
                    } else {
                        ((EntitySpawnPacket) e.getPacket()).rewriteMetadata();
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
package net.blitzcube.peapi;

import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.fake.IFakeEntityFactory;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry;
import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.listener.IListener;
import net.blitzcube.peapi.api.packet.IEntityPacket;
import net.blitzcube.peapi.api.packet.IEntityPacketFactory;
import net.blitzcube.peapi.api.packet.IEntityPacketSpawn;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class PacketEntityAPIPlugin extends JavaPlugin implements Listener {
    private final Map<Integer, EntityType> ids = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        PacketEntityAPI.initialize(this, api -> api.addListener(new Demo(api.getModifierRegistry(), api
                .getPacketFactory(), api.getEntityFactory())));
    }

    @Override
    public void onDisable() {

    }

    @SuppressWarnings("unchecked")
    private class Demo implements IListener {
        private final IEntityModifier<String> name;
        private final IEntityModifier<Boolean> nameVisible;
        private final IEntityModifier<Integer> size;
        private final IEntityModifier<Boolean> marker;
        private final IEntityModifier<Boolean> invisible;
        private final IEntityModifier<Boolean> asInvisible;
        private final IEntityModifier<Boolean> noAI;
        private final IEntityModifier<Boolean> silent;

        private final IFakeEntityFactory entity;
        private final IEntityPacketFactory packet;

        Demo(IEntityModifierRegistry reg, IEntityPacketFactory factory, IFakeEntityFactory fakeFactory) {
            this.name = reg.lookup(EntityType.ARMOR_STAND, "CUSTOM_NAME");
            this.nameVisible = reg.lookup(EntityType.ARMOR_STAND, "CUSTOM_NAME_VISIBLE");
            this.size = reg.lookup(EntityType.SLIME, "SIZE");
            this.marker = reg.lookup(EntityType.ARMOR_STAND, "MARKER");
            this.invisible = reg.lookup(EntityType.SILVERFISH, "INVISIBLE");
            this.asInvisible = reg.lookup(EntityType.ARMOR_STAND, "INVISIBLE");
            this.noAI = reg.lookup(EntityType.SILVERFISH, "NO_AI");
            this.silent = reg.lookup(EntityType.SILVERFISH, "SILENT");

            this.entity = fakeFactory;
            this.packet = factory;
        }

        @Override
        public ListenerPriority getPriority() {
            return ListenerPriority.NORMAL;
        }

        @Override
        public void onEvent(IEntityPacketEvent e) {
            if (e.getPacket() instanceof IEntityPacketSpawn) {
                IFakeEntity sf1 = entity.createFakeEntity(EntityType.SILVERFISH);
                IFakeEntity sf2 = entity.createFakeEntity(EntityType.SILVERFISH);
                IFakeEntity sf3 = entity.createFakeEntity(EntityType.SILVERFISH);
                IFakeEntity slime = entity.createFakeEntity(EntityType.SLIME);
                IFakeEntity armorStand1 = entity.createFakeEntity(EntityType.ARMOR_STAND);
                IFakeEntity armorStand2 = entity.createFakeEntity(EntityType.ARMOR_STAND);

                sf1.setLocation(((IEntityPacketSpawn) e.getPacket()).getLocation().clone().add(new Vector(0, 1.5, 0)));
                sf2.setLocation(((IEntityPacketSpawn) e.getPacket()).getLocation().clone().add(new Vector(0, 1.5, 0)));
                sf3.setLocation(((IEntityPacketSpawn) e.getPacket()).getLocation().clone().add(new Vector(0, 1.5, 0)));
                slime.setLocation(((IEntityPacketSpawn) e.getPacket()).getLocation().clone().add(new Vector(0, 1.5,
                        0)));
                armorStand1.setLocation(((IEntityPacketSpawn) e.getPacket()).getLocation().clone().add(new Vector(0,
                        1.5, 0)));
                armorStand2.setLocation(((IEntityPacketSpawn) e.getPacket()).getLocation().clone().add(new Vector(0,
                        1.5, 0)));

                //These are commented out because debugging:
                invisible.setValue(sf1.getModifiableEntity(), true);
                invisible.setValue(sf2.getModifiableEntity(), true);
                invisible.setValue(sf3.getModifiableEntity(), true);
                invisible.setValue(slime.getModifiableEntity(), true);
                asInvisible.setValue(armorStand1.getModifiableEntity(), true);
                asInvisible.setValue(armorStand2.getModifiableEntity(), true);

                silent.setValue(sf1.getModifiableEntity(), true);
                silent.setValue(sf2.getModifiableEntity(), true);
                silent.setValue(sf3.getModifiableEntity(), true);
                noAI.setValue(sf1.getModifiableEntity(), true);
                noAI.setValue(sf2.getModifiableEntity(), true);
                noAI.setValue(sf3.getModifiableEntity(), true);

                name.setValue(armorStand1.getModifiableEntity(), "This is a test!");
                name.setValue(armorStand2.getModifiableEntity(), "Welcome to MLAPI!");

                nameVisible.setValue(armorStand1.getModifiableEntity(), true);
                nameVisible.setValue(armorStand2.getModifiableEntity(), true);

                marker.setValue(armorStand1.getModifiableEntity(), true);
                marker.setValue(armorStand2.getModifiableEntity(), true);

                size.setValue(slime.getModifiableEntity(), -1);

                IEntityPacket[] as1packets = packet.createObjectSpawnPacket(armorStand1.getIdentifier());
                IEntityPacket[] as2packets = packet.createObjectSpawnPacket(armorStand2.getIdentifier());

                e.context().queueDispatch(
                        packet.createEntitySpawnPacket(sf1.getIdentifier()),
                        packet.createEntitySpawnPacket(sf2.getIdentifier()),
                        packet.createEntitySpawnPacket(sf3.getIdentifier()),
                        packet.createEntitySpawnPacket(slime.getIdentifier()),
                        as1packets[0],
                        as2packets[0]
                ).queueDispatch(
                        as1packets[1],
                        as2packets[1]
                ).queueDispatch(new IEntityPacket[]{
                        packet.createMountPacket(sf1.getIdentifier(), armorStand1.getIdentifier()),
                        packet.createMountPacket(sf2.getIdentifier(), sf1.getIdentifier()),
                        packet.createMountPacket(slime.getIdentifier(), sf2.getIdentifier()),
                        packet.createMountPacket(armorStand2.getIdentifier(), slime.getIdentifier()),
                        packet.createMountPacket(sf3.getIdentifier(), armorStand2.getIdentifier()),
                        packet.createMountPacket(e.getPacket().getIdentifier(), sf3.getIdentifier())
                }, 1);
            }
        }

        @Override
        public EntityType[] getTargets() {
            return EntityType.values();
        }
    }
}
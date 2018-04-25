package net.blitzcube.peapi.packet;

import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.packet.IEntityAnimationPacket;
import net.blitzcube.peapi.api.packet.IEntityClickPacket;
import net.blitzcube.peapi.api.packet.IEntityPacket;
import net.blitzcube.peapi.api.packet.IEntityPacketFactory;
import org.bukkit.Art;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 4/23/2018.
 */
public class EntityPacketFactory implements IEntityPacketFactory {
    @Override
    public IEntityPacket createAnimationPacket(IEntityIdentifier entity, IEntityAnimationPacket.AnimationType type) {
        EntityPacket p = new EntityAnimationPacket(entity);
        ((EntityAnimationPacket) p).setAnimation(type);
        return p;
    }

    @Override
    public IEntityPacket createClickPacket(IEntityIdentifier entity, IEntityClickPacket.ClickType type) {
        EntityClickPacket p = new EntityClickPacket(entity);
        p.setClickType(type);
        return p;
    }

    @Override
    public IEntityPacket createDataPacket(IEntityIdentifier entity) {
        EntityPacket p = new EntityDataPacket(entity);
        IFakeEntity f;
        if (entity.isFakeEntity() && (f = entity.getFakeEntity().get()) != null) {
            ((EntityDataPacket) p).setMetadata(f.getModifiableEntity().getWatchableObjects());
        }
        return p;
    }

    @Override
    public IEntityPacket createDestroyPacket(IEntityIdentifier... entities) {
        EntityDestroyPacket p = new EntityDestroyPacket();
        for (IEntityIdentifier i : entities) p.addToGroup(i);
        p.apply();
        return p;
    }

    @Override
    public IEntityPacket[] createEquipmentPacket(IEntityIdentifier entity, EntityEquipment equipment) {
        EntityEquipmentPacket[] packets = new EntityEquipmentPacket[6];
        for (int i = 0; i < 6; i++) packets[i] = new EntityEquipmentPacket(entity);
        //Better way to do this?
        packets[0].setItem(equipment.getItemInMainHand());
        packets[0].setSlot(EquipmentSlot.HAND);
        packets[1].setItem(equipment.getItemInOffHand());
        packets[1].setSlot(EquipmentSlot.OFF_HAND);
        packets[2].setItem(equipment.getHelmet());
        packets[2].setSlot(EquipmentSlot.HEAD);
        packets[3].setItem(equipment.getChestplate());
        packets[3].setSlot(EquipmentSlot.CHEST);
        packets[4].setItem(equipment.getLeggings());
        packets[4].setSlot(EquipmentSlot.LEGS);
        packets[5].setItem(equipment.getBoots());
        packets[5].setSlot(EquipmentSlot.FEET);
        return packets;
    }

    @Override
    public IEntityPacket createEquipmentPacket(IEntityIdentifier entity, EquipmentSlot slot, ItemStack item) {
        EntityEquipmentPacket p = new EntityEquipmentPacket(entity);
        //Better way to do this?
        p.setItem(item);
        p.setSlot(slot);
        return p;
    }

    @Override
    public IEntityPacket createMountPacket(IEntityIdentifier vehicle, IEntityIdentifier... passengers) {
        EntityMountPacket p = new EntityMountPacket(vehicle);
        for (IEntityIdentifier passenger : passengers) p.addToGroup(passenger);
        p.apply();
        return p;
    }

    @Override
    public IEntityPacket createEntitySpawnPacket(IEntityIdentifier i) {
        if (i.isFakeEntity()) {
            IFakeEntity f;
            if ((f = i.getFakeEntity().get()) == null) return null;
            if (PacketEntityAPI.OBJECTS.containsKey(f.getType())) {
                throw new IllegalArgumentException("Tried to spawn an object with an entity packet!");
            } else {
                EntitySpawnPacket p = new EntitySpawnPacket(i);
                p.setEntityType(f.getType());
                p.setLocation(f.getLocation());
                p.setVelocity(new Vector(0, 0, 0));
                p.setMetadata(f.getModifiableEntity().getWatchableObjects());
                return p;
            }
        } else {
            if (i.getEntity() == null) i.moreSpecific();
            Entity e;
            if (i.getEntity() == null || (e = i.getEntity().get()) == null) return null;
            if (PacketEntityAPI.OBJECTS.containsKey(e.getType())) {
                throw new IllegalArgumentException("Tried to spawn an object with an entity packet!");
            } else {
                EntitySpawnPacket p = new EntitySpawnPacket(i);
                p.setEntityType(e.getType());
                p.setLocation(e.getLocation());
                p.setVelocity(e.getVelocity());
                return p;
            }
        }
    }

    @Override
    public IEntityPacket[] createObjectSpawnPacket(IEntityIdentifier identifier) {
        boolean fake = false;
        IFakeEntity f = null;
        Entity e = null;
        EntityType t;
        if (identifier.isFakeEntity()) {
            fake = true;
            if ((f = identifier.getFakeEntity().get()) == null) return null;
            t = f.getType();
        } else {
            if (identifier.getEntity() == null) identifier.moreSpecific();
            if (identifier.getEntity() == null || (e = identifier.getEntity().get()) == null) return null;
            t = e.getType();
        }
        if (!PacketEntityAPI.OBJECTS.containsKey(t))
            throw new IllegalStateException("Tried to spawn an entity with an object packet!");
        ObjectSpawnPacket p = new ObjectSpawnPacket(identifier, t);
        p.setEntityType(t);
        p.setLocation(fake ? f.getLocation() : e.getLocation());
        if (t.equals(EntityType.PAINTING)) {
            if (fake && !f.hasField("title")) throw new IllegalStateException("A title has not been specified!");
            p.setTitle(nameFromArt(fake ? (Art) f.getField("title") : ((Painting) e).getArt()));
            if (fake && !f.hasField("direction")) throw new IllegalStateException("A direction has not been " +
                    "specified!");
            p.setDirection(fake ? (BlockFace) f.getField("direction") : ((Painting) e).getFacing());
        } else if (t.equals(EntityType.LIGHTNING) || t.equals(EntityType.EXPERIENCE_ORB)) {
            if (!fake) throw new IllegalStateException("Do not use the #createObjectSpawnPacket for lightning or " +
                    "experience orb entities!");
            if (t.equals(EntityType.EXPERIENCE_ORB)) {
                if (!f.hasField("orbcount")) throw new IllegalStateException("Cannot create an experience orb " +
                        "summon packet without an orbcount specified!");
                p.setOrbCount((Integer) f.getField("orbcount"));
            }
        } else {
            p.setVelocity(fake ?
                    (f.hasField("velocity") ?
                            (Vector) f.getField("velocity") :
                            new Vector(0, 0, 0)) :
                    e.getVelocity()
            );
            p.setData(getData(fake, f, e, t));
        }
        if (fake) {
            EntityDataPacket d = new EntityDataPacket(identifier);
            d.setMetadata(f.getModifiableEntity().getWatchableObjects());
            return new IEntityPacket[]{p, d};
        } else {
            return new IEntityPacket[]{p};
        }
    }

    @SuppressWarnings("deprecation")
    private int getData(boolean fake, IFakeEntity f, Entity e, EntityType t) {
        switch (t) {
            case ARROW:
            case TIPPED_ARROW:
            case SPECTRAL_ARROW:
            case FIREBALL:
            case SMALL_FIREBALL:
            case DRAGON_FIREBALL:
            case WITHER_SKULL:
                if (fake) {
                    return f.hasField("shooter") ? (int) f.getField("shooter") : 0;
                } else {
                    ProjectileSource s = ((Projectile) e).getShooter();
                    if (!(s instanceof Entity)) return 0;
                    return ((Entity) s).getEntityId();
                }
            case FALLING_BLOCK:
                int type = fake ? (int) f.getField("id") : ((FallingBlock) e).getBlockId();
                int data = fake ? (int) f.getField("data") : ((FallingBlock) e).getBlockData();
                return type | (data << 12);
            case DROPPED_ITEM:
                return 1;
            case LLAMA_SPIT:
                return 0;
            case ITEM_FRAME:
                switch (fake ? (BlockFace) f.getField("direction") : ((ItemFrame) e).getFacing()) {
                    case WEST:
                        return 1;
                    case NORTH:
                        return 2;
                    case EAST:
                        return 3;
                    default:
                        return 0;
                }
            case MINECART:
                return 0;
            case MINECART_CHEST:
                return 1;
            case MINECART_FURNACE:
                return 2;
            case MINECART_TNT:
                return 3;
            case MINECART_MOB_SPAWNER:
                return 4;
            case MINECART_HOPPER:
                return 5;
            case MINECART_COMMAND:
                return 6;
        }
        if (fake) {
            return f.hasField("velocity") ? 1 : 0;
        } else {
            return 1;
        }
    }

    private String nameFromArt(Art art) {
        switch (art) {
            case KEBAB:
                return "Kebab";
            case AZTEC:
                return "Aztec";
            case ALBAN:
                return "Alban";
            case AZTEC2:
                return "Aztec2";
            case BOMB:
                return "Bomb";
            case PLANT:
                return "Plant";
            case WASTELAND:
                return "Wasteland";
            case POOL:
                return "Pool";
            case COURBET:
                return "Courbet";
            case SEA:
                return "Sea";
            case SUNSET:
                return "Sunset";
            case CREEBET:
                return "Creebet";
            case WANDERER:
                return "Wanderer";
            case GRAHAM:
                return "Graham";
            case MATCH:
                return "Match";
            case BUST:
                return "Bust";
            case STAGE:
                return "Stage";
            case VOID:
                return "Void";
            case SKULL_AND_ROSES:
                return "SkullAndRoses";
            case WITHER:
                return "Wither";
            case FIGHTERS:
                return "Fighters";
            case POINTER:
                return "Pointer";
            case PIGSCENE:
                return "Pigscene";
            case BURNINGSKULL:
                return "BurningSkull";
            case SKELETON:
                return "Skeleton";
            case DONKEYKONG:
                return "DonkeyKong";
        }
        return null;
    }

    @Override
    public IEntityPacket createStatusPacket(IEntityIdentifier identifier, byte status) {
        EntityStatusPacket p = new EntityStatusPacket(identifier);
        p.setStatus(status);
        return p;
    }
}

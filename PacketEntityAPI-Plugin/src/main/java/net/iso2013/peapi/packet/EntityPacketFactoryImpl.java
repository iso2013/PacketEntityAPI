package net.iso2013.peapi.packet;

import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.entity.RealEntityIdentifier;
import net.iso2013.peapi.api.entity.fake.FakeEntity;
import net.iso2013.peapi.api.packet.*;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import net.iso2013.peapi.util.EntityTypeUtil;
import net.iso2013.peapi.util.ReflectUtil;
import org.bukkit.Art;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by iso2013 on 4/23/2018.
 */
public class EntityPacketFactoryImpl implements EntityPacketFactory {
    private static final Class<?> cbBlockData;
    private static final Method getCombinedID;
    private static final Method getState;

    static {
        cbBlockData = ReflectUtil.getCBClass("CraftBlockData");
        Class<?> nmsBD = ReflectUtil.getNMSClass("Block");
        getCombinedID = ReflectUtil.getMethod(nmsBD, "getCombinedId");
        getState = ReflectUtil.getMethod(cbBlockData, "getState");
    }


    @Override
    public EntityAnimationPacket createAnimationPacket(EntityIdentifier entity, EntityAnimationPacket
            .AnimationType type) {
        EntityAnimationPacketImpl p = new EntityAnimationPacketImpl(entity);
        p.setAnimation(type);
        return p;
    }

    @Override
    public EntityClickPacket createClickPacket(EntityIdentifier entity, EntityClickPacket.ClickType type) {
        EntityClickPacketImpl p = new EntityClickPacketImpl(entity);
        p.setClickType(type);
        return p;
    }

    @Override
    public EntityDataPacket createDataPacket(EntityIdentifier entity) {
        EntityDataPacketImpl p = new EntityDataPacketImpl(entity);
        if (entity instanceof FakeEntity) {
            p.setMetadata(((FakeEntity) entity).getWatchableObjects());
        }
        return p;
    }

    @Override
    public EntityDestroyPacket createDestroyPacket(EntityIdentifier... entities) {
        EntityDestroyPacketImpl p = new EntityDestroyPacketImpl();
        for (EntityIdentifier i : entities) p.addToGroup(i);
        p.apply();
        return p;
    }

    @Override
    public EntityEquipmentPacket[] createEquipmentPacket(EntityIdentifier entity, EntityEquipment equipment) {
        EntityEquipmentPacketImpl[] packets = new EntityEquipmentPacketImpl[6];
        for (int i = 0; i < 6; i++) packets[i] = new EntityEquipmentPacketImpl(entity);
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
    public EntityEquipmentPacket createEquipmentPacket(EntityIdentifier entity, EquipmentSlot slot, ItemStack item) {
        EntityEquipmentPacketImpl p = new EntityEquipmentPacketImpl(entity);
        //Better way to do this?
        p.setItem(item);
        p.setSlot(slot);
        return p;
    }

    @Override
    public EntityMountPacket createMountPacket(EntityIdentifier vehicle, EntityIdentifier... passengers) {
        EntityMountPacketImpl p = new EntityMountPacketImpl(vehicle);
        for (EntityIdentifier passenger : passengers) p.addToGroup(passenger);
        p.apply();
        return p;
    }

    @Override
    public EntitySpawnPacket createEntitySpawnPacket(EntityIdentifier i) {
        if (i instanceof FakeEntity) {
            FakeEntity f = (FakeEntity) i;
            if (EntityTypeUtil.isObject(f.getType())) {
                throw new IllegalArgumentException("Tried to spawn an object with an entity packet!");
            } else {
                EntitySpawnPacketImpl p = new EntitySpawnPacketImpl(i);
                p.setEntityType(f.getType());
                p.setLocation(f.getLocation());
                p.setVelocity(new Vector(0, 0, 0));
                p.setMetadata(f.getWatchableObjects());
                return p;
            }
        } else if (i instanceof RealEntityIdentifier) {
            Entity e = ((EntityIdentifierImpl.RealEntityIdentifier) i).getEntity();
            if (EntityTypeUtil.isObject(e.getType())) {
                throw new IllegalArgumentException("Tried to spawn an object with an entity packet!");
            } else {
                EntitySpawnPacketImpl p = new EntitySpawnPacketImpl(i);
                p.setEntityType(e.getType());
                p.setLocation(e.getLocation());
                p.setVelocity(e.getVelocity());
                return p;
            }
        } else {
            throw new IllegalArgumentException("Cannot spawn an unknown entity!");
        }
    }

    @Override
    public EntityPacket[] createObjectSpawnPacket(EntityIdentifier identifier) {
        boolean fake = identifier instanceof FakeEntity;
        FakeEntity f = fake ? (FakeEntity) identifier : null;
        Entity e = identifier instanceof RealEntityIdentifier ? ((RealEntityIdentifier) identifier).getEntity() :
                null;
        if (!fake && e == null) return null;
        EntityType t = fake ? f.getType() : e.getType();

        if (!EntityTypeUtil.isObject(t))
            throw new IllegalStateException("Tried to spawn an entity with an object packet!");
        ObjectSpawnPacketImpl p = new ObjectSpawnPacketImpl(identifier, t);

        p.setEntityType(t);
        p.setLocation(fake ? f.getLocation() : e.getLocation());

        if (t.equals(EntityType.PAINTING)) {
            if (fake && !f.hasField("title")) throw new IllegalStateException("A title has not been specified!");
            p.setArt((fake ? (Art) f.getField("art") : ((Painting) e).getArt()));
            if (fake && !f.hasField("direction")) throw new IllegalStateException("A direction has not been " +
                    "specified!");
            p.setDirection(fake ? (BlockFace) f.getField("direction") : e.getFacing());
        } else if (t.equals(EntityType.LIGHTNING) || t.equals(EntityType.EXPERIENCE_ORB)) {
            if (!fake) throw new IllegalStateException("Do not use the #createObjectSpawnPacket for lightning or " +
                    "experience orb entities!");
            if (t.equals(EntityType.EXPERIENCE_ORB)) {
                if (!f.hasField("orbCount")) throw new IllegalStateException("Cannot create an experience orb " +
                        "summon packet without an orbCount specified!");
                p.setOrbCount((Integer) f.getField("orbCount"));
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
            EntityDataPacketImpl d = new EntityDataPacketImpl(identifier);
            d.setMetadata(f.getWatchableObjects());
            return new EntityPacket[]{p, d};
        } else {
            return new EntityPacket[]{p};
        }
    }

    @SuppressWarnings("deprecation")
    private int getData(boolean fake, FakeEntity f, Entity e, EntityType t) {
        if (t.name().equals("TIPPED_ARROW")) {
            if (fake) {
                return f.hasField("shooter") ? (int) f.getField("shooter") : 0;
            } else {
                ProjectileSource s = ((Projectile) e).getShooter();
                if (!(s instanceof Entity)) return 0;
                return ((Entity) s).getEntityId();
            }
        }
        switch (t) {
            case ARROW:
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
                BlockData bd = fake ? (BlockData) f.getField("blockdata") : ((FallingBlock) e).getBlockData();
                if (getState != null) {
                    try {
                        return (int) getCombinedID.invoke(null, getState.invoke(cbBlockData.cast(bd)));
                    } catch (IllegalAccessException | InvocationTargetException ignored) {
                    }
                }

                return bd.getMaterial().getId();
            case DROPPED_ITEM:
            case MINECART_CHEST:
                return 1;
            case LLAMA_SPIT:
            case MINECART:
                return 0;
            case ITEM_FRAME:
                switch (fake ? (BlockFace) f.getField("direction") : e.getFacing()) {
                    case DOWN:
                        return 0;
                    case UP:
                        return 1;
                    case NORTH:
                        return 2;
                    case SOUTH:
                        return 3;
                    case WEST:
                        return 4;
                    case EAST:
                        return 5;
                }
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

    @Override
    public EntityStatusPacket createStatusPacket(EntityIdentifier identifier, byte status) {
        EntityStatusPacketImpl p = new EntityStatusPacketImpl(identifier);
        p.setStatus(status);
        return p;
    }

    @Override
    public EntityPotionAddPacket createEffectAddPacket(EntityIdentifier identifier, PotionEffect effect) {
        EntityPotionAddPacketImpl p = new EntityPotionAddPacketImpl(identifier);
        p.setEffect(effect);
        return p;
    }

    @Override
    public EntityPotionRemovePacket createEffectRemovePacket(EntityIdentifier identifier, PotionEffectType type) {
        EntityPotionRemovePacketImpl p = new EntityPotionRemovePacketImpl(identifier);
        p.setEffectType(type);
        return p;
    }

    @Override
    public EntityMovePacket createMovePacket(EntityIdentifier identifier, Vector location, Vector direction,
                                             boolean onGround, EntityMovePacket.MoveType type) {
        EntityMovePacketImpl p = new EntityMovePacketImpl(identifier, type);
        if (location != null && type != EntityMovePacket.MoveType.LOOK)
            p.setNewPosition(location, type == EntityMovePacket.MoveType.TELEPORT);
        if (direction != null && type != EntityMovePacket.MoveType.LOOK) p.setNewDirection(direction);
        p.setOnGround(onGround);
        return p;
    }
}

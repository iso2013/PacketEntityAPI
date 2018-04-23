package net.blitzcube.peapi.entity.fake;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.blitzcube.peapi.entity.EntityIdentifier;
import net.blitzcube.peapi.entity.hitbox.Hitbox;
import net.blitzcube.peapi.entity.modifier.ModifiableEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class FakeEntity implements IFakeEntity {
    private final int id;
    private final UUID uuid;
    private final EntityType type;
    private final EntityIdentifier identifier;
    private Location location;
    private IHitbox hitbox;
    private IModifiableEntity modifiableEntity;
    private Map<String, IEntityModifier> modifiers;
    private Map<String, Object> fields;
    private BiFunction<Player, IFakeEntity, Boolean> checkIntersect;

    FakeEntity(int id, UUID uuid, EntityType type, Map<String, IEntityModifier> modifiers) {
        this.id = id;
        this.uuid = uuid;
        this.type = type;
        this.identifier = new EntityIdentifier(this);
        this.modifiers = modifiers;
        this.hitbox = Hitbox.getByType(type);
        this.modifiableEntity = new ModifiableEntity.WatcherBased(new WrappedDataWatcher());
        for (IEntityModifier m : modifiers.values()) m.unsetValue(modifiableEntity, true);
        this.checkIntersect = (p, e) -> e.getHitbox().intersects(
                p.getEyeLocation().toVector(),
                p.getEyeLocation().getDirection(),
                location.toVector()
        );
    }

    @Override
    public int getEntityID() {
        return id;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public EntityType getType() {
        return type;
    }

    @Override
    public IHitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void setHitbox(IHitbox hitbox) {
        this.hitbox = hitbox;
    }

    @Override
    public IEntityIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public IModifiableEntity getModifiableEntity() {
        return modifiableEntity;
    }

    @Override
    public Map<String, IEntityModifier> getModifiers() {
        return modifiers;
    }

    @Override
    public boolean checkIntersect(Player target) {
        return checkIntersect.apply(target, this);
    }

    @Override
    public void setCheckIntersect(BiFunction<Player, IFakeEntity, Boolean> checkIntersect) {
        this.checkIntersect = checkIntersect;
    }

    @Override
    public Object getField(String name) {
        return fields.get(name);
    }

    @Override
    public void setField(String name, Object value) {
        fields.put(name, value);
    }

    @Override
    public boolean hasField(String name) {
        return fields.containsKey(name);
    }
}

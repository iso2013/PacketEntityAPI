package net.blitzcube.peapi.entity.fake;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.blitzcube.peapi.entity.hitbox.Hitbox;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import net.blitzcube.peapi.entity.modifier.ModifiableEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Map;
import java.util.UUID;

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

    FakeEntity(int id, UUID uuid, EntityType type, Map<String, IEntityModifier> modifiers) {
        this.id = id;
        this.uuid = uuid;
        this.type = type;
        this.identifier = new EntityIdentifier(this);
        this.modifiers = modifiers;
        this.hitbox = Hitbox.getByType(type);
        this.modifiableEntity = new ModifiableEntity.WatcherBased(new WrappedDataWatcher());
        for (IEntityModifier m : modifiers.values()) m.unsetValue(modifiableEntity, true);
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
}

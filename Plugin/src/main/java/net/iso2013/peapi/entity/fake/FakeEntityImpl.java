package net.iso2013.peapi.entity.fake;

import net.iso2013.peapi.api.entity.fake.FakeEntity;
import net.iso2013.peapi.api.entity.hitbox.Hitbox;
import net.iso2013.peapi.api.entity.modifier.EntityModifier;
import net.iso2013.peapi.entity.hitbox.HitboxImpl;
import net.iso2013.peapi.entity.modifier.ModifiableEntityImpl;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class FakeEntityImpl extends ModifiableEntityImpl.ListBased implements FakeEntity {
    private final int id;
    private final UUID uuid;
    private final EntityType type;
    private final Map<String, EntityModifier> modifiers;
    private final Map<String, Object> fields;
    private Location location;
    private Hitbox hitbox;
    private BiFunction<Player, FakeEntity, Boolean> checkIntersect;

    FakeEntityImpl(int id, UUID uuid, EntityType type, Map<String, EntityModifier> modifiers) {
        super(new ArrayList<>());
        this.id = id;
        this.uuid = uuid;
        this.type = type;
        this.modifiers = modifiers;
        this.fields = new HashMap<>();
        this.hitbox = HitboxImpl.getByType(type);
        for (EntityModifier m : modifiers.values()) m.unsetValue(this, true);
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
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    @Override
    public Map<String, EntityModifier> getModifiers() {
        return modifiers;
    }

    @Override
    public boolean checkIntersect(Player target) {
        return checkIntersect.apply(target, this);
    }

    @Override
    public void setCheckIntersect(BiFunction<Player, FakeEntity, Boolean> checkIntersect) {
        this.checkIntersect = checkIntersect;
    }

    @Override
    public Object getField(String name) {
        return fields.get(name.toLowerCase());
    }

    @Override
    public void setField(String name, Object value) {
        fields.put(name.toLowerCase(), value);
    }

    @Override
    public boolean hasField(String name) {
        return fields.containsKey(name.toLowerCase());
    }
}

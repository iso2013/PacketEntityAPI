package net.blitzcube.peapi.api.entity.fake;

import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-23
 */
public interface IFakeEntity {
    /**
     * Gets the entity ID that was assigned to this fake entity.
     *
     * @return the integer entity ID
     */
    int getEntityID();

    /**
     * Gets the universally unique ID that was assigned to this fake entity.
     *
     * @return the {@link UUID} assigned to this entity
     */
    UUID getUUID();

    /**
     * Gets the location that this entity exists at. This location is used for the spawn packet and hitbox calculations.
     *
     * @return the {@link Location} that this entity exists at
     */
    Location getLocation();

    /**
     * Sets the location that this entity exists at. This is used for the spawn packet and hitbox calculations.
     *
     * @param location the new {@link Location} to spawn at
     */
    void setLocation(Location location);

    /**
     * Gets the type of this entity. This cannot be modified after creation for technical reasons.
     * @return the {@link EntityType} of this entity
     */
    EntityType getType();

    /**
     * Gets the hitbox of this entity.
     * @return the hitbox of this entity
     */
    IHitbox getHitbox();

    /**
     * Sets the hitbox of this entity. This can be used to change the apparent 'size' of the entity's click boundary.
     * Changing this to a smaller box will result in the original area of the large box being a dead zone, where
     * clicking does nothing. When changing to a larger box, the new area can only be interacted with by using the
     * attack action.
     * <br>
     * Hitboxes can be generated through the {@link IFakeEntityFactory} interface. An instance of this factory should be
     * obtained from the {@link net.blitzcube.peapi.api.IPacketEntityAPI} root instance.
     * @param value the new hitbox to use for this entity
     */
    void setHitbox(IHitbox value);

    /**
     * Gets the identifier of this entity. Use this identifier when spawning packets through a
     * {@link net.blitzcube.peapi.api.packet.IEntityPacketFactory}. An instance of this factory should be obtained from
     * the {@link net.blitzcube.peapi.api.IPacketEntityAPI} root instance.
     * @return the identifier that corresponds to this entity
     */
    IEntityIdentifier getIdentifier();

    /**
     * Gets the modifiable entity that is tied to this entity. Use this in coordination with {@link IEntityModifier}s to
     * change entity metadata - such as name, invisibility, size, color, etc. Modifier instances should be obtained from
     * the {@link net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry}, which is obtained from the
     * {@link net.blitzcube.peapi.api.IPacketEntityAPI} root instance. <b>Note: these modifiers should be CACHED.</b>
     * <br>
     * Modifers can also be obtained from {@link #getModifiers()}.
     * @return the modifiable entity reference that is tied to this entity
     */
    IModifiableEntity getModifiableEntity();

    /**
     * Gets all of the modifiers that can be applied to this fake entity, given the entity type. These
     * @return a map of all of the valid modifiers
     */
    Map<String, IEntityModifier> getModifiers();

    /**
     * Checks whether or not the {@link Player} provided is intersecting the fake entity. This is used by the
     * PacketEntityAPI engine to allow for the clicking of fake entities. To override the function used to check this,
     * see {@link #setCheckIntersect(BiFunction)}
     * @param target the player to test
     * @return whether or not the player is intersecting the fake entity
     */
    boolean checkIntersect(Player target);

    /**
     * Overrides the function that is used to check if a player is intersecting with a fake entity's hitbox.
     * @param checkIntersect the new function to use during these checks
     */
    void setCheckIntersect(BiFunction<Player, IFakeEntity, Boolean> checkIntersect);

    /**
     * Gets a parameter that has been previously set for the spawning of this object. For examples of titles, see
     * #setField.
     * <br>
     * You can also store custom data in here.
     *
     * @param name a string identifier for the parameter
     * @return the defined value of the field
     */
    Object getField(String name);

    /**
     * Sets a parameter that is used in the spawning of this object. For example:
     * <br>
     * "title" - The {@link org.bukkit.Art} that should be shown on a painting.
     * <br>
     * "direction" - A {@link org.bukkit.block.BlockFace} that provides the direction of a painting or item frame.
     * <br>
     * "orbcount" - The integer number of experience that an orb is worth.
     * <br>
     * "velocity" - The velocity to spawn the object with.
     * <br>
     * "shooter" - The integer Entity ID of the person who fired this fake entity.
     * <br>
     * "id" - The type ID of the falling block's appearance.
     * <br>
     * "data" - The data value of the falling block's appearance.
     * <br>
     *
     * You can also store custom data in here.
     * @param name a name of a parameter given above
     * @param value the value to set it to
     */
    void setField(String name, Object value);

    /**
     * Checks whether or not a given spawn parameter has been specified.
     * <br>
     * You can also store custom data in here.
     *
     * @param name the name of the parameter to check for
     * @return whether or not the parameter was found
     */
    boolean hasField(String name);
}
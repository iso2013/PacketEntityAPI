package net.iso2013.peapi.api.packet;

import org.bukkit.potion.PotionEffectType;

/**
 * Created by iso2013 on 6/8/2018.
 */
public interface EntityPotionRemovePacket extends EntityPacket {
    /**
     * Gets the potion effect type that will be removed
     *
     * @return the potion effect
     */
    PotionEffectType getEffectType();

    /**
     * Set the effect type to be removed from the entity
     *
     * @param value the new effect
     */
    void setEffectType(PotionEffectType value);
}

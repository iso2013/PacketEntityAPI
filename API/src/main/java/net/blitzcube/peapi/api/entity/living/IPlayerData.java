package net.blitzcube.peapi.api.entity.living;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import net.blitzcube.peapi.api.entity.ILivingData;
import org.bukkit.entity.Entity;

public interface IPlayerData extends ILivingData {
    Float getAdditionalHearts();

    void setAdditionalHearts(Float value);

    Integer getScore();

    void setScore(Integer value);

    Boolean isLeftPantsShown();

    Boolean isRightPantsShown();

    Boolean isHatShown();

    Boolean isCapeShown();

    Boolean isJacketShown();

    Boolean isLeftSleeveShown();

    Boolean isRightSleeveShown();

    Byte getMainHand();

    void setMainHand(Byte value);

    NbtCompound getLeftShoulderParrot();

    void setLeftShoulderParrot(NbtCompound value);

    NbtCompound getRightShoulderParrot();

    void setRightShoulderParrot(NbtCompound value);

    void setLeftPantsShown(boolean value);

    void setRightPantsShown(boolean value);

    void setHatShown(boolean value);

    void setCapeShown(boolean value);

    void setJacketShown(boolean value);

    void setLeftSleeveShown(boolean value);

    void setRightSleeveShown(boolean value);

    void unsetAdditionalHearts();

    void unsetScore();

    void unsetLeftPantsShown(Entity e);

    void unsetRightPantsShown(Entity e);

    void unsetHatShown(Entity e);

    void unsetCapeShown(Entity e);

    void unsetJacketShown(Entity e);

    void unsetLeftSleeveShown(Entity e);

    void unsetRightSleeveShown(Entity e);

    void unsetMainHand();

    void unsetLeftShoulderParrot();

    void unsetRightShoulderParrot();

    void resetToDefault();

    void clearAll();
}

package net.blitzcube.peapi.api.entity;

public interface IAreaEffectCloudData extends IEntityData {
    Float getRadius();

    void setRadius(Float value);

    Integer getColor();

    void setColor(Integer value);

    Boolean isShowAsPoint();

    Integer getParticleID();

    void setParticleID(Integer value);

    Integer getParticleOption1();

    void setParticleOption1(Integer value);

    Integer getParticleOption2();

    void setParticleOption2(Integer value);

    void setShowAsPoint(Boolean value);

    void unsetRadius();

    void unsetColor();

    void unsetShowAsPoint();

    void unsetParticleID();

    void unsetParticleOption1();

    void unsetParticleOption2();

    void resetToDefault();

    void clearAll();
}

package net.blitzcube.peapi.api.entity.wrappers;

import org.bukkit.entity.Villager;

/**
 * Created by iso2013 on 10/12/19.
 */
public class VillagerData {
    private Villager.Type type;
    private Villager.Profession profession;
    private int level;

    public VillagerData() {
        this.type = Villager.Type.PLAINS;
        this.profession = Villager.Profession.NONE;
        this.level = 1;
    }

    public VillagerData(Villager.Type type, Villager.Profession profession, int level) {
        this.type = type;
        this.profession = profession;
        this.level = level;
    }

    public Villager.Type getType() {
        return type;
    }

    public void setType(Villager.Type type) {
        this.type = type;
    }

    public Villager.Profession getProfession() {
        return profession;
    }

    public void setProfession(Villager.Profession profession) {
        this.profession = profession;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

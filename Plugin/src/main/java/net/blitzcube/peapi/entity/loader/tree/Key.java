package net.blitzcube.peapi.entity.loader.tree;

import com.google.gson.JsonElement;
import net.blitzcube.peapi.entity.modifiers.GenericModifier;

import java.util.List;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class Key extends Node.Attribute {
    private int index;
    private String type;
    private String label;
    private JsonElement def;

    public Key(int index, String type, String label, JsonElement def) {
        this.index = index;
        this.type = type;
        this.label = label;
        this.def = def;
    }

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public Object getDef() {
        return def;
    }

    @Override
    public List<GenericModifier> asGenericModifier() {
        //TODO
        return null;
    }
}

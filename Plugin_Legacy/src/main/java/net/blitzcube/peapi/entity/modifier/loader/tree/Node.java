package net.blitzcube.peapi.entity.modifier.loader.tree;

import net.blitzcube.peapi.entity.modifier.modifiers.GenericModifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class Node {
    private final String name;
    private final List<Node> children = new ArrayList<>();
    private final List<Attribute> attributes = new ArrayList<>();
    private transient Node parent;

    Node(Node parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() { return name; }

    public List<Attribute> getAttributes() { return attributes; }

    public List<Node> getChildren() { return children; }

    public Node getParent() { return parent; }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public static abstract class Attribute {
        public abstract List<GenericModifier> asGenericModifier();
    }
}

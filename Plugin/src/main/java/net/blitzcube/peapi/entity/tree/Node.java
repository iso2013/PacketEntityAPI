package net.blitzcube.peapi.entity.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class Node {
    private final transient Node parent;
    private final String name;
    private final List<Node> children = new ArrayList<>();
    private final List<Attribute> attributes = new ArrayList<>();

    public Node(Node parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public void addChild(Node child) {children.add(child);}

    public void addAttribute(Attribute attribute) {attributes.add(attribute);}

    public String getName() { return name; }

    public List<Attribute> getAttributes() { return attributes; }

    public List<Node> getChildren() { return children; }

    public Node getParent() { return parent; }

    public class Attribute {}
}

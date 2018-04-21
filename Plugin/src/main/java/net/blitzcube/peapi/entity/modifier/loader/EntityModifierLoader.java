package net.blitzcube.peapi.entity.modifier.loader;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.blitzcube.peapi.entity.modifier.loader.tree.Bitmask;
import net.blitzcube.peapi.entity.modifier.loader.tree.Node;
import net.blitzcube.peapi.entity.modifier.loader.tree.TreeDeserializer;
import net.blitzcube.peapi.entity.modifier.modifiers.GenericModifier;
import org.bukkit.entity.EntityType;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class EntityModifierLoader {
    public static ImmutableMap<EntityType, ImmutableList<GenericModifier>> getModifiers(InputStream dataStream) {
        Gson loader = new GsonBuilder()
                .registerTypeAdapter(
                        new TypeToken<Node>() {}.getType(),
                        new TreeDeserializer()
                ).registerTypeAdapter(
                        new TypeToken<Node.Attribute>() {}.getType(),
                        new TreeDeserializer.AttributeDeserializer()
                ).registerTypeAdapter(
                        new TypeToken<Bitmask.Key>() {}.getType(),
                        new TreeDeserializer.BitmaskKeyDeserializer()
                ).create();

        Map<EntityType, Node> nodeMap = new HashMap<>();

        Node root = loader.fromJson(new InputStreamReader(dataStream), Node.class);
        traverse(nodeMap, root, null);

        Map<EntityType, ImmutableList<GenericModifier>> output = new HashMap<>();

        for (Map.Entry<EntityType, Node> e : nodeMap.entrySet()) {
            List<GenericModifier> modifiers = new ArrayList<>();
            Node current = e.getValue();
            do {
                for (Node.Attribute a : current.getAttributes()) modifiers.addAll(a.asGenericModifier());
            } while ((current = current.getParent()) != null);
            output.put(e.getKey(), ImmutableList.copyOf(modifiers));
        }

        return ImmutableMap.copyOf(output);
    }

    private static void traverse(Map<EntityType, Node> nodeMap, Node node, Node parent) {
        if (node.getChildren().size() > 0) {
            for (Node n : node.getChildren()) traverse(nodeMap, n, node);
        } else {
            Optional<EntityType> t;
            if (!(t = Enums.getIfPresent(EntityType.class, node.getName())).isPresent()) throw new IllegalStateException
                    ("Expected a valid entity type for a final node!");
            nodeMap.put(t.get(), node);
        }
        node.setParent(parent);
    }
}

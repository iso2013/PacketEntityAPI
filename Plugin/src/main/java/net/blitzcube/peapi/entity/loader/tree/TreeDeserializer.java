package net.blitzcube.peapi.entity.loader.tree;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class TreeDeserializer implements JsonDeserializer<Node> {
    @Override
    public Node deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        if (!json.isJsonObject()) throw new JsonParseException("Expected an object, but found an invalid type!");
        JsonObject obj = json.getAsJsonObject();
        Node n = new Node(null, obj.get("name").getAsString());
        if (obj.has("keys")) n.getAttributes().addAll(context.deserialize(
                obj.get("keys"),
                new TypeToken<List<Node.Attribute>>() {}.getType()
        ));
        if (obj.has("children")) n.getChildren().addAll(context.deserialize(
                obj.get("children"),
                new TypeToken<List<Node>>() {}.getType()
        ));
        n.getChildren().forEach(node -> node.setParent(n));
        return n;
    }

    public static class AttributeDeserializer implements JsonDeserializer<Node.Attribute> {
        @Override
        public Node.Attribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
                JsonParseException {
            if (!json.isJsonObject()) throw new JsonParseException("Expected an object, but found an invalid type!");
            JsonObject obj = json.getAsJsonObject();
            if (obj.has("label")) {
                //This is something besides a bitmask
                return new Key(
                        obj.get("index").getAsInt(),
                        obj.get("type").getAsString(),
                        obj.get("label").getAsString(),
                        obj.get("def")
                );
            } else {
                //This is a bitmask.
                Bitmask b = new Bitmask(obj.get("index").getAsInt(), obj.get("def").getAsByte());
                b.getKeys().addAll(context.deserialize(
                        obj.get("keys"),
                        new TypeToken<List<Bitmask.Key>>() {}.getType()
                ));
                return b;
            }
        }
    }

    public static class BitmaskKeyDeserializer implements JsonDeserializer<Bitmask.Key> {
        @Override
        public Bitmask.Key deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
                JsonParseException {
            if (!json.isJsonObject()) throw new JsonParseException("Expected an object, but found an invalid type!");
            JsonObject obj = json.getAsJsonObject();
            return new Bitmask.Key(obj.get("mask").getAsByte(), obj.get("label").getAsString());
        }
    }
}

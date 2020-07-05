package net.iso2013.peapi.database;

import net.iso2013.peapi.entity.hitbox.HitboxImpl;
import net.iso2013.peapi.util.ReflectUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 10/11/19.
 */
public class DatabaseLoader {
    private static final Pattern GROUP_STRUCTURE = Pattern.compile("---- ((?:[A-Z]| )+) ----");
    private static final Pattern HEADER_STRUCTURE = Pattern.compile("^(.+): ?(.+)");

    private final Logger logger;
    private final Map<String, List<String>> entries = new HashMap<>();

    private boolean fromJar = true;
    private boolean requireEntities = true;

    private int objectsLoaded = 0;
    private int entitiesLoaded = 0;
    private int modifiersLoaded = 0;
    private int sizesLoaded = 0;

    public DatabaseLoader(JavaPlugin parent) {
        this.logger = parent.getLogger();

        logger.info("-------- PEAPI database information --------");

        InputStream is;
        try {
            is = new FileInputStream(new File(parent.getDataFolder(), "structure.txt"));
            logger.info("Using structure.txt from plugin directory.");
            fromJar = false;
        } catch (FileNotFoundException e) {
            String s = Bukkit.getServer().getClass().getPackage().getName();
            s = s.substring(s.lastIndexOf('.') + 1);
            s = s.substring(0, 5);
            is = this.getClass().getResourceAsStream("/structure_" + s + ".txt");
            if (is == null) {
                logger.severe("WARNING: Failed to find a suitable structure file for version " + s + ". Defaulting to" +
                        " v1_16.");
                is = this.getClass().getResourceAsStream("/structure_v1_16.txt");
            } else {
                logger.info("Using structure data for version " + s + "_X.");
            }
        }

        if (is == null) {
            logger.info("Something went wrong while retrieving structure information. Plugin will now disable.");
            logger.info("--------------------------------------------");
            Bukkit.getPluginManager().disablePlugin(parent);
            return;
        }

        Scanner reader = new Scanner(is);
        String header = null;
        List<String> groupLines = null;
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            Matcher m = GROUP_STRUCTURE.matcher(line);
            if (m.find()) {
                if (header != null) {
                    entries.put(header, groupLines);
                }
                header = m.group(1);
                groupLines = new ArrayList<>();
            } else if (groupLines != null && !line.startsWith("#") && !line.startsWith("//") && !line.isEmpty()) groupLines.add(line);
        }
        if (header != null) {
            entries.put(header, groupLines);
        }

        if (entries.isEmpty()) {
            logger.info("File was empty. Structure data is required. Plugin will now disable.");
            logger.info("--------------------------------------------");
        }

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        requireEntities = ReflectUtil.getVersionNumber() < 14;
    }

    public void processHeader() {
        List<String> data = entries.getOrDefault("HEADER", new ArrayList<>());
        Map<String, String> values = new HashMap<>();
        data.forEach(s -> {
            Matcher m = HEADER_STRUCTURE.matcher(s);
            if (m.find())
                values.put(m.group(1).toUpperCase(), m.group(2));
        });
        logger.info("Author: " + values.getOrDefault("AUTHOR", "Not specified"));
        logger.info("Version: " + values.getOrDefault("VERSION", "Not specified"));
        if (!fromJar) {
            logger.info("Last Update: " + values.getOrDefault("LAST UPDATE", "Not specified"));
            logger.info("Update URL: " + values.getOrDefault("UPDATE URL", "Not specified"));
            if (values.containsKey("NOTES"))
                logger.info("Notes: " + values.get("NOTES"));
            logger.info("Minecraft Version: " + values.getOrDefault("MINECRAFT VERSION", "Not specified"));
        }
        if (values.containsKey("NEEDS ENTITIES")) {
            requireEntities = Boolean.valueOf(values.get("NEEDS ENTITIES"));
        }
    }

    public Map<EntityType, Integer> loadObjects() {
        Map<EntityType, Integer> result = loadEntityMap("OBJECT IDS");
        objectsLoaded = result.size();
        return result;
    }

    public Map<EntityType, Integer> loadEntities() {
        Map<EntityType, Integer> result = loadEntityMap("ENTITY IDS");
        entitiesLoaded = result.size();
        return result;
    }

    private Map<EntityType, Integer> loadEntityMap(String key) {
        Map<EntityType, Integer> result = new HashMap<>();

        int neg = -1;
        for(String line : entries.getOrDefault(key, new ArrayList<>())) {
            try {
                String[] contents = line.split("\\s+");
                result.put(EntityType.valueOf(contents[0]), contents.length >= 2 ? Integer.valueOf(contents[1]) :
                        neg--);
            } catch (IllegalArgumentException e) {
                logger.warning("Parsing failed for line `" + line + "` - is it a valid entity?");
            }
        }

        return result;
    }

    public List<EntityModifierEntry<?>> loadModifiers() {
        List<EntityModifierEntry<?>> result = entries.getOrDefault("METADATA KEYS", new ArrayList<>()).stream()
                .map((Function<String, EntityModifierEntry<?>>) s -> EntityModifierEntry.create(s.split("\\s+"),
                        logger))
                .filter(Objects::nonNull).collect(Collectors.toList());
        modifiersLoaded = result.size();
        return result;
    }

    public Map<EntityType, HitboxImpl> loadHitboxes() {
        Map<EntityType, HitboxImpl> result = new HashMap<>();

        entries.getOrDefault("ENTITY DIMENSIONS", new ArrayList<>()).forEach(line -> {
            String[] data = line.split("\\s+");

            EntityType t;
            try {
                t = EntityType.valueOf(data[0]);
            } catch (IllegalArgumentException e) {
                logger.severe("Could not find the entity type `" + data[0] + "` - is it spelled properly?");
                return;
            }

            float side, height;
            try {
                side = Float.valueOf(data[1]);
            } catch (NumberFormatException e) {
                logger.severe("The number `" + data[1] + "` is not formatted correctly. Skipping entry.");
                return;
            }

            try {
                height = data.length == 3 ? Float.valueOf(data[2]) : side;
            } catch (NumberFormatException e) {
                logger.severe("The number `" + data[1] + "` is not formatted correctly. Skipping entry.");
                return;
            }

            result.put(t, new HitboxImpl(side, height));
        });

        sizesLoaded = result.size();

        return result;
    }

    public void printReport() {
        if (requireEntities && objectsLoaded > 0 && entitiesLoaded > 0) {
            logger.info("Finished loading data:");
            logger.info(objectsLoaded + " objects and " + entitiesLoaded + " entities");
        } else if (requireEntities) {
            logger.warning("Failed to load any objects or entities, and they were marked as required!");
            logger.info("Finished loading data:");
        }
        logger.info(modifiersLoaded + " metadata keys");
        logger.info(sizesLoaded + " hitboxes");
        logger.info("--------------------------------------------");
    }
}

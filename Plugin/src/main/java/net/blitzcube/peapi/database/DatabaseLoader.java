package net.blitzcube.peapi.database;

import net.blitzcube.peapi.entity.hitbox.Hitbox;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by iso2013 on 10/11/19.
 */
public class DatabaseLoader {
    private final Logger logger;
    private final BufferedReader reader;

    private int objects;
    private int modifiers;
    private int hitboxes;

    public DatabaseLoader(JavaPlugin parent) {
        this.logger = parent.getLogger();

        InputStream is;
        try {
            is = new FileInputStream(new File(parent.getDataFolder(), "structure.txt"));
            logger.info("Using structure.txt from plugin directory.");
        } catch (FileNotFoundException e) {
            String s = Bukkit.getServer().getClass().getPackage().getName();
            s = s.substring(s.lastIndexOf('.') + 1);
            s = s.substring(0, 5);
            is = this.getClass().getResourceAsStream("/structure_" + s + ".txt");
            if (is == null) {
                logger.severe("WARNING: Failed to find a suitable structure file for version " + s + ". Defaulting to" +
                        " v1_14.");
                is = this.getClass().getResourceAsStream("/structure_v1_14.txt");
            } else {
                logger.info("Using structure data version " + s + ". Include this in bug reports!");
            }
        }

        if (is == null) {
            logger.severe("Something went wrong while retrieving structure information. Plugin will now disable.");
            Bukkit.getPluginManager().disablePlugin(parent);
            reader = null;
            return;
        }

        reader = new BufferedReader(new InputStreamReader(is));
    }

    public Map<EntityType, Integer> loadObjects() {
        Map<EntityType, Integer> result = new HashMap<>();

        String line;
        try {
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] contents = line.split(" ");
                try {
                    result.put(EntityType.valueOf(contents[0]), Integer.valueOf(contents[1]));
                } catch (IllegalArgumentException e) {
                    logger.warning("Lookup failed for line `" + line + "`.");
                }
            }
        } catch (IOException e) {
            logger.severe("WARNING: Failed to read objects list from structure file. Errors may occur.");
            e.printStackTrace();
        }

        objects = result.size();

        return result;
    }

    public List<EntityModifierEntry<?>> loadModifiers() {
        List<EntityModifierEntry<?>> result = new ArrayList<>();

        String line;
        try {
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                EntityModifierEntry<?> created = EntityModifierEntry.create(line.split("\\s+"), logger);
                if (created != null)
                    result.add(created);
            }
        } catch (IOException e) {
            logger.severe("WARNING: Failed to read objects list from structure file. Errors may occur.");
            e.printStackTrace();
        }

        modifiers = result.size();

        return result;
    }

    public Map<EntityType, Hitbox> loadHitboxes() {
        Map<EntityType, Hitbox> result = new HashMap<>();

        String line;
        try {
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] data = line.split("\\s+");

                if(data.length != 3) {
                    logger.severe("Corrupted line in structure file; skipping it.");
                    continue;
                }

                EntityType t = EntityType.UNKNOWN;
                try {
                    t = EntityType.valueOf(data[0]);
                } catch (IllegalArgumentException e) {
                    logger.severe("Lookup failed for entity type " + data[0] + ".");
                }
                float side = Float.valueOf(data[1]);
                float height = Float.valueOf(data[2]);

                result.put(t, new Hitbox(side, height));
            }
        } catch (IOException e) {
            logger.severe("WARNING: Failed to read hitboxes list from structure file. Errors may occur.");
            e.printStackTrace();
        }

        hitboxes = result.size();

        return result;
    }

    public void printReport() {
        logger.info("-- Structure loading complete --");
        logger.info("Loaded Hitboxes: " + hitboxes);
        logger.info("Loaded Modifiers: " + modifiers);
        logger.info("Loaded Objects: " + objects);
        logger.info("--------------------------------");
    }
}

package net.blitzcube.peapi.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by iso2013 on 10/11/19.
 */
public class DatabaseLoader {
    private final Logger logger;
    private final BufferedReader reader;

    public DatabaseLoader(JavaPlugin parent) {
        this.logger = parent.getLogger();

        InputStream is;
        try {
            is = new FileInputStream(new File(parent.getDataFolder(), "structure.txt"));
        } catch (FileNotFoundException e) {
            String s = Bukkit.getServer().getClass().getPackage().getName();
            s = s.substring(s.lastIndexOf('.') + 1);
            s = s.substring(0, 5);
            is = this.getClass().getResourceAsStream("/structure_" + s + ".txt");
            if(is == null){
                logger.severe("WARNING: Failed to find a suitable structure file for version " + s + ". Defaulting to v1_14.");
                is = this.getClass().getResourceAsStream("/structure_v1_14.txt");
            }
        }

        if(is == null) {
            logger.severe("Something went wrong while retrieving structure information. Plugin will now disable.");
            Bukkit.getPluginManager().disablePlugin(parent);
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
                } catch (IllegalArgumentException e){
                    logger.warning("Lookup failed for line `" + line + "`.");
                }
            }
        } catch (IOException e) {
            logger.severe("WARNING: Failed to read objects list from structure file. Errors may occur.");
            e.printStackTrace();
        }

        return result;
    }

    public List<EntityModifierEntry<?>> loadModifiers() {
        List<EntityModifierEntry<?>> result = new ArrayList<>();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if(line.isEmpty()) continue;
                EntityModifierEntry<?> created = EntityModifierEntry.create(line.split("\\s+"), logger);
                if(created != null)
                    result.add(created);
            }
        } catch (IOException e) {
            logger.severe("WARNING: Failed to read objects list from structure file. Errors may occur.");
            e.printStackTrace();
        }

        return result;
    }
}

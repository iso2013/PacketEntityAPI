package net.blitzcube.peapi.entity;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.blitzcube.peapi.api.entity.IModifiableEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class ModifiableEntity {
    public static class WatcherBased implements IModifiableEntity {
        private final WrappedDataWatcher watcher;

        public WatcherBased(WrappedDataWatcher watcher) {
            this.watcher = watcher;
        }

        @Override
        public List<WrappedWatchableObject> getWatchableObjects() {
            return watcher.getWatchableObjects();
        }

        @Override
        public WrappedDataWatcher getDataWatcher() {
            return watcher;
        }

        @Override
        public Map<Integer, Object> getRawObjects() {
            Map<Integer, Object> map = new HashMap<>();
            for (WrappedWatchableObject wwo : watcher.getWatchableObjects())
                map.put(wwo.getIndex(), wwo.getValue());
            return map;
        }

        @Override
        public Object read(int index) {
            return watcher.getObject(index);
        }

        @Override
        public void write(int index, Object newValue, WrappedDataWatcher.Serializer serializer) {
            watcher.setObject(index, serializer, newValue, true);
        }

        @Override
        public void clear(int index) {
            watcher.remove(index);
        }

        @Override
        public boolean contains(int index) {
            return watcher.hasIndex(index);
        }
    }

    public static class ListBased implements IModifiableEntity {
        private final List<WrappedWatchableObject> objects;

        public ListBased(List<WrappedWatchableObject> objects) {
            this.objects = objects != null ? objects : new ArrayList<>();
        }

        @Override
        public List<WrappedWatchableObject> getWatchableObjects() {
            return objects;
        }

        @Override
        public WrappedDataWatcher getDataWatcher() {
            return null;
        }

        @Override
        public Map<Integer, Object> getRawObjects() {
            Map<Integer, Object> map = new HashMap<>();
            for (WrappedWatchableObject wWO : objects)
                map.put(wWO.getIndex(), wWO.getValue());
            return map;
        }

        @Override
        public Object read(int index) {
            return objects.stream().filter(wWO -> wWO.getIndex() == index)
                    .map(WrappedWatchableObject::getValue).findFirst().orElse(null);
        }

        @Override
        public void write(int index, Object newValue, WrappedDataWatcher.Serializer serializer) {
            clear(index);
            objects.add(
                    new WrappedWatchableObject(
                            new WrappedDataWatcher.WrappedDataWatcherObject(
                                    index, serializer
                            ),
                            newValue
                    )
            );
        }

        @Override
        public void clear(int index) {
            objects.removeIf(wWO -> wWO.getIndex() == index);
        }

        @Override
        public boolean contains(int index) {
            return objects.stream().anyMatch(wWO -> wWO.getIndex() == index);
        }
    }

    public static class MapBased implements IModifiableEntity {
        private final Map<Integer, Object> rawObjects;

        public MapBased(Map<Integer, Object> rawObjects) {
            this.rawObjects = rawObjects;
        }


        @Override
        public List<WrappedWatchableObject> getWatchableObjects() {
            return null;
        }

        @Override
        public WrappedDataWatcher getDataWatcher() {
            return null;
        }

        @Override
        public Map<Integer, Object> getRawObjects() {
            return rawObjects;
        }

        @Override
        public Object read(int index) {
            return rawObjects.get(index);
        }

        @Override
        public void write(int index, Object newValue, WrappedDataWatcher.Serializer serializer) {
            rawObjects.put(index,
                    new WrappedWatchableObject(
                            new WrappedDataWatcher.WrappedDataWatcherObject(
                                    index, serializer
                            ),
                            newValue
                    )
            );
        }

        @Override
        public void clear(int index) {
            rawObjects.remove(index);
        }

        @Override
        public boolean contains(int index) {
            return rawObjects.containsKey(index);
        }
    }
}

package net.blitzcube.peapi.entity.modifiers;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.IModifiableEntity;
import org.bukkit.block.BlockFace;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class DirectionModifier extends GenericModifier<BlockFace> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(EnumWrappers.Direction
            .class);

    public DirectionModifier(int index, String label, BlockFace def) {
        super(BlockFace.class, index, label, def);
    }

    @Override
    public BlockFace getValue(IModifiableEntity target) {
        EnumWrappers.Direction bp = (EnumWrappers.Direction) target.read(super.index);
        if (bp == null) return null;
        switch (bp) {
            case DOWN:
                return BlockFace.DOWN;
            case UP:
                return BlockFace.UP;
            case NORTH:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.SOUTH;
            case WEST:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.EAST;
        }
        return null;
    }

    @Override
    public void setValue(IModifiableEntity target, BlockFace newValue) {
        if (newValue != null) {
            EnumWrappers.Direction d;
            switch (newValue) {
                case DOWN:
                    d = EnumWrappers.Direction.DOWN;
                    break;
                case UP:
                    d = EnumWrappers.Direction.UP;
                    break;
                case NORTH:
                    d = EnumWrappers.Direction.NORTH;
                    break;
                case SOUTH:
                    d = EnumWrappers.Direction.SOUTH;
                    break;
                case WEST:
                    d = EnumWrappers.Direction.WEST;
                    break;
                case EAST:
                    d = EnumWrappers.Direction.EAST;
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect value for block face!");
            }
            target.write(super.index, d, serializer);
        } else super.unsetValue(target);
    }
}

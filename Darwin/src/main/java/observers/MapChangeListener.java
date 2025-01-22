package observers;
import physical.abstractt.World;
import others.Statistics;
public interface MapChangeListener {
    void mapChanged(World world, Statistics statistics);
}

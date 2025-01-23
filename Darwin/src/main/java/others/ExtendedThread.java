package others;
import mains.*;

public class ExtendedThread extends Thread {
    public final Simulation simulation;
    public ExtendedThread(Simulation simulation) {
        super(simulation);
        this.simulation = simulation;
    }

    public ExtendedThread restart() {
        simulation.stopRunning(); // Safely stop the current simulation
        ExtendedThread newExtendedThread = new ExtendedThread(simulation);
        newExtendedThread.start();
        return newExtendedThread;
    }
    public void pauseSimulation() {
        simulation.pauseSimulation();
    }

    public void resumeSimulation() {
        simulation.resumeSimulation();
    }
}


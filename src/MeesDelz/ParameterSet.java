package MeesDelz;

public interface ParameterSet {
    double getK();

    double getEMaxGreedyEpsilon();

    int getRollouts();

    default int getRemainingLimit(){
        return this.getRollouts() > 0 ? 7 : 5;
    }
}


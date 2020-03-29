package MeesDelz;

public class ParameterSet {
    public double K;
    public double eMaxGreedyEpsilon;

    ParameterSet(double K, double eMaxGreedyEpsilon) {
        this.K =K;
        this.eMaxGreedyEpsilon = eMaxGreedyEpsilon;
    }

    public double reward;
}


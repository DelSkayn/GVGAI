package MeesDelz;

import java.util.ArrayList;
import java.util.Random;

public interface ParameterOptimizer {
    double [] KValues = {1.2,1.3,Math.sqrt(2),1.5,1.6};
    double [] eMaxGreedyValues = {0.025,0.05,0.1};
    int [] rolloutValues = {0,3,5};
    double exploration = 0.1;


    /// Pick a parameter from a set.
    ParameterSet choose(Random rand);

    /// Change a parameter according to a reward
    void update(double reward, ParameterSet param);
}

package MeesDelz;

import java.util.ArrayList;

public class NaiveMonteCarloParameterOptimizer {
    private double [] KValues = {1.2,1.3,Math.sqrt(2),1.5,1.6};
    private double [] eMaxGreedyValues = {0.025,0.05,0.1};

    class Param{
        double value = 0;
        double reward = 0;
        double eval = 0;
    }
    private Param [][] params;

    NaiveMonteCarloParameterOptimizer(){
        params = new Param[2][5];
        for(int i = 0;i < KValues.length;i++){
            this.params[0][i] = new Param();
            this.params[0][i].value = KValues[i];
        }

    }

}

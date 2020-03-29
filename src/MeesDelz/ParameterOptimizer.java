package MeesDelz;

import java.util.ArrayList;
import java.util.Random;

public class ParameterOptimizer {

    ParameterOptimizer() {
        this.options = new ArrayList<>();
        for(double K: KValues){
            for(double G: eMaxGreedyValues){
                options.add(new ParameterSet(K,G));
            }
        }
    }
    private double [] KValues = {1.2,1.3,Math.sqrt(2),1.5,1.6};
    private double [] eMaxGreedyValues = {0.025,0.05,0.1};

    private double exploration = 0.1;

    private ArrayList<ParameterSet> options;
    ParameterSet best;

    /// Pick a parameter from a set.
    public ParameterSet chooseParameter(Random rand) {
        if(this.best == null){
            int choice = rand.nextInt(options.size());
            this.best = this.options.get(choice);
            return this.best;
        }
        if(rand.nextDouble() < this.exploration){
            int choice = rand.nextInt(options.size());
            return  this.options.get(choice);
        }else{
            return best;
        }
    }

    /// Change a parameter according to a reward
    public void update(double reward, ParameterSet param) {
        param.reward += reward;
        if(param.reward > this.best.reward){
            this.best = param;
        }
    }
}

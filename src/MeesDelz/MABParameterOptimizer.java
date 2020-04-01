package MeesDelz;

import java.util.ArrayList;
import java.util.Random;

public class MABParameterOptimizer implements ParameterOptimizer {
    private ArrayList<MABParameterSet> options;
    MABParameterSet best;

    static class MABParameterSet implements ParameterSet{
        private double K;
        private double eMaxGreedyEpsilon;
        private int rollouts;

        MABParameterSet(double K, double emax, int rollouts){
            this.K = K;
            this.eMaxGreedyEpsilon = emax;
            this.rollouts = rollouts;
        }

        protected double reward;
        protected double evals;
        protected double score(){
            return reward / evals;
        }

        @Override
        public double getK() {
            return K;
        }

        @Override
        public double getEMaxGreedyEpsilon() {
            return eMaxGreedyEpsilon;
        }

        @Override
        public int getRollouts() {
            return 3;
        }
    }

    MABParameterOptimizer(){
        this.options = new ArrayList<>();
        for(double K: KValues)
            for (double G : eMaxGreedyValues)
                for(int roll: rolloutValues)
                    options.add(new MABParameterSet(K, G,roll));
    }

    @Override
    public ParameterSet choose(Random rand) {
        if (this.best == null || this.best.score() == 0.0) {
            int choice = rand.nextInt(options.size());
            this.best = this.options.get(choice);
            return this.best;
        }
        if (rand.nextDouble() < this.exploration) {
            int choice = rand.nextInt(options.size());
            return this.options.get(choice);
        } else {
            return best;
        }
    }

    @Override
    public void update(double reward, ParameterSet p){
        MABParameterSet param = (MABParameterSet) p;
        param.reward += reward;
        param.evals += 1;
        if(param.score() > this.best.score()) {
            this.best = param;
        }
    }
}

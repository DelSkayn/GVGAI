package MeesDelz;

import core.game.StateObservation;
import ontology.Types;
import tools.ElapsedCpuTimer;
import tracks.singlePlayer.tools.Heuristics.StateHeuristic;
import tracks.singlePlayer.tools.Heuristics.WinScoreHeuristic;

import java.util.Random;

public class SingleMCTSPlayer {
    private Random rand;
    private Agent agent;
    private MCTSNode root;
    private ParameterSet p;
    private int num_actions;
    private Types.ACTIONS[] actions;
    private StateHeuristic heuristic;

    SingleMCTSPlayer(Random rand, Agent agent, int num_actions, Types.ACTIONS [] actions, StateHeuristic heuristic){
        this.rand = rand;
        this.agent = agent;
        this.root = null;
        this.p = new ParameterSet();
        this.num_actions = num_actions;
        this.actions = actions;
        this.heuristic = heuristic;
    }

    double rollout(MCTSNode node){
        StateObservation s = node.state.copy();
        int i = 0;
        while (!s.isGameOver() && i <= p.MAX_ROLLOUT_DEPTH){
            Types.ACTIONS act = actions[rand.nextInt(num_actions)];
            s.advance(act);
        }
        return heuristic.evaluateState(s);
    }

    void iterate(){
        System.out.println("CALLED");
        // Select and expand
        MCTSNode node = root;
        while (!node.isTerminal()){
            if (!node.isFullyExpanded()){
                node = node.expand();
                break;
            } else{
                node = node.bestChild(p);
            }
        }
        //simulate
        double reward = rollout(node);
        // back propegate
        while (node != null){
            node.visits += 1;
            node.reward += reward;
            node = node.parent;
        }
    }

    public Types.ACTIONS genMove(){
        int best = -1;
        MCTSNode child = null;
        for(int i = 0;i < root.num_children;i++){
            MCTSNode cur = root.children[i];
            if(cur.visits > best){
                best = cur.visits;
                child = cur;
            }
        }
        System.out.print("BEST:");
        System.out.println(best);
        this.root = child;
        if(child != null){
            return child.action;
        }else {
            return actions[rand.nextInt(num_actions)];
        }
    }

    public void init(StateObservation state){
        if(this.root == null)
            this.root = new MCTSNode(null,state,Types.ACTIONS.ACTION_NIL);
    }

    public void run(ElapsedCpuTimer elapsedTimer){
        double avgTimeTaken = 0;
        double acumTimeTaken = 0;
        long remaining = elapsedTimer.remainingTimeMillis();
        int numIters = 0;
        StateObservation tempState;

        int remainingLimit = 2;
        while (remaining > 2 * avgTimeTaken && remaining > remainingLimit) {
            ElapsedCpuTimer elapsedTimerIteration = new ElapsedCpuTimer();

            iterate();
            numIters += 1;

            acumTimeTaken += (elapsedTimerIteration.elapsedMillis());

            avgTimeTaken = acumTimeTaken / numIters;
            remaining = elapsedTimer.remainingTimeMillis();
        }
        System.out.print("NUM ITER ");
        System.out.println(numIters);
    }
}

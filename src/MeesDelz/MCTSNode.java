package MeesDelz;

import core.game.StateObservation;
import ontology.Types;

import java.util.ArrayList;

public class MCTSNode {
    StateObservation state;
    public int visits;
    public double reward;
    public boolean terminal;
    Types.ACTIONS action;

    MCTSNode[] children;
    int num_children;
    ArrayList<Types.ACTIONS> pending;
    MCTSNode parent;

    MCTSNode(MCTSNode parent, StateObservation state, Types.ACTIONS action) {
        this.parent = parent;
        this.num_children = 0;
        this.state = state;
        this.terminal = state.isGameOver();
        this.pending = state.getAvailableActions();
        this.children = new MCTSNode[pending.size()];
        this.action = action;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public boolean isLeaf() {
        return this.num_children == 0;
    }

    public boolean isFullyExpanded() {
        return this.pending.size() == 0;
    }

    public MCTSNode expand() {
        Types.ACTIONS act = this.pending.remove(pending.size() - 1);
        StateObservation s = state.copy();
        s.advance(act);
        MCTSNode child = new MCTSNode(this, s,act);
        children[num_children] = child;
        num_children += 1;
        return child;
    }

    double score(ParameterSet p) {
        double exploit = reward / visits;
        double expand = p.CI * Math.sqrt( Math.log(parent.visits) / visits);
        return expand + exploit;
    }

    MCTSNode bestChild(ParameterSet p){
        double score = Double.NEGATIVE_INFINITY;
        MCTSNode node = null;
        for(int i = 0;i < num_children;i++){
            double s = children[i].score(p);
            if(s > score){
                score = s;
                node = children[i];
            }
        }
        return node;
    }
}

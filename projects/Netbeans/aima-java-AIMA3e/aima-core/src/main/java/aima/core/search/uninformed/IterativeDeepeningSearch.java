package aima.core.search.uninformed;

import java.util.List;

import aima.core.agent.Action;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.18, page
 * 89.<br>
 * <br>
 * 
 * <pre>
 * function ITERATIVE-DEEPENING-SEARCH(problem) returns a solution, or failure
 *   for depth = 0 to infinity  do
 *     result &lt;- DEPTH-LIMITED-SEARCH(problem, depth)
 *     if result != cutoff then return result
 * </pre>
 * 
 * Figure 3.18 The iterative deepening search algorithm, which repeatedly
 * applies depth-limited search with increasing limits. It terminates when a
 * solution is found or if the depth- limited search returns failure, meaning
 * that no solution exists.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Ruediger Lunde
 */
public class IterativeDeepeningSearch implements SearchForActions {
	public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
	public static final String METRIC_PATH_COST = "pathCost";

	// Not infinity, but will do, :-)
	private final static int INFINITY = Integer.MAX_VALUE;

	private final NodeExpander nodeExpander;
	private final Metrics metrics;

	public IterativeDeepeningSearch() {
		this(new NodeExpander());
	}
	
	public IterativeDeepeningSearch(NodeExpander nodeExpander) {
		this.nodeExpander = nodeExpander;
		this.metrics = new Metrics();
	}
	
	
	// function ITERATIVE-DEEPENING-SEARCH(problem) returns a solution, or
	// failure
	@Override
	public List<Action> search(Problem p) {
		clearInstrumentation();
		// for depth = 0 to infinity do
		for (int i = 0; i <= INFINITY; i++) {
			// result <- DEPTH-LIMITED-SEARCH(problem, depth)
			DepthLimitedSearch dls = new DepthLimitedSearch(i, nodeExpander);
			List<Action> result = dls.search(p);
			updateMetrics(dls.getMetrics());
			// if result != cutoff then return result
			if (!dls.isCutOff(result))
				return result;
		}
		return SearchUtils.failure();
	}

	@Override
	public NodeExpander getNodeExpander() {
		return nodeExpander;
	}
	
	@Override
	public Metrics getMetrics() {
		return metrics;
	}

	/**
	 * Sets the nodes expanded and path cost metrics to zero.
	 */
	private void clearInstrumentation() {
		metrics.set(METRIC_NODES_EXPANDED, 0);
		metrics.set(METRIC_PATH_COST, 0);
	}

	//
	// PRIVATE METHODS
	//

	private void updateMetrics(Metrics dlsMetrics) {
		metrics.set(METRIC_NODES_EXPANDED,
				metrics.getInt(METRIC_NODES_EXPANDED) + dlsMetrics.getInt(METRIC_NODES_EXPANDED));
		metrics.set(METRIC_PATH_COST, dlsMetrics.getDouble(METRIC_PATH_COST));
	}
}
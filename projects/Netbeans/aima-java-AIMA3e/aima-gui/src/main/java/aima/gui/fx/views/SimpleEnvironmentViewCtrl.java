package aima.gui.fx.views;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Environment;
import aima.core.agent.EnvironmentView;
import javafx.application.Platform;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

/**
 * Controller class for a simple environment view. It logs informations about
 * environment changes on a text area and can be used for any kind of
 * environment. More specific environment views can be created on this base by
 * adding state visualization to the split pane of this implementation.
 * 
 * @author Ruediger Lunde
 *
 */
public class SimpleEnvironmentViewCtrl implements EnvironmentView {

	protected SplitPane splitPane;
	protected TextArea textArea;

	/**
	 * Adds a split pane and a text area to the provided pane. The result is
	 * an environment view which prints messages about environment changes on
	 * the text area.
	 */
	public SimpleEnvironmentViewCtrl(StackPane parent) {
		splitPane = new SplitPane();
		textArea = new TextArea();
		textArea.setMinWidth(0.0);
		splitPane.getItems().add(textArea);
		parent.getChildren().add(splitPane);
	}

	public void initialize(Environment env) {
		if (!textArea.getText().isEmpty())
			textArea.appendText("\n");
		updateEnvStateView(env);
	}
	
	/**
	 * Should not be called from an FX application thread.
	 */
	@Override
	public void notify(String msg) {
		Platform.runLater(() -> textArea.appendText("\n" + msg));
	}

	/**
	 * Should not be called from an FX application thread.
	 */
	@Override
	public void agentAdded(Agent agent, Environment source) {
		Platform.runLater(() -> {
			textArea.appendText("\nAgent added.");
			updateEnvStateView(source);
		});

	}

	/**
	 * Should not be called from an FX application thread.
	 */
	@Override
	public void agentActed(Agent agent, Action action, Environment source) {
		Platform.runLater(() -> {
			textArea.appendText("\nAgent acted: " + action.toString());
			updateEnvStateView(source);
		});
	}

	/**
	 * Is called after agent actions. This implementation does nothing.
	 */
	protected void updateEnvStateView(Environment env) {

	}
}

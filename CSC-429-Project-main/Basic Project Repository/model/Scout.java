package model;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;

public class Scout implements IView, IModel {

    private Properties dependencies;
    private ModelRegistry myRegistry;


    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    // Error messages
    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";

    public Scout() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Scout");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowTransactionChoiceView();
    }

    private void createAndShowTransactionChoiceView() {
        Scene currentScene = (Scene)myViews.get("TransactionChoiceView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("TransactionChoiceView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("TransactionChoiceView", currentScene);
        }
        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    @Override
    public void updateState(String key, Object value) {
        // DEBUG System.out.println("Teller.updateState: key: " + key);
        stateChangeRequest(key, value);
    }

    @Override
    public Object getState(String key) {
        return null;
    }

    @Override
    public void subscribe(String key, IView subscriber) {
        myRegistry.subscribe(key, subscriber);
    }

    @Override
    public void unSubscribe(String key, IView subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);
        if (key.equals("TransactionChoiceView") == true) {
            createAndShowTransactionChoiceView();
        }

        myRegistry.updateSubscribers(key, this);
    }

    private void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }

    public void swapToView(Scene newScene)
    {
        if (newScene == null)
        {
            System.out.println("Scout.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();
        //Place in center
        WindowPosition.placeCenter(myStage);
    }
}

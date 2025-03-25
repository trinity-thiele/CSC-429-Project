package model;

import impresario.IView;

public class ScoutCollection extends EntityBase implements IView {

    protected ScoutCollection(String tablename) {
        super(tablename);
    }

    public void updateState(String key, Object value) {

    }

    public Object getState(String key) {
        return null;
    }

    public void stateChangeRequest(String key, Object value) {

    }


    protected void initializeSchema(String tableName) {

    }
}

package com.sdu;

public enum WarehouseState{
    Idle(0),
    Executing(1),
    Error(2);

    private final int stateId;

    WarehouseState(int state) {
        this.stateId = state;
    }

    public int getState() {
        return stateId;
    }

    public static WarehouseState getWarehouseState(int i){
        for (WarehouseState state :
                WarehouseState.values()) {
            if(state.getState() == i){
                return state;
            }
        }
        return null;
    }
}

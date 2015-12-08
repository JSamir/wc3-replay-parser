package com.w3gdata.actionblocks;

import java.util.ArrayList;
import java.util.List;

public class ChangeSelection extends ActionBlock {
    public static final int ID = 0x16;

    public static final int ADD_TO_SELECTION = 0x01;

    public static final int REMOVE_FROM_SELECTION = 0x02;

    public int selectMode;

    public int unitsBuildingsNumber;

    public List<ObjPair> selectedObjs;

    public ChangeSelection() {
        selectedObjs = new ArrayList<>(unitsBuildingsNumber);
    }
}

package com.w3gdata.actionblock;

public class MinimapSignal extends ActionBlock {
    public static final int ID = 0x68;

    public static final int ADDITIONAL_BYTE = 0x67;//for WarCraft III patch version <= 1.06

    public int locationX;

    public int locationY;

    public int unknown;
}
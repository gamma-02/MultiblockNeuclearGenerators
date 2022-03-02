package gamma02.neucleargenerators.common.Util;

import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

public class InternalType {
    public InternalType CORE;
    public InternalType COOLANT;

    private int tempGen;
    @Nullable
    private Integer powerGen;
    private Type type;
    InternalType(Type type, int tempGen, @Nullable Integer powerGen){
        this.type = type;
        this.tempGen = tempGen;
        this.powerGen = powerGen;
    }

    public enum Type{
        PASSIVE_COOLANT,
        POWER,
        GENERATION,
        ACTIVE_COOLANT;
    }
}

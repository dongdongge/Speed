package soyouarehere.imwork.speed.framework.struct;

public abstract class FunctionNoParamsNoResult extends Function {

    public String mFunctionName;

    public FunctionNoParamsNoResult(String mFunctionName) {
        super(mFunctionName);
        this.mFunctionName = mFunctionName;
    }


    public abstract void function();
}

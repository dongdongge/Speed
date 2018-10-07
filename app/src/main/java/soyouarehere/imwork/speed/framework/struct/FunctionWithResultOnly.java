package soyouarehere.imwork.speed.framework.struct;

public abstract class FunctionWithResultOnly<Params> extends Function {
    public String mFunctionName;

    public FunctionWithResultOnly(String mFunctionName) {
        super(mFunctionName);
        this.mFunctionName = mFunctionName;
    }


    public abstract void function(Params params);

}

package soyouarehere.imwork.speed.framework.struct;

public abstract class FunctionWithParamOnly<Params> extends Function{
    public String mFunctionName;
    public FunctionWithParamOnly(String mFunctionName) {
        super(mFunctionName);
        this.mFunctionName = mFunctionName;
    }


    public abstract void function(Params params);

}

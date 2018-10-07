package soyouarehere.imwork.speed.framework.struct;

/**
 *
 * @param <Result>
 * @param <Params>
 */
public abstract class FunctionWithParamAndResult<Result,Params> extends Function{
    public String mFunctionName;

    public FunctionWithParamAndResult(String mFunctionName) {
        super(mFunctionName);
        this.mFunctionName = mFunctionName;
    }
    public abstract Result function(Params params);

}

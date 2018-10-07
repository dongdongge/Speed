package soyouarehere.imwork.speed.framework.struct;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class FunctionManager {

    private FunctionManager() {
        mFunctionNoParamsNoResult = new HashMap<>();
        mFunctionWithParamOnly = new HashMap<>();
        mFunctionWithResultOnly = new HashMap<>();
        mFunctionWithParamAndResult = new HashMap<>();
    }

    public FunctionManager getInstance() {
        return Help.manager;
    }

    private static class Help {
        static FunctionManager manager = new FunctionManager();
    }


    private Map<String, FunctionNoParamsNoResult> mFunctionNoParamsNoResult;
    private Map<String, FunctionWithParamOnly> mFunctionWithParamOnly;
    private Map<String, FunctionWithResultOnly> mFunctionWithResultOnly;
    private Map<String, FunctionWithParamAndResult> mFunctionWithParamAndResult;

    public FunctionManager addFunction(FunctionNoParamsNoResult functionNoParamsNoResult) {
        mFunctionNoParamsNoResult.put(functionNoParamsNoResult.mFunctionName, functionNoParamsNoResult);
        return this;
    }


    public void invokeFunc(String funcName) {
        if (TextUtils.isEmpty(funcName)) {
            return;
        }
        if (mFunctionNoParamsNoResult != null) {
            FunctionNoParamsNoResult f = mFunctionNoParamsNoResult.get(funcName);
            if (f != null) {
                f.function();
            } else {
                try {
                    throw new FunctionException("no has function " + funcName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

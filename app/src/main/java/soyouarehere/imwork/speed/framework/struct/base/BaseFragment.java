package soyouarehere.imwork.speed.framework.struct.base;

import android.support.v4.app.Fragment;

import soyouarehere.imwork.speed.framework.struct.FunctionManager;

public class BaseFragment extends Fragment {


    FunctionManager mFunctionManager;


    public void setFunctionManager(FunctionManager mFunctionManager) {
        this.mFunctionManager = mFunctionManager;
    }

}

package lz.lmf.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * DESCRIPTION:父类Activity
 * AUTHOR:ShenYang
 * MAIL：shenyang@ejiangyin.com
 * Date:2016-09-09
 * TIME:09:29
 */
public class BaseActivity extends Activity implements NetBroadcastReceiver.NetEvevt {


    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netModile;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        evevt = this;
        inspectNet();
    }


    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netModile = Tools.getNetWrokState(BaseActivity.this);
        return isNetConnect();
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netModile) {
        // TODO Auto-generated method stub
        this.netModile = netModile;
        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netModile == 1) {
            return true;
        } else if (netModile == 0) {
            return true;
        } else if (netModile == -1) {
            return false;

        }
        return false;
    }
}

package com.yxld.xzs.entity;

import java.util.List;

/**
 * Created by William on 2017/11/28.
 */

public class NightWarehouseListBean extends BaseBack {

    /**
     * rows : [{"id":85,"chukudanBianhao":"yc20171104105500001","chukurenMing":"ls_zongbu",
     * "chukurenId":202,"tijiaoShijian":"2017-11-04 10:55:00.0","beihuoren":null,
     * "beikuorenId":null,"beihuoShijian":null,"linghuoren":"王煌","linghuorenId":446,
     * "linghuoShijian":null,"linghuoZhuangtai":1,"gongsiId":1,"xiangmuId":346,
     * "chulizhuangtai":null,"xiangmuName":null},{"id":80,
     * "chukudanBianhao":"yc20171103174500001","chukurenMing":"admin_wqj","chukurenId":221,
     * "tijiaoShijian":"2017-11-03 17:45:00.0","beihuoren":null,"beikuorenId":null,
     * "beihuoShijian":null,"linghuoren":"王煌","linghuorenId":446,"linghuoShijian":null,
     * "linghuoZhuangtai":2,"gongsiId":1,"xiangmuId":346,"chulizhuangtai":null,
     * "xiangmuName":null},{"id":79,"chukudanBianhao":"yc20171103174400001",
     * "chukurenMing":"admin_wqj","chukurenId":221,"tijiaoShijian":"2017-11-03 17:44:00.0",
     * "beihuoren":null,"beikuorenId":null,"beihuoShijian":null,"linghuoren":"王煌",
     * "linghuorenId":446,"linghuoShijian":null,"linghuoZhuangtai":1,"gongsiId":1,
     * "xiangmuId":346,"chulizhuangtai":null,"xiangmuName":null}]
     * total : 3
     */

    private int total;
    private List<NightWarehouseBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<NightWarehouseBean> getRows() {
        return rows;
    }

    public void setRows(List<NightWarehouseBean> rows) {
        this.rows = rows;
    }

}

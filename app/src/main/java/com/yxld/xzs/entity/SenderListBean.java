package com.yxld.xzs.entity;

import java.util.List;

/**
 *
 * Created by William on 2017/11/29.
 */

public class SenderListBean extends BaseBack {

    /**
     * rows : [{"cxwyPeisongId":90,"cxwyPeisongName":"黄艳","cxwyPeisongPhone":"15652525252",
     * "cxwyPeisongTotal":null,"cxwyPeisongProject":null,"cxwyPeisongPassword":null,
     * "cxwyPeisongTotalPrice":null,"cxwyPeisongState":null,"cxwyPeisongFuzeren":null,
     * "cxwyPeisongBackup":null,"cxwyType":null,"xnName":null},{"cxwyPeisongId":96,
     * "cxwyPeisongName":"中远项目经理","cxwyPeisongPhone":"13875231886","cxwyPeisongTotal":null,
     * "cxwyPeisongProject":null,"cxwyPeisongPassword":null,"cxwyPeisongTotalPrice":null,
     * "cxwyPeisongState":null,"cxwyPeisongFuzeren":null,"cxwyPeisongBackup":null,
     * "cxwyType":null,"xnName":null},{"cxwyPeisongId":136,"cxwyPeisongName":"刘诗中远",
     * "cxwyPeisongPhone":"18636523523","cxwyPeisongTotal":null,"cxwyPeisongProject":null,
     * "cxwyPeisongPassword":null,"cxwyPeisongTotalPrice":null,"cxwyPeisongState":null,
     * "cxwyPeisongFuzeren":null,"cxwyPeisongBackup":null,"cxwyType":null,"xnName":null},
     * {"cxwyPeisongId":238,"cxwyPeisongName":"配送员","cxwyPeisongPhone":"13875231886",
     * "cxwyPeisongTotal":null,"cxwyPeisongProject":null,"cxwyPeisongPassword":null,
     * "cxwyPeisongTotalPrice":null,"cxwyPeisongState":null,"cxwyPeisongFuzeren":null,
     * "cxwyPeisongBackup":null,"cxwyType":null,"xnName":null},{"cxwyPeisongId":239,
     * "cxwyPeisongName":"中远曾玲","cxwyPeisongPhone":"13167149187","cxwyPeisongTotal":null,
     * "cxwyPeisongProject":null,"cxwyPeisongPassword":null,"cxwyPeisongTotalPrice":null,
     * "cxwyPeisongState":null,"cxwyPeisongFuzeren":null,"cxwyPeisongBackup":null,
     * "cxwyType":null,"xnName":null},{"cxwyPeisongId":244,"cxwyPeisongName":"王",
     * "cxwyPeisongPhone":"13077377823","cxwyPeisongTotal":null,"cxwyPeisongProject":null,
     * "cxwyPeisongPassword":null,"cxwyPeisongTotalPrice":null,"cxwyPeisongState":null,
     * "cxwyPeisongFuzeren":null,"cxwyPeisongBackup":null,"cxwyType":null,"xnName":null},
     * {"cxwyPeisongId":312,"cxwyPeisongName":"吴清杰","cxwyPeisongPhone":"17608769590",
     * "cxwyPeisongTotal":null,"cxwyPeisongProject":null,"cxwyPeisongPassword":null,
     * "cxwyPeisongTotalPrice":null,"cxwyPeisongState":null,"cxwyPeisongFuzeren":null,
     * "cxwyPeisongBackup":null,"cxwyType":null,"xnName":null},{"cxwyPeisongId":437,
     * "cxwyPeisongName":"万文秀","cxwyPeisongPhone":"15266666666","cxwyPeisongTotal":null,
     * "cxwyPeisongProject":null,"cxwyPeisongPassword":null,"cxwyPeisongTotalPrice":null,
     * "cxwyPeisongState":null,"cxwyPeisongFuzeren":null,"cxwyPeisongBackup":null,
     * "cxwyType":null,"xnName":null},{"cxwyPeisongId":440,"cxwyPeisongName":"向磊",
     * "cxwyPeisongPhone":"15243648097","cxwyPeisongTotal":null,"cxwyPeisongProject":null,
     * "cxwyPeisongPassword":null,"cxwyPeisongTotalPrice":null,"cxwyPeisongState":null,
     * "cxwyPeisongFuzeren":null,"cxwyPeisongBackup":null,"cxwyType":null,"xnName":null},
     * {"cxwyPeisongId":458,"cxwyPeisongName":"晓晓","cxwyPeisongPhone":"18711001698",
     * "cxwyPeisongTotal":null,"cxwyPeisongProject":null,"cxwyPeisongPassword":null,
     * "cxwyPeisongTotalPrice":null,"cxwyPeisongState":null,"cxwyPeisongFuzeren":null,
     * "cxwyPeisongBackup":null,"cxwyType":null,"xnName":null}]
     * total : 10
     */

    private int total;
    private List<SenderBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SenderBean> getRows() {
        return rows;
    }

    public void setRows(List<SenderBean> rows) {
        this.rows = rows;
    }

}

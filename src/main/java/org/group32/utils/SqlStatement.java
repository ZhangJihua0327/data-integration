package org.group32.utils;

import org.group32.pojo.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SqlStatement implements Serializable {
    private static final String ContractSql = "INSERT INTO dm_v_tr_contract_mx (uid, contract_no, apply_no, artificial_no, occur_date, loan_cust_no, cust_name,\n" +
            "                                 buss_type, occur_type, is_credit_cyc, curr_type, buss_amt, loan_pert, term_year,\n" +
            "                                 term_mth, term_day, base_rate_type, base_rate, float_type, rate_float, rate, pay_times,\n" +
            "                                 pay_type, direction, loan_use, pay_source, putout_date, matu_date, vouch_type,\n" +
            "                                 is_oth_vouch, apply_type, extend_times, actu_out_amt, bal, norm_bal, dlay_bal,\n" +
            "                                 dull_bal, owed_int_in, owed_int_out, fine_pr_int, fine_intr_int, dlay_days, five_class,\n" +
            "                                 class_date, mge_org, mgr_no, operate_org, operator, operate_date, reg_org, register,\n" +
            "                                 reg_date, inte_settle_type, is_bad, frz_amt, con_crl_type, shift_type, due_intr_days,\n" +
            "                                 reson_type, shift_bal, is_vc_vouch, loan_use_add, finsh_type, finsh_date, sts_flag,\n" +
            "                                 src_dt, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DjkSql = "INSERT INTO dm_v_tr_djk_mx (uid, card_no, tran_type, tran_type_desc, tran_amt, tran_amt_sign, mer_type, mer_code,\n" +
            "                            rev_ind, tran_desc, tran_date, val_date, pur_date, tran_time, acct_no, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DsfSql = "INSERT INTO dm_v_tr_dsf_mx (tran_date, tran_log_no, tran_code, channel_flg, tran_org, tran_teller_no, dc_flag, tran_amt,\n" +
            "                            send_bank, payer_open_bank, payer_acct_no, payer_name, payee_open_bank, payee_acct_no,\n" +
            "                            payee_name, tran_sts, busi_type, busi_sub_type, etl_dt, uid)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DuebillSql = "INSERT INTO dm_v_tr_duebill_mx (uid, acct_no, receipt_no, contract_no, subject_no, cust_no, loan_cust_no, cust_name, buss_type, curr_type, buss_amt, putout_date, matu_date, actu_matu_date, buss_rate, actu_buss_rate, intr_type, intr_cyc, pay_times, pay_cyc, extend_times, bal, norm_bal, dlay_amt, dull_amt, bad_debt_amt, owed_int_in, owed_int_out, fine_pr_int, fine_intr_int, dlay_days, pay_acct, putout_acct, pay_back_acct, due_intr_days, operate_org, operator, reg_org, register, occur_date, loan_use, pay_type, pay_freq, vouch_type, mgr_no, mge_org, loan_channel, ten_class, src_dt, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String EtcSql = "INSERT INTO dm_v_tr_etc_mx (uid, etc_acct, card_no, car_no, cust_name, tran_date, tran_time, tran_amt_fen, real_amt, conces_amt, tran_place, mob_phone, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String GrwySql = "INSERT INTO dm_v_tr_grwy_mx (uid, mch_channel, login_type, ebank_cust_no, tran_date, tran_time, tran_code, tran_sts, return_code, return_msg, sys_type, payer_acct_no, payer_acct_name, payee_acct_no, payee_acct_name, tran_amt, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String GzdfSql = "INSERT INTO dm_v_tr_gzdf_mx (belong_org, ent_acct, ent_name, eng_cert_no, acct_no, cust_name, uid, tran_date, tran_amt, tran_log_no, is_secu_card, trna_channel, batch_no, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String HuanbSql = "INSERT INTO dm_v_tr_huanb_mx (tran_flag, uid, cust_name, acct_no, tran_date, tran_time, tran_amt, bal, tran_code, dr_cr_code, pay_term, tran_teller_no, pprd_rfn_amt, pprd_amotz_intr, tran_log_no, tran_type, dscrp_code, remark, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String HuanxSql = "INSERT INTO dm_v_tr_huanx_mx (tran_flag, uid, cust_name, acct_no, tran_date, tran_time, tran_amt, cac_intc_pr, tran_code, dr_cr_code, pay_term, tran_teller_no, intc_strt_date, intc_end_date, intr, tran_log_no, tran_type, dscrp_code, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SaSql = "INSERT INTO dm_v_tr_sa_mx   (uid, card_no, cust_name, acct_no, det_n, curr_type, tran_teller_no, cr_amt, bal, tran_amt, tran_card_no, tran_type, tran_log_no, dr_amt, open_org, dscrp_code, remark, tran_time, tran_date, sys_date, tran_code, remark_1, oppo_cust_name, agt_cert_type, agt_cert_no, agt_cust_name, channel_flag, oppo_acct_no, oppo_bank_no, src_dt, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SbybSql = "INSERT INTO dm_v_tr_sbyb_mx (uid, cust_name, tran_date, tran_sts, tran_org, tran_teller_no, tran_amt_fen, tran_type, return_msg, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String SdrqSql = "INSERT INTO dm_v_tr_sdrq_mx (hosehld_no, acct_no, cust_name, tran_type, tran_date, tran_amt_fen, channel_flg, tran_org, tran_teller_no, tran_log_no, batch_no, tran_sts, return_msg, etl_dt, uid)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String ShopSql = "INSERT INTO v_tr_shop_mx (tran_channel, order_code, shop_code, shop_name, hlw_tran_type, tran_date, tran_time, tran_amt,\n" +
            "                          current_status, score_num, pay_channel, uid, legal_name, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SjyhSql = "INSERT INTO dm_v_tr_sjyh_mx (uid, mch_channel, login_type, ebank_cust_no, tran_date, tran_time, tran_code, tran_sts, return_code, return_msg, sys_type, payer_acct_no, payer_acct_name, payee_acct_no, payee_acct_name, tran_amt, etl_dt)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static Map<Class<? extends POJO>, String> sqlMap = new HashMap<Class<? extends POJO>, String>();

    static {
        sqlMap.put(DmVTrContractMx.class, ContractSql);
        sqlMap.put(DmVTrDjkMx.class, DjkSql);
        sqlMap.put(DmVTrDsfMx.class, DsfSql);
        sqlMap.put(DmVTrDuebillMx.class, DuebillSql);
        sqlMap.put(DmVTrEtcMx.class, EtcSql);
        sqlMap.put(DmVTrGrwyMx.class, GrwySql);
        sqlMap.put(DmVTrGzdfMx.class, GzdfSql);
        sqlMap.put(DmVTrHuanbMx.class, HuanbSql);
        sqlMap.put(DmVTrHuanxMx.class, HuanxSql);
        sqlMap.put(DmVTrSaMx.class, SaSql);
        sqlMap.put(DmVTrSbybMx.class, SbybSql);
        sqlMap.put(DmVTrSdrqMx.class, SdrqSql);
        sqlMap.put(DmVTrSjyhMx.class, SjyhSql);
        sqlMap.put(VTrShopMx.class, ShopSql);
    }

    public static String getSQl(Class<? extends POJO> tclass) {
        return sqlMap.get(tclass);
    }
}

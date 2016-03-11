package com.dessapi.util;

public class SQLConstantUtil {
	
	public static final String LOGIN_CHECK_QUERY= " select * from user_login where username=? ";
	public static final String PRODUCT_LIST_QUERY= " SELECT distinct product_id, product_name FROM product_mst where product_status='A' ";
	public static final String CATEGORY_LIST_QUERY= " SELECT category_id, category FROM api.category_mst where category_status='A' ";
	public static final String SELECTED_PRODUCT_LIST_QUERY= " SELECT product_id, product_name FROM product_mst where product_status='A' and  product_id in ";
	public static final String REGISTERATION_SESSION_INSERT_QUERY = " insert into eval_session (eval_id, low, medium, high, created_dt) values(?, ?, ?, ?, now()) ";
	public static final String REGISTERATION_PRODUCTS_INSERT_QUERY = " insert into product_eval values (?, ?) ";
	public static final String EVAL_FEATURE_INSERT_QUERY = " insert into feature_eval values (?, ?, ?) ";
	public static final String FEATURES_LIST_QUERY= " select c.category, f.feature_id,f.feature from api.category_mst c, api.feature_mst f where c.category_id=f.category_id and c.category_status='A' and f.feature_status='A' ";	
	public static final String EVAL_SCORE_INSERT_QUERY = " insert into eval_score select distinct pe.eval_id, pe.product_id, fe.feature_id, (fe.rating * fr.rating) as score  "
			+ "from api.product_eval pe, api.prod_feature_rating fr, api.feature_eval fe where pe.eval_id = fe.eval_id and pe.product_id = fr.product_id and fr.feature_id=fe.feature_id and pe.eval_id = ? ";
	public static final String EVAL_SCORE_QUERY = " select COALESCE(cm.category_id,'~') as cat_id, COALESCE((select cm1.category from category_mst cm1 where cm1.category_id=cm.category_id),'--TOTAL--') as category, pe.product_id, "
			+ " (select pm1.product_name from product_mst pm1 where pm1.product_id=pe.product_id) as product, CONCAT(sum(fe.rating * fr.rating),'/',sum(fe.rating * 10)) as score  from api.product_eval pe, api.prod_feature_rating fr, api.feature_eval fe, api.category_mst cm, api.feature_mst fm "
			+ " where pe.eval_id = fe.eval_id and pe.product_id = fr.product_id and fr.feature_id=fe.feature_id and fm.category_id=cm.category_id and fr.feature_id=fm.feature_id and fe.feature_id=fm.feature_id and pe.eval_id = ? group by pe.product_id,cm.category_id with rollup";
	
	/*public static final String EVAL_SCORE_CAT_DETAILS_QUERY = " select  pe.product_id, (select pm1.product_name from product_mst pm1 where pm1.product_id=pe.product_id) as product, fm.feature_id, (select fm1.feature from feature_mst fm1 where fm1.feature_id =fe.feature_id) as feature, "
			+ " CONCAT('Score:',(fe.rating * fr.rating), '<br>Comments:', COALESCE(fr.comments,'NA'))  as score_comment from api.product_eval pe, api.prod_feature_rating fr, api.feature_eval fe, api.category_mst cm, api.feature_mst fm where pe.eval_id = fe.eval_id and pe.product_id = fr.product_id and fr.feature_id=fe.feature_id "
			+ " and fm.category_id=cm.category_id and fr.feature_id=fm.feature_id and fe.feature_id=fm.feature_id and pe.eval_id =? and cm.category_id=? order by 1 ";*/
	
	public static final String EVAL_SCORE_CAT_DETAILS_QUERY = " select  pe.product_id, (select pm1.product_name from product_mst pm1 where pm1.product_id=pe.product_id) as product, fm.feature_id, (select fm1.feature from feature_mst fm1 where fm1.feature_id =fe.feature_id) as feature, "
			+ " CONCAT('Score:',CONCAT((fe.rating * fr.rating),'/',(fe.rating * 10)), '<br>', COALESCE(fr.relevance,'NA'))  as relevance from api.product_eval pe, api.prod_feature_rating fr, api.feature_eval fe, api.category_mst cm, api.feature_mst fm where pe.eval_id = fe.eval_id and pe.product_id = fr.product_id and fr.feature_id=fe.feature_id "
			+ " and fm.category_id=cm.category_id and fr.feature_id=fm.feature_id and fe.feature_id=fm.feature_id and pe.eval_id =? and cm.category_id=? order by 1 ";
			
	public static final String EVAL_SCORE_DETAILS_QUERY = "select cm.category_id, (select cm1.category from category_mst cm1 where cm1.category_id=cm.category_id) as category, pe.product_id, (select pm1.product_name from product_mst pm1 where pm1.product_id=pe.product_id) as product,  "
			+ "fm.feature_id, (select fm1.feature from feature_mst fm1 where fm1.feature_id =fe.feature_id) as feature, CONCAT(sum(fe.rating * fr.rating),'/',sum(fe.rating * 10)) as score  from api.product_eval pe, api.prod_feature_rating fr, api.feature_eval fe, api.category_mst cm, api.feature_mst fm where pe.eval_id = fe.eval_id "
			+ "and pe.product_id = fr.product_id and fr.feature_id=fe.feature_id and fm.category_id=cm.category_id and fr.feature_id=fm.feature_id and fe.feature_id=fm.feature_id and pe.eval_id = ? order by 1,3,5 ";
	
	public static final String EVAL_CSV_RPT_QUERY = " select pe.product_id, (select pm1.product_name from product_mst pm1 where pm1.product_id=pe.product_id) as product, (select cm1.category from category_mst cm1 where cm1.category_id=cm.category_id) as category, (select fm1.feature from feature_mst fm1 where fm1.feature_id =fe.feature_id) as feature, "
			+ "CONCAT((fe.rating * fr.rating),'/',(fe.rating * 10)) as score, COALESCE(fr.comments,'NA')  as comment from api.product_eval pe, api.prod_feature_rating fr, api.feature_eval fe, api.category_mst cm, api.feature_mst fm where pe.eval_id = fe.eval_id and pe.product_id = fr.product_id and fr.feature_id=fe.feature_id and fm.category_id=cm.category_id "
			+ "and fr.feature_id=fm.feature_id and fe.feature_id=fm.feature_id and pe.eval_id =?  order by 1,2,3 ";
	
	public static final String SIGN_UP_INSERT_QUERY = " INSERT INTO api.user_mst  (first_name, last_name, email, password, phone, company, designation, signup_dt, approval_status)  VALUES (?, ?, ?, ?, ?, ?, ?, now(), 'N') ";
	public static final String COMPANYNAME_CHECK_QUERY= " SELECT eval_id FROM eval_session where eval_id=? ";
}

//package test;
//
//import org.apache.log4j.Logger;
//
//import java.text.DateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.hql.internal.ast.HqlASTFactory;
//
//import xk.model.Tuser;
//import xk.util.HibernateSessionFactoryUtil;
//
//import com.alibaba.druid.sql.visitor.functions.Function;
//import com.sun.jdi.Method;
//
//
//public class TestHql {
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = Logger.getLogger(TestHql.class);
//
//	static Session session = HibernateSessionFactoryUtil.getSessionFactory()
//			.getCurrentSession();
//
//	public static void main(String[] args) {
//		Transaction tx = session.beginTransaction();
//
//		Map<String, Object> map = new HashMap();
//
//		List<Tuser> tList = findTuser("");
//		
//		for(Tuser tuser:tList){
//			System.out.println(tuser.getUname());
////			if(tuser.getProfile()!=null){
////				System.out.println("Profile is not null and Email is "+tuser.getProfile().getEmail());
////			}else {
////				System.out.println("Profile is null ");
////			}
//		}
//		
//		tx.commit();
//	}
//	
//	public static List<Tuser> findTuser(String id){
//		String hqlString = "from Tuser t where 1=1";
//		Query query = session.createQuery(hqlString);
//		return query.list();
//	}
//	
//}
//
// HQL语句 根据六种人员查询，注意六种人员的属性名一样 SUCCESS
// String fixhql="select distinct tp from Tproject ,Tuser";
// String tailhql=" ) ";
// String shql=fixhql;
// for (int i=1;i<3;i++){
// shql=" select tp from Tproject tp, Tuser tu where tu.username=:name"+i+" and tp in ( "+shql+tailhql;
// }
// ===================================================================


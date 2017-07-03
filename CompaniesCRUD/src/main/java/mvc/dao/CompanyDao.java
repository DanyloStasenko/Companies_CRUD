package mvc.dao;

import mvc.model.Company;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDao {
    private static final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

    @Autowired
    protected SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public void add(Company company){
        getSession().persist(company);
        logger.info(company + " added");
    }

    public void update(Company company){
        getSession().update(company);
        logger.info(company + " updated");
    }

    public void removeById(int id){
        Session session = getSession();
        Company company = (Company) session.load(Company.class, new Integer(id));
        if (company != null){
            session.delete(company);
            logger.info(company + " removed");
        }
        else {
            logger.info("Can't remove model. Model is null");
        }
    }

    @SuppressWarnings("unchecked")
    public Company getById(int id){
        logger.info("Getting company by id: " + id);
        Criteria criteria = getSession().createCriteria(Company.class);
        criteria.add(Restrictions.eq("id", id));
        return (Company)criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Company> getAll(){
        logger.info("Getting criteria list");
        Criteria criteria =  getSession().createCriteria(Company.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }
}

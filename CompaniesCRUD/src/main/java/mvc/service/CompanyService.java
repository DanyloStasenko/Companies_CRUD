package mvc.service;

import mvc.dao.CompanyDao;
import mvc.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CompanyService {

    @Autowired(required = true)
    @Qualifier(value = "companyDao")
    private CompanyDao companyDao;

    public void addCompany(Company company) {
        companyDao.add(company);
    }

    public void updateCompany(Company company) {
        companyDao.update(company);
    }

    public void removeCompanyById(int id) {
        if (companyDao.getById(id).getChildCompanies().isEmpty()){
            System.err.println("deleting");
            companyDao.removeById(id);
        }
    }

    public Company getCompanyById(int id) {
        return companyDao.getById(id);
    }

    public List<Company> getCompaniesList() {
        return companyDao.getAll();
    }
}

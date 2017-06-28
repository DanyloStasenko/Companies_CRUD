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
        Company companyToDelete = companyDao.getById(id);

        if (companyToDelete.getChildCompanies() != null){
            for (Company company : companyToDelete.getChildCompanies()) {
                removeCompanyById(company.getId());
            }
        } else {
            if (companyToDelete.getParent() != null){
                companyToDelete.getParent().getChildCompanies().remove(companyToDelete);
                companyDao.update(companyToDelete.getParent());
            }
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

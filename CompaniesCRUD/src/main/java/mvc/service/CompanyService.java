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
        if (company.getParent() == null || company.getParent().getId() == 0){
            company.setParent(null);
        }
        companyDao.add(company);
    }

    public void updateCompany(Company company) {
        if (company.getParent().getId() == 0){
            company.setParent(null);
        }
        companyDao.update(company);
    }

    public void removeCompanyById(int id) {
        if (companyDao.getById(id).getChildCompanies().isEmpty()){
            // if company hasn't child companies - we are removing it
            companyDao.removeById(id);
        } else {
            // if company has some child companies - ew must remove all of them
            for (Company company : companyDao.getById(id).getChildCompanies()) {
                removeCompanyById(company.getId());
            }
            companyDao.removeById(id);
        }
    }

    public Company getCompanyById(int id) {
        return companyDao.getById(id);
    }

    public List<Company> getCompaniesList() {
        List<Company> list = companyDao.getAll();

        // deleting all chield-companies from list, to show them only 1 time: as fields of root-companies
        list.removeIf(company -> company.getParent() != null);

        // calculating child earnings
        for (Company company : list) {
            company.setChildEarnings(company.getChildEarnings());
        }

        return list;
    }
}

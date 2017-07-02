package mvc.controller;

import mvc.model.Company;
import mvc.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/test")
     public String test(ModelMap model) {

        companyService.addCompany(new Company("Windows", 250));
        companyService.addCompany(new Company("Apple", 200));
        companyService.addCompany(new Company("Yahoo", 100));
        companyService.addCompany(new Company("Dell", 350));

        model.addAttribute("companies", companyService.getCompaniesList());

        return "redirect:/companies";
    }

    @RequestMapping("/test2")
    public String test3(ModelMap model) {

        Company child = new Company("Amazon", 100);
        companyService.addCompany(child);

        Company parent = companyService.getCompanyById(1);
        child.setParent(parent);
        companyService.updateCompany(child);

        return "redirect:/companies";
    }


    @RequestMapping("/companies")
	public String companies(ModelMap model) {

        model.addAttribute("companyToEdit", new Company());

        List<Company> list = companyService.getCompaniesList();
        list.removeIf(company -> company.getParent() != null);

        for (Company company : list) {
            company.setChildEarnings(company.getChildEarnings());
            if (company.getChildCompanies().isEmpty()){
                company.setChildEarnings(0);
            }
        }

        model.addAttribute("companies", list);

        return "companies";
	}

    @RequestMapping("edit/{id}")
    public String editTask(@PathVariable("id") int id, Model model){
        model.addAttribute("companyToEdit", companyService.getCompanyById(id));

        List<Company> list = companyService.getCompaniesList();
        list.removeIf(company -> company.getParent() != null);

        for (Company company : list) {
            company.setChildEarnings(company.getChildEarnings());
        }

        model.addAttribute("companies", list);

        return "companies";
    }

    /**
     * Adding and editing companies
     */
    @RequestMapping(value = "/companies/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("companyToEdit") Company company, Model model){
        if (company.getId() == 0){
            if (company.getParent().getId() == 0){
                company.setParent(null);
            }

            companyService.addCompany(company);
        } else {
            if (company.getParent().getId() == 0){
                company.setParent(null);
            }
            try{
                companyService.updateCompany(company);
            } catch (Exception e){
                model.addAttribute("message", "Parent ID not found!");
                return "companies";
            }
        }

        return "redirect:/companies";
    }

    /**
     * Removing
     */
    @RequestMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") int id, Model model){
        companyService.removeCompanyById(id);
        return "redirect:/companies";
    }
}
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

        Company company1 = new Company();
        company1.setName("Windows");

        Company company2 = new Company();
        company2.setName("Apple");

        Company company3 = new Company();
        company3.setName("Yahoo");

        companyService.addCompany(company1);
        companyService.addCompany(company2);
        companyService.addCompany(company3);

        model.addAttribute("companies", companyService.getCompaniesList());

        return "redirect:/companies";
    }

    @RequestMapping("/test2")
    public String test3(ModelMap model) {

        Company company4 = new Company();
        company4.setName("4");

        companyService.addCompany(company4);

        Company company1 = companyService.getCompanyById(1);
        company4.setParent(company1);
        companyService.updateCompany(company4);

        return "redirect:/companies";
    }


    @RequestMapping("/companies")
	public String companies(ModelMap model) {

        // model.addAttribute("companies", companyService.getCompaniesList());
        // System.err.println(companyService.getCompaniesList());

        model.addAttribute("companyToEdit", new Company());

        List<Company> list = companyService.getCompaniesList();
        list.removeIf(company -> company.getParent() != null);

        model.addAttribute("companies", list);

        return "companies";
	}

    @RequestMapping("edit/{id}")
    public String editTask(@PathVariable("id") int id, Model model){
        model.addAttribute("companyToEdit", companyService.getCompanyById(id));
        return "companies";
    }

    /**
     * Adding and editing tasks
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
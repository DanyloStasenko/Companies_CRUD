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

        // model.addAttribute("companies", companyService.getCompaniesList());

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
        model.addAttribute("companies", companyService.getCompaniesList());

        return "companies";
	}

    @RequestMapping("edit/{id}")
    public String editCompany(@PathVariable("id") int id, Model model){

        model.addAttribute("companyToEdit", companyService.getCompanyById(id));
        model.addAttribute("companies", companyService.getCompaniesList());

        return "companies";
    }

    /**
     * Adding and editing companies
     */
    @RequestMapping(value = "/companies/add", method = RequestMethod.POST)
    public String addCompany(@ModelAttribute("companyToEdit") Company company, Model model){
        if (company.getId() == 0){
            companyService.addCompany(company);
        } else {
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
    public String removeCompany(@PathVariable("id") int id, Model model){
        companyService.removeCompanyById(id);
        return "redirect:/companies";
    }
}
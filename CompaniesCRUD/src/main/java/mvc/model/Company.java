package mvc.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "aproximatedEarnings")
    private double aproximatedEarnings;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Company parent;

    @OneToMany(mappedBy="parent", fetch = FetchType.EAGER)
    private List<Company> childCompanies;

    private double childEarnings;

    /** Constructors */
    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, double aproximatedEarnings) {
        this.aproximatedEarnings = aproximatedEarnings;
        this.name = name;
    }

    public Company(int id, double aproximatedEarnings, String name) {
        this.id = id;
        this.aproximatedEarnings = aproximatedEarnings;
        this.name = name;
    }

    /** Getters and setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAproximatedEarnings() {
        return aproximatedEarnings;
    }

    public void setAproximatedEarnings(double aproximatedEarnings) {
        if (aproximatedEarnings > 0){
            this.aproximatedEarnings = aproximatedEarnings;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Company> getChildCompanies() {
        return childCompanies;
    }

    public void setChildCompanies(List<Company> childCompanies) {
        this.childCompanies = childCompanies;
    }

    public void addChildCompany(Company company){
        childCompanies.add(company);
    }

    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }

    public double getChildEarnings() {

        if (childCompanies.isEmpty()) {
            return aproximatedEarnings;
        } else {
            double sum = 0;
            for (Company childCompany : childCompanies) {
                sum += childCompany.getChildEarnings();
            }

           return sum + aproximatedEarnings;
        }
    }

    public void setChildEarnings(double childEarnings) {
        this.childEarnings = childEarnings;
    }

    @Override
    public String toString() {
        return "<pre>" + name + "&emsp;" + aproximatedEarnings + "<br>" + childCompanies + "</pre>";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

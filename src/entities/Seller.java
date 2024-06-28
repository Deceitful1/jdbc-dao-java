package entities;

import java.util.Date;

public class Seller
{
    private int id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;

    public Seller(int id, String name, String email, Date birthDate, Double baseSalary)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Double getBaseSalary()
    {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary)
    {
        this.baseSalary = baseSalary;
    }

}

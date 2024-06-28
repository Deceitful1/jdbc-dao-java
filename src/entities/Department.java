package entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Department implements Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public Department(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Department()
    {
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id);
    }

    @Override
    public String toString()
    {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}

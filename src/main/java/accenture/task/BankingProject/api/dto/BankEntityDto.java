package accenture.task.BankingProject.api.dto;


import java.util.Objects;

public class BankEntityDto extends BankDto{

    private Long id;

    public BankEntityDto(){}

    public BankEntityDto(Long id) {
        this.id = id;
    }

    public BankEntityDto(String address, Long id) {
        super(address);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankEntityDto that = (BankEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "BankEntityDto{" +
                "id=" + id +
                '}';
    }
}

package models;

import java.util.List;

public class BankDto {

    private Long amount;

    private String type;

    private String date;
    private Long userId;

    private List<Bank> list;

    public List<Bank> getList() {
        return list;
    }

    public void setList(List<Bank> List) {
        this.list = List;
    }

    private Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

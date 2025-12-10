package models;

import java.util.List;

public class PersonalDetailsDto {
    private List<PersonalDetail> personalDetailsList;

    public List<PersonalDetail> getPersonalDetailsList() {
        return personalDetailsList;
    }

    public void setPersonalDetailsList(List<PersonalDetail> personalDetailsList) {
        this.personalDetailsList = personalDetailsList;
    }
}

package controllers;

import dao.BankDao;
import dao.PersonalDetailDao;
import models.BankDto;
import models.PersonalDetailDto;
import ninja.NinjaTest;
import ninja.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankControllerTest extends NinjaTest {
    @Mock
    private BankDao bankDao;

    @Mock
    private PersonalDetailDao personalDetailDao;

    private BankController bankController;

    @Before
    public void setupTest(){
        bankController = new BankController();
        bankController.bankDao = bankDao;
        bankController.personalDetailDao = personalDetailDao;

    }

    @Test
    public void test1() throws Exception {
        PersonalDetailDto personaldetailDto = new PersonalDetailDto();

        personaldetailDto.setName("Gresh");
        personaldetailDto.setAddress("Gehlab");
        personaldetailDto.setCity("Palwal");
        personaldetailDto.setDob("2003-02-05");
        personaldetailDto.setEmail("gresh@gmail.com");
        personaldetailDto.setGender("Male");
        personaldetailDto.setState("Haryana");
        personaldetailDto.setPan("DLDPG3514C");
        personaldetailDto.setPincode((long)121103);
        personaldetailDto.setCard((long)765656888);
        personaldetailDto.setPin((long)69040406);
        personaldetailDto.setAadhar((long)633148477);


        when(personalDetailDao.postPersonalDetails(personaldetailDto)).thenReturn(personaldetailDto);
        Result result = bankController.postPersonalDetail(personaldetailDto);
        assertEquals(200,result.getStatusCode());
    }

//    @Test
//    public void test2() throws Exception {
//        when(personalDetailDao.postPersonalDetails(null)).thenReturn(null);
//        Result result = bankController.postPersonalDetail(null);
//        assertEquals(400,result.getStatusCode());
//    }


}

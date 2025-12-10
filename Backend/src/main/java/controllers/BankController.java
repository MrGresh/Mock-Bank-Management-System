package controllers;

import com.google.inject.Inject;
import dao.*;
import models.*;
import ninja.Results;
import ninja.Result;
import ninja.params.Param;
import ninja.params.PathParam;

import java.util.Date;

public class BankController {


    @Inject
    BankDao bankDao;

    @Inject
    PersonalDetailDao personalDetailDao;

//    public Result postBankData(BankDto bankDto) throws Exception{
//        bankDao.postWithdraw(bankDto);
//        return Results.ok().json().render("success");
//    }
    public Result postDeposit(BankDto bankDto) throws Exception{

        if(bankDto.getAmount()<=0){
            bankDto=null;
            return Results.ok().json().render(bankDto);
        }
        bankDao.postDeposit(bankDto);
        return Results.ok().json().render(bankDto);
    }
    public Result postDepositRecieved(BankDto bankDto) throws Exception{

        if(bankDto.getAmount()<=0){
            bankDto=null;
            return Results.ok().json().render(bankDto);
        }
        bankDao.postDepositRecieved(bankDto);
        return Results.ok().json().render(bankDto);
    }

    public Result postWithdraw(BankDto bankDto) throws Exception{


        long balance = bankDao.getBalance(bankDto.getUserId());
        if(bankDto.getAmount()>balance) {
            bankDto=null;
            return Results.ok().json().render(bankDto);
        }
        else if( bankDto.getAmount()<=0){
            bankDto.setType("Gresh");
            return Results.ok().json().render(bankDto);
        }
        bankDao.postWithdraw(bankDto);
        return Results.ok().json().render(bankDto);
    }
    public Result postWithdrawTransferred(BankDto bankDto) throws Exception{


        long balance = bankDao.getBalance(bankDto.getUserId());
        if(bankDto.getAmount()>balance) {
            bankDto=null;
            return Results.ok().json().render(bankDto);
        }
        else if( bankDto.getAmount()<=0){
            bankDto.setType("Gresh");
            return Results.ok().json().render(bankDto);
        }
        bankDao.postWithdrawTransferred(bankDto);
        return Results.ok().json().render(bankDto);
    }

    public Result getMiniStatement(@Param("userId") long userId){
        return Results.ok().json().render(bankDao.getMiniStatement(userId));
    }

    public Result getBalance(@Param("userId") long userId){
        System.out.println("We are in Controller");
        return Results.ok().json().render(bankDao.getBalance(userId));
    }

    public Result pinChange(@Param("PIN") long PIN,@Param("id") long id){
        bankDao.pinChange(PIN,id);
        return Results.ok().json().render("Success");
    }





    /*Optional*/
    public Result getAllBankDetail(){

        BanksDto object = new BanksDto();
        object = bankDao.getAllBankDetail();
        return Results.json().render(object);
    }

    public Result postPersonalDetail(PersonalDetailDto personaldetailDto) throws Exception{
        System.out.println("We are in Controller");

        PersonalDetailDto personalDetailDto = personalDetailDao.postPersonalDetails(personaldetailDto);
        return Results.ok().json().render(personalDetailDto);
    }




    public Result verifyUser(@Param("id") Long id){

        System.out.println("We are in Controller of User Matching");
        PersonalDetail pd = new PersonalDetail();
        pd = personalDetailDao.getpersonalDetailsById(id);

        return Results.ok().json().render(pd);
    }
    public Result getpersonalDetailsByIdxc(@Param("id") Long id){

        System.out.println("We are in Controller of User Matching");
        PersonalDetail pd = new PersonalDetail();
        pd = personalDetailDao.getpersonalDetailsByIdxc(id);

        return Results.ok().json().render(pd);
    }


    public Result getpersonalDetailsByCard(@Param("card") Long card){

        PersonalDetail pd = new PersonalDetail();
        pd = personalDetailDao.getpersonalDetailsByCard(card);

        return Results.ok().json().render(pd);
    }

    public Result Userlogin(@Param("card") Long card,@Param("pin") Long pin){

        PersonalDetail pd = new PersonalDetail();
        pd = personalDetailDao.Userlogin(card,pin);

        return Results.ok().json().render(pd);
    }
}

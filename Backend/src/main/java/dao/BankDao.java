package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.*;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import etc.LoggedInUser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
public class BankDao {
    private static Logger log = LogManager.getLogger(BankDao.class);
    @Inject    /*Inject All the properties like methods , fields and constructors to use......*/
    Provider<EntityManager> entitiyManagerProvider;

    @Transactional
    public void postDeposit(BankDto bankDto){
    EntityManager entityManager = entitiyManagerProvider.get();
        Bank bank = new Bank();

        bank.setUserId(bankDto.getUserId());
        bank.setAmount(Math.abs(bankDto.getAmount()));

        bank.setDate(new Date().toString());
        bank.setType("Deposit");
        entityManager.persist(bank);
    }
    @Transactional
    public void postDepositRecieved(BankDto bankDto){
        EntityManager entityManager = entitiyManagerProvider.get();
        Bank bank = new Bank();


        bank.setUserId(bankDto.getUserId());
        bank.setAmount(Math.abs(bankDto.getAmount()));

        bank.setDate(new Date().toString());
        bank.setType("Recieved");
        entityManager.persist(bank);
    }
    @Transactional
    public void postWithdraw(BankDto bankDto){
        EntityManager entityManager = entitiyManagerProvider.get();
        Bank bank = new Bank();

        bank.setUserId(bankDto.getUserId());
        bank.setAmount(Math.abs(bankDto.getAmount()));
        bank.setDate(new Date().toString());
        bank.setType("Withdraw");
        entityManager.persist(bank);
    }
    @Transactional
    public void postWithdrawTransferred(BankDto bankDto){
        EntityManager entityManager = entitiyManagerProvider.get();
        Bank bank = new Bank();

        bank.setUserId(bankDto.getUserId());
        bank.setAmount(Math.abs(bankDto.getAmount()));
        bank.setDate(new Date().toString());
        bank.setType("Transferred");
        entityManager.persist(bank);
    }
    @Transactional
    public List<Bank> getMiniStatement(Long userId){

        log.debug("getMiniStatement isCalled "+userId);
        EntityManager entityManager = entitiyManagerProvider.get();

//        TypedQuery<Bank> q = entityManager.createQuery("select x from Bank x where x.id=:BankID", Bank.class);
//
//        List<Bank> list = q.setParameter("BankID",id).getResultList();

       List<Bank> bankList = entityManager.createNamedQuery("bank.findById",Bank.class)
               .setParameter("userId",userId).getResultList();

        if(bankList.size()==0) bankList=null;

        BankDto obj = new BankDto();
        obj.setList(bankList);

//        return bankList;
        return obj.getList();
    }

    @Transactional
    public Long getBalance(Long userId){
        EntityManager entityManager = entitiyManagerProvider.get();
        List<Bank> art = entityManager.createNamedQuery("bank.findById",Bank.class).setParameter("userId",userId).getResultList();
        long res=0;

        if(art.size()==0) res=0;
        else if(art.size()!=0){
            long d = 0,w=0;
            for (int i = 0; i < art.size(); i++) {
                if(art.get(i).getType().equalsIgnoreCase("Deposit")||art.get(i).getType().equalsIgnoreCase("Recieved")) d = d + art.get(i).getAmount();
                else w = w + art.get(i).getAmount();
            }
            res = d - w;
        }

        return res;
    }
    @Transactional
    public void pinChange(Long PIN,Long id){
        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<PersonalDetail> q = entityManager.createQuery("select a from PersonalDetail a where a.id = :ID",PersonalDetail.class);
        List<PersonalDetail> art = q.setParameter("ID",id).getResultList();
        if(art!=null){
            art.get(0).setPin(PIN);
        }
    }



//    public void postDeposit(BankDepositDto obj){
//        EntityManager entityManager = entitiyManagerProvider.get();
//        Bank bank = new Bank();
//        bank.setAmount(obj.getAmount());
//        bank.setType("Deposit");
//        bank.setDate(new Date().toString());
//        bank.setId(obj.getId());
//        bank.setBalance((long)2000);
//        entityManager.persist(bank);
//        return;
//    }
    @UnitOfWork /*to make sure that when you use multiple repositories, they share a single database context
                .merges many small database updates in a single batch to optimize the number of round-trips. */
    public BanksDto getAllBankDetail(){

        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<Bank> q = entityManager.createQuery("select x from Bank x", Bank.class);

        List<Bank> list = q.getResultList();

        BanksDto obj = new BanksDto();

        obj.setList(list);
        return obj;
    }
}

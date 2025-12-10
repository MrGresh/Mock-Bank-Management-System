package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.*;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import ninja.jpa.UnitOfWork;

public class PersonalDetailDao {
    @Inject    /*Inject All the properties like methods , fields and constructors to use......*/
    Provider<EntityManager> entitiyManagerProvider;

    @UnitOfWork /*to make sure that when you use multiple repositories, they share a single database context
                .merges many small database updates in a single batch to optimize the number of round-trips. */
    public PersonalDetail getpersonalDetailsById(Long id){


        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<PersonalDetail> q = entityManager.createQuery("select x from PersonalDetail x where id=:Userid", PersonalDetail.class);
        List<PersonalDetail> list = q.setParameter("Userid",id).getResultList();

        PersonalDetail pd = new PersonalDetail();
        if(list.size()!=0) pd=list.get(0);
        else pd=null;
        return pd;
    }

    public PersonalDetail getpersonalDetailsByCard(Long card){
        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<PersonalDetail> q = entityManager.createQuery("select x from PersonalDetail x where card=:UserCard", PersonalDetail.class);
        List<PersonalDetail> list = q.setParameter("UserCard",card).getResultList();

        PersonalDetail pd = new PersonalDetail();
        if(list.size()!=0) pd=list.get(0);
        else pd=null;
        return pd;
    }
    public PersonalDetail getpersonalDetailsByIdxc(Long id){
        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<PersonalDetail> q = entityManager.createQuery("select x from PersonalDetail x where id=:Userid", PersonalDetail.class);
        List<PersonalDetail> list = q.setParameter("Userid",id).getResultList();

        PersonalDetail pd = new PersonalDetail();
        if(list.size()!=0) pd=list.get(0);
        else pd=null;
        return pd;
    }

    public PersonalDetail Userlogin(Long card,Long pin){

        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<PersonalDetail> q = entityManager.createQuery("select x from PersonalDetail x where card=:Card and pin=:Pin",PersonalDetail.class);
        List<PersonalDetail> pd = q.setParameter("Card",card).setParameter("Pin",pin).getResultList();
        PersonalDetail p = new PersonalDetail();
        if(pd.size()!=0)
            p = pd.get(0);
        else p=null;
        return p;
    }



    @Transactional  /*@Transactional annotation is the metadata that specifies the semantics of the transactions on a method.*/
    public PersonalDetailDto postPersonalDetails (PersonalDetailDto personaldetailDto) throws Exception {

        try{
            EntityManager entityManager = entitiyManagerProvider.get();

            // personalDetail obj = new personalDetail(Name,email);
            // entityManager.persist(obj);


            PersonalDetail personaldetail = new PersonalDetail();

            personaldetail.setPincode(personaldetailDto.getPincode());
            personaldetail.setAddress(personaldetailDto.getAddress());
            personaldetail.setCity(personaldetailDto.getCity());
            personaldetail.setDob(personaldetailDto.getDob());
            personaldetail.setEmail(personaldetailDto.getEmail());
            personaldetail.setGender(personaldetailDto.getGender());
            personaldetail.setName(personaldetailDto.getName());
            personaldetail.setState(personaldetailDto.getState());
            personaldetail.setPan(personaldetailDto.getPan());
            personaldetail.setPin(personaldetailDto.getPin());
            personaldetail.setAadhar(personaldetailDto.getAadhar());
            personaldetail.setCard(personaldetailDto.getCard());

            System.out.println(personaldetail.getName()+"yfdetd6r765fr5fdg");
            entityManager.persist(personaldetail);




            return personaldetailDto;

        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }


}

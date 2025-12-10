import { HttpClient } from '@angular/common/http';
import { Component,OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';
import {faGithub,faLinkedin,faWhatsapp} from '@fortawesome/free-brands-svg-icons';


@Component({
  selector: 'app-bank',
  templateUrl: './bank.component.html',
  styleUrls: ['./bank.component.css']
  
})




export class BankComponent implements OnInit {
  
  icon1=faGithub;
  icon2=faLinkedin;
  icon3=faWhatsapp;


  constructor(private formBuilder: FormBuilder, private _http: HttpClient,private router:Router,public auth:AuthServiceService) {
    (() => { console.log("I am self invoking function inside constructor"); })(); 
   }


  currentTime:string='';
 
  ngOnInit(): void {
    this.auth.WithdrawForm.reset();
    this.auth.DepositForm.reset();
    this.auth.PinForm.reset();
    this.auth.MoneyForm.reset();
    clearInterval(this.auth.intervals)
    setInterval(() => {this.currentTime = new Date().toLocaleString();}, 1000);
    
    let user = localStorage.getItem('user');
    let userId = user && JSON.parse(user).id;
    
    if(userId==null) this.auth.session="        No Session        ";
    else this.auth.startTimer(121);

  } 


  
  defaultName:any;
  getName(){
    let user = localStorage.getItem('user');
    let userName = user && JSON.parse(user).name;
    if(userName==null) {
      this.defaultName='<i>Name</i>';
    }
    else this.defaultName=userName;
  }

  defaultId:any;
  getId(){
    let user = localStorage.getItem('user');
    let userId = user && JSON.parse(user).id;
    if(userId==null) this.defaultId="<i>ID</i>";
    else this.defaultId=userId;
  }

  logOut(){
    clearInterval(this.auth.intervals)
    localStorage.removeItem('user');
    localStorage.clear();
    this.auth.session=null;
    this.auth.Balance=null;
    this.auth.userStatement=null;
    this.auth.WithdrawForm.reset();
    this.auth.DepositForm.reset();
    this.auth.PinForm.reset();
    this.auth.MoneyForm.reset();
    this.router.navigate(['login'])
  }


}



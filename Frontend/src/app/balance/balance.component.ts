import { Component } from '@angular/core';
import { AuthServiceService } from '../auth-service.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.css']
})
export class BalanceComponent {
  balance:any;
  constructor(public auth:AuthServiceService,private router:Router) { }
  ngOnInit(): void {
    this.getbalance()
    
  }
  getbalance(){
    this.balance=this.auth.Balance;
  }
  getName(){
    let user = localStorage.getItem('user');
    let userName = user && JSON.parse(user).name;
    if(userName==null) userName="Name";
    return userName;
  }
  back(){
    this.router.navigate(['bank'])
    clearInterval(this.auth.intervals)
  }
}

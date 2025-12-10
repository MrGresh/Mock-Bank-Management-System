import { Component } from '@angular/core';
import { AuthServiceService } from '../auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ministatement',
  templateUrl: './ministatement.component.html',
  styleUrls: ['./ministatement.component.css']
})
export class MinistatementComponent {
  res:any=[];
  title:any;
  id:any;
  constructor(public auth:AuthServiceService,private router:Router) { }
  ngOnInit(): void {this.getMIni()}

  getMIni(){
    if(this.auth.userStatement==null) this.title="Sorry No Record Found"
    else if(this.auth.userStatement!=null) {
      this.res=this.auth.userStatement;
      this.id=this.auth.ids;
    }
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

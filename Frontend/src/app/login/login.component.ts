import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../auth-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  userID: any;
  userName: any;

  constructor(
    private auth: AuthServiceService
  ) {}
  ngOnInit(): void {}

  getAuth() { return this.auth; }
}

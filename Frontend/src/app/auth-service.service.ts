import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {
  constructor(private _http: HttpClient, private router: Router) {}
  userStatement: any;
  Balance: any;
  loginUserId: any;

  getBalance() {
    let user = localStorage.getItem('user');
    let userIdY = user && JSON.parse(user).id;

    if (userIdY == null) {
      alert('Please Login First');
    } else {
      this._http
        .get<any>(`http://localhost:8080/api/getBalance?userId=${userIdY}`, {
          observe: 'response',
        })
        .subscribe(
          (result) => {
            this.Balance = result.body;
            this.router.navigate(['BankBalance']);
          },
          (err) => {
            alert('Something went wrong');
          }
        );
    }
  }

  ids: any;

  getMiniStatement() {
    let user = localStorage.getItem('user');
    let userIdx = user && JSON.parse(user).id;

    if (userIdx == null) {
      alert('Please Login First');
    } else {
      this._http
        .get<any>(
          `http://localhost:8080/api/getMiniStatement?userId=${userIdx}`,
          { observe: 'response' }
        )
        .subscribe(
          (result) => {
            if (result.body != null) {
              this.userStatement = result.body;
              this.router.navigate(['MiniStatement']);
              return this.userStatement;
            } else {
              this.userStatement = null;
              this.router.navigate(['MiniStatement']);
            }
          },
          (err) => {
            alert('Something went wrong');
          }
        );
    }
  }

  timeLeft: any;
  intervals: any;
  session: any;

  startTimer(y: number) {
    let user = localStorage.getItem('user');
    let userName = user && JSON.parse(user).name;
    this.timeLeft = y;
    this.intervals = setInterval(() => {
      if (this.timeLeft > 100) {
        this.timeLeft--;
        console.log(this.timeLeft + ' ' + userName);
        this.session = 'Session : ' + this.timeLeft + ' Seconds Left';
      } else if (this.timeLeft > 10) {
        this.timeLeft--;
        console.log(this.timeLeft + ' ' + userName);
        this.session = 'Session : ' + this.timeLeft + ' Seconds Left ';
      } else if (this.timeLeft > 1) {
        this.timeLeft--;
        console.log(this.timeLeft + ' ' + userName);
        this.session = ' Session : ' + this.timeLeft + ' Seconds Left ';
      } else if (this.timeLeft == 1) {
        this.timeLeft--;
        console.log(this.timeLeft + ' ' + userName);
        this.session = '    ' + 'Session : Expired' + '     ';
      } else {
        clearInterval(this.intervals);
        this.session = null;
        this.Balance = null;
        this.userStatement = null;
        localStorage.removeItem('user');
        localStorage.clear();
        alert('Your Session is Expired !');
        this.router.navigate(['login']);
      }
    }, 1000);
    return this.session;
  }

  MoneyForm = new FormGroup({
    id: new FormControl('', Validators.required),
    amount: new FormControl('', [Validators.required, Validators.maxLength(5)]),
  });

  sendMoney(data: any) {
    let user = localStorage.getItem('user');
    let userId = user && JSON.parse(user).id;

    const data1 = {
      userId: userId,
      amount: this.MoneyForm.value.amount,
    };
    const data2 = {
      userId: this.MoneyForm.value.id,
      amount: this.MoneyForm.value.amount,
    };
    if (userId == null) {
      alert('Please Login First');
      this.MoneyForm.reset();
      return;
    } else if (data1.userId == data2.userId) {
      alert('Sorry , You are Sending Money to your account');
      this.MoneyForm.reset();
      return;
    }
    let x = 0;

    this._http
      .get<any>(
        `http://localhost:8080/getpersonalDetailsByIdxc?id=${data2.userId}`,
        { observe: 'response' }
      )
      .subscribe(
        (result) => {
          if (result.body != null) {
            if (confirm('You are Sending Money to ' + result.body.name)) x = 1;
            if (x == 0) return;
            this._http
              .post<any>(
                'http://localhost:8080/api/postWithdrawTransferred',
                data1,
                { withCredentials: true }
              )
              .subscribe(
                (res) => {
                  console.log(res);

                  if (res != null && res.type == null) {
                    this._http
                      .post<any>(
                        'http://localhost:8080/api/postDepositRecieved',
                        data2,
                        { withCredentials: true }
                      )
                      .subscribe(
                        (res) => {
                          alert('Transaction Successfully');
                          this.MoneyForm.reset();
                        },
                        (err) => {
                          alert('Something went wrong in Depositing Money');
                        }
                      );
                  } else if (res != null && res.type != null) {
                    alert('Enter a Valid Amount');
                    this.MoneyForm.reset();
                  } else if (res == null) {
                    alert('Sorry Not Enough Funds');
                    this.MoneyForm.reset();
                  }
                },
                (err) => {
                  alert('Something went wrong in Withdrawing Money');
                }
              );
          } else {
            alert("User doesn't exist");
            this.MoneyForm.reset();
          }
        },
        (err) => {
          alert('Something went wrong in User Matching');
        }
      );
  }

  DepositForm = new FormGroup({
    amount: new FormControl('', [Validators.required, Validators.maxLength(5)]),
  });

  WithdrawForm = new FormGroup({
    amount: new FormControl('', [Validators.required, Validators.maxLength(5)]),
  });

  PinForm = new FormGroup({
    pin: new FormControl('', [
      Validators.required,
      Validators.pattern(/^\d{8}$/),
    ]),
  });

  get pin2(): FormControl {
    return this.PinForm.get('pin') as FormControl;
  }

  deposit() {
    let user = localStorage.getItem('user');
    let userId = user && JSON.parse(user).id;
    const data = {
      userId: userId,
      amount: this.DepositForm.value.amount,
    };

    if (userId == null) {
      alert('Please Login First');
      this.DepositForm.reset();
    } else {
      this._http
        .post<any>('http://localhost:8080/api/postDeposit', data, {
          withCredentials: true,
        })
        .subscribe(
          (res) => {
            if (res != null) {
              alert('Amount ' + data.amount + ' Deposit Successfully.');
            } else {
              alert('Please Enter a Valid Amount');
            }
            this.DepositForm.reset();
          },
          (err) => {
            alert('Something went wrong');
          }
        );
    }
  }

  withdraw() {
    let user = localStorage.getItem('user');
    let userId = user && JSON.parse(user).id;
    const data = {
      userId: userId,
      amount: this.WithdrawForm.value.amount,
    };
    if (userId == null) {
      alert('Please Login First');
      this.WithdrawForm.reset();
    } else {
      this._http
        .post<any>('http://localhost:8080/api/postWithdraw', data, {
          withCredentials: true,
        })
        .subscribe(
          (res) => {
            console.warn();

            if (res != null && res.type == null) {
              alert('Amount ' + data.amount + ' Withdraw Successfully.');
            } else if (res != null && res.type != null) {
              alert('Enter a Valid Amount');
            } else if (res == null) {
              alert('Sorry Not Enough Funds');
            }
            this.WithdrawForm.reset();
          },
          (err) => {
            alert('Something went wrong');
          }
        );
    }
  }

  pinChange() {
    let user = localStorage.getItem('user');
    let userId = user && JSON.parse(user).id;
    const data = {
      userId: userId,
      pin: this.PinForm.value.pin,
    };
    if (userId == null) {
      alert('Please Login First');
      this.PinForm.reset();
    } else {
      this._http
        .put<any>(
          `http://localhost:8080/api/pinChange?PIN=${data.pin}&id=${data.userId}`,
          { withCredentials: true },
          { observe: 'response' }
        )
        .subscribe(
          (res) => {
            alert('Pin Change Successful');
            this.PinForm.reset();
            localStorage.removeItem('user');
            localStorage.clear();
            clearInterval(this.intervals);
            this.router.navigate(['login']);
          },
          (err) => {
            alert('Something went wrong');
          }
        );
    }
  }

  loginForm = new FormGroup({
    card: new FormControl('', [Validators.required]),
    pin: new FormControl('', [Validators.required]),
  });

  get card(): FormControl {
    return this.loginForm.get('card') as FormControl;
  }

  get pin(): FormControl {
    return this.loginForm.get('pin') as FormControl;
  }

  loginProcess(data: any) {
    this._http
      .get<any>(
        `http://localhost:8080/api/Userlogin?card=${data.card}&pin=${data.pin}`,
        { observe: 'response' }
      )
      .subscribe(
        (result) => {
          if (result && result.body) {
            clearInterval(this.intervals);
            this.WithdrawForm.reset();
            this.DepositForm.reset();
            this.PinForm.reset();
            this.MoneyForm.reset();
            localStorage.setItem('user', JSON.stringify(result.body));
            this.loginForm.reset();
            this.router.navigate(['bank']);
          } else {
            alert('Incorrect login Id or pin');
          }
        },
        (err) => {
          alert('Something went wrong');
        }
      );
  }

  signUp() {
    this.router.navigate(['signup']);
  }
}

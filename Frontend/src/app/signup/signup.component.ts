
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormControl,FormGroup ,ReactiveFormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup
  constructor(private formBuilder: FormBuilder,private _http:HttpClient,private router:Router) { }
  ngOnInit(): void {
    this.signupForm=this.formBuilder.group({
      name:['',[Validators.required,Validators.minLength(3),Validators.pattern('^[a-zA-Z ]*$')]],
      dob:['',[Validators.required]],
      gender:['',[Validators.required]],
      email:['',[Validators.required,Validators.email]],
      address:['',[Validators.required,Validators.minLength(3),Validators.pattern('^[a-zA-Z0-9 ]*$')]],
      pincode:['',[Validators.required,Validators.pattern(/^\d{6}$/)]],
      city:['',[Validators.required,Validators.minLength(3),Validators.pattern('^[a-zA-Z ]*$')]],
      state:['',[Validators.required,Validators.minLength(3),Validators.pattern('^[a-zA-Z ]*$')]],
      card:['',[Validators.required,Validators.pattern(/^\d{16}$/)]], //    username : /^[a-zA-Z0-9_]+$/  ensure that the input only contains letters, numbers, and underscores.
      pin:['',[Validators.required,Validators.pattern(/^\d{8}$/)]],  //       
      /*

      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/

At least 8 characters
At least one uppercase letter
At least one lowercase letter
At least one number
At least one special character: @$!%*?&
      */
      pan:['',[Validators.required,Validators.pattern(/^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/)]],
      aadhar:['',[Validators.required,Validators.pattern(/^\d{12}$/)]]
    })
  }
  
  get name():FormControl{
    return this.signupForm.get('name') as FormControl;
  }
  get dob():FormControl{
    return this.signupForm.get('dob') as FormControl;
  }
  get gender():FormControl{
    return this.signupForm.get('gender') as FormControl;
  }
  get email():FormControl{
    return this.signupForm.get('email') as FormControl;
  }
  get pincode():FormControl{
    return this.signupForm.get('pincode') as FormControl;
  }
  get address():FormControl{
    return this.signupForm.get('address') as FormControl;
  }
  get city():FormControl{
    return this.signupForm.get('city') as FormControl;
  }
  get state():FormControl{
    return this.signupForm.get('state') as FormControl;
  }
  get card():FormControl{
    return this.signupForm.get('card') as FormControl;
  }
  get pin():FormControl{
    return this.signupForm.get('pin') as FormControl;
  }
  get pan():FormControl{
    return this.signupForm.get('pan') as FormControl;
  }
  get aadhar():FormControl{
    return this.signupForm.get('aadhar') as FormControl;
  }
  
  

  signUp(){
    
    this._http.get<any>(`http://localhost:8080/api/VerifySignUp?card=${this.signupForm.value.card}`,{observe:'response'}).subscribe((result)=>{
      console.log(result+" : "+result.body);
      
    if(result==null||result.body==null){
      this._http.post<any>("http://localhost:8080/api/postPersonalDetail",this.signupForm.value,{ withCredentials: true }).subscribe((res)=>{
      alert("Signup Successfully");
      this.signupForm.reset();
      this.router.navigate(['login'])
    }, 
    err=>{
      alert("Something went wrong")
    })
    }  
    else{
        alert("Sorry a User with the login Id already exist")
        this.signupForm.value.card.reset();
      }
  }, 
    err=>{
      alert("Something went wrong")
    })
  }

  back(){
    this.router.navigate(['login'])
  }
}
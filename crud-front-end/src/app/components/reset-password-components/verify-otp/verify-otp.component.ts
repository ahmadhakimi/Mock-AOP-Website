import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../auth.service';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Component({
  selector: 'app-verify-otp',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatButton,
    MatInputModule,
    RouterModule,
  ],
  templateUrl: './verify-otp.component.html',
  styleUrl: './verify-otp.component.scss',
})
export class VerifyOtpComponent {
  verifyOtpForm: FormGroup;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  durationInSeconds = 3;

  userEmail: string = '';
  showResendLink: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.verifyOtpForm = this.formBuilder.group({
      otpNumber: ['', [Validators.required, Validators.pattern(/^\d{6}$/)]],
    });

    this.userEmail = sessionStorage.getItem('userEmail') || '';
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.showResendLink = true;
    }, 5000);
  }

  onSubmit() {
    if (this.verifyOtpForm.valid) {
      this.authService.verifyOtp(this.verifyOtpForm.value.otpNumber).subscribe({
        next: (response) => {
          // navigate to the next step of otp
          this.router.navigate(['/reset-password']);
          // triggered snackbar function for successful otp
          this.verifiedSnackBar();
          // remove useremail in the session storage once otp is verified
          sessionStorage.removeItem('userEmail');
          console.log(response);
        },
        error: (err) => {
          console.error('Error: ', err.message);
          this.invalidSnackBar();
        },
      });
    } else {
      this.invalidSnackBar();
      console.error('The form is not complete');
    }
  }

  onResendOtp(event: Event) {
    event.preventDefault();
    if (this.userEmail) {
      this.authService.resendOtp(this.userEmail).subscribe({
        next: (response) => {
          this.resendSnackbar();
          console.log(response);
        },
        error: (err) => {
          this.invalidSnackBar();
          console.error('Error resending OTP', err);
        },
      });
    } else {
      console.error('No email found in session storage');
    }
  }

  // cancel operation and go to the previous page
  onCancel() {
    this.router.navigate(['/forgot-password']);
    console.log('Canccel button clicked');
  }

  verifiedSnackBar() {
    this.snackBar.open('Password Reset Successful', 'OK', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
    });
  }

  invalidSnackBar() {
    this.snackBar.open('OTP is Invalid', 'OK', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
    });
  }

  resendSnackbar() {
    this.snackBar.open('New otp is generated. please check your email', 'OK', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
    });
  }
}

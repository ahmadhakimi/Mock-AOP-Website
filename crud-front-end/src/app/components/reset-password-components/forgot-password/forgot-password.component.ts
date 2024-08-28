import { Component } from '@angular/core';
import { AuthService } from '../../../auth.service';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
  FormGroup,
} from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NgFor,
    MatInputModule,
    MatFormFieldModule,
    MatButton,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.scss',
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  durationInSeconds = 3;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.email, Validators.required]],
    });
  }

  // forgot password

  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      this.authService
        .forgotPassword(this.forgotPasswordForm.value.email)
        .subscribe({
          next: (response) => {
            this.sentOtpSnackbar();
            this.router.navigate(['/verify-otp']);
            
            // alert(response);
            console.log('OTP has been sent', response);
          },
          error: (err) => {
            alert(err.error.message || 'An error occurred. Please try again.');
          },
        });
    } else {
      alert('Please enter a valid email address.');
    }
  }

  sentOtpSnackbar() {
    this.snackBar.open(
      'The OTP has sent to ' + this.forgotPasswordForm.get('email')?.value,
      'OK',
      {
        horizontalPosition: this.horizontalPosition,
        verticalPosition: this.verticalPosition,
        duration: this.durationInSeconds * 1000,
      }
    );
  }
}

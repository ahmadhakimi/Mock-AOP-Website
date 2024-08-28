import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../auth.service';
import { MatIconModule } from '@angular/material/icon';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { error } from 'console';
import { Router } from '@angular/router';
import {
  passwordCompositionValidator,
  passwordLengthValidator,
} from '../../../shared/validators';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [
    MatCardModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
  ],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss',
})
export class ResetPasswordComponent {
  resetPasswordForm: FormGroup;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  durationInSeconds = 3;

  //  store the user's email in the userEmail string for resend OTP
  userEmail: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.resetPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      newPassword: [
        '',
        [
          Validators.required,
          passwordLengthValidator(), // Custom validator for length
          passwordCompositionValidator(), // Custom validator for composition
        ],
      ],
      confirmPassword: [
        '',
        [
          Validators.required,
          passwordLengthValidator(),
          passwordCompositionValidator(),
        ],
      ],
    });
  }

  onSubmit() {
    if (this.resetPasswordForm.valid) {
      this.authService
        .resetPassword(
          this.resetPasswordForm.value.email,
          this.resetPasswordForm.value.newPassword,
          this.resetPasswordForm.value.confirmPassword
        )
        .subscribe({
          next: (response) => {
            this.router.navigate(['/login']);
            this.successSnackbar();
            console.log(response);
          },
        });
    } else {
      this.invalidSnackbar();
      console.error('invalid format input');
    }
  }

  // toggle hide password
  hide = true;

  // toast snackbar
  successSnackbar() {
    this.snackBar.open(
      'Successfully reset password. Please login with your new password',
      'OK',
      {
        horizontalPosition: this.horizontalPosition,
        verticalPosition: this.verticalPosition,
        duration: this.durationInSeconds * 1000,
      }
    );
  }

  invalidSnackbar() {
    this.snackBar.open('Please check if there is invalid input', 'OK', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
    });
  }

 
}

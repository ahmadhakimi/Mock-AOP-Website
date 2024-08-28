import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import {
  MatFormFieldControl,
  MatFormFieldModule,
} from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { AuthService } from '../../auth.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCardModule,
    ReactiveFormsModule,
    NgIf,
    MatFormFieldModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatSelectModule,
    MatInputModule,
    FormsModule,
    RouterModule,
    //   HttpClientModule,
  ],
  providers: [AuthService],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  durationInSeconds = 5;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    // Initialize loginForm in the constructor to satisfy TypeScript strict property initialization
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  get loginFormData() {
    return this.loginForm.controls;
  }

  ngOnInit(): void {
    // Initialization logic if any
  }

  hide = true;

  // Example submit function
  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.login(email, password).subscribe({
        next: (response) => {
          this.successSnackBar();
          // Handle successful login
          this.router.navigate(['/home']);
          console.log('login successful', response);
        },
        error: (error) => {
          this.failureSnackBar();
          // Handle login error
          console.error('Login error:', error);
        },
      });
    }
  }

  // display successful login snackbar
  successSnackBar() {
    this.snackBar.open('Login Successful', 'OK', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
    });
  }

  // display invalid login snackbar

  failureSnackBar() {
    this.snackBar.open('Login not authenticated', 'OK', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
    });
  }
}

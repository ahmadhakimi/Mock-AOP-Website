import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login';
import { StaffComponent } from './components/staff/staff.component';
import { CompanyComponent } from './components/company/company.component';
import { LayoutComponent } from './components/layout/layout.component';
import { authGuard } from './auth.guard';
import { PassComponent } from './components/pass/pass.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { ForgotPasswordComponent } from './components/reset-password-components/forgot-password/forgot-password.component';
import { VerifyOtpComponent } from './components/reset-password-components/verify-otp/verify-otp.component';
import { ResetPasswordComponent } from './components/reset-password-components/reset-password/reset-password.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'home',
        component: HomeComponent,
      },

      {
        path: 'staff',
        component: StaffComponent,
      },

      {
        path: 'company',
        component: CompanyComponent,
      },

      {
        path: 'pass',
        component: PassComponent,
      },

      {
        path: 'user-profile',
        component: UserProfileComponent,
      },

      {
        path: 'logout',
        redirectTo: '/login',
      },
    ],
  },

  {
    path: 'login',
    component: LoginComponent,
  },

  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
  },

  {
    path: 'verify-otp',
    component: VerifyOtpComponent,
  },

  {
    path: 'reset-password',
    component: ResetPasswordComponent,
  },

  {
    path: '**',
    redirectTo: '/login', // Redirect any unknown paths to login
  },
];

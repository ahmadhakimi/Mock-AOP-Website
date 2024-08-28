import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Staff } from './interfaces/staff';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loginUrl = 'http://localhost:8080/api/auth/authenticate';
  private createUrl = 'http://localhost:8080/api/auth/register';
  private logoutUrl = 'http://localhost:8080/api/auth/logout';
  private forgotUrl = 'http://localhost:8080/api/auth/generate';
  private verifyUrl = 'http://localhost:8080/api/auth/verify';
  private resetUrl = 'http://localhost:8080/api/auth/reset';
  private resendUrl = 'http://localhost:8080/api/auth/resend';

  constructor(private http: HttpClient) {}

  // register service

  register(staff: Staff): Observable<any> {
    return this.http.post<any>(`${this.createUrl}`, staff);
  }

  // login service

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(this.loginUrl, { email, password }).pipe(
      map((response) => {
        if (response && response.token) {
          sessionStorage.setItem('currentUser', JSON.stringify(response));
        }
        return response;
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post(this.logoutUrl, {}, { responseType: 'text' }).pipe(
      map((response) => {
        sessionStorage.removeItem('currentUser');
        return response;
      })
    );
  }

  // get isLoggedIn(): boolean {
  //   return !!sessionStorage.getItem('currentUser');
  // }

  get isLoggedIn(): boolean {
    // Ensure we're in a browser environment before accessing sessionStorage
    if (typeof window !== 'undefined' && window.sessionStorage) {
      return !!sessionStorage.getItem('currentUser');
    }
    return false;
  }

  // forgot passwrod
  forgotPassword(email: string): Observable<any> {
    const params = new HttpParams().set('email', email);
    sessionStorage.setItem('userEmail', email);
    return this.http.post(`${this.forgotUrl}`, {}, { params });
  }

  resendOtp(email: string): Observable<any> {
    // Ensure to use the correct URL and handle errors
    return this.http.post(`${this.resendUrl}?email=${email}`, {}).pipe(
      catchError(this.handleError) // Error handling method
    );
  }

  // verify otp service
  verifyOtp(otp: string): Observable<any> {
    const params = new HttpParams().set('otp', otp);
    // remove userEmail from session storage
    sessionStorage.removeItem('userEmail');

    return this.http.post(`${this.verifyUrl}`, {}, { params });
  }

  // reset password
  resetPassword(
    email: string,
    newPassword: string,
    confirmPassword: string
  ): Observable<any> {
    return this.http.post(`${this.resetUrl}`, {
      email,
      newPassword,
      confirmPassword,
    });
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occured', error);
    return throwError(
      () => new Error('Something went wrong, please try again.')
    );
  }
}

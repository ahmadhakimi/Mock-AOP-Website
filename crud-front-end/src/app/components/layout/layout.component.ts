import { Component, NgModule, OnInit, ViewChild } from '@angular/core';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule, MatSidenav } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { CommonModule } from '@angular/common';
import { MatMenuModule } from '@angular/material/menu';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import {
  MatSlideToggleModule,
  MatSlideToggleChange,
} from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { StaffService } from '../../services/staff.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatButtonModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatMenuModule,
    CommonModule,
    RouterModule,
    RouterLink,
    FormsModule,
    // HttpClientModule,
  ],
  providers: [AuthService],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss',
})
export class LayoutComponent implements OnInit {
  @ViewChild('sidenav') sidenav!: MatSidenav;

  sideNavOpen = false;
  isChecked: boolean = false;

  constructor(private router: Router, private authService: AuthService) {
    if (this.isSessionStorageAvailable()) {
      this.isChecked = sessionStorage.getItem('theme') === 'dark';
      this.applyTheme(this.isChecked);
    }
  }

  // close drawer when navigate between pages
  ngOnInit(): void {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        if (this.sidenav) {
          this.sidenav.close();
        }
      });
  }

  // toggle theme
  toggleTheme(event: MatSlideToggleChange) {
    this.isChecked = event.checked;
    sessionStorage.setItem('theme', this.isChecked ? 'dark' : 'light');
    this.applyTheme(this.isChecked);
  }

  applyTheme(isDark: boolean) {
    // Ensure that only one theme class is applied at a time
    document.body.classList.remove('theme-light', 'theme-dark');
    document.body.classList.add(isDark ? 'theme-dark' : 'theme-light');
  }

  // toggle open-close drawer
  toggleSideNav() {
    this.sideNavOpen = !this.sideNavOpen;
  }

  // local storage available method

  isSessionStorageAvailable(): boolean {
    try {
      const testKey = 'sessionStorageTest';
      sessionStorage.setItem(testKey, 'test');
      sessionStorage.removeItem(testKey);
      return true;
    } catch (e) {
      return false;
    }
  }

  userProfile() {
    
  }

  logout() {
    this.authService.logout().subscribe(
      (response) => {
        console.log(response);
        this.router.navigate(['/login']); // Navigate to the login page or another appropriate page
      },
      (error) => {
        console.error('Logout failed', error);
      }
    );
  }



  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }
}
